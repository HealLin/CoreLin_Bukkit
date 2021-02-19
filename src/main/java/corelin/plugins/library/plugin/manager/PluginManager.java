package corelin.plugins.library.plugin.manager;

import corelin.plugins.library.api.CoreLinApi;
import corelin.plugins.library.plugin.CoreLinPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/12/20 17:34
 * @版本 1.0
 */
public class PluginManager {

    private CoreLinApi api;
    private Map<String , CoreLinPlugin> pluginMap = new HashMap<>();


    public PluginManager(CoreLinApi coreLinApi) {
        this.api = coreLinApi;
    }

    public void addPlugin(CoreLinPlugin plugin){
        this.pluginMap.put(plugin.getName() , plugin);
    }

    public CoreLinPlugin getPluginName(String name){
        return this.pluginMap.get(name);
    }



    /**
     * 加载插件,只会返回继承于CoreLinPlugin的插件
     * @param file 文件路径
     * @return 返回的如果是Bukkit的普通插件那么为null
     */
    public CoreLinPlugin loadPlugin(File file){
        Plugin plugin;
        try {
            plugin = Bukkit.getPluginManager().loadPlugin(file);
        } catch (InvalidPluginException | InvalidDescriptionException e) {
            return null;
        }
        if (plugin instanceof CoreLinPlugin){
            return (CoreLinPlugin) plugin;
        }
        return null;
    }

    /**
     * 和正常的Bukkit卸载插件一样
     * @param plugin 直接传入Bukkit的插件主类即可
     */
    public void disablePlugin(Plugin plugin){
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}
