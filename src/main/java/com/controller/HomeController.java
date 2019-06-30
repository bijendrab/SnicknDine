package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.model.Queries;
import com.service.QueriesService;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class HomeController {

	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping({ "/index", "/index1" })
	public String sayIndex() {
		return "index1";
	}

	@RequestMapping("/hello")
	public ModelAndView sayHello() {
		return new ModelAndView("hello", "hello", "Hello Mr.Ismail");
	}

	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null)
			model.addAttribute("error", "Invalid username and Password");
		if (logout != null)
			model.addAttribute("logout", "You have logged out successfully");
		return "login";
	}


	@RequestMapping("/aboutus")
	public String sayAbout() {
		return "aboutUs";
	}

	@Autowired
	private QueriesService queryService;

	@RequestMapping(value = "/contactus")
	public ModelAndView getQuery() {
		Queries query = new Queries();
		return new ModelAndView("contactUs", "contact", query);
	}

	@RequestMapping(value = "/contactus", method = RequestMethod.POST)
	public String addQuery(@Valid @ModelAttribute(value = "contact") Queries query, Model model, BindingResult result) {

		if (result.hasErrors())
			return "contactUs";

		queryService.addQuery(query);
		model.addAttribute("querySuccess",
				"Thank you, Your Message stored in our Server we will contact through corresponding Mail");
		return "login";

	}

}
