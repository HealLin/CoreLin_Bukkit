package corelin.plugins.library.plugin.utils;

import corelin.plugins.library.api.plugin.annotation.SpigotPlugin;
import corelin.plugins.library.utils.ReflectionUtils;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 1:28
 */
public class PluginDescriptionFileUtils {

    @Getter
    private PluginDescriptionFile descriptionFile = new PluginDescriptionFile("a" , "a" , "a");

    public PluginDescriptionFile spigotPluginToDescription(SpigotPlugin spigotPlugin){
        this.setName(spigotPlugin.pluginName());
        this.setVersion(spigotPlugin.version());
        this.setAuthors(dataToArray(spigotPlugin.author()));
        this.setPrefix(spigotPlugin.prefix());
        this.setDescription(spigotPlugin.description());
        this.setDepend(dataToArray(spigotPlugin.depend()));
        this.setSoftDepend(dataToArray(spigotPlugin.softDepend()));
        this.setLoadBefore(dataToArray(spigotPlugin.loadBefore()));
        return this.getDescriptionFile();
    }

    public SpigotPlugin DescriptionToSpigotPlugin(PluginDescriptionFile descriptionFile){
        SpigotPlugin plugin = new SpigotPlugin(){
            @Override
            public Class<? extends Annotation> annotationType() {
                return SpigotPlugin.class;
            }

            @Override
            public String pluginName() {
                return descriptionFile.getName();
            }

            @Override
            public String version() {
                return descriptionFile.getVersion();
            }

            @Override
            public String author() {
                return arrayToData(descriptionFile.getAuthors());
            }

            @Override
            public String prefix() {
                return descriptionFile.getPrefix();
            }

            @Override
            public String description() {
                return descriptionFile.getDescription();
            }

            @Override
            public String depend() {
                return arrayToData(descriptionFile.getDepend());
            }

            @Override
            public String softDepend() {
                return arrayToData(descriptionFile.getSoftDepend());
            }

            @Override
            public String loadBefore() {
                return arrayToData(descriptionFile.getLoadBefore());
            }
        };
        return plugin;
    }



    public PluginDescriptionFileUtils(String mainClass){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "main").set(this.descriptionFile , mainClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "name").set(this.descriptionFile , name);
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "rawName").set(this.descriptionFile , name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setVersion(String version){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "version").set(this.descriptionFile , version);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setPrefix(String prefix){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "prefix").set(this.descriptionFile , prefix);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setDescription(String description){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "description").set(this.descriptionFile , description);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setDepend(List<String> dependList){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "depend").set(this.descriptionFile , dependList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSoftDepend(List<String> softDependList){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "softDepend").set(this.descriptionFile , softDependList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setLoadBefore(List<String> loadBefore){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "loadBefore").set(this.descriptionFile , loadBefore);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void setAuthors(List<String> authors){
        try {
            ReflectionUtils.getDeclaredField(descriptionFile.getClass() , "authors").set(this.descriptionFile , authors);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static List<String> dataToArray(String data){
        String[] newData = data.split(",");
        return Arrays.asList(newData);
    }

    public static String arrayToData(List<String> list){
        StringBuilder buffer = new StringBuilder();
        for (String l : list){
            buffer.append(l).append(",");
        }
        return buffer.toString();
    }




}
