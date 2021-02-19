package corelin.plugins.library.module;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.api.module.ModuleAPI;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 13:11
 */
public class ModuleMain {

    private CoreLin coreLin;

    @Setter
    @Getter
    private ModuleAPI moduleAPI;

    public ModuleMain(CoreLin coreLin) {
        this.coreLin = coreLin;
    }


}
