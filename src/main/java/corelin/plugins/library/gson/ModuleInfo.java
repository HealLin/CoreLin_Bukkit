package corelin.plugins.library.gson;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 择忆霖心
 * @时间 2021/2/19 21:16
 */
@NoArgsConstructor
@Data
public class ModuleInfo {


    /**
     * url : http://
     * md5 : dasdnasjkodas
     */

    @com.fasterxml.jackson.annotation.JsonProperty("url")
    private String url;
    @com.fasterxml.jackson.annotation.JsonProperty("md5")
    private String md5;
}
