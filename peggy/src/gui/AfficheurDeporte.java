package gui;

import java.text.DecimalFormat;

public class AfficheurDeporte {

	public AfficheurDeporte() {
	}
	
	//ajoute un produit sur l'afficheur
	public static void addNewProduct(String produit, int prix) {
		if(Pertelian.GetStatus()){
			Pertelian.Write("+" + formatCredit(prix) + " " + produit,2);
			Pertelian.Update();
		}		
	}
	
	public static void addRechargement(int valueRecharge) {
		if(Pertelian.GetStatus()){
			Pertelian.Write("rechargement",2);
			Pertelian.Write(formatCredit(valueRecharge)+" ?",3);
			Pertelian.Update();
		}
	}
	
	public static void validerRechargement() {
		if(Pertelian.GetStatus()){
			Pertelian.Write("       COMPTE",2);
			Pertelian.Write("      RECHARGE",3);
			Pertelian.Update();
		}		
	}
	
	public static void setCredit(int credit) {
		if(Pertelian.GetStatus()){
			Pertelian.Write("SOLDE : " + formatCredit(credit),4);
			Pertelian.Update();
		}
	}
	
	public static void setTotal(int total) {
		if(Pertelian.GetStatus()){
			Pertelian.Write("TOTAL : " + formatCredit(total),3); 
			Pertelian.Update();
		}		
	}	
	
	public static void setNomBonjour(String nom) {
		if(Pertelian.GetStatus())
		{
				Pertelian.Clear();
				Pertelian.Write(nom,1);
				Pertelian.Write("      BONJOUR!",2);
				Pertelian.Update();
		}
	}
	
	public static void setBienvenue() {
		if(Pertelian.GetStatus())
		{
				Pertelian.Clear();
				Pertelian.Write("BONJOUR",1);
				Pertelian.Update();
		}
	}
	
	public static void setOuvert() {
		if(Pertelian.GetStatus())
		{
				Pertelian.Clear();
				Pertelian.Write("     EN ATTENTE",2);
				Pertelian.Write("  DE TA CARTE ETU!",3);
				Pertelian.Update();
		}
	}
	
	public static void setFerme() {
		if(Pertelian.GetStatus())
		{
				Pertelian.Clear();
				Pertelian.Update();
		}
	}
	
	public static void setInconnu() {
		if(Pertelian.GetStatus())
		{
				Pertelian.Clear();
				Pertelian.Write("UTILISATEUR INCONNU",1);
				Pertelian.Update();
		}
	}
	
	public static void setNom(String nom) {
		if(Pertelian.GetStatus())
		{
				Pertelian.Clear();
				Pertelian.Write(nom,1);
				Pertelian.Update();
		}
	}
	
	public static void setViderPanier() {
		if(Pertelian.GetStatus())
		{
				Pertelian.Write("    ",2);
				Pertelian.Write("",3);
				Pertelian.Update();
		}
	}
	
	public static String formatCredit(int credit) {
		Float var = new Float(credit)/100;
		
		DecimalFormat df = null;
		if(var%1 == 0)
			df = new DecimalFormat("#######0");
		else
			df = new DecimalFormat("#######0.00");
    	
        return df.format(var).toString()+"E";
	}
}
