package gui;

import java.text.DecimalFormat;

public class Produit {
	public int id;
	public String type; //c'est genre canette
	public String nom;
	public int prixtotal=0;
	public int credit=0;
	public int quantite=0; //utile pour le panier
	public int idPhoto;
	
	public Produit(int obj_id, String obj_name, String obj_type, int credit) {
		this.id = obj_id;
		this.nom = obj_name;
		this.type = obj_type;
		this.prixtotal = credit;
		this.credit = credit;
		this.quantite = 1;
	}
	
	public Produit(int obj_id, String obj_name, String obj_type, int credit, int img_id) {
		this.id = obj_id;
		this.nom = obj_name;
		this.type = obj_type;
		this.idPhoto = img_id;
		this.credit = credit;
		this.prixtotal = credit;
		this.quantite = 1;
	}

	public String getPrixTotal() {
		Float var = new Float(prixtotal)/100;
		
		DecimalFormat df = null;
		if(var%1 == 0)
			df = new DecimalFormat("#######0");
		else
			df = new DecimalFormat("#######0.00");
    	
        return df.format(var).toString()+" €";
	}
	
}
