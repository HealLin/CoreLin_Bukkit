package corelin.plugins.library.module;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.utils.GameVersion;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/11/22 19:51
 * @版本 1.0
 */
public class CoreLibModuleManager {

    private boolean isEnd = false;
    private CoreLin coreLin;
    private File directoryFile;
    private CoreLibModuleLoad moduleLoad;

    @Getter
    private List<CoreModuleInfo> enableModuleInfo = new ArrayList<>();

    public CoreLibModuleManager(CoreLin coreLin){
        this.coreLin = coreLin;

    }


    public void load(){
        this.directoryFile = new File(this.coreLin.getDataFolder() , "module");
        if (!this.directoryFile.exists()){
            this.directoryFile.mkdirs();
        }
        this.moduleLoad  = new CoreLibModuleLoad(this.coreLin , this);
        //检开始预加载模块
        preLoading();
        //装载类和依赖
        loadLibsFile();
        //开始加载
        if (this.enableModuleInfo.size() == 0){
            coreLin.info("没有模块需要加载!");
        }else{
            coreLin.info("共计:" + this.enableModuleInfo.size() + "个模块需要加载");
        }

        //开始执行模块的onLoad方法，即为正式被加载了
        this.enableModuleInfo.forEach(this::loadAllModule);
    }

    /**
     * 预先加载一下所有模块拿到信息查看是否和当前核心版本兼容
     * 使用ASM查询模块的信息
     */
    private void preLoading() {
        for (File ps : Objects.requireNonNull(this.directoryFile.listFiles())){
            //如果是文件夹或者不是jar包就跳过
            if (ps.isDirectory() || !(ps.getName().endsWith(".jar"))){
                continue;
            }

        }
        /*if (!this.coreLib.getBasis().isHasUse()){
            this.coreLib.info("找不到主要模块，正在为您准备下载");
            DownloadModule downloadModule = new DownloadModule(this.coreLib);
           // downloadModule.downloadMainModule();
            //DownloadModule downloadModule = new DownloadModule();

        }*/
    }

   /* public List<CoreLibModuleFileInfo> getFileInfo() throws IOException, InvalidDescriptionException {
        List<CoreLibModuleFileInfo> fileInfoList = new ArrayList<>();
        for (File ps : Objects.requireNonNull(this.directoryFile.listFiles())){
            //如果是文件夹或者不是jar包就跳过
            if (ps.isDirectory() || !(ps.getName().endsWith(".jar"))){
                continue;
            }
            JarFile jar = new JarFile(ps);
            JarEntry entry = jar.getJarEntry("plugin.yml");
            InputStream stream = null;
            if (entry == null){
                continue;
            }
            stream = jar.getInputStream(entry);
            PluginDescriptionFile descriptionFile = new PluginDescriptionFile(stream);
            CoreLibModuleFileInfo fileInfo = new CoreLibModuleFileInfo(
                    descriptionFile.getName() , descriptionFile.getName() , descriptionFile.getMain()
            );
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }
*/
    public void addModule(CoreModuleInfo moduleInfo){
        this.enableModuleInfo.add(moduleInfo);
    }

    public void loadAllModule(CoreModuleInfo moduleInfo){
        this.coreLin.info("§a模块§6" + moduleInfo.getName() + "§a开始被加载!");
        moduleInfo.getModule().setCoreModuleInfo(this.coreLin , moduleInfo);
        moduleInfo.getModule().onLoad(moduleInfo);
    }

    /**
     * 关闭服务器会执行这个
     */
    public void onEnd(){
        this.isEnd = true;
        this.coreLin.info("正在关闭所有模块....");
        this.enableModuleInfo.forEach(this::disableModule);
        this.enableModuleInfo.clear();
    }

    /**
     * 将模块卸载
     * @param moduleInfo
     */
    public void disableModule(CoreModuleInfo moduleInfo){
        CoreLibModule module = moduleInfo.getModule();
        this.coreLin.info("模块:" + moduleInfo.getName() + "正在被卸载");
        if (module.enable){
            module.enable = false;
            //告诉模块他将被卸载
            module.onRemove();
            this.coreLin.info("模块:" + moduleInfo.getName() + "卸载完成");
            if (!this.isEnd){
                this.enableModuleInfo.remove(moduleInfo);
            }
            moduleInfo.setClassLoader(null);
        }else{
            this.coreLin.info("模块:" + moduleInfo.getName() + "卸载完成");
        }
    }

    @SneakyThrows
    public void loadLibsFile()  {
        for (File ps : Objects.requireNonNull(this.directoryFile.listFiles())){
            //如果是文件夹或者不是jar包就跳过
            if (ps.isDirectory() || !(ps.getName().endsWith(".jar"))){
                continue;
            }
            CoreModuleInfo moduleInfo = moduleLoad.loadModule(ps);
            if (moduleInfo != null){
                this.moduleLoad.getInfoMap().put(moduleInfo.getName() , moduleInfo);
            }
        }
        moduleLoad.values().forEach(moduleLoad::loadPremise);
    }


}
