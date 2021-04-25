package corelin.plugins.library.plugin.java;

import corelin.plugins.library.plugin.CoreLinPlugin;
import corelin.plugins.library.plugin.loader.CoreLinPluginLoader;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 择忆霖心
 * @时间 2021/4/25 9:49
 */
public class CoreLinPluginMainClassLoader extends CoreLinPluginClassLoader{


    public CoreLinPluginMainClassLoader(CoreLinPluginLoader pluginLoader, ClassLoader classLoader, PluginDescriptionFile description, File dataFolder, File file) throws IOException {
        super(pluginLoader, classLoader, description, dataFolder, file);
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    @Override
    public void initialize(CoreLinPlugin plugin) {

    }
}
