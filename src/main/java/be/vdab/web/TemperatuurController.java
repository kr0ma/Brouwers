package be.vdab.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.exceptions.KanTemperatuurNietLezenException;
import be.vdab.services.TemperatuurService;


@Controller
@RequestMapping("temperatuur")
class TemperatuurController {
	private final TemperatuurService temperatuurService;
	private final static String VIEW = "temperatuur/gemeente";
	
	@Autowired
	TemperatuurController(TemperatuurService temperatuurService) {
		this.temperatuurService = temperatuurService;
	}
	
	@RequestMapping(path = "{plaats}", method = RequestMethod.GET)
	ModelAndView naarDollar(@PathVariable String plaats) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		try {
			modelAndView.addObject("temperatuur", temperatuurService.getTemperature(plaats));
		} catch (KanTemperatuurNietLezenException ex) {
			modelAndView.addObject("fout", "Kan temperatuur niet lezen");
		}
		return modelAndView;
	}
}
