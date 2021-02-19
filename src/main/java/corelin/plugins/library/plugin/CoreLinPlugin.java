package corelin.plugins.library.plugin;

import corelin.plugins.library.api.CoreLinApi;
import corelin.plugins.library.api.plugin.annotation.SpigotPlugin;
import corelin.plugins.library.plugin.command.CoreAddCommand;
import corelin.plugins.library.plugin.config.CoreLinConfig;
import corelin.plugins.library.plugin.config.player.PlayerDataListener;
import corelin.plugins.library.plugin.config.player.PlayerDataManager;
import corelin.plugins.library.plugin.interfaces.CommandHelp;
import corelin.plugins.library.plugin.interfaces.PermissionHandle;
import corelin.plugins.library.plugin.java.CoreLinPluginClassLoader;
import corelin.plugins.library.plugin.utils.server.BukkitServer;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 0:55
 */
public abstract class CoreLinPlugin implements Plugin {

    private CoreLinApi api;

    /**
     *
     * 默认配置文件
     */
    private CoreLinConfig coreLinConfig;

    @Setter
    private PlayerDataManager playerDataManager;

    //插件加载初始化  ->开始
    private PluginDescriptionFile descriptionFile;

    private Logger logger;

    private Server server;

    private File file;

    private File dataFolder;

    private CoreLinPluginClassLoader classLoader;

    private boolean naggable = true;

    private boolean isEnabled = false;

    private PluginLoader loader;

    public CoreLinPlugin(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof CoreLinPluginClassLoader)) {
            throw new IllegalStateException("未知加载器，插件无法加载");
        } else {
            ((CoreLinPluginClassLoader)classLoader).initialize(this);
        }
    }




    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        this.onStart();
    }

    @Override
    public void onDisable() {
        this.onEnd();
    }


    public abstract void onStart();

    public abstract void onEnd();

    public void onDone(){

    }


    public boolean isConfig(){
        return false;
    }


    public void info(Object o){
        Bukkit.getConsoleSender().sendMessage("§c[§a"+ this.getName() + "§c]§b信息输出:§6" + o);

    }

    protected void initialization(){

    }

    private void loadInitialization(PluginLoader loader ,PluginDescriptionFile pluginInfo , Server server , File file , File dataFolder ,
                                    CoreLinPluginClassLoader classLoader){
        this.loader = loader;
        this.descriptionFile = pluginInfo;
        this.logger = new PluginLogger(this);
        this.server = server;
        this.file = file;
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
        this.api = CoreLinApi.getInstance();
        this.api.getPluginManager().addPlugin(this);
        this.coreLinConfig = new CoreLinConfig(this.api.getCoreLin() , this);
        if (this.isConfig()){
            this.coreLinConfig.initialization();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return this.getCoreLinConfig().getConfiguration();
    }

    @Override
    public void reloadConfig() {
        this.getCoreLinConfig().reload();
    }

    @Override
    public void saveConfig() {
        this.getCoreLinConfig().save();
    }

    public CoreLinConfig getCoreLinConfig(){
        return this.coreLinConfig;
    }



    /**
     * 免除使用plugin.yml文件的注册
     * @param name 指令
     * @param cmd 指令类
     * @param handle 权限处理
     * @param help 指令帮助
     * @param title 提示
     * @param <T>
     * @return 返回指令类
     */
    public <T> T addCommand(String name , T cmd , PermissionHandle handle , CommandHelp help , String title){
        CoreAddCommand<T> addCommand = new CoreAddCommand<T>(name , cmd ,handle , help , title );
        BukkitServer.instance.getServer().getCommandMap().register(name , addCommand);
        return cmd;
    }

    /**
     * 需要在plugin.yml提前注册
     * @param name 指令
     * @param cmd 指令类
     * @param help 指令帮助
     * @param title 提示
     * @param <T>
     * @return 返回指令类
     */
    public <T extends CommandExecutor> T setCommand(String name , T cmd , PermissionHandle handle , CommandHelp help , String title){
        // this.getCommand(name).setExecutor(this.libCommand.setCommand(name , cmd , handle , title));
        // return cmd;
        return null;
    }

    public void registerEvents(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener , this);
    }


    protected void copyFile(String path , boolean replace){
        this.coreLinConfig.saveResource(path , replace); }


    public  PlayerDataManager getPlayerDataManager(PlayerDataListener listener){
        if (this.playerDataManager == null){
            this.playerDataManager = new PlayerDataManager(this , listener );
        }
        return this.playerDataManager;
    }

    @Override
    public File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return this.descriptionFile;
    }

    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }


    @Override
    public InputStream getResource(String filename) {
        return this.coreLinConfig.getResource(filename);
    }

    @Override
    public void saveDefaultConfig() {

    }

    @Override
    public void saveResource(String s, boolean b) {
        this.coreLinConfig.saveResource(s , b);
    }


    @Override
    public PluginLoader getPluginLoader() {
        return this.loader;
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }


    @Override
    public boolean isNaggable() {
        return this.naggable;
    }

    @Override
    public void setNaggable(boolean b) {
        this.naggable = b;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s1) {
        return null;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public String getName() {
        return this.descriptionFile.getName();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }


}
