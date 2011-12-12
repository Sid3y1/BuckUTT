package gui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;
import org.apache.axis.encoding.Base64;


/**
 * Toutes les intéractions avec la BDD pour la gestion des users
 * @author reivax
 */
public class User {
	private int id;
    private String nom;
    private String prenom;
    private String surnom;
    private int idPhoto;
    private int solde;
    private Vector<Produit> produits;
    private String nomPhoto;
    private int id_meanOfPaiement;
    private int creditRechargement;
    
    public User() {
    	
    }
    public int setUser(String data, int mean_of_log) {
  
    	try {
    		int retour = MainFrame.PBUY.loadBuyer(data, mean_of_log, MainFrame.ip);
    		
    		if(PopupError.retourMethode(retour)) {    				    		
            	String[] result = ComplexData.getData(MainFrame.PBUY.getBuyerIdentity());	
            	
            	id = Integer.parseInt(result[0]);
            	prenom = result[1];
            	nom = result[2];
            	surnom = result[3];
            	idPhoto = Integer.parseInt(result[4]); //TODO quand on aura exporté les photos
            	//idPhoto = 8;
            	solde =  Integer.parseInt(result[5]);
            	
            	AfficheurDeporte.setNomBonjour(prenom + " " + nom);
            	
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
    		else {
    			//System.out.println(retour);
    		}
    		return retour;
		} catch (RemoteException e) {
			//System.out.println("bug wsdl");
			return 0;
		}
    }
    
    public Vector<Produit> getVectorPropositions() {    	
    	produits = new Vector<Produit>();

    	try {   		
    		String retourCSV = MainFrame.PBUY.getProposition();
    		
    		if (retourCSV.length() > 5) //au pif =) juste pour éviter que ça bug quand le mec a pas assez de tune pour un produit
    		{
	    		String[] result = ComplexData.getLines(retourCSV);	
	    		String[] result2 = null;
	    		
	    		if(result.length > 0) {
	        		for (int y=0; y<result.length; y++) {
	        			
	    				result2 = ComplexData.getData(result[y]);
	    				int obj_id  = Integer.parseInt(result2[0]);
	        			String obj_name = result2[1];
	        			String obj_type = result2[2];
	        			int img_id  = Integer.parseInt(result2[3]);
	        			int credit;
	        			try {
	        				credit  = Integer.parseInt(result2[4]);
						} catch (Exception e) {
							credit = 0;
						}	
	        			
						//FIXME corriger niveau serveur
						//if(credit < 0)
						//	credit = 0;
						
	        			Produit produit = new Produit(obj_id, obj_name, obj_type, credit, img_id);
	        			produits.add(produit);
	            	}
	    		}
    		}
		} catch (RemoteException e) {
		}

		return produits;
    }
    
    public boolean enregistrerPhoto(int idPhoto) {
		
		try {
        	String photo = MainFrame.PBUY.getImage(idPhoto);
        	
        	String[] result = photo.split(";");
        	String[] result2 = result[0].split(",");
        	
        	String id;
        	String image;
        	String typeImage;
        	
        	id = result2[0].replaceAll("\"", "");
        	typeImage = result2[1].replaceAll("\"", "");
        	image = result2[4].replaceAll("\"", "");
        	  	
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
    
    public void logout() {
    	/*try {
    		MainFrame.PBUY.
		} catch (RemoteException e) {
		}*/
    }
    
    /** récupérer le prénom
     * @return le prénom du user
     */
    public String getPrenom() {
    	return prenom;
    }
    
    /** récupérer le nom
     * @return le nom du user
     */
    public String getNom() {
    	return nom;
    }
    
    /** récupérer le surnom
     * @return le surnom du user
     */
    public String getSurnom() {
    	return surnom;
    }
    
    /** récupérer le solde
     * @return le solde du user (format xx.xx € plutot... pas en centimes) 
     */
    public String getSolde() {
    	Float var = new Float(solde)/100;
		
		DecimalFormat df = null;
		if(var%1 == 0)
			df = new DecimalFormat("#######0");
		else
			df = new DecimalFormat("#######0.00");
    	
        return df.format(var).toString()+" €";
    }
    
    /** récupérer le solde
     * @return le solde du user (format xx.xx € plutot... pas en centimes) 
     */
    public int getCredit() {
        return solde;
    }
    
    public String getPhoto() {
		return "photos/" + nomPhoto + ".jpg";
	}
    
    public void setMeanOfPaiement(int id_meanOfPaiement)
    {
    	this.id_meanOfPaiement = id_meanOfPaiement;
    }
    
    public void setRechargement(int rechargement)
    {
    	this.creditRechargement = rechargement;
    }
    
    public int validerRechargement()
    {
    	int etat = 400;
    	try {
			try {
				etat = MainFrame.PBUY.reload(creditRechargement, id_meanOfPaiement, "Peggy (" + java.net.InetAddress.getLocalHost().getHostName()  + ")");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AfficheurDeporte.validerRechargement();
			return etat;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return etat;
		}
    }
}
