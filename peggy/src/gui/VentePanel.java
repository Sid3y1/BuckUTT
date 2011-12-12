/*
voir pour tout passer en taille relative en fonction de la taille de la fenetre
*/

package gui;

import java.awt.*;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author reivax
 */
public class VentePanel extends ContentPanel {
    
	private static final long serialVersionUID = 1L;
	private JPanel infoUser = new JPanel();
	private JPanel blocGauche = new JPanel();
	public JPanel blocDroit = new JPanel();
	private boolean badgeReady = true; //permettra de bloquer le badgage si la ² n'est pas terminée
	public Panier panier = new Panier();
	private Vector<Produit> produits;
	private User user;
	private Vector<Color> couleurs = new Vector<Color>(); //couleurs des boutons produit
	private int memoIdCategorie = 0; //mémo de la dernière catégorie
	private Iterator<Color> itCouleur = null;
	private Color memoCouleur = null;
	private String modeAction = "vente"; //ou rechargement
	private String idEtu = null;
	private int memo_mean_of_log;
	//pour le rechargement
	private int sommeRecharge;
	private String moyenPaiement;
	
    public VentePanel() {
    	initVectorCouleur(); //le vecteur des couleurs des boutons de produits
    }
    
    public JPanel ShowGUI() {
    	setLayout(null);
        
        add(blocGauche());
        add(blocDroit());
        
        return(this);
    }
    
    private JPanel blocGauche() {
        blocGauche.setBounds(5, 5, 260, (MainFrame.height - 40));
        blocGauche.setLayout(null);
        blocGauche.setOpaque(false);
        //blocGauche.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        blocGauche.add(addBlocBoutonsBlocGauche());
        blocGauche.add(addInfoUser());
        
        if (MainFrame.vendeur.isModeManuel())
        	addBoutonModeManuel();
        
        return blocGauche;
    }
    
    private JPanel blocDroit() {
    	blocDroit.setBounds(270, 5, (MainFrame.width - 275), (MainFrame.height - 40));
    	blocDroit.setLayout(null);
    	blocDroit.setOpaque(false);
    	
    	return blocDroit;
    }
    
