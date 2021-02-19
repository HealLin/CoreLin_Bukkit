package corelin.plugins.library.utils;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.plugin.loader.CoreLinPluginLoader;
import org.bukkit.Bukkit;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 10:29
 */
public class ServerChecking {

    /**
     * 是否是CatServer服务端
     */
    public static boolean isCatServer;

    public static void initialization(CoreLin coreLin){
        isCatServer = isCatServer(coreLin);
    }

    public static boolean isCatServer(CoreLin coreLin){
        try {
            Class.forName("catserver.server.CatServer");
            coreLin.info("检测到CatServer,一切兼容CatServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
