package club.dulaoshi.blog.conf.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author djg
 * @date 2019/5/30 15:09
 * @des 七牛配置类
 */
@Component
@ConfigurationProperties(prefix = "blog.qiniu")
@Data
public class QiniuProp {
    /**
     * 外链域名
     */
    private String endPoint;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;


    /**
     * 命名空间
     */
    private String bucketName;

}
