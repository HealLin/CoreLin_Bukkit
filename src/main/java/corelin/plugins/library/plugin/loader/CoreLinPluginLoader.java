package corelin.plugins.library.plugin.loader;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.plugin.java.CoreLinPluginClassLoader;
import corelin.plugins.library.utils.PluginDescriptionFileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 0:56
 */
public class CoreLinPluginLoader implements PluginLoader {

    private Server server;

    private final Pattern[] fileFilters = new Pattern[]{Pattern.compile("\\.jar$")};
    private final List<CoreLinPluginClassLoader> loaders = new CopyOnWriteArrayList<CoreLinPluginClassLoader>();

    private CoreLin coreLin;


    public CoreLinPluginLoader(Server instance){
        Validate.notNull(instance, "服务器实例不能为空");
        this.server = instance;
        this.coreLin = CoreLin.getInstance();
    }


    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
        Validate.notNull(file, "文件不能为空");
        if (!file.exists()) {
            throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " 不存在"));
        }
        PluginDescriptionFile description;
        try {
            description = getPluginDescription(file);
        } catch (InvalidDescriptionException ex) {
            throw new InvalidPluginException(ex);
        }

        final File parentFile = file.getParentFile();
        final File dataFolder = new File(parentFile, description.getName());
        @SuppressWarnings("deprecation")
        final File oldDataFolder = new File(parentFile, description.getRawName());

        // Found old data folder
        if (dataFolder.equals(oldDataFolder)) {
            // They are equal -- nothing needs to be done!
        } else if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
            server.getLogger().warning(String.format(
                    "While loading %s (%s) found old-data folder: `%s' next to the new one `%s'",
                    description.getFullName(),
                    file,
                    oldDataFolder,
                    dataFolder
            ));
        } else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
            if (!oldDataFolder.renameTo(dataFolder)) {
                throw new InvalidPluginException("Unable to rename old data folder: `" + oldDataFolder + "' to: `" + dataFolder + "'");
            }
            server.getLogger().log(Level.INFO, String.format(
                    "While loading %s (%s) renamed data folder: `%s' to `%s'",
                    description.getFullName(),
                    file,
                    oldDataFolder,
                    dataFolder
            ));
        }

        if (dataFolder.exists() && !dataFolder.isDirectory()) {
            throw new InvalidPluginException(String.format(
                    "Projected datafolder: `%s' for %s (%s) exists and is not a directory",
                    dataFolder,
                    description.getFullName(),
                    file
            ));
        }

        for (final String pluginName : description.getDepend()) {
            Plugin current = server.getPluginManager().getPlugin(pluginName);

            if (current == null) {
                throw new UnknownDependencyException(pluginName);
            }
        }

        CoreLinPluginClassLoader loader;
        try {
            loader = this.coreLin.getModuleMain().getModuleAPI().getPluginClassLoad(this, getClass().getClassLoader(),
                    description, dataFolder, file);
        } catch (Exception ex) {
            throw ex;
        }

        loaders.add(loader);

        return loader.getPlugin();
    }

    public PluginDescriptionFile classLoadPlugin(File file){
        try {
            JarFile jar = new JarFile(file);
            Enumeration<JarEntry> entry = jar.entries();
            while (entry.hasMoreElements()) {
                JarEntry jarEntry = entry.nextElement();
                if (jarEntry.isDirectory() || (!jarEntry.getName().endsWith(".class"))) {
                    continue;
                }
                InputStream inputStream = jar.getInputStream(jarEntry);
                ClassReader cr = new ClassReader(inputStream);
                ClassNode node = new ClassNode();
                cr.accept(node , Opcodes.ASM4);
                //如果有注解
                if (node.visibleAnnotations != null){
                    for (AnnotationNode no : node.visibleAnnotations){
                        if (no.desc.equals("Lcom/corelin/lib/api/plugin/annotation/SpigotPlugin;")) {
                            PluginDescriptionFileUtils descriptionFile = new PluginDescriptionFileUtils(jarEntry.getName()
                                    .replace("/" , ".").replace(".class" , ""));
                            for (int i = 0 ; i < (no.values.size() - 1) ; i++){
                                String name = String.valueOf(no.values.get(i));
                                String data = String.valueOf( no.values.get(i + 1));
                                i++;
                                switch (name){
                                    case "pluginName":{
                                        descriptionFile.setName(data);
                                        break;
                                    }
                                    case "version":{
                                        descriptionFile.setVersion(data);
                                        break;
                                    }
                                    case "prefix":{
                                        descriptionFile.setPrefix(data);
                                        break;
                                    }
                                    case "description":{
                                        descriptionFile.setDescription(data);
                                        break;
                                    }
                                    case "depend":{
                                        descriptionFile.setDepend(
                                                PluginDescriptionFileUtils.dataToArray(data)
                                        );
                                        break;
                                    }
                                    case "author":{
                                        descriptionFile.setAuthors(
                                                PluginDescriptionFileUtils.dataToArray(data)
                                        );
                                        break;
                                    }
                                    case "softDepend":{
                                        descriptionFile.setSoftDepend(
                                                PluginDescriptionFileUtils.dataToArray(data)
                                        );
                                        break;
                                    }
                                    case "loadBefore":{
                                        descriptionFile.setLoadBefore(
                                                PluginDescriptionFileUtils.dataToArray(data)
                                        );
                                        break;
                                    }
                                    default:{
                                        break;
                                    }
                                }
                            }
                            return descriptionFile.getDescriptionFile();
                        }

                    }
                }

            }
        } catch (IOException e) {
            //  e.printStackTrace();
        }
        return null;
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        Validate.notNull(file, "文件不能为空");

        JarFile jar = null;
        InputStream stream = null;

        try {
            jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("plugin.yml");

            if (entry == null) {
                //找不到plugin.yml开始注解搜寻模式
                PluginDescriptionFile descriptionFile = this.classLoadPlugin(file);
                if (descriptionFile == null){
                    throw new InvalidDescriptionException(new FileNotFoundException("Jar内没有plugin.yml文件"));
                }
                return descriptionFile;
            }

            stream = jar.getInputStream(entry);

            return new PluginDescriptionFile(stream);

        } catch (IOException | YAMLException ex) {
            throw new InvalidDescriptionException(ex);
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (IOException e) {
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return this.fileFilters.clone();
    }

    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
        return null;
    }

    @Override
    public void enablePlugin(Plugin plugin) {

    }

    @Override
    public void disablePlugin(Plugin plugin) {

    }
}
