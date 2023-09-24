package com.ramij.inventory.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@Log4j2
public class ProductController {
	@GetMapping("/hello")
	public String sayHello () {
		return "Hello guys";
	}


}
