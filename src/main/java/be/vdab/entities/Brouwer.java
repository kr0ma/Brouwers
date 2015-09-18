package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import be.vdab.valueobjects.Adres;

@Entity
@Table(name="brouwers")
@XmlRootElement
public class Brouwer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotBlank
	private String naam;
	
	@Min(0)
	private Integer omzet;
	
	@NotNull
	@Valid	
	@Embedded
	private Adres adres;
	
	public Brouwer(){}
	
	public Brouwer(String naam, Integer omzet, Adres adres) {
		this.naam = naam;
		this.omzet = omzet;
		this.adres = adres;
	}
	
	public Brouwer(long id, String naam, Integer omzet, Adres adres) {
		this(naam, omzet, adres);
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public Integer getOmzet() {
		return omzet;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setOmzet(Integer omzet) {
		this.omzet = omzet;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
