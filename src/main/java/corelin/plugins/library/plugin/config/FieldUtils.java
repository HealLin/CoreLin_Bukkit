package corelin.plugins.library.plugin.config;

/**
 * @author 择忆霖心
 * @version 1.0
 * @date 2020/3/29 18:13
 */
public class FieldUtils<T>{

    boolean has;
    private T data;

    public FieldUtils(boolean has ,T data){
        this.has = has;
        this.data = data;
    }

    public boolean has() {
        return false;
    }

    public T get() throws Exception {
        if (this.has){
            return data;
        }
        throw new Exception("无法强制访问");
    }
}
