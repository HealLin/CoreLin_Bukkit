package corelin.plugins.library.plugin.config.player;

import corelin.plugins.library.plugin.CoreLinPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/12/25 22:45
 * @版本 1.0
 */
public class PlayerDataManager implements Listener {

    private final ConcurrentHashMap<UUID, PlayerDataConfig> playerMap = new ConcurrentHashMap<>();

    private CoreLinPlugin plugin;

    private File playerDataFile;

    public PlayerDataManager(CoreLinPlugin plugin , PlayerDataListener listener){
        this.plugin = plugin;
        if (listener == null){
            Bukkit.getPluginManager().registerEvents(this , plugin);
        }else{
            listener.setManager(this);
            Bukkit.getPluginManager().registerEvents(listener , plugin);
        }
        this.playerDataFile = new File(this.plugin.getDataFolder() , "playerData");
        if (!this.playerDataFile.exists()){
            //创建插件文件夹
            this.playerDataFile.mkdirs();
        }
    }

    public void addPlayer(Player player , PlayerDataConfig playerDataConfig){
        this.playerMap.put(player.getUniqueId() , playerDataConfig);
    }

    public void addPlayer(UUID uuid , PlayerDataConfig config){
        this.playerMap.put(uuid , config);
    }

    public PlayerDataConfig delPlayer(Player player){
        return this.playerMap.remove(player.getUniqueId());
    }

    public PlayerDataConfig delPlayer(UUID uuid){
        return this.playerMap.remove(uuid);
    }

    public PlayerDataConfig createPlayerData(Player player){
        return new PlayerDataConfig(new File(this.playerDataFile , player.getUniqueId() + ".yml")  , player );
    }

    public PlayerDataConfig getPlayerData(String player){
        return this.playerMap.get(Bukkit.getPlayer(player).getUniqueId());
    }

    public PlayerDataConfig getPlayerData(Player player){
        if (player != null){
            return this.playerMap.get(player.getUniqueId());
        }
        return null;
    }

    public PlayerDataConfig getPlayerData(UUID uuid){
        if (uuid != null){
            return this.playerMap.get(uuid);
        }
        return null;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        this.addPlayer(event.getPlayer() , this.createPlayerData(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        this.delPlayer(event.getPlayer());
    }

}
