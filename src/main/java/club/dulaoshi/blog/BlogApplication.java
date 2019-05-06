package club.dulaoshi.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author djg
 * @data 2019-05-05
 * @dec springboot 启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"club.dulaoshi.blog.dao"})
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
