/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import org.apache.axis.encoding.Base64;

/**
 * Toutes les intéractions avec la BDD pour la gestion des users
 * 
 * @author reivax
 */
public class Vendeur {
	private int id;
	private String nom;
	private String prenom;
	private String surnom;
	private int idPhoto;
	private int isAdmin;
	private String nomPhoto;
	private boolean seller = false;
	private boolean reloader = false;
	private boolean pointMan = false;
	private boolean modeManuel = false;

	/**
	 * constructeur à partir de l'id (soit badgé, soit entré "manuellement")
	 * 
	 * @param id
	 * @return state
	 */
	public Vendeur() {

	}

	public int SetVendeur(String data, int meanOfLog) {
		
		
		try {
			int retour = MainFrame.PBUY.loadSeller(data, meanOfLog, MainFrame.ip, MainFrame.id_point);
			
			if (PopupError.retourMethode(retour)) {
				String[] result = ComplexData.getData(MainFrame.PBUY.getSellerIdentity());	
								
				this.id = Integer.parseInt(result[0]);
				prenom = result[1];
				nom = result[2];
				surnom = result[3];
				idPhoto = Integer.parseInt(result[4]); //TODO quand on aura exporté les photos
				//idPhoto = 8;
				
				//on cherche si la photo existe ou pas
			    if ((new File("photos/" + idPhoto + ".jpg")).exists()) {
			    	nomPhoto = Integer.toString(idPhoto);
			    } else {
			    	if(enregistrerPhoto(idPhoto)) {
			    		if ((new File("photos/" + idPhoto + ".jpg")).exists()) {
					    	nomPhoto = Integer.toString(idPhoto);
					    }
			    		else {
			    			nomPhoto = "pas_de_photo";
			    		}
			    	}
			    	else {
			    		nomPhoto = "pas_de_photo";
			    	}
			    }

			}
			return retour;
		} catch (RemoteException e) {
			return 0;
		}
	}

	public boolean isAdmin() {
		if (isAdmin == 1)
			return true;
		else
			return false;
	}
	
	public boolean isPointMan() {
		return pointMan;
	}
	
	public boolean isSeller() {
		return seller;
	}

	public boolean isReloader() {
		return reloader;
	}
	
	public boolean isModeManuel() {
		return modeManuel;
	}
	
	/**
	 * vérifie le mot de passe de l'user...
	 * 
	 * @return true si le mot de passe est correct (et que donc, il a le droit
	 *         de se connecter), false sinon
	 */
	public boolean validConnexion(String password, int meanOfLog) {		
		
		try {
			Integer retour = MainFrame.PBUY.checkPasswordSeller(password);
			if (PopupError.retourMethode(retour)) {
				//isAdmin = MainFrame.PBUY.isSellerAdmin();
				if (MainFrame.PBUY.isSeller() == 1)
					seller = true;
				else
					seller = false;
				if (MainFrame.PBUY.isReloader() == 1)
					reloader = true;
				else
					reloader = false;
				if (MainFrame.PBUY.isPointMan() == 1)
					pointMan = true;
				else
					pointMan = false;
				if (MainFrame.PBUY.isModeManuel() == 1)
					modeManuel = true;
				else
					modeManuel = false;
								
				return true;
			} 
			else {
				return false;
			}

		} catch (RemoteException e) {
			return false;
		}

	}
	
	public boolean enregistrerPhoto(int idPhoto) {
		
		try {       	
        	String[] result = ComplexData.getData(MainFrame.PBUY.getImage(idPhoto));	
        	
        	String id = result[0].replaceAll("\"", "");;
        	String typeImage = result[1];
        	String image = result[4];
        	  	
        	if (typeImage.contains("jpeg")) {
        		
        		File file = new File("photos/"+id+".jpg");
               	 try {
               		 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                   	 byte[] buffer = Base64.decode(image);
               		 bos.write(buffer);
               		 bos.close();
               		 
               		 return true;
					} catch (IOException e) {
					}
				
        	}
		} catch (RemoteException e) {
		}
		
		return false;
	}

	/**
	 * récupérer le prénom
	 * 
	 * @return le prénom du user
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * récupérer le nom
	 * 
	 * @return le nom du user
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * récupérer le surnom
	 * 
	 * @return le surnom du user
	 */
	public String getSurnom() {
		return surnom;
	}

	/**
	 * récupérer la photo... bon là, je sais pas trop comment on va gérer ça...
	 * Sid me parlait de chopper le code brute, et d'en faire un nouveau
	 * fichier...
	 */
	public String getPhoto() {
		return "photos/" + nomPhoto + ".jpg";
	}
}
