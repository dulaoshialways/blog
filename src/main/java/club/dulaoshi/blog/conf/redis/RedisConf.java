package club.dulaoshi.blog.conf.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.SimpleDateFormat;

/**
 * @author djg
 * @Description: 序列化
 * @date 2019/4/26
 */
@Configuration
@EnableCaching
public class RedisConf {
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        ObjectMapper mapper = new ObjectMapper();
        /**设置存储到redis中的日期格式*/
        mapper.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmss"));
        Jackson2JsonRedisSerializer jackson2Serializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2Serializer.setObjectMapper(mapper);
        RedisSerializer redisSerializer = jackson2Serializer;
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
