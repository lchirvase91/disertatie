package javaBeans;

import java.io.Serializable;

public class ClientBean implements Serializable {
	
	private String nume;
	private String judet;
	private String localitate;
	private String adresa;
	private String telefon;
	private String email;
	
	public ClientBean(String nume, String judet, String localitate,
			String adresa, String telefon, String email) {
		super();
		this.nume = nume;
		this.judet = judet;
		this.localitate = localitate;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getJudet() {
		return judet;
	}

	public void setJudet(String judet) {
		this.judet = judet;
	}

	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
