package be.vdab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import be.vdab.dao.BrouwerDAO;
import be.vdab.entities.Brouwer;

@ReadOnlyTransactionalService
class BrouwerServiceImpl implements BrouwerService{
	private final BrouwerDAO brouwerDAO;

	@Autowired
	BrouwerServiceImpl(BrouwerDAO brouwerDAO) {
		this.brouwerDAO = brouwerDAO;
	}

	@Override
	@ModifyingTransactionalServiceMethod
	public void create(Brouwer brouwer) {
		brouwer.setId(brouwerDAO.save(brouwer).getId());		
	}

	@Override
	public List<Brouwer> findAll() {
		return brouwerDAO.findAll();
	}

	@Override
	public List<Brouwer> findByNaam(String beginNaam) {
		return brouwerDAO.findByNaamStartingWith(beginNaam);
	}	
	
}
