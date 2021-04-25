package corelin.plugins.library;

import corelin.plugins.library.api.CoreLinApi;
import corelin.plugins.library.module.CoreLibModuleManager;
import corelin.plugins.library.module.ModuleMain;
import corelin.plugins.library.plugin.loader.CoreLinPluginLoader;
import corelin.plugins.library.utils.server.BukkitServer;
import corelin.plugins.library.utils.GameVersion;
import corelin.plugins.library.utils.ServerChecking;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONAware;

/**
 * @author 择忆霖心
 * @时间 2021/2/10 16:12
 */
public class CoreLin extends JavaPlugin {

    private static CoreLin instance;

    @Getter
    private ModuleMain moduleMain;

    @Getter
    private CoreLinApi api;

    @Getter
    private CoreLibModuleManager moduleManager;

    public CoreLin(){
        //只允许new一下
        if (instance != null){
            return;
        }
        this.api = new CoreLinApi(this);
        instance = this;
        this.info("开始加载......");
        BukkitServer server = new BukkitServer(this);
        new GameVersion();
        this.info("检测到游戏版本:" + GameVersion.gameVersion);
        ServerChecking.initialization(this);
        this.moduleMain = new ModuleMain(this);
        moduleManager = new CoreLibModuleManager(this);
        this.moduleManager.load();
        this.info("替换插件加载器!");
        Bukkit.getPluginManager().registerInterface(CoreLinPluginLoader.class);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public void info(Object o){
        Bukkit.getConsoleSender().sendMessage("§c[§a择忆霖心前置§c]§b信息输出:§6" + o);

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static CoreLin getInstance(){
        return CoreLin.instance;
    }


}
