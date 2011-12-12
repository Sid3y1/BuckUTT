package gui;
/**
 * Classe MainFrame
 * Correspond à la fenêtre principale
 * Actuellement, elle est optimisée pour une taille d'écran eeeTop. Pour passer en plein écran, _
 * il suffit de passer la variable eeeTop à false (résultats non garantis ^^)
 * Actuellement, le badgage est géré en keyListener. C'est amené à changer...
 * Chaque fenêtre est composé d'un corps (content) et d'un footer. Seul le content change d'une page à l'autre.
 * Si on trouve des infos à mettre dans le footer en fonction des pages, ça peut être possible de le modifier _
 * en fonction des pages.
 * 
 * @author reivax
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.rmi.RemoteException;

import javax.swing.*;

//A COMMENTER POUR CHANGER DE SERVEUR PROD OU DEV
import org.dyndns.buckutt.server.PBUY_class_php.PBUYPortProxy;
//import _1._10._10._10.PBUY_class_php.PBUYPortProxy;







public class MainFrame extends JFrame implements KeyListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static int width; //largeur de la fenetre
    public static int height; //hauteur de l'écran
    private boolean eeeTop = true; //true pour dimension eeeTop, false pour s'adapter direct à l'écran... utile pour voir ce que ça rendrait sur un eeeTop pendant le développement
    private static JPanel content;
    private static JPanel footer;
    private static ConnexionPanel connexionPanel = new ConnexionPanel();
    private static VentePanel ventePanel;
    private static String fenetreActive;
    public static Vendeur vendeur;
    public static PBUYPortProxy PBUY = new PBUYPortProxy();
    public static int id_point;
    public static String nom_point;
    private String codeEtu = "";
    public static String ip = "";
    public static String versionPeggy = "DEV V2.1E";
    
    /** 
     * Création de la fenêtre principale et du footer.
     */
    public MainFrame() {
    	super();
    	
        if(eeeTop) {
            setSize(1366,768);
            setResizable(false); //On interdit la redimensionnement de la fenêtre
            width = getWidth();
            height = getHeight();
        }
        else {
            setResizable(true);
            setExtendedState(MAXIMIZED_BOTH); //marche si on autorise le resizable. utile si pas tjs des eeeTop
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            width = screen.width;
            height = screen.height;
        }
        
        setTitle("Buckutt");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
               
        content = new JPanel();
        content.setLayout(null);
        content.setBounds(0,0,width,height);
        
        add(content);
        java.net.InetAddress i = null;
		try {
			i = java.net.InetAddress.getLocalHost();
			ip = i.getHostName();
		}
		catch(Exception e){e.printStackTrace();}
		AfficheurDeporte.setBienvenue();
		

		
        addKeyListener(this);
        requestFocus();
        
        vendeur = new Vendeur(); //on crée un vendeur, qui sera ainsi commun à toutes les pages
        MainFrame.showConnexionPanel();
    }
    
    
    /**
     * Affiche la page de connexion 
     */
    public static void showConnexionPanel() {
    	
    	PBUY = new PBUYPortProxy();
    	updateIdPoint();
    	
    	content.setVisible(false);
    	content.removeAll();
    	
    	connexionPanel = new ConnexionPanel();
    	JPanel contenu = connexionPanel.ShowGUI();
    	contenu.setBounds(0,0,width,height-30);
    	content.add(contenu);
    	
    	footer = new Footer(nom_point, "connexion");
    	footer.setBounds(0,height-30,width, 30);
    	content.add(footer);

    	AfficheurDeporte.setFerme();
    	
    	content.revalidate();
    	content.setVisible(true);
    	
    	fenetreActive = "connexion";
    }
    
    /**
     * Affiche la page de vente
     * @param idSellet id du vendeur 
     */
    public static void showVentePanel(String idSeller) {
    	content.removeAll();
    	content.setVisible(false);
    	
    	ventePanel = new VentePanel();
    	JPanel contenu = ventePanel.ShowGUI();
    	contenu.setBounds(0,0,width,height-30);
    	content.add(contenu);
    	
    	footer = new Footer(nom_point, "vente");
    	footer.setBounds(0,height-30,width, 30);
    	content.add(footer);

    	content.revalidate();
    	content.setVisible(true);

    	fenetreActive = "vente";
    	
    	AfficheurDeporte.setOuvert();
    	
		//ThreadDelog cp1 = new ThreadDelog (100);
    	//cp1.start();
    }
    
    /**
     * Met à jour le id_point
     */
    public static void updateIdPoint() {	
    	File file = new File("id_point");
		FileReader fr;
		try {
			String str;
			fr = new FileReader(file);
			str = "";
			int i = 0;
			//Lecture des données
			while((i = fr.read()) != -1)
				str += (char)i;	
			
			str = str.replaceAll("\n", "");
			if(id_point != Integer.parseInt(str)) {
				id_point = Integer.parseInt(str);
				nom_point = PBUY.getPointName(id_point);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void keyPressed(KeyEvent evt){ } //obligé de la mettre
    public void keyReleased(KeyEvent evt){ }  //obligé de la mettre
    public void keyTyped(KeyEvent ev) { //là, c'est la méthode qu'on modifie pour faire genre quelqu'un a badgé...  	
    	char c = ev.getKeyChar();
        
        if (Character.getNumericValue(c) != -1) {
        	codeEtu += Character.getNumericValue(c);
        }
        
        if (Character.getNumericValue(c) == -1) {       	
            if(fenetreActive == "connexion") {
            	connexionPanel.hideBoutonModeManuel();
                connexionPanel.idEtu = codeEtu;
                connexionPanel.annuleUser();
                connexionPanel.updateInfoUser(4); //mean of log 4 => trame
            }
            else if(fenetreActive == "vente") {
            	try {
					System.out.println(PBUY.isLoadedSeller());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	ventePanel.setIdEtu(codeEtu);
            	ventePanel.updateInfoUser(4); //mean of log 4 => trame
            }
            codeEtu = "";
        }
    }

}
