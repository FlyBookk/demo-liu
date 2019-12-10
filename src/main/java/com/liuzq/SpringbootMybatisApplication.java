package com.liuzq;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.liuzq.dao")
@EnableAsync //开启异步调用(需要注意异步调用将会使用一个简单线程连接工具每次都会创建一个新的线程,且创建的线程不被管理,所以基于内存优化考虑应该尽量使用自定义线程池控制线程数量达到内存优化效果)
public class SpringbootMybatisApplication {
    Logger log = LoggerFactory.getLogger(SpringbootMybatisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }

	/*// restful服务消费
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			String quote = restTemplate.getForObject(
					"http://www.baidu.com", String.class);
			log.info(quote.toString());
		};
	}*/


}
