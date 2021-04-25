package corelin.plugins.library.utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 择忆霖心
 * 该类为静态类，用于检测bukkit的游戏版本处理类
 * @时间 2021/2/19 10:55
 */
public class GameVersion {

    public static String gameVersion;

    public GameVersion(){
        gameVersion = getVersion().substring(getVersion().indexOf("MC:") + 3)
                .replace(" " , "")
                .replace(")" , "");
    }

    public String getVersion() {
        Server server = Bukkit.getServer();
        try {
            Method method = server.getClass().getDeclaredMethod("getVersion");
            method.setAccessible(true);
            return (String) method.invoke(server);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
