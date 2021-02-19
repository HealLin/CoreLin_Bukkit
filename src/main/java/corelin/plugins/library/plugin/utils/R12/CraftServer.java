package corelin.plugins.library.plugin.utils.R12;

import corelin.plugins.library.plugin.utils.server.CraftBukkitServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 14:47
 */
public class CraftServer implements CraftBukkitServer {

    private Server server;

    public CraftServer(){
        this.server = Bukkit.getServer();
    }

    @Override
    public SimpleCommandMap getCommandMap(){
        try {
            Field field = this.server.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (SimpleCommandMap) field.get(this.server);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
