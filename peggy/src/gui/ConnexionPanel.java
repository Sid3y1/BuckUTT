package gui;
/**
 * Classe ConnexionPanel
 * Correspond à la page de connexion à l'interface de vente
 * 
 * 
 * @author reivax
 */
import java.awt.*;
import javax.swing.*;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;
import java.io.*;

public class ConnexionPanel extends ContentPanel {
	private static final long serialVersionUID = 1L;
	private JPanel blocGauche = new JPanel();
	private JPanel blocDroit = new JPanel();
	private JPanel blocAide = new JPanel();
	private JPanel infoUser = new JPanel(); //infoUser à gauche
	private JPanel blocDigicode = new JPanel(); //bloc digicode à droite
    private JButton boutonModeManuel;
    public String idEtu="";
    private int mean_of_log;
    final Digicode digicodeMDP = new Digicode(true); //true, pour remplacer les chiffres par des étoiles dans l'afficheur... on le place ici pour pouvoir le vider dans showDigicode
    
    public ConnexionPanel() {
    }
    
    public JPanel ShowGUI() {
        setLayout(null);

        blocGauche.setBounds(0, 25, MainFrame.width/2, (MainFrame.height - 50));
        blocGauche.setOpaque(false);
        blocGauche.setLayout(null);
        
        blocDroit.setBounds(MainFrame.width/2, 25, MainFrame.width/2, (MainFrame.height - 50));
        blocDroit.setOpaque(false);
        blocDroit.setLayout(null);
        
        blocAide.setBounds(0, 25, MainFrame.width, 50);
        blocAide.setOpaque(false);
        blocAide.setLayout(null);
        
        blocDigicode.setBounds((MainFrame.width/2-250)/2, (MainFrame.height/2 - 225), 250, 370);
        blocDigicode.setLayout(null);
        
        //add(addBoutonModeManuel());
        add(addInfoUser()); //JPanel où on update les infos user
        
        add(addBoutonBonus()); //bouton pour les Easter eggs
        add(addBoutonCouleur()); //bouton choix de la couleur

        blocDroit.add(addDigicode()); //bloc digicode
        
        add(blocGauche);
        add(blocDroit);
        add(blocAide);
        
        JLabelAide(1);
        
        return(this);
    }
    
