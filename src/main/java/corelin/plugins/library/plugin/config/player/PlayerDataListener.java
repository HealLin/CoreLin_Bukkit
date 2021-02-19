package corelin.plugins.library.plugin.config.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

/**
 * @author 择忆霖心
 * @简述
 * 当想自定义玩家配置文件事件操作
 * @时间 2020/12/25 22:35
 * @版本 1.0
 */
public abstract class PlayerDataListener implements Listener {

    @Setter
    @Getter
    private PlayerDataManager manager;


}
