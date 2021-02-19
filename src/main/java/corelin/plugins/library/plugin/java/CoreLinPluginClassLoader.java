package corelin.plugins.library.plugin.java;

import corelin.plugins.library.api.plugin.annotation.SpigotPlugin;
import corelin.plugins.library.plugin.CoreLinPlugin;
import corelin.plugins.library.plugin.loader.CoreLinPluginLoader;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

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
