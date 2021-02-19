package corelin.plugins.library.api.module;

import corelin.plugins.library.plugin.java.CoreLinPluginClassLoader;
import corelin.plugins.library.plugin.loader.CoreLinPluginLoader;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 11:30
 */
public interface ModuleAPI {

    <T extends CoreLinPluginClassLoader> T getPluginClassLoad(CoreLinPluginLoader pluginLoader, ClassLoader classLoader,
                                                              PluginDescriptionFile description, File dataFolder, File file);

}
