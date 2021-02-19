package corelin.plugins.library.module;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.module.annotation.CoreModule;
import corelin.plugins.library.module.java.ModuleClassLoader;
import corelin.plugins.library.utils.GameVersion;
import lombok.Getter;
import org.bukkit.plugin.InvalidPluginException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/11/9 23:40
 * @版本 1.0
 */
public class CoreLibModuleLoad {

    private CoreLin coreLib;
    private CoreLibModuleManager manager;

    @Getter
    private Map<String , CoreModuleInfo> infoMap = new HashMap<>();
    private List<String> loadList = new ArrayList<>();

    public CoreLibModuleLoad(CoreLin coreLib, CoreLibModuleManager coreModuleManager){
        this.coreLib = coreLib;
        this.manager = coreModuleManager;
    }



    /**
     * 开始加载模块
     * @param ps
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public CoreModuleInfo loadModule(File ps) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvalidPluginException {
        CoreModuleInfo moduleInfo = null;
        JarFile jar = new JarFile(ps);
        ModuleClassLoader classLoader = new ModuleClassLoader(ps , this.getClass().getClassLoader());
        Enumeration<JarEntry> entry = jar.entries();
        while (entry.hasMoreElements()){
            JarEntry jarEntry = entry.nextElement();
            if (jarEntry.isDirectory() || (!jarEntry.getName().endsWith(".class"))){
                continue;
            }
            String name = jarEntry.getName().replace("/" , ".").replace(".class" , "");
            Class<?> clazz =  classLoader.loadClass(name);
            if (clazz.isAnnotationPresent(CoreModule.class)){
                CoreModule module = clazz.getAnnotation(CoreModule.class);
                if (!GameVersion.gameVersion.equals(module.useVersion())){
                    this.coreLib.info("模块" + module.name() + "需要的游戏版本是" + module.gameVersion() + "你的游戏版本" + GameVersion.gameVersion);
                    return null;
                }
                Class<? extends CoreLibModule> pluginClass;
                try {
                    pluginClass = clazz.asSubclass(CoreLibModule.class);
                    //已经new了但是插件没有模块没有启动

                } catch (ClassCastException ex) {
                    throw new InvalidPluginException("模块主类，没有继承CoreLibModule,无法启用", ex);
                }
                CoreLibModule coreModule = pluginClass.newInstance();
                moduleInfo = new CoreModuleInfo(
                        module, coreModule , ps , classLoader
                );
                return moduleInfo;
            }
        }
        return moduleInfo;
    }

    public Collection<CoreModuleInfo> values(){
        return this.infoMap.values();
    }


    /**
     * 加载前置模块
     * @param info
     * @return
     */
    public boolean loadPremise(CoreModuleInfo info){
        for (String n : info.getRely()){
            CoreModuleInfo coreModules = this.infoMap.get(n);
            if (coreModules != null){
                if (this.loadList.contains(coreModules.getName())){
                    continue;
                }
                register(coreModules);
                this.loadList.add(n);
                continue;
            }
            coreLib.info("模块:" + info.getName() + "(" + info.getFile().getName() + ")" + "缺少前置");
            for (String p : info.getRely()){
                coreLib.info("-" + p);
            }
            return false;
        }
        register(info);
        return true;
    }

    void register(CoreModuleInfo info){
        this.manager.addModule(info);
    }



}
