package be.vdab.restservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.vdab.entities.Brouwer;
import be.vdab.exceptions.BrouwerNietGevondenException;
import be.vdab.services.BrouwerService;

@RestController
@RequestMapping("/brouwers")
@ExposesResourceFor(Brouwer.class)
class BrouwerRestController {
	private BrouwerService brouwerService;
	private EntityLinks entityLinks;
	
	@Autowired
	public BrouwerRestController(BrouwerService brouwerService, EntityLinks entityLinks) {
		this.brouwerService = brouwerService;
		this.entityLinks = entityLinks;
	}
	
	@RequestMapping(path = "{brouwer}", method = RequestMethod.GET)
	BrouwerResource read(@PathVariable Brouwer brouwer){
		if (brouwer == null){
			throw new BrouwerNietGevondenException();
		}
		return new BrouwerResource(brouwer, entityLinks);
	}	
	
	@RequestMapping(method = RequestMethod.GET, params = "beginnaam")
	BrouwersResource findAll(String beginnaam){
		return new BrouwersResource(brouwerService.findByNaam(beginnaam), entityLinks);
	}
	
	
	@ExceptionHandler(BrouwerNietGevondenException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void brouwerNietGevonden() {
	}
	

}
