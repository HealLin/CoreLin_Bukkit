package corelin.plugins.library.module;

import corelin.plugins.library.module.annotation.CoreModule;
import corelin.plugins.library.module.java.ModuleClassLoader;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/11/9 23:55
 * @版本 1.0
 */
public class CoreModuleInfo {

    /**
     * 模块名称
     */
    @Getter
    private final String name;

    /**
     * 模块描述
     */
    @Getter
    private final String description;

    /**
     * 模块版本
     */
    @Getter
    private final String version;

    /**
     * 前置模块
     */
    @Getter
    private final String[] rely;

    /**
     * 模块实例
     */
    @Getter
    private final CoreLibModule module;

    /**
     * 模块jar
     */
    @Getter
    private final File file;

    @Getter
    private final String useVersion;


    @Getter
    @Setter
    private ModuleClassLoader classLoader;

    public CoreModuleInfo(CoreModule module, CoreLibModule coreModule, File ps , ModuleClassLoader classLoader) {
        this.name = module.name();
        this.description = module.description();
        this.version = module.version();
        this.rely = module.rely();
        this.module = coreModule;
        this.file = ps;
        this.useVersion = module.useVersion();
        this.classLoader = classLoader;
    }
}
