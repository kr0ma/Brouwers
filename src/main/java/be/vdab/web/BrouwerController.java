package be.vdab.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.entities.Brouwer;
import be.vdab.services.BrouwerService;
import be.vdab.valueobjects.BeginNaamForm;

@Controller
@RequestMapping("/brouwers")
class BrouwerController {

	private static final String BROUWERS_VIEW = "brouwers/brouwers";
	private static final String BEGINNAAM_VIEW = "brouwers/beginnaam";
	private static final String TOEVOEGEN_VIEW = "brouwers/toevoegen";
	private static final String ALFABET_VIEW = "brouwers/alfabet";
	
	private static final String REDIRECT_URL_NA_TOEVOEGEN = "redirect:/brouwers";

	private final char[] alfabet = new char['Z' - 'A' + 1];

	private final BrouwerService brouwerService;

	@Autowired
	BrouwerController(BrouwerService brouwerService) {
		this.brouwerService = brouwerService;
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			alfabet[letter - 'A'] = letter;
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	ModelAndView brouwers() {
		return new ModelAndView(BROUWERS_VIEW, "brouwers", brouwerService.findAll());
	}

	@RequestMapping(path = "beginnaam", method = RequestMethod.GET)
	ModelAndView beginNaamForm() {
		return new ModelAndView(BEGINNAAM_VIEW).addObject(new BeginNaamForm());
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "beginnaam")
	ModelAndView beginNaamForm(@Valid BeginNaamForm beginNaamForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView(BEGINNAAM_VIEW);
		if (!bindingResult.hasErrors()) {
			List<Brouwer> brouwers = brouwerService.findByNaam(beginNaamForm.getBeginnaam());
			if (brouwers.isEmpty()){
				bindingResult.reject("geenBrouwers");
			} else {
				modelAndView.addObject("brouwers", brouwers);
			}			
		}
		return modelAndView;
	}

	@RequestMapping(path = "toevoegen", method = RequestMethod.GET)
	ModelAndView createForm() {
		return new ModelAndView(TOEVOEGEN_VIEW, "brouwer", new Brouwer());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Brouwer brouwer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return TOEVOEGEN_VIEW;
		}
		brouwerService.create(brouwer);
		return REDIRECT_URL_NA_TOEVOEGEN;
	}

	@RequestMapping(path = "alfabet", method = RequestMethod.GET)
	ModelAndView alfabet() {
		return new ModelAndView(ALFABET_VIEW, "alfabet", alfabet);
	}

	@RequestMapping(path = "alfabet/{letter}", method = RequestMethod.GET)
	ModelAndView read(@PathVariable Character letter) {
		return new ModelAndView(ALFABET_VIEW).addObject("alfabet", alfabet).addObject("brouwers",
				brouwerService.findByNaam(String.valueOf(letter)));
	}
	
	@InitBinder("brouwer")
	void initBinderPostcodeReeks(WebDataBinder binder) {
		binder.initDirectFieldAccess();
	}
}
