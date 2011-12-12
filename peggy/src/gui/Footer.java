package gui;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class Footer extends JPanel {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Footer(String point, String page) {
    	setLayout(null);
    	Color couleurParDefaut = new Color(255, 164, 209);
    	File file = new File("couleur");
		FileReader fr;
		try {
			String str;
			fr = new FileReader(file);
			str = "";
			int i = 0;
			//Lecture des données
			while((i = fr.read()) != -1)
				str += (char)i;	
			
			String[] result = str.split(",");
			setBackground(new Color(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2])));
		} catch (FileNotFoundException e) {
			setBackground(couleurParDefaut);
		} catch (IOException e) {
			setBackground(couleurParDefaut);
		}
		
		//add(addBoutonAide());
		
		JLabel nomPoint = new JLabel(point + " | " + MainFrame.versionPeggy);
		nomPoint.setForeground(Color.WHITE);
    	nomPoint.setBounds(10,5,125,25);
        add(nomPoint);
        
        if(page == "vente") {
        	JLabel nomVendeur = new JLabel("Connecté en tant que " + MainFrame.vendeur.getPrenom() + " " + MainFrame.vendeur.getNom());
        	nomVendeur.setForeground(Color.WHITE);
        	nomVendeur.setHorizontalTextPosition(SwingConstants.RIGHT);
        	nomVendeur.setHorizontalAlignment(SwingConstants.RIGHT);
        	nomVendeur.setBounds(MainFrame.width-405,5,400,25);
            add(nomVendeur);
        }
		
		JLabel copyright = new JLabel("Peggy pour BuckUTT. Remarques, bugs : buckutt@utt.fr");
    	copyright.setForeground(Color.WHITE);
    	copyright.setHorizontalTextPosition(SwingConstants.CENTER);
    	copyright.setHorizontalAlignment(SwingConstants.CENTER);
    	copyright.setBounds(200,5,MainFrame.width-400,25);
        add(copyright);
    }
    
    private JButton addBoutonAide() {
    	JButton boutonCouleur = new JButton(new ImageIcon(getClass().getResource("/images/aide-icone-20.png")));
    	boutonCouleur.setBounds(5, 5, 20, 20);
    	boutonCouleur.setFocusable(false);
    	boutonCouleur.setOpaque(false);
    	boutonCouleur.setBackground(Color.BLUE);
    	
    	boutonCouleur.setFocusPainted(false);
    	boutonCouleur.setBorderPainted(false);
    	boutonCouleur.setContentAreaFilled(false);

    	boutonCouleur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	//ouvrir fenetre aide
            	PageAide fenetreAide = new PageAide();
            	fenetreAide.setLocationRelativeTo(null);
            	fenetreAide.setVisible(true);
            }
        });
    	
    	return boutonCouleur;
    }  
}
