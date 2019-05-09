package club.dulaoshi.blog.lucene;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author djg
 * @date 2019/5/9 10:58
 * @des
 */
@Component
@ConfigurationProperties(prefix = "lucene")
@Data
public class LucenePath {
    private String path;
}
