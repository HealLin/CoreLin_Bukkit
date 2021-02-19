package corelin.plugins.library.utils;

import java.lang.reflect.Field;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 1:34
 */
public class ReflectionUtils {

    public static <T> Field getDeclaredField(Class<T> cls , String fieldName){
        Field nameField = null;
        try {
            nameField = cls.getDeclaredField("name");
            nameField.setAccessible(true);
            return nameField;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return nameField;
    }
}
