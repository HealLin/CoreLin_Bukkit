package corelin.plugins.library.plugin.utils.server;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.utils.GameVersion;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 14:51
 */
public class BukkitServer {

    public static BukkitServer instance;

    private CoreLin coreLin;

    private String version;

    public BukkitServer(CoreLin coreLin){
        instance = this;
        this.coreLin = coreLin;
    }

    public CraftBukkitServer getServer(){
        switch (GameVersion.gameVersion){
            case "1.12.2":{

            }
            default:{
                return null;
            }
        }
    }
}
