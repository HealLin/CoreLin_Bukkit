package corelin.plugins.library.utils.R12;

import corelin.plugins.library.utils.server.CraftBukkitServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    @Override
    public String getVersion() {
        try {
            Method method = this.server.getClass().getDeclaredMethod("getVersion");
            method.setAccessible(true);
            return (String) method.invoke(this.server);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
