package corelin.plugins.library.plugin.config;

import corelin.plugins.library.CoreLin;
import corelin.plugins.library.plugin.CoreLinPlugin;
import corelin.plugins.library.plugin.config.annotation.CoreConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: TODO
 * @作者: 择忆霖心
 * @author: 择忆霖心
 * @时间: 2020/4/5 23:48
 * @版本: 1.0
 */
public class CoreLinConfig {

    private CoreLin coreLin;
    private CoreLinPlugin plugin;
    private File directoryFile;
    private File file;
    private YamlConfiguration configuration;


    public CoreLinConfig(CoreLin coreLin, CoreLinPlugin plugin) {
        this.coreLin = coreLin;
        this.plugin = plugin;
        this.directoryFile = this.plugin.getDataFolder();
        this.file = new File(this.directoryFile , "config.yml");
    }

    public void initialization(){
        if (!directoryFile.exists()){
            directoryFile.mkdirs();
        }
        if (!this.file.exists()){
            saveResource("config.yml" , false);
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void copyFile(String path , boolean replace){
        saveResource(path , replace);
    }


    public void save(){
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public File getDirectoryFile() {
        return directoryFile;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            if (in != null) {
                File outFile = new File(this.directoryFile, resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(this.directoryFile, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                }
            }
        } else {
        }
    }

    public InputStream getResource(String filename) {
        if (filename != null) {
            try {
                URL url = this.plugin.getClass().getClassLoader().getResource(filename);
                if (url == null) {
                    return null;
                } else {
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);
                    return connection.getInputStream();
                }
            } catch (IOException var4) {
                return null;
            }
        }
        return null;
    }

    /**
     * 将带有CoreLin的注解映射
     * @param path 路径
     * @param fileConfig 配置
     * @param config 实例
     * @param needTitle 提示字段
     * @param useTitle 是否使用提示
     * @param replace 是否替换
     * @param <T>
     */
    public static <T> void iniCGC(String path , FileConfiguration fileConfig , T config , String needTitle , boolean useTitle , boolean replace){
        if (!path.isEmpty()){
            path = path + ".";
        }
        Field[] fields = config.getClass().getDeclaredFields();
        try {
            String title = "";
            if (useTitle){
                config.getClass().getDeclaredField(needTitle).set(config , getConfig( path + needTitle , fileConfig , config.getClass().getDeclaredField(needTitle).getType() , "" ,false, "" , true));
                title = (String) config.getClass().getDeclaredField(needTitle).get(config);
            }
            for (Field f : fields){
                f.setAccessible(true);
                String fieldName = f.getName();
                if (fieldName.equalsIgnoreCase(needTitle)){
                    continue;
                }
                if (f.isAnnotationPresent(CoreConfig.class)){
                    CoreConfig coreConfig = f.getAnnotation(CoreConfig.class);
                    f.set(config, getConfig(path  + fieldName, fileConfig , f.getType() ,coreConfig.type() , coreConfig.title() , title , replace));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Object getConfig(String path , FileConfiguration config , Class type ,  String customizeType , boolean isTitle , String title , boolean replace){
        if (customizeType.isEmpty()){
            try {
                if (type.equals(String.class)){
                    if (isTitle){
                        if (replace){
                            return title + config.getString(path , "").replace("&" , "§");
                        }
                        return title + config.getString(path , "");
                    }
                    if (replace){
                        return config.getString(path , "").replace("&" , "§");
                    }
                    return config.getString(path , "");
                }else if (type.equals(int.class)){
                    return config.getInt(path , 0);
                }else if (type.equals(boolean.class)){
                    return config.getBoolean(path , false);
                }else if (type.equals(List.class)){
                    ArrayList<String> arrayList = new ArrayList<>();
                    config.getStringList(path).forEach((l) ->{
                        arrayList.add(l.replace("&" , "§"));
                    });
                    return arrayList;
                }else if (type.equals(double.class)){
                    return config.getDouble(path , 0);
                }else{
                    return null;
                }
            }catch (NullPointerException e){
                System.out.println("问题字段" + path);
                System.out.println("问题类型" + type.getName());
                e.printStackTrace();
                return null;
            }
        }else{
            switch (customizeType.toLowerCase()){
                case "string":{
                    if (isTitle){
                        if (replace){
                            return title + config.getString(path , "").replace("&" , "§");
                        }
                        return title + config.getString(path , "");
                    }
                    if (replace){
                        return config.getString(path , "").replace("&" , "§");
                    }
                    return config.getString(path , "");
                }
                case "int":{
                    return config.getInt(path , 0);
                }
                case "boolean":{
                    return config.getBoolean(path , false);
                }
                case "maplist":{
                    return config.getMapList(path);
                }
                case "stringlist":{
                    return config.getStringList(path);
                }
                case "intlist":{
                    FieldUtils<String> a = getClassField(null ,"");
                    return config.getIntegerList(path);
                }
                default:{
                    return null;
                }

            }
        }
    }

    /**
     * 可以通过反射直接获取任意类的字段
     * @param config 需要获取的子端
     * @param name 字段名称
     * @param <T> 类型
     * @return 字段包装
     */
    public static <T> FieldUtils<T> getClassField (Object config ,  String name){
        try {
            Field field = config.getClass().getDeclaredField(name);
            field.setAccessible(true);
            T o = (T) field.get(config);
            return new FieldUtils<T>(true , o);
        } catch (Exception e) {
            return new FieldUtils<T>(false , null);
        }
    }

}
