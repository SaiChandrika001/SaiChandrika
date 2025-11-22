package com.pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pro.service.DevService;
import com.pro.service.ProdService;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ProfileApplication {

	public static void main(String[] args) {
        ApplicationContext ctx=SpringApplication.run(ProfileApplication.class, args);
        
        if (ctx.containsBean("devService")) {
            ctx.getBean(DevService.class).run();
        }

        if (ctx.containsBean("prodService")) {
            ctx.getBean(ProdService.class).run();
        }
	}

}
