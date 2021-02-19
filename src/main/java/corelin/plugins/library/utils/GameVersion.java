package corelin.plugins.library.utils;

import corelin.plugins.library.utils.server.BukkitServer;
import corelin.plugins.library.utils.server.CraftBukkitServer;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 10:55
 */
public class GameVersion {

    public static String gameVersion;

    public GameVersion(){
        CraftBukkitServer bukkitServer = BukkitServer.instance.getServer();
        gameVersion = bukkitServer.getVersion().substring(bukkitServer.getVersion().indexOf("MC:") + 3)
                .replace(" " , "")
                .replace(")" , "");
    }
}
