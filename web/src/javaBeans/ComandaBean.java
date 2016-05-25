package javaBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javaBeans.ColetBean;

public class ComandaBean implements Serializable {

	private String id_comanda;
	private ClientBean expeditor;
	private ClientBean destinatar;
	private Date data_comanda;
	private String nr_colete;
	private String greutate;
	private String observatii;
	private String awb;
	
	public ComandaBean() {
		super();
	}

	public ComandaBean(ClientBean expeditor, ClientBean destinatar,
			String nr_colete, String greutate, String observatii) {
		super();
		this.expeditor = expeditor;
		this.destinatar = destinatar;
		this.nr_colete = nr_colete;
		this.greutate = greutate;
		this.observatii = observatii;
	}

	
	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getId_comanda() {
		return id_comanda;
	}

	public void setId_comanda(String id_comanda) {
		this.id_comanda = id_comanda;
	}

	public ClientBean getExpeditor() {
		return expeditor;
	}

	public void setExpeditor(ClientBean expeditor) {
		this.expeditor = expeditor;
	}

	public ClientBean getDestinatar() {
		return destinatar;
	}

	public void setDestinatar(ClientBean destinatar) {
		this.destinatar = destinatar;
	}

	public Date getData_comanda() {
		return data_comanda;
	}

	public void setData_comanda(Date data_comanda) {
		this.data_comanda = data_comanda;
	}

	public String getNr_colete() {
		return nr_colete;
	}

	public void setNr_colete(String nr_colete) {
		this.nr_colete = nr_colete;
	}

	public String getGreutate() {
		return greutate;
	}

	public void setGreutate(String greutate) {
		this.greutate = greutate;
	}

	public String getObservatii() {
		return observatii;
	}

	public void setObservatii(String observatii) {
		this.observatii = observatii;
	}
	
	
	
}
