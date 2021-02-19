package corelin.plugins.library.plugin.config.player;

import corelin.plugins.library.api.time.TimeApi;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/12/25 22:13
 * @版本 1.0
 */
public final class PlayerDataConfig implements Listener {

    @Getter
    private File file;

    @Getter
    private Player player;

    @Getter
    private YamlConfiguration configuration;

    protected PlayerDataConfig(File file , Player player){
        this.player = player;
        this.setFile(file);
    }

    public void setFile(File file){
        this.file = file;
        if (!this.file.exists()){
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadConfig();
            this.configuration.set("名称:" , this.player.getName());
            this.configuration.set("UUID:" , this.player.getUniqueId().toString());
            this.configuration.set("创建日期:"  , TimeApi.getTime(""));
            this.saveConfig();
            return;
        }
        loadConfig();
    }

    void loadConfig(){
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void saveConfig(){
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








}
