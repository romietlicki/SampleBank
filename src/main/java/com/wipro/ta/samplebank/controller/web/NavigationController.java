package com.wipro.ta.samplebank.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wipro.ta.samplebank.auth.Login;

@Controller
public class NavigationController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String rootRedirect() {
		return "redirect:index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession session) {
		return "index";
	}

	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "admin/home";
	}

	@ModelAttribute("login")
	public Login getLogin() {
		return new Login();
	}
}