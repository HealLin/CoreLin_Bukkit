package corelin.plugins.library.api;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.api.module.ModuleAPI;
import corelin.plugins.library.plugin.manager.PluginManager;
import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 1:30
 */
public class CoreLinApi {

    @Getter
    private CoreLin coreLin;

    private static CoreLinApi instance;

    @Getter
    private PluginManager pluginManager;

    public CoreLinApi(CoreLin coreLin) {
        instance = this;
        this.coreLin = coreLin;
        this.pluginManager = new PluginManager(this);
    }

    public void setModuleAPI(ModuleAPI moduleAPI){
        this.coreLin.getModuleMain().setModuleAPI(moduleAPI);
    }


    public static CoreLinApi getInstance(){
        return instance;
    }
}
