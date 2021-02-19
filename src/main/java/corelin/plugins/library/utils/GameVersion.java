package corelin.plugins.library.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 10:55
 */
public class GameVersion {

    public static String gameVersion;

    public GameVersion(){
        CraftServer server = (CraftServer) Bukkit.getServer();
        gameVersion = server.getVersion().substring(server.getVersion().indexOf("MC:") + 3)
                .replace(" " , "")
                .replace(")" , "");
    }
}
