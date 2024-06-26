package gob.hidalgo.curso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gob.hidalgo.curso.beans.GuestPreferences;
import gob.hidalgo.curso.components.administracion.ParametrosC;

@Controller
public class SecurityController {
	
	@Autowired
	private GuestPreferences guestPreferences;
	
	@Autowired
	private ParametrosC parametrosC;
	
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model) {
		model.addAttribute("guestPreferences", guestPreferences);
		model.addAttribute("ParametrosC", parametrosC);
		return "login";
	}
}
