package corelin.plugins.library.plugin.command;

import corelin.plugins.library.plugin.command.annotation.HCD;
import corelin.plugins.library.plugin.command.type.CommandType;
import corelin.plugins.library.plugin.interfaces.CommandHelp;
import corelin.plugins.library.plugin.interfaces.PermissionHandle;
import corelin.plugins.library.plugin.interfaces.PlayerPermissionHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/11/22 21:57
 * @版本 1.0
 */
public class CoreAddCommand<T>  extends Command implements TabCompleter {


    private Map<HCD, Method> cmdMap = new HashMap<>();
    private CommandHelp commandHelp;
    private String title;
    private T cmd;
    private PermissionHandle handle;

    @Setter
    @Getter
    private PlayerPermissionHandler playerPermissionHandler;

    private Method useTab = null;


    public CoreAddCommand(String name, T cmd, PermissionHandle handle , CommandHelp help, String title) {
        super(name);
        this.title = title;
        this.handle = handle;
        Method[] methods = cmd.getClass().getDeclaredMethods();
        try {
            for (Method m : methods){
                m.setAccessible(true);
                if (m.isAnnotationPresent(HCD.class)){
                    HCD hgc = m.getAnnotation(HCD.class);
                    this.cmdMap.put(hgc , m);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.commandHelp = help;
        this.cmd =  cmd;
    }


    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        for (HCD h : this.cmdMap.keySet()){
            if (h.length() == args.length  || h.length() == -1){
                if (isArgs(h.cmd() , args)){
                    if (this.isType(h.type() , sender)){
                        String per = this.hasPermission(h.permission() , sender);
                        if (per.isEmpty()){
                            try {
                                this.cmdMap.get(h).invoke(this.cmd , sender , args);
                                return true;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }else{
                            sender.sendMessage( handle.run(per).replace("<permission>" , per));
                            return true;
                        }

                    }
                    //使用指令的类型不正确
                    sender.sendMessage(this.title + h.trueType());
                    return true;
                }
            }
        }
        //帮助
        this.commandHelp.run(sender , s , this , args);
        return true;
    }

    String hasPermission(String[] permission , CommandSender sender){
        if (permission.length == 0){
            return "";
        }
        for (String p : permission){
            if (!sender.hasPermission(p)){
                return p;
            }
        }
        return "";
    }



    boolean isArgs(String[] setCmds, String[] nowCmds){
        if (setCmds.length == 0){
            return true;
        }
        for (int i = 0 ; i < setCmds.length ; i++){
            if (!setCmds[i].equalsIgnoreCase(nowCmds[i])){
                return false;
            }
        }
        return true;
    }

    boolean isType(CommandType[] hgcTypes , CommandSender sender){
        for (CommandType t : hgcTypes){
            if (t == this.getType(sender)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return super.tabComplete(sender, alias, args);
    }

    CommandType getType(CommandSender sender){
        if (sender instanceof Player){
            return CommandType.Player;
        }else{
            return CommandType.Sender;
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (useTab == null){
            return super.tabComplete(sender , s  , args , null);
        }
        try {
            return (List<String>) this.useTab.invoke(this.cmd , sender , args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
