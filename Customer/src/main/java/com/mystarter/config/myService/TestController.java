package com.mystarter.config.myService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mystarter.config.MyService;


@RestController
public class TestController {
	
	private final MyService myService;

	public TestController(MyService myService) {
		this.myService = myService;
	}
	  @GetMapping("/hello")
	    public String hello() {
	        return myService.hello();
	    }
	

}