    private JPanel addBlocBoutonsBlocGauche() {
    	JPanel blocDeconnexion = new JPanel();
    	blocDeconnexion.setOpaque(false);
    	blocDeconnexion.setBounds(0, 0, 260, 64);
    	blocDeconnexion.setLayout(null);
    	
    	JButton boutonDeconnexion = new JButton(new ImageIcon(getClass().getResource("/images/sortie-icone.png")));
    	boutonDeconnexion.setBounds(5, 5, 48, 48);
    	boutonDeconnexion.setFocusable(false);
    	boutonDeconnexion.setOpaque(false);
    	boutonDeconnexion.setBackground(Color.BLUE);
    	boutonDeconnexion.setFocusPainted(false);
    	boutonDeconnexion.setBorderPainted(false);
    	boutonDeconnexion.setContentAreaFilled(false);	
    	boutonDeconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	//on vide le panier si en mode loggué
            	if (!badgeReady)
            		panier.viderPanier();
            	annuleUser();
            	MainFrame.showConnexionPanel();
            }
        });
    	
    	blocDeconnexion.add(boutonDeconnexion);
    	
    	final JButton boutonVendre = new JButton();
    	final JButton boutonRecharger = new JButton();
    	
    	if (MainFrame.vendeur.isSeller())
    	{
    		boutonVendre.setIcon(new ImageIcon(getClass().getResource("/images/panier-icone.png")));
    		boutonRecharger.setIcon(new ImageIcon(getClass().getResource("/images/argent-icone-16.png")));
    	}
    	else if (MainFrame.vendeur.isReloader())
    	{
    		boutonVendre.setIcon(new ImageIcon(getClass().getResource("/images/panier-icone-16.png")));
    		boutonRecharger.setIcon(new ImageIcon(getClass().getResource("/images/argent-icone.png")));
    		modeAction = "rechargement";
    	}
    	
    	boutonVendre.setBounds(124, 0, 64, 64);
    	boutonVendre.setFocusable(false);
    	boutonVendre.setOpaque(false);
    	boutonVendre.setBackground(Color.BLUE);
    	boutonVendre.setFocusPainted(false);
    	boutonVendre.setBorderPainted(false);
    	boutonVendre.setContentAreaFilled(false);

    	
    	boutonRecharger.setBounds(188, 0, 64, 64);
    	boutonRecharger.setFocusable(false);
    	boutonRecharger.setOpaque(false);
    	boutonRecharger.setBackground(Color.BLUE);
    	
    	boutonRecharger.setFocusPainted(false);
    	boutonRecharger.setBorderPainted(false);
    	boutonRecharger.setContentAreaFilled(false);

    	boutonVendre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	boutonVendre.setIcon(new ImageIcon(getClass().getResource("/images/panier-icone.png")));
            	boutonRecharger.setIcon(new ImageIcon(getClass().getResource("/images/argent-icone-16.png")));
            	
            	infoUser.setVisible(false);
            	infoUser.removeAll();
            	infoUser.setLayout(null);
            	viderBlocDroit();
            	
            	modeAction = "vente";
            	badgeReady = true;
            	updateInfoUser(memo_mean_of_log);
            	
            	updateBlocDroit();
            	panier.viderPanier();
            	//annuleUser();
            }
        });
    	
    	boutonRecharger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	boutonVendre.setIcon(new ImageIcon(getClass().getResource("/images/panier-icone-16.png")));
            	boutonRecharger.setIcon(new ImageIcon(getClass().getResource("/images/argent-icone.png")));
            	
            	infoUser.setVisible(false);
            	infoUser.removeAll();
            	infoUser.setLayout(null);
            	viderBlocDroit();
            	
            	modeAction = "rechargement";
            	badgeReady = true;
            	updateInfoUser(memo_mean_of_log);
            	
            	updateBlocDroit();
            	panier.viderPanier();
            	//annuleUser();
            }
        });
    	
    	if (MainFrame.vendeur.isSeller())
    		blocDeconnexion.add(boutonVendre);
    	if (MainFrame.vendeur.isReloader())
    		blocDeconnexion.add(boutonRecharger);
    	
    	return blocDeconnexion;
    }
    
    //JPanel où on met les infos sur les gens
    private JPanel addInfoUser() {
    	infoUser.setBounds(0, 65, 260, MainFrame.height - 105);
        infoUser.setOpaque(false);
        infoUser.setLayout(null);

        return infoUser;
    }
    
    private void addBoutonModeManuel() {
    	JButton addBoutonModeManuel = new JButton(new ImageIcon(getClass().getResource("/images/boutonManuel.png")));
    	addBoutonModeManuel.setBounds(10, 30, 240, 40);
    	addBoutonModeManuel.setFocusable(false);
    	addBoutonModeManuel.setFocusPainted(false);
    	addBoutonModeManuel.setBorderPainted(false);
    	addBoutonModeManuel.setContentAreaFilled(false);
    	addBoutonModeManuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	addDigicode();
            }
        });
        infoUser.add(addBoutonModeManuel);
    }
    
    public void annuleUser() {
        infoUser.setVisible(false);
        infoUser.removeAll();
        
    	idEtu = null;
    	
    	badgeReady = true;
    	AfficheurDeporte.setOuvert();
    	
    	if (MainFrame.vendeur.isModeManuel())
    		addBoutonModeManuel();
        
        infoUser.revalidate();
        infoUser.setVisible(true); 
    }
    
    public void updateInfoUser(int mean_of_log) { 	
    	memo_mean_of_log = mean_of_log;
    	if (badgeReady == true) {
	    	infoUser.setVisible(false);
	    	infoUser.removeAll();

	    	user = new User();
	    	
	    	if ((idEtu!=null) && (user.setUser(idEtu, mean_of_log) == 1)) {
		        JLabel photo = new JLabel(new ImageIcon(user.getPhoto()));
		        photo.setBounds(30, 5, 200, 250);
		        photo.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
		        photo.setAlignmentX(CENTER_ALIGNMENT);
		        
		        //si le nom prénom est méga long, on le met sur 2 lignes et on cut le surnom
		        if ((user.getPrenom() + " " + user.getNom()).length() > 45) {
		        	JLabel labelPrenom = new JLabel(user.getPrenom());
			        labelPrenom.setBounds(0, 255, 260, 30);
			        labelPrenom.setHorizontalAlignment(SwingConstants.CENTER);
			        labelPrenom.setVerticalAlignment(SwingConstants.TOP);
			        labelPrenom.setPreferredSize(new Dimension(250, 28));
			        labelPrenom.setFont(new Font("Arial", Font.BOLD, 20));
			        labelPrenom.setForeground(Color.BLACK);;
			        
			        JLabel labelNom = new JLabel(user.getNom());
			        labelNom.setBounds(0, 285, 260, 30);
			        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
			        labelNom.setVerticalAlignment(SwingConstants.CENTER);
			        labelNom.setPreferredSize(new Dimension(250, 28));
			        labelNom.setFont(new Font("Arial", Font.BOLD, 20));
			        labelNom.setForeground(Color.BLACK);
		        	
		        	infoUser.add(labelPrenom);
			        infoUser.add(labelNom);
		        }
		        else {
		        	JLabel labelNom = new JLabel(user.getPrenom() + " " + user.getNom());
		        	labelNom.setBounds(0, 255, 260, 30);
		        	labelNom.setHorizontalAlignment(SwingConstants.CENTER);
			        labelNom.setVerticalAlignment(SwingConstants.TOP);
			        labelNom.setPreferredSize(new Dimension(250, 28));
			        labelNom.setFont(new Font("Arial", Font.BOLD, 20));
			        labelNom.setForeground(Color.BLACK);;
			        
			        JLabel labelSurnom = new JLabel(user.getSurnom());
			        labelSurnom.setBounds(0, 285, 260, 30);
			        labelSurnom.setHorizontalAlignment(SwingConstants.CENTER);
			        labelSurnom.setVerticalAlignment(SwingConstants.CENTER);
			        labelSurnom.setPreferredSize(new Dimension(250, 28));
			        labelSurnom.setFont(new Font("Arial", Font.BOLD, 23));
			        labelSurnom.setForeground(Color.BLACK);
			        
			        infoUser.add(labelNom);
			        infoUser.add(labelSurnom);
		        }

		        JLabel labelSolde = new JLabel("Solde : " + user.getSolde());
		        labelSolde.setBounds(0, 310, 260, 35);
		        labelSolde.setHorizontalAlignment(SwingConstants.CENTER); //0 ça veut dire centré
		        labelSolde.setVerticalAlignment(SwingConstants.CENTER); //ça centre en haut...
		        labelSolde.setPreferredSize(new Dimension(250, 28));
		        labelSolde.setFont(new Font("Arial", Font.BOLD, 23));
		        labelSolde.setForeground(Color.BLACK);
		        
		        infoUser.add(photo);
		        infoUser.add(labelSolde);
		        
		      //  if (modeAction == "vente") {
			        JButton boutonAnnuler = new JButton(new ImageIcon(getClass().getResource("/images/boutonAnnulerInfoUser.png")));
			        boutonAnnuler.setBounds(20, 340, 105, 40);
			        boutonAnnuler.setFocusable(false);
			        boutonAnnuler.setFocusPainted(false);
			        boutonAnnuler.setBorderPainted(false);
			        boutonAnnuler.setContentAreaFilled(false);
			        boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
			            public void actionPerformed(java.awt.event.ActionEvent evt) { 
			            	panier.viderPanier();
			            	annuleUser();
			            	viderBlocDroit();
			            	badgeReady = true;
			            }
			        });
			        
			        JButton boutonViderPanier = new JButton(new ImageIcon(getClass().getResource("/images/boutonViderInfoUser.png")));
			        boutonViderPanier.setBounds(135, 340, 105, 40);
			        boutonViderPanier.setFocusable(false);
			        boutonViderPanier.setFocusPainted(false);
			        boutonViderPanier.setBorderPainted(false);
			        boutonViderPanier.setContentAreaFilled(false);
			        boutonViderPanier.addActionListener(new java.awt.event.ActionListener() {
			            public void actionPerformed(java.awt.event.ActionEvent evt) { 
			            	panier.viderPanier();
			            	updateBlocDroit();
			            }
			        });
			        
			        JButton boutonTerminer = new JButton(new ImageIcon(getClass().getResource("/images/boutonTerminer.png")));
			        boutonTerminer.setBounds(5, MainFrame.height-185, 250, 80);
			        boutonTerminer.setFocusable(false);
			        boutonTerminer.setFocusPainted(false);
			        boutonTerminer.setBorderPainted(false);
			        boutonTerminer.setContentAreaFilled(false);
			        boutonTerminer.addActionListener(new java.awt.event.ActionListener() {
			            public void actionPerformed(java.awt.event.ActionEvent evt) {
			            	
			            	try {
								int retour = MainFrame.PBUY.endTransaction();
								if(PopupError.retourMethode(retour))
								{	
					            	addDerniereAction(user.getPrenom() + " " + user.getNom() + " - achat pour " + panier.getPrixTotal());
					            	annuleUser();
					            	badgeReady = true;
								}
								
							} catch (RemoteException e) {
							}
			            	
							
			            }
			        });
			        
			        panier = new Panier();
			        panier.setCreditMax(user.getCredit());
			        
			        infoUser.add(boutonAnnuler);
			        infoUser.add(boutonViderPanier);
			        infoUser.add(boutonTerminer);
			        infoUser.add(panier.afficherPanier());
			        
		        /*} else if (modeAction == "rechargement") {
		        	JButton boutonAnnuler = new JButton(new ImageIcon(getClass().getResource("/images/boutonAnnulerInfoUser.png")));
			        boutonAnnuler.setBounds((260-105)/2, 340, 105, 40);
			        boutonAnnuler.setFocusable(false);
			        boutonAnnuler.setFocusPainted(false);
			        boutonAnnuler.setBorderPainted(false);
			        boutonAnnuler.setContentAreaFilled(false);
			        boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
			            public void actionPerformed(java.awt.event.ActionEvent evt) { 
			            	panier.viderPanier();
			            	annuleUser();
			            	viderBlocDroit();
			            	badgeReady = true;
			            }
			        });
			        
			        infoUser.add(boutonAnnuler);
		    	}*/	
		        
		        AfficheurDeporte.setNomBonjour(user.getPrenom() + " " + user.getNom());
		        AfficheurDeporte.setCredit(user.getCredit());
		        
		        badgeReady = false;
		        updateBlocDroit();  
	    	}
	    	else {
	    		/*JLabel labelInconnu = new JLabel("Identifiant inconnu");
	    		labelInconnu.setBounds(0, 305, 260, 30);
	    		labelInconnu.setHorizontalAlignment(SwingConstants.CENTER); //0 ça veut dire centré
	    		labelInconnu.setVerticalAlignment(SwingConstants.CENTER); //ça centre en haut...
	    		labelInconnu.setPreferredSize(new Dimension(250, 28));
	    		labelInconnu.setFont(new Font("Arial", Font.BOLD, 22));
	    		labelInconnu.setForeground(Color.BLACK);

	    		JButton boutonAnnuler = new JButton(new ImageIcon(getClass().getResource("/images/boutonAnnulerConnexion.png")));
		        boutonAnnuler.setBounds(10, 355, 240, 40);
		        boutonAnnuler.setFocusable(false);
		        boutonAnnuler.setFocusPainted(false);
		        boutonAnnuler.setBorderPainted(false);
		        boutonAnnuler.setContentAreaFilled(false);
		        boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) { 
		            	annuleUser();
		            	badgeReady = true;
		            }
		        });
		        
		        AfficheurDeporte.setInconnu();
	    		
		        infoUser.add(labelInconnu);
		        infoUser.add(boutonAnnuler);	
		        */
	    		annuleUser();
            	badgeReady = true;
	    	} 	
	    	infoUser.revalidate();
	    	infoUser.setVisible(true);
    	}
    }

    private void addDigicode() {
    	infoUser.setVisible(false);
    	infoUser.removeAll();
    	infoUser.setLayout(null);
        
    	JPanel blocDigicodeIdEtu = new JPanel();
        blocDigicodeIdEtu.setBounds(5, 25, 250, 370);
        blocDigicodeIdEtu.setLayout(null);
        blocDigicodeIdEtu.setBackground(Color.DARK_GRAY);
        blocDigicodeIdEtu.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
        
        final Digicode digicode = new Digicode(false); //false : afficher le code
        JButton boutonOk = new JButton();
        boutonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	idEtu = digicode.getCode();
            	memo_mean_of_log = 2;
                updateInfoUser(2); //2 => id etu
            }
        });
        digicode.addBoutonOk(boutonOk);
        blocDigicodeIdEtu.add(digicode.affiche());
        
        infoUser.add(blocDigicodeIdEtu);
        
        infoUser.revalidate();
        infoUser.setVisible(true);     	
    }
    
    //là on récupèrera les propositions
    private void updateBlocDroit() {
    	//viderBlocDroit();
    	if (badgeReady == false) { //ça veut dire que qqn est badgé
    		if (modeAction == "vente") {
    			produits = user.getVectorPropositions();
            	itCouleur = couleurs.listIterator(0); //on remet à 0 l'iterateur à chaque fois pour garder les mes couleurs
            	afficheProduits(1);
	    	} else if (modeAction == "rechargement") {
	    		pageRechargement(1);
	    	}	
    	}
    }
    
    private void viderBlocDroit() { 	
    	blocDroit.setVisible(false);
    	blocDroit.removeAll();
    	blocDroit.revalidate();
    	blocDroit.setVisible(true);
    }
    
    private void afficheProduits(final int page) {
    	blocDroit.setVisible(false);
    	blocDroit.removeAll();
    	
    	int lignesBoutons = 0;
    	if(page == 1)
    		lignesBoutons = (int)Math.ceil((float)(getDernierProduit(page) - getPremierProduit(page))/6);
    	else
    		lignesBoutons = (int)Math.ceil((float)(2+getDernierProduit(page) - getPremierProduit(page))/6);
    	
    	//en fonction du nombre de boutons, on redimensionne le cadre (la base : 6 lignes de 6 boutons)
    	blocDroit.setBounds(270, 5, (MainFrame.width - 275), (int) Math.ceil((MainFrame.height - 50)/6)*lignesBoutons);
    	blocDroit.setLayout(new GridLayout(0, 6, 10, 10));

    	if (page > 1) {
    		JButton bouton = new JButton("<html><div style=\"text-align:center\"> &lt;&lt; page "+ (page - 1) +"</div></html>");
            bouton.setFocusable(false);
            bouton.setFont(new Font("Arial", Font.BOLD, 22));
            bouton.setBackground(Color.WHITE);
            bouton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	afficheProduits(page - 1);
                }
            });
            blocDroit.add(bouton);
    	}

    	Iterator<Produit> it = produits.iterator();
    	int i_bouton = 1;
    	while(it.hasNext()) {
    		Object obj = it.next();
    		Produit produit = (Produit)obj;
    		if(produits.size() < 37) //si - de 37 boutons, 1 page
	    		addBoutonProduit(produit);   
    		else if(i_bouton >= getPremierProduit(page) && i_bouton <= getDernierProduit(page)) //sinon, on gère mieux =)
    				addBoutonProduit(produit);   
    		else if((produits.size() - getDernierProduit(page) == 1) && i_bouton == produits.size()) //sinon, on gère mieux =)
	    		addBoutonProduit(produit);   
    		i_bouton++;
    	}
    	
    	if(getDernierProduit(page) != produits.size() && (produits.size() - getDernierProduit(page) != 1))
    	{
    		JButton bouton = new JButton("<html><div style=\"text-align:center\">page "+ (page + 1) +" &gt;&gt; </div></html>");
            bouton.setFocusable(false);
            bouton.setFont(new Font("Arial", Font.BOLD, 22));
            bouton.setBackground(Color.WHITE);
            bouton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	afficheProduits(page + 1);
                }
            });
            blocDroit.add(bouton);
    	}
    	   	
		blocDroit.revalidate();
		blocDroit.setVisible(true);
    }
    
    private int getPremierProduit(int page) {
    	if (produits.size() < 37 || page == 1)
    		return 1;
    	else
    		return 36 + (page - 2)*34;
    }
    
    private int getDernierProduit(int page) {
    	if (produits.size() < 37)
    		return produits.size();
    	else if (produits.size() - 35 - (page-1)*34 > 0)
    		return 35 + (page-1)*34;
    	else
    		return produits.size();
    }
    
    private void addBoutonProduit(final Produit produit) {
     	JButton bouton = new JButton();
    	bouton.setLayout(null);
    	
    	JLabel nomProd = new JLabel("<html><center>"+produit.nom + "</center></html>");
		nomProd.setBounds(0, 0, 175, 80);
		nomProd.setLayout(null);
		nomProd.setFont(new Font("Arial", Font.BOLD, 20));
		nomProd.setHorizontalAlignment(SwingConstants.CENTER);
		nomProd.setHorizontalAlignment(SwingConstants.CENTER);
    	
		JLabel prixProd = new JLabel("<html><center>"+produit.getPrixTotal()+"</center></html>");
		prixProd.setBounds(0, 80, 175, 30);
		prixProd.setLayout(null);
		prixProd.setFont(new Font("Arial", Font.BOLD, 20));
		prixProd.setHorizontalTextPosition(SwingConstants.CENTER);
		prixProd.setHorizontalAlignment(SwingConstants.CENTER);
		
		bouton.add(nomProd);
		bouton.add(prixProd);
		
		bouton.setFocusable(false);
		//bouton.setFocusPainted(false);
        //bouton.setBorderPainted(false);
        //bouton.setContentAreaFilled(false);
		//bouton.setBackground(getCouleurProduit(produit.categorie));
		bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panier.ajouterArticle(produit);
                updateBlocDroit();
                AfficheurDeporte.addNewProduct(produit.nom, produit.prixtotal);
                AfficheurDeporte.setTotal(panier.prixTotal);
            }
        });
    	
        blocDroit.add(bouton);
    }
    
    private Color getCouleurProduit(int id_produit) {
    	if(memoIdCategorie != id_produit) {
    		memoIdCategorie = id_produit;
        	if(!itCouleur.hasNext()) {
        		itCouleur = couleurs.iterator();
        	}
        	Object obj2 = itCouleur.next();
        	memoCouleur = (Color)obj2;
    	}
    	
    	return memoCouleur;
    }
    
    private void initVectorCouleur() {
    	couleurs.add(new Color(181, 230, 29)); //vert pastel
    	couleurs.add(new Color(255, 160, 66)); //orange pastel
    	couleurs.add(new Color(128, 255, 255)); //cyan
    	couleurs.add(new Color(243, 196, 5)); //jaune fort
    	couleurs.add(new Color(255, 164, 209)); //rose clair
    	couleurs.add(new Color(106, 181, 181)); //bleu pastel
    	/*
    	couleurs.add(new Color(44, 154, 177)); //bleu clair  
    	couleurs.add(new Color(33, 47, 143)); //bleu foncé
    	couleurs.add(new Color(210, 43, 122)); //rose foncé
    	couleurs.add(new Color(255, 255, 128)); //jaune pastel
    	*/
    	
    	itCouleur = couleurs.iterator();	
    }
    
    private void pageRechargement(int step) {
    	//step 1 : mettre la somme
    	//step 2 : mettre le moyen de paiement
    	//step 3 : valider
    	    	
    	blocDroit.setVisible(false);
    	blocDroit.removeAll();
    	blocDroit.setBounds(270, 5, (MainFrame.width - 275), (MainFrame.height - 40));
    	blocDroit.setLayout(null);
    	
    	JLabel indication = new JLabel();
    	indication.setBounds(0, 50, (MainFrame.width - 275), 40);
    	indication.setLayout(null);
    	indication.setFont(new Font("Arial", Font.BOLD, 20));
    	indication.setForeground(Color.BLACK);
    	indication.setBackground(Color.WHITE);
    	indication.setHorizontalAlignment(SwingConstants.CENTER);
    	indication.setHorizontalAlignment(SwingConstants.CENTER);
    	
    	//A DECOMMENTER POUR ECOCUP
    	if (step == 1) {
    		indication.setText("Quelle est la somme à recharger ?");
    		addDigicodeRechargement();
    		
    		//partie ECOCUP
        	JButton boutonEcocup = new JButton("1 GOBELET");
        	boutonEcocup.setBounds(325, 550, 450, 75);
        	boutonEcocup.setLayout(null);
        	boutonEcocup.setFocusable(false);
        	boutonEcocup.setFont(new Font("Arial", Font.BOLD, 28));
        	boutonEcocup.setBackground(Color.LIGHT_GRAY);
        	boutonEcocup.setForeground(Color.BLACK);
        	boutonEcocup.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                	moyenPaiement = "Gobelet";
                	user.setMeanOfPaiement(6);
                	sommeRecharge = 100;
                	user.setRechargement(100);
                	pageRechargement(3);
                }
            });
        	blocDroit.add(boutonEcocup);
        	
        	JButton boutonEcocup2 = new JButton("2");
        	boutonEcocup2.setBounds(325, 630, 100, 75);
        	boutonEcocup2.setLayout(null);
        	boutonEcocup2.setFocusable(false);
        	boutonEcocup2.setFont(new Font("Arial", Font.BOLD, 28));
        	boutonEcocup2.setBackground(Color.LIGHT_GRAY);
        	boutonEcocup2.setForeground(Color.BLACK);
        	boutonEcocup2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                	moyenPaiement = "Gobelet";
                	user.setMeanOfPaiement(6);
                	sommeRecharge = 200;
                	user.setRechargement(200);
                	pageRechargement(3);
                }
            });
        	blocDroit.add(boutonEcocup2);
        	
        	JButton boutonEcocup3 = new JButton("3");
        	boutonEcocup3.setBounds(425, 630, 100, 75);
        	boutonEcocup3.setLayout(null);
        	boutonEcocup3.setFocusable(false);
        	boutonEcocup3.setFont(new Font("Arial", Font.BOLD, 28));
        	boutonEcocup3.setBackground(Color.LIGHT_GRAY);
        	boutonEcocup3.setForeground(Color.BLACK);
        	boutonEcocup3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                	moyenPaiement = "Gobelet";
                	user.setMeanOfPaiement(6);
                	sommeRecharge = 300;
                	user.setRechargement(300);
                	pageRechargement(3);
                }
            });
        	blocDroit.add(boutonEcocup3);
        	
        	JButton boutonEcocup4 = new JButton("4");
        	boutonEcocup4.setBounds(525, 630, 100, 75);
        	boutonEcocup4.setLayout(null);
        	boutonEcocup4.setFocusable(false);
        	boutonEcocup4.setFont(new Font("Arial", Font.BOLD, 28));
        	boutonEcocup4.setBackground(Color.LIGHT_GRAY);
        	boutonEcocup4.setForeground(Color.BLACK);
        	boutonEcocup4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                	moyenPaiement = "Gobelet";
                	user.setMeanOfPaiement(6);
                	sommeRecharge = 400;
                	user.setRechargement(400);
                	pageRechargement(3);
                }
            });
        	blocDroit.add(boutonEcocup4);
        	
        	JButton boutonEcocup5 = new JButton("5");
        	boutonEcocup5.setBounds(625, 630, 100, 75);
        	boutonEcocup5.setLayout(null);
        	boutonEcocup5.setFocusable(false);
        	boutonEcocup5.setFont(new Font("Arial", Font.BOLD, 28));
        	boutonEcocup5.setBackground(Color.LIGHT_GRAY);
        	boutonEcocup5.setForeground(Color.BLACK);
        	boutonEcocup5.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { 
                	moyenPaiement = "Gobelet";
                	user.setMeanOfPaiement(6);
                	sommeRecharge = 500;
                	user.setRechargement(500);
                	pageRechargement(3);
                }
            });
        	blocDroit.add(boutonEcocup5);
    		//fin partie ECOCUP
    		
    		
    		
    		
    	}
    	else if (step == 2) {
    		indication.setText("Quel est le moyen de paiement ?");
    		addChoixMoyenPaiement();
    	}
    	else if (step == 3) {
    		indication.setText("Recharger " + getSomme() + " en " + moyenPaiement + "?");
    		addValidationPaiement();
    		AfficheurDeporte.addRechargement(sommeRecharge);
    	}
    	
    	blocDroit.add(indication);
    	
    	blocDroit.revalidate();
		blocDroit.setVisible(true);
    }
    
    private void addDigicodeRechargement() {        
    	JPanel blocDigicodeRechargement = new JPanel();
    	blocDigicodeRechargement.setBounds((MainFrame.width-275-250)/2, 100, 250, 370);
    	blocDigicodeRechargement.setLayout(null);
    	blocDigicodeRechargement.setBackground(Color.DARK_GRAY);
    	blocDigicodeRechargement.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
        
        final Digicode digicode = new Digicode(false); //false : afficher le code
        digicode.setModeEuro(true);
        JButton boutonOk = new JButton();
        boutonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	sommeRecharge = Integer.parseInt(digicode.getCode());
            	user.setRechargement(sommeRecharge);
            	pageRechargement(2);
            }
        });
        digicode.addBoutonOk(boutonOk);
        blocDigicodeRechargement.add(digicode.affiche());
        
        blocDroit.add(blocDigicodeRechargement);	
    }
	
    private void addChoixMoyenPaiement() {        
    	JPanel blocChoixPaiement = new JPanel();
    	blocChoixPaiement.setBounds((MainFrame.width-275-250)/2, 100, 250, 370);
    	blocChoixPaiement.setLayout(null);
    	blocChoixPaiement.setOpaque(false);
        
    	blocChoixPaiement.setLayout(new GridLayout(0, 1, 10, 10));
    	
    	JButton choixEspece = new JButton("Espèces");
    	choixEspece.setFocusable(false);
    	choixEspece.setFont(new Font("Arial", Font.BOLD, 20));
    	choixEspece.setBackground(Color.DARK_GRAY);
    	choixEspece.setForeground(Color.WHITE);
    	choixEspece.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	moyenPaiement = "espèces";
            	user.setMeanOfPaiement(2);
            	pageRechargement(3);
            }
        });
    	
    	JButton choixCheque = new JButton("Chèque");
    	choixCheque.setFocusable(false);
    	choixCheque.setFont(new Font("Arial", Font.BOLD, 20));
    	choixCheque.setBackground(Color.DARK_GRAY);
    	choixCheque.setForeground(Color.WHITE);
    	choixCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	moyenPaiement = "chèque";
            	user.setMeanOfPaiement(4);
            	pageRechargement(3);
            }
        });
    	
    	JButton choixCB = new JButton("Carte bancaire");
    	choixCB.setFocusable(false);
    	choixCB.setFont(new Font("Arial", Font.BOLD, 20));
    	choixCB.setBackground(Color.DARK_GRAY);
    	choixCB.setForeground(Color.WHITE);
    	choixCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	moyenPaiement = "carte bancaire";
            	user.setMeanOfPaiement(1);
            	pageRechargement(3);
            }
        });
    	
        blocChoixPaiement.add(choixEspece);
        blocChoixPaiement.add(choixCheque);
        blocChoixPaiement.add(choixCB);
    	
        blocDroit.add(blocChoixPaiement);
    }
    
    private void addValidationPaiement() {        
    	JPanel blocValidationPaiement = new JPanel();
    	blocValidationPaiement.setBounds((MainFrame.width-275-370)/2, 100, 370, 280);
    	blocValidationPaiement.setLayout(null);
    	blocValidationPaiement.setOpaque(false);
        
    	blocValidationPaiement.setLayout(new GridLayout(0, 2, 10, 10));
    	
    	JButton annuler = new JButton(new ImageIcon(getClass().getResource("/images/annulerPaiement.png")));
    	annuler.setFocusable(false);
    	annuler.setFocusPainted(false);
    	annuler.setBorderPainted(false);
    	annuler.setContentAreaFilled(false);
    	annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	pageRechargement(1);
            }
        });
    	
    	JButton valider = new JButton(new ImageIcon(getClass().getResource("/images/validerPaiement.png")));
    	valider.setFocusable(false);
    	valider.setFocusable(false);
    	valider.setFocusPainted(false);
    	valider.setBorderPainted(false);
    	valider.setContentAreaFilled(false);
    	valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	
            	//TODO Gérer le ça marche pas
            	if (PopupError.retourMethode(user.validerRechargement()))
				{
                	if (MainFrame.vendeur.isSeller())
                	{
                		modeAction = "vente";
                		blocGauche.setVisible(false);
                		blocGauche.removeAll();
                		blocGauche.setVisible(true);
                    	blocGauche.setLayout(null);
                		blocGauche.setBounds(5, 5, 260, (MainFrame.height - 40));
                        blocGauche.setLayout(null);
                        blocGauche.setOpaque(false);
                        //blocGauche.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
                        
                        blocGauche.add(addBlocBoutonsBlocGauche());
                        blocGauche.add(addInfoUser());
                        
                        if (MainFrame.vendeur.isModeManuel())
                        	addBoutonModeManuel();
                		
                		infoUser.setVisible(false);
                    	infoUser.removeAll();
                    	infoUser.setLayout(null);
                    	viderBlocDroit();
                    	
                    	
                    	badgeReady = true;
                    	updateInfoUser(memo_mean_of_log);
                    	
                    	updateBlocDroit();
                    	panier.viderPanier();        		
                	}
                	else
                	{
                		addDerniereAction(user.getPrenom() + " " + user.getNom() + " - rechargement de " + getSomme());   
                		annuleUser();
                		badgeReady = true;
                
                	}
				}
            	

            }
        });
    	
    	blocValidationPaiement.add(annuler);
    	blocValidationPaiement.add(valider);
    	
        blocDroit.add(blocValidationPaiement);
    }
    
    private String getSomme() {
    	Float var = new Float(sommeRecharge)/100;
		
		DecimalFormat df = null;
		if(var%1 == 0)
			df = new DecimalFormat("#######0");
		else
			df = new DecimalFormat("#######0.00");
    	
        return df.format(var).toString()+" €";	
	}
    
    public void setIdEtu(String idEtu) {
    	this.idEtu = idEtu;
    }
    
    private void addDerniereAction(String texte) {
    	blocDroit.setVisible(false);
    	blocDroit.removeAll();
    	blocDroit.setBounds(270, 100, (MainFrame.width - 275), (MainFrame.height - 40));
    	blocDroit.setLayout(null);
    	
    	JLabel message = new JLabel("Dernière action : " + texte);
    	message.setBounds(30, 0, MainFrame.width-305, 50);
    	message.setHorizontalAlignment(SwingConstants.LEFT);
    	message.setFont(new Font("Arial", Font.BOLD, 20));
    	message.setForeground(Color.BLACK);
        
    	blocDroit.add(message);
    	
    	blocDroit.revalidate();
    	blocDroit.setVisible(true);	
    }
}
