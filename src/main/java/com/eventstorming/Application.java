path: {{name}}/{{{options.packagePath}}}
fileName: {{namePascalCase}}Application.java
---
package {{options.package}};
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import {{options.package}}.config.kafka.KafkaProcessor;

@Configuration
@ImportResource("classpath*:egovframework/spring/context-*.xml")
@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
@EnableFeignClients
public class {{namePascalCase}}Application {
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run({{namePascalCase}}Application.class, args);
    }
}