package org.eop.hbmy.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixinjie
 * @since 2018-10-18
 */
@Controller
@RequestMapping("")
public class IndexController {

	@GetMapping("/index")
	public String index() {
		return "index/index";
	}
}