    private JButton addBoutonModeManuel() {      
    	boutonModeManuel = new JButton(new ImageIcon(getClass().getResource("/images/boutonManuelConnexion.png")));
    	boutonModeManuel.setBounds((MainFrame.width/2-300)/2, MainFrame.height - 120, 300, 70);
    	boutonModeManuel.setFocusable(false);
    	boutonModeManuel.setFocusPainted(false);
    	boutonModeManuel.setBorderPainted(false);
    	boutonModeManuel.setContentAreaFilled(false);
    	boutonModeManuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 
            	
            	//PopupError.retourMethode(401);
            	
            	
            	hideBoutonModeManuel();
                annuleUser(); //éventuellement, si on est en double badge manuel...
            	addIdEtuManuel();
            	
            }
        });
        
        return boutonModeManuel;
    }
    
    
    public void hideBoutonModeManuel() {
    	//boutonModeManuel.setVisible(false);
    }
    
    private void showBoutonModeManuel() {
    	//boutonModeManuel.setVisible(true);
    }
    
    private JPanel addInfoUser() {
    	infoUser.setLayout(null);
        infoUser.setBounds(0, 125, MainFrame.width/2, MainFrame.height - 200);
        infoUser.setOpaque(false);
        
        return infoUser;
    }
    
    public void updateInfoUser(int mean_of_log) {
    	infoUser.removeAll();
    	this.mean_of_log = mean_of_log;
    	
    	int retour = MainFrame.vendeur.SetVendeur(idEtu, mean_of_log);
    	if(retour == 1) { //si l'id existe
            JLabel photo = new JLabel(new ImageIcon(MainFrame.vendeur.getPhoto()));
	        photo.setBounds((MainFrame.width/2-200)/2, (MainFrame.height/2 - 350), 200, 250);
	        photo.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
	        photo.setAlignmentX(CENTER_ALIGNMENT);
             
            JLabel labelNom = new JLabel(MainFrame.vendeur.getPrenom() + " " + MainFrame.vendeur.getNom());
            labelNom.setBounds((MainFrame.width/2-500)/2, (MainFrame.height/2 - 60), 500, 40);
            labelNom.setHorizontalAlignment(0); //0 ça veut dire centré
            labelNom.setPreferredSize(new Dimension(500, 40));
            labelNom.setFont(new Font("Arial", Font.BOLD, 26));
            labelNom.setForeground(Color.BLACK);
            
            JLabel labelSurnom = new JLabel(MainFrame.vendeur.getSurnom());
            labelSurnom.setBounds((MainFrame.width/2-500)/2, (MainFrame.height/2 - 20), 500, 40);
            labelSurnom.setHorizontalAlignment(0); //0 ça veut dire centré
            labelSurnom.setPreferredSize(new Dimension(500, 40));
            labelSurnom.setFont(new Font("Arial", Font.BOLD, 26));
            labelSurnom.setForeground(Color.BLACK);

            infoUser.add(photo);
            infoUser.add(labelNom);
            infoUser.add(labelSurnom);
            
            JLabelAide(2);
            showDigicode();
            
            
            JButton boutonAnnuler = new JButton(new ImageIcon(getClass().getResource("/images/boutonAnnulerConnexion.png")));
            boutonAnnuler.setBounds((MainFrame.width/2-240)/2, (MainFrame.height/2 + 20), 240, 40);
            boutonAnnuler.setFocusable(false);
            boutonAnnuler.setFocusPainted(false);
            boutonAnnuler.setBorderPainted(false);
            boutonAnnuler.setContentAreaFilled(false);
            boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                    annuleUser();
                    showBoutonModeManuel();
                    JLabelAide(1);
                }
            });
            infoUser.add(boutonAnnuler);
            
            infoUser.revalidate();
            infoUser.setVisible(true);
            
    	}
    	else { //si l'id existe pas
    		//JLabel labelErreur = new JLabel("Identifiant inconnu");
        	//labelErreur.setBounds((MainFrame.width/2-500)/2, (MainFrame.height/2 - 60), 500, 40);
        	//labelErreur.setHorizontalAlignment(0); //0 ça veut dire centré
        	//labelErreur.setPreferredSize(new Dimension(500, 40));
        	//labelErreur.setFont(new Font("Arial", Font.BOLD, 26));
        	//labelErreur.setForeground(Color.BLACK);
        	
            //infoUser.add(labelErreur);
    		annuleUser();
            showBoutonModeManuel();
            JLabelAide(1);
    	}
    	
    	
        
    }
    
    //pour entrer manuellement l'id etu
    public void addIdEtuManuel() {
        infoUser.setVisible(true);
        infoUser.removeAll();
        
        JPanel blocDigicodeIdEtu = new JPanel();
        blocDigicodeIdEtu.setLayout(null);
        blocDigicodeIdEtu.setBounds((MainFrame.width/2-250)/2, (MainFrame.height/2 - 350), 250, 370);
        blocDigicodeIdEtu.setBackground(Color.DARK_GRAY);
        blocDigicodeIdEtu.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
        
        final Digicode digicode = new Digicode(false); //false : afficher le code
        JButton boutonOk = new JButton();
        boutonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idEtu = digicode.getCode();
                infoUser.setVisible(false);
                updateInfoUser(2); //mean of log = 2 => id etu
            }
        });
        
        digicode.addBoutonOk(boutonOk);
        blocDigicodeIdEtu.add(digicode.affiche());
        
        infoUser.add(blocDigicodeIdEtu);
        infoUser.revalidate();
    }
    
    public void annuleUser() {
        infoUser.setVisible(false);
        blocDigicode.setVisible(false);
    }
    
    private void showDigicode() {
    	blocDigicode.setVisible(false);
    	blocDigicode.removeAll();
    	blocDigicode.setLayout(null);
    	blocDigicode.setBackground(Color.DARK_GRAY);
    	blocDigicode.setOpaque(true);
    	
    	digicodeMDP.clearEcran(); //si un mec avait tenté de se connecter avant, il faut vider l'écran pour pas avoir le "incorrect"
    	blocDigicode.add(digicodeMDP.affiche());
    	
    	blocDigicode.revalidate();
    	blocDigicode.setVisible(true);
    	
    }
    
    //digicode pour code PIN
    private JPanel addDigicode() {
    	blocDigicode.setVisible(false);
    	blocDigicode.setLayout(null);
    	blocDigicode.setBackground(Color.DARK_GRAY);
    	
        JButton boutonOk = new JButton();
        boutonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (MainFrame.vendeur.validConnexion(digicodeMDP.getCode(), mean_of_log)) {
            		annuleUser();
            		digicodeMDP.clearEcran();
            		if (MainFrame.vendeur.isPointMan()) {
            			JLabelAide(3);
            			addChoixAdmin();
            		}
            		else {
            			MainFrame.showVentePanel(idEtu);
            		}
            	}
            	else {	
            		digicodeMDP.codeErreur();
            	}
            }
        });
        digicodeMDP.addBoutonOk(boutonOk);
        blocDigicode.add(digicodeMDP.affiche());
        
        return blocDigicode;
    }
    
    //Boutons pour choisir les options si on est admin
    private void addChoixAdmin() {
    	blocDigicode.setVisible(false);
    	blocDigicode.removeAll();
    	blocDigicode.setOpaque(false);
    	
    	blocDigicode.setLayout(new GridLayout(0, 1, 10, 10));
    	
    	JButton boutonPointDeVente = new JButton("Vendre");
    	boutonPointDeVente.setFocusable(false);
    	boutonPointDeVente.setFont(new Font("Arial", Font.BOLD, 20));
    	boutonPointDeVente.setBackground(Color.DARK_GRAY);
    	boutonPointDeVente.setForeground(Color.WHITE);
    	boutonPointDeVente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	MainFrame.showVentePanel(idEtu);
            }
        });
    	
    	JButton boutonChoixPoint = new JButton("Choix du Point");
    	boutonChoixPoint.setFocusable(false);
    	boutonChoixPoint.setFont(new Font("Arial", Font.BOLD, 20));
    	boutonChoixPoint.setBackground(Color.DARK_GRAY);
    	boutonChoixPoint.setForeground(Color.WHITE);
        boutonChoixPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	addChoixPoint();
            }
        });
    	
        blocDigicode.add(boutonPointDeVente);
        blocDigicode.add(boutonChoixPoint);
        
    	blocDigicode.setVisible(true);
    }
    
    //menu pour changer le point
    private void addChoixPoint() {
    	blocDigicode.setVisible(false);
    	blocDigicode.removeAll();
    	blocDigicode.setOpaque(false);
    	
    	blocDigicode.setLayout(new GridLayout(0, 2, 10, 10));
    	
    	try {
			//on récupère la liste des points
    		String csvPoints = MainFrame.PBUY.getAllPoints().replaceAll("\n", "");;
			
    		if(csvPoints.contains(";")) {
    			String[] result = csvPoints.split(";");
        		
        		for (int y=0; y<result.length; y++) {
        			String[] result2 = null;
    				result2 = result[y].split(",");
        			
    				final int idPoint = Integer.parseInt(result2[0].replaceAll("\"", ""));
    				String nomPoint = result2[1].replaceAll("\"", "");
    				
        			//pour chaque point, on crée un bouton qu'on ajoute
    				JButton boutonPoint = new JButton(nomPoint);
    				boutonPoint.setFocusable(false);
    				boutonPoint.setFont(new Font("Arial", Font.BOLD, 15));
    				boutonPoint.setBackground(Color.DARK_GRAY);
    				boutonPoint.setForeground(Color.WHITE);
    				boutonPoint.addActionListener(new java.awt.event.ActionListener() {
    		            public void actionPerformed(java.awt.event.ActionEvent evt) { 
    		            	changementPoint(idPoint);
    		            	MainFrame.showConnexionPanel(); //on recharge le page pour prendre en compte le changement
    		            }
    		        });
    		        blocDigicode.add(boutonPoint);
            	}
    		}
		} catch (RemoteException e) {
			//System.out.println("le csv ne renvoit pas de points");
		}
		
    	blocDigicode.setVisible(true);
    }
    
    private void addChangementCouleur() {
    	blocDigicode.setVisible(false);
    	blocDigicode.removeAll();
    	blocDigicode.setOpaque(false);
    	blocDigicode.setLayout(new GridLayout(0, 3, 10, 10));
    	
    	//création vecteur couleur : les couleurs sont choisies par Maxou au cas où vous trouvez ça moche
    	Vector<String> couleurs = new Vector<String>(); //couleurs des boutons produit
    	couleurs.add("67,163,88"); //vert pastel
    	couleurs.add("84,81,149"); //violet
    	couleurs.add("128,255,255"); //cyan
    	couleurs.add("255,127,39"); //orange mat
    	couleurs.add("255,164,209"); //rose clair
    	couleurs.add("106,181,181"); //bleu pastel
    	couleurs.add("167,63,94"); //rose framboise
    	couleurs.add("117,62,67"); //bordeaux
    	couleurs.add("179,255,179"); //vert fade
    	couleurs.add("255,170,85"); //orange fade
    	couleurs.add("255,64,64"); //rouge
    	couleurs.add("136,132,134"); //gris
    	//couleurs.add("0,0,0"); //mode nuit
    	//couleurs.add("128,64,0"); //mode caca
    	
    	Iterator<String> itCouleur = couleurs.iterator();
    	
    	while(itCouleur.hasNext()) {
    		Object obj = itCouleur.next();
    		String couleur = (String)obj;
    		final String nomCouleur = couleur.toString();
    		JButton boutonPoint = new JButton();
			boutonPoint.setFocusable(false);
			boutonPoint.setFont(new Font("Arial", Font.BOLD, 15));
			String[] result = nomCouleur.split(","); //on choppe le code RVB de chaque couleur
			boutonPoint.setBackground(new Color(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2])));
			boutonPoint.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	File file = new File("couleur"); //on l'écrit dans un fichier pour le mémoriser
	        		FileWriter fw;
	        		try {
	        			fw = new FileWriter(file);
	        			String str = nomCouleur;
	        			fw.write(str);
	        			fw.close();
	        		} catch (FileNotFoundException e) {
	        			System.out.println("problème au niveau du fichier couleur");
	        		} catch (IOException e) {
	        			System.out.println("problème au niveau du fichier couleur");
	        		}
	            	MainFrame.showConnexionPanel();
	            }
	        });
			blocDigicode.add(boutonPoint); //on ajoute chaque bouton dans la grid
    	}
    	blocDigicode.revalidate();
    	blocDigicode.setVisible(true);
    }
    
    //changement du point
    private void changementPoint(int idPoint) {
    	File file = new File("id_point");		
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			String str = ""+idPoint;
			fw.write(str);
			fw.close();
		} catch (FileNotFoundException e) {
			System.out.println("problème au niveau du fichier point");
		} catch (IOException e) {
			System.out.println("problème au niveau du fichier point");
		}
    }
    
    //on ajoute un bouton pour choisir la couleur
    private JButton addBoutonCouleur() {
    	JButton boutonCouleur = new JButton(new ImageIcon(getClass().getResource("/images/couleur-icone.png")));
    	boutonCouleur.setBounds(MainFrame.width-64, MainFrame.height-100, 64, 64);
    	boutonCouleur.setFocusable(false);
    	boutonCouleur.setOpaque(false);
    	boutonCouleur.setFocusPainted(false);
    	boutonCouleur.setBorderPainted(false);
    	boutonCouleur.setContentAreaFilled(false);
    	boutonCouleur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	addChangementCouleur();
            }
        });
    	
    	return boutonCouleur;
    }
    
    //on cache le bouton bonus pour les easter eggs
    private JButton addBoutonBonus() {
    	JButton boutonBonus = new JButton();
    	boutonBonus.setBounds(MainFrame.width/2 - 25, MainFrame.height - 100, 50, 50);
    	boutonBonus.setFocusable(false);
    	boutonBonus.setOpaque(false);    	
    	boutonBonus.setFocusPainted(false);
    	boutonBonus.setBorderPainted(false);
    	boutonBonus.setContentAreaFilled(false);
    	boutonBonus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	addDigicodeBonus();
            	hideBoutonModeManuel();
            }
        });
    	
    	return boutonBonus; 
    }
    
    //on met le digicode caché pour les easter eggs
    private void addDigicodeBonus() {
    	 infoUser.setVisible(false);
         infoUser.removeAll();
         
         JPanel blocDigicodeIdEtu = new JPanel();
         blocDigicodeIdEtu.setLayout(null);
         blocDigicodeIdEtu.setBounds((MainFrame.width/2-250)/2, (MainFrame.height/2 - 350), 250, 370);
         blocDigicodeIdEtu.setBackground(Color.DARK_GRAY);
         blocDigicodeIdEtu.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
         
         final Digicode digicode = new Digicode(true); //false : afficher le code
         JButton boutonOk = new JButton();
         boutonOk.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 showBoutonModeManuel();
                 String code = digicode.getCode();
                 //chaque jeu est dédicacé à un développeur buckutt =) mais qui ?
            	 if (code.equals("110886")) {
                	Buck1 buck = new Buck1();
             		buck.setLocationRelativeTo(null);
             		buck.setVisible(true);
                 }
            	 else if (code.equals("101289")) {
                 	Buck2 buck = new Buck2();
              		buck.setLocationRelativeTo(null);
              		buck.setVisible(true);
                 }
            	 else if (code.equals("180887")) {
                  	Buck3 buck = new Buck3();
               		buck.setLocationRelativeTo(null);
               		buck.setVisible(true);
                  }
            	 annuleUser();
             }
         });
         digicode.addBoutonOk(boutonOk);
         blocDigicodeIdEtu.add(digicode.affiche());
         infoUser.add(blocDigicodeIdEtu);
         infoUser.revalidate();
         infoUser.setVisible(true);
    }
    
    private void JLabelAide(int step) {
    	blocAide.setVisible(false);
    	blocAide.removeAll();
    	
    	JLabel messageAide = new JLabel();
    	
    	if (step == 1)
    		messageAide.setText("Pour commencer, badge ta carte étu");
    	else if (step == 2)
    		messageAide.setText("Salut " + MainFrame.vendeur.getPrenom() + ", entre ton code PIN");
    	else if (step == 3)
    		messageAide.setText("C'est à quel sujet ?");
    	
    	messageAide.setBounds(0, 0, MainFrame.width, 50);
    	messageAide.setHorizontalAlignment(0);
    	messageAide.setFont(new Font("Arial", Font.BOLD, 20));
    	messageAide.setForeground(Color.BLACK);
        
    	blocAide.add(messageAide);
    	
    	blocAide.revalidate();
    	blocAide.setVisible(true);	
    }
}
