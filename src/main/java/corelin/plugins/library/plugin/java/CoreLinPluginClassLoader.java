package corelin.plugins.library.plugin.java;

import corelin.plugins.library.plugin.CoreLinPlugin;
import corelin.plugins.library.plugin.loader.CoreLinPluginLoader;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 1:43
 */
public abstract class CoreLinPluginClassLoader extends URLClassLoader {


    public CoreLinPluginClassLoader(CoreLinPluginLoader pluginLoader, ClassLoader classLoader,
                                    PluginDescriptionFile description, File dataFolder, File file) throws IOException {
        super(new URL[] {file.toURI().toURL()}, classLoader);
    }

    public abstract Plugin getPlugin();

    public abstract void initialize(CoreLinPlugin plugin);
}
