package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.*;

public class Panier {
	
	public JPanel blocPanier;
	private JPanel blocProduit;
	private JPanel blocPrix;
	private Vector<Produit> vecProduit = new Vector<Produit>();
	public int prixTotal = 0;
	public int creditUser = 0;
	//créer un array qui garde les id, nom, quantité
	
	public Panier() {
		blocPanier = new JPanel();
		blocPanier.setBounds(0, 380, 260, MainFrame.height - 570);
		blocPanier.setLayout(null);
		blocPanier.setOpaque(false);
		blocPanier.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Panier"));
	}
	
	public void setCreditMax(int creditMax) {
		creditUser = creditMax;
	}
	
	public void ajouterArticle(Produit produit) {
		boolean aAjouter = true;
		
		//encaisse
		try {
			//on ajoute au panier seulement après être sur que l'achat se soit bien passé
			if (produit.prixtotal > creditUser || (prixTotal+produit.prixtotal) > creditUser) //si pas assez de tune
			{
				PopupError.retourMethode(462);
			}
			else
			{		
				if (PopupError.retourMethode(MainFrame.PBUY.select(produit.id, produit.prixtotal, "panier Peggy")))
				{
					if (!produit.type.equals("category"))
					{
						Iterator<Produit> it = vecProduit.iterator();
						while (it.hasNext()) {
							Object obj = it.next();
							Produit prod = (Produit)obj;
							if(prod.id == produit.id) {
								prod.quantite++;
								prod.prixtotal = prod.prixtotal + produit.credit;
								aAjouter = false;
							}
						}
					
						if(aAjouter) {
							vecProduit.add(produit);
						}
					}
				}
			}
			//else... faudrait indiquer au vendeur que ça a bugué
		} catch (RemoteException e) {
		}
		
		updatePanier();
	}
	
	public void retirerArticle(int idArticle) {
		//on cherche dans l'array l'id... on supprime la ligne
		Produit prodDelete = null;
		Iterator<Produit> it = vecProduit.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			Produit prod = (Produit)obj;
			if(prod.id == idArticle) {
				prodDelete = prod;
			}
		}
		vecProduit.remove(prodDelete); //on suppr en dehors de la boucle pour éviter les bugs, quand ça tourne trop vite :)
		updatePanier();
	}
	
	public void viderPanier() {
		try {
			//on ajoute au panier seulement après être sur que l'achat se soit bien passé
			if (MainFrame.PBUY.cancelCart() == 1) {
				vecProduit.removeAllElements();
				updatePanier();
				AfficheurDeporte.setViderPanier();
			}
			//else... faudrait indiquer au vendeur que ça a bugué
		} catch (RemoteException e) {
		}
	}
	
	public JPanel afficherPanier() {
		return blocPanier;
	}
	
	public void updatePanier() {
		blocPanier.setVisible(false);
		
		blocPanier.removeAll();

		blocProduit = new JPanel();
		blocPrix = new JPanel();
		blocProduit.setBounds(5, 15, 250, MainFrame.height - 610);
		blocPrix.setBounds(5, MainFrame.height - 600, 250, 30);
		blocProduit.setOpaque(false);
		blocPrix.setOpaque(false);
		blocPrix.setLayout(null);
		
		blocProduit.setLayout(new GridLayout(5, 2, 1, 1)); //Layout qui permet de placer les boutons sous forme de grille... d'où le nom
        
		prixTotal=0;
		Iterator<Produit> it = vecProduit.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			final Produit prod = (Produit)obj;
			
			JLabel labelPanier = new JLabel(prod.quantite + "|" + prod.nom + "|" + prod.getPrixTotal());
			labelPanier.setVerticalAlignment(SwingConstants.TOP); //ça centre en haut...
			labelPanier.setHorizontalTextPosition(SwingConstants.LEFT);
			labelPanier.setHorizontalAlignment(SwingConstants.LEFT);
			labelPanier.setFocusable(false);
			labelPanier.setOpaque(true);
			labelPanier.setBackground(Color.DARK_GRAY);
			labelPanier.setForeground(Color.WHITE);
			
			labelPanier.setBorder(javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			
			prixTotal+=prod.prixtotal;
			
			blocProduit.add(labelPanier);	
		}
		
		JLabel labelTotal = new JLabel("Total : " + getPrixTotal());
		labelTotal.setBounds(0, 0, 250, 30);
		labelTotal.setFont(new Font("Arial", Font.BOLD, 18));
		//labelTotal.setBackground(Color.DARK_GRAY);
		labelTotal.setForeground(Color.WHITE);			
		labelTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		labelTotal.setHorizontalAlignment(SwingConstants.CENTER);
		blocPrix.add(labelTotal);
		
		blocPanier.add(blocProduit);
		blocPanier.add(blocPrix);

		blocPanier.revalidate();
		
		blocPanier.setVisible(true);
	}
	
	public String getPrixTotal() {
        Float var = new Float(prixTotal)/100;
		
		DecimalFormat df = null;
		if(var%1 == 0)
			df = new DecimalFormat("#######0");
		else
			df = new DecimalFormat("#######0.00");
    	
        return df.format(var).toString()+" €";
	}
}