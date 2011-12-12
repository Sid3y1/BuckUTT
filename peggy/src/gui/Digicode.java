package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 *
 * @author reivax
 */

/*
 * Digicode crée une interface pour mettre des codes chiffrés en tactile
 */
public class Digicode extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel fenetre;
    private JPanel panelDigicode;
    private JLabel ecranCode;
    private String code = "";
    boolean masquer = true; //cache ou pas le code
    boolean modeEuro = false; //genre, TPE avec la virgule
    
    /*
     * masquer (true / false) permet d'afficher à l'écran le code entré ou pas (remplacé alors par des ****)
    */
    public Digicode(boolean masquer) {
        this.masquer = masquer;
        fenetre = new JPanel();
        fenetre.setOpaque(false);
        fenetre.setSize(250, 370);
        fenetre.setLayout(null);
        
        JPanel afficheur = new JPanel();
        afficheur.setBounds(10, 5, 230, 30);
        afficheur.setBackground(Color.WHITE.darker());
        afficheur.setOpaque(true);
        
        ecranCode = new JLabel("");
        ecranCode.setBounds(10, 10, 230, 30);
        ecranCode.setFont(new Font("Arial", Font.BOLD, 22));
        ecranCode.setForeground(Color.BLACK);
        ecranCode.setHorizontalAlignment(0);
        
        fenetre.add(ecranCode);
        fenetre.add(afficheur);
        panelDigicode = new JPanel();
        panelDigicode.setOpaque(false);
        panelDigicode.setBounds(10, 40, 230, 320);
        panelDigicode.setLayout(new GridLayout(4, 3, 10, 10)); //GridLAyout pour les boutons du digicode      
        
        final boolean copieMasquer = masquer; //pas propre... mais seule façon trouvé pour envoyer la valeur de masquer au public void suivant
        JButton boutons[] = new JButton[10]; //on crée les boutons chiffrés
        for (int i=1; i<10; i++) {
            final int copie = i; //pareil, pour passer au public void
            //boutons[i].setLayout(null);
            boutons[i] = new JButton(new ImageIcon(getClass().getResource("/images/bouton"+i+".png")));
            boutons[i].setPreferredSize(new Dimension(70, 70));
            boutons[i].setFocusable(false);
            boutons[i].setFocusPainted(false);
            boutons[i].setBorderPainted(false);
            boutons[i].setContentAreaFilled(false);
            
            boutons[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	if(ecranCode.getText() == "INCORRECT") //si on était en mode erreur
                		setTextEcran("");
                	
                    if(copieMasquer == true) //si true, on remplace par des ***
                    	setTextEcran(ecranCode.getText()+"X");
                    else //sinon, on copie les chiffres
                    	setTextEcran(code+copie);
                    code = code+copie;
                }
            });
            panelDigicode.add(boutons[i]); //on ajoute le bouton
        }
        
        //on crée le bouton de remise à zéro
        JButton boutonRAZ = new JButton(new ImageIcon(getClass().getResource("/images/boutonAnnuler.png")));
        boutonRAZ.setPreferredSize(new Dimension(70, 70));
        boutonRAZ.setFocusable(false);
        boutonRAZ.setFocusPainted(false);
        boutonRAZ.setBorderPainted(false);
        boutonRAZ.setContentAreaFilled(false);
        boutonRAZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clearEcran();
            }
        });
        panelDigicode.add(boutonRAZ);
        
        //et le 0
        JButton bouton0 = new JButton(new ImageIcon(getClass().getResource("/images/bouton0.png")));
        bouton0.setPreferredSize(new Dimension(70, 70));
        bouton0.setFocusable(false);
        bouton0.setFocusPainted(false);
        bouton0.setBorderPainted(false);
        bouton0.setContentAreaFilled(false);
        bouton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(ecranCode.getText() == "INCORRECT") //si on était en mode erreur
            		setTextEcran("");
            	
                if(copieMasquer == true)
                		setTextEcran(ecranCode.getText()+"X");
                    else
                    	setTextEcran(code+"0");
                code = code+"0";
            }
        });
        panelDigicode.add(bouton0);
        
        fenetre.add(panelDigicode); //on ajoute le panel à la fenetre principale
    }
    
    /*
     * on envoie le bouton OK à cette méthode. Ca permet de gérer le listener dans la classe qui appelle digiocde... c'est plus easy
    */
    public void addBoutonOk(JButton boutonOk) {
    	
    	boutonOk.setIcon(new ImageIcon(getClass().getResource("/images/boutonOK.png"))); 
    	boutonOk.setPreferredSize(new Dimension(70, 70));
    	boutonOk.setFocusable(false);
    	boutonOk.setFocusPainted(false);
    	boutonOk.setBorderPainted(false);
    	boutonOk.setContentAreaFilled(false);
    	
        panelDigicode.add(boutonOk);
        panelDigicode.revalidate();
    }
    
    public void setModeEuro(boolean modeEuro) {
    	this.modeEuro = modeEuro;
    }
    
    private void setTextEcran(String texte) {
    	if(modeEuro && texte != "") {
    		float var = (float)Integer.parseInt(texte)/100;
    		DecimalFormat df = null;
    		df = new DecimalFormat("#######0.00");
    		ecranCode.setText(df.format(var).toString()+" €");
    	}		
    	else
    		ecranCode.setText(texte);
    }
    
    /*
     * pour récupérer le panel
    */
    public JPanel affiche() {
    	return fenetre;
    }
    
    /*
     * pour récupérer le code...
    */
    public String getCode() {
    	if (code == "")
    		code = "0000"; //évite les bugs si on envoie null
    	
    	return code;
    }
    
    /*
     * pour vider l'écran...
    */
    public void clearEcran() {
    	setTextEcran("");
    	code = "";
    }
    
    public void codeErreur() {
    	setTextEcran("INCORRECT");
    	code = "";
    }
}
