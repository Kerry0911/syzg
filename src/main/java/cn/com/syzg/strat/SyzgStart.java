package cn.com.syzg.strat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("cn.com.syzg")
@MapperScan("cn.com.syzg.repository")
@SpringBootApplication
public class SyzgStart {
    public static void main(String[] args) {
        SpringApplication.run(SyzgStart.class, args);
    }
}
