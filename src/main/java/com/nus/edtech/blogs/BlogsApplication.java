package com.nus.edtech.blogs;

import com.nus.edtech.blogs.listener.MessageQueueListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BlogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsApplication.class, args);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new MessageQueueListener(), 10, 1, TimeUnit.SECONDS);
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

}