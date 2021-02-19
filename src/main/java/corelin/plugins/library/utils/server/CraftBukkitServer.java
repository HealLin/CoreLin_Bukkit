package corelin.plugins.library.utils.server;

import org.bukkit.command.SimpleCommandMap;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 14:52
 */
public interface CraftBukkitServer {

    SimpleCommandMap getCommandMap();

    String getVersion();
}
