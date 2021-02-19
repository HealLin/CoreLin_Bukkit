package corelin.plugins.library.plugin.interfaces;


import org.bukkit.command.CommandSender;

/**
 * @author 择忆霖心
 * @时间 2021/2/13 7:23
 */
public interface PlayerPermissionHandler {


    /**
     * 检测是否有权限
     * @param sender
     * @param permission
     * @return
     */
    boolean hasPermission(CommandSender sender , String permission);
}
