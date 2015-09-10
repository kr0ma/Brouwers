package be.vdab.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import be.vdab.entities.Brouwer;
import be.vdab.valueobjects.Adres;

@Repository
class BrouwerDAOImpl implements BrouwerDAO{
	private final Map<Long, Brouwer> brouwers = new ConcurrentHashMap<>();

	BrouwerDAOImpl() {
		brouwers.put(1L, new Brouwer(1, "AB Inbev", 15000, new Adres("Brouwerijplein", "1", 3000, "Leuven")));
		brouwers.put(2L, new Brouwer(2, "l'Abbaye de Brogne", null, new Adres("Gasthuisstraat", "31", 1000, "Brussel")));
		brouwers.put(3L, new Brouwer(3, "Abdij Notre-Dame d'Orval", 12000, new Adres("Varkenstraat", "72", 2268, "Orvalstad")));
		brouwers.put(4L, new Brouwer(4, "Abdij Notre-Dame de Scourmont", 5000, new Adres("Melkstraat", "98", 9505, "Scourmont")));
		brouwers.put(5L, new Brouwer(5, "Brouwerij Val-Dieu", 9500, new Adres("Valdieulaan", "19", 6870, "Val-dieuke")));
		brouwers.put(6L, new Brouwer(6, "Brouwerij Hapkin", 2000, new Adres("Diksmuide weg", "22", 8600, "Kortemark")));
		brouwers.put(7L, new Brouwer(7, "Brouwerij Amelot", 10500, new Adres("Koestraat", "44", 9700, "Oudenaarde")));
	}
	
	@Override
	public void create(Brouwer brouwer) {
		brouwer.setId(Collections.max(brouwers.keySet()) + 1);
		brouwers.put(brouwer.getId(), brouwer);		
	}

	@Override
	public List<Brouwer> findAll() {
		return new ArrayList<>(brouwers.values());
	}

	@Override
	public List<Brouwer> findByNaam(String beginNaam) {
		beginNaam = beginNaam.toUpperCase();
		List<Brouwer> brouwersMetBeginNaam = new ArrayList<>();
		for (Brouwer brouwer : brouwers.values()){
			if (brouwer.getNaam().toUpperCase().startsWith(beginNaam)){
				brouwersMetBeginNaam.add(brouwer);
			}
		}
		return brouwersMetBeginNaam;
	}

}
