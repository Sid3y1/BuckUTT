package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.FeatureDescriptor;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Buck3 extends JFrame {
	
	private JButton[] pouet = new JButton[100];
	private JPanel jeu = new JPanel();
	private boolean joueur = true;
	private JLabel aQui = new JLabel();
	private int tailleBouton = 50;
	private int nombreClic = 10;
	private int compteurClic;
	private int width;
	private int height;
	private int tps_depart = 0;
	private int tps_arrivee = 0;
	private JLabel labelPoints = new JLabel();
	private boolean clic = false;
	
	public Buck3() {
		super();
		
		setSize(MainFrame.width,MainFrame.height);
        setResizable(false);
        setTitle("Buckutt Popup");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        compteurClic = nombreClic;
        
        add(addPanel());
        
	}
	
	public JPanel addPanel() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(null);
		fenetre.setBounds(MainFrame.width, MainFrame.height, 0, 0);
		fenetre.setBackground(Color.GRAY);	
		
		jeu.setBounds(150, 0, MainFrame.width-150, MainFrame.height);
		jeu.setOpaque(false);
		jeu.setLayout(null);
		
		updateJeu();
		
		fenetre.add(jeu);
		
		fenetre.add(boutonFermeture());
		fenetre.add(boutonNew());
		fenetre.add(labelPoint());
		
		return fenetre;
	}
	
	private void updateJeu() {
		if(compteurClic == nombreClic) {			
			Date dateJeu = new Date();
			tps_depart = (int)dateJeu.getTime();
			viderLabel();
		}
		
		if(clic) {
        	compteurClic--;
        	clic = false;
		}
		
		
		
		if(compteurClic <= 0){
			jeu_fini();
		} 
		else {
			jeu.setVisible(false);
			jeu.removeAll();

			width = (int)(Math.random() * (MainFrame.width-150-tailleBouton));
			height = (int)(Math.random() * (MainFrame.height-tailleBouton));
			
			pouet[compteurClic] = new JButton();
			pouet[compteurClic].setBounds(width, height, tailleBouton, tailleBouton);
			pouet[compteurClic].setPreferredSize(new Dimension(50, 50));
			pouet[compteurClic].setBackground(Color.BLUE);
			pouet[compteurClic].addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	clic=true;
	            	updateJeu();
	            }
	        });
			
			jeu.add(pouet[compteurClic]);
			
			jeu.revalidate();
			jeu.setVisible(true);
		}
	}
	
	private JLabel labelPoint() {
		labelPoints.setText("Quand tu veux");
		labelPoints.setBounds(0, 300, 150, 50);
		
		return labelPoints;
	}
	
	private void viderLabel() {
		labelPoints.setText("");
		labelPoints.revalidate();
	}
	
	private void jeu_fini() {
		jeu.setVisible(false);
		jeu.removeAll();
		
		Date dateFin = new Date();
		tps_arrivee = (int) dateFin.getTime();
		
		labelPoints.setText((tps_arrivee - tps_depart)+"");
	}
	
	private JButton boutonFermeture() {
		
		JButton boutonFermer = new JButton(new ImageIcon(getClass().getResource("/images/croixFermer.png")));
		boutonFermer.setBounds(0, 0, 50, 50);
		boutonFermer.setFocusable(false);
    	boutonFermer.setOpaque(false);    	
    	boutonFermer.setFocusPainted(false);
    	boutonFermer.setBorderPainted(false);
    	boutonFermer.setContentAreaFilled(false);

    	
    	boutonFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	dispose();
            }
        });
    	
    	return boutonFermer; 
    	
	}
	
	private JButton boutonNew() {
		
		JButton boutonFermer = new JButton("NEW");
		
		boutonFermer.setBounds(0, MainFrame.height - 50, 150, 50);
		boutonFermer.setFont(new Font("Arial", Font.BOLD, 30));
		boutonFermer.setFocusable(false);
    	boutonFermer.setOpaque(false);    	
    	boutonFermer.setFocusPainted(false);
    	boutonFermer.setBorderPainted(false);
    	boutonFermer.setContentAreaFilled(false);

    	
    	boutonFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	dispose();
            	Buck3 buck = new Buck3();
        		buck.setLocationRelativeTo(null);
        		buck.setVisible(true);
            }
        });
    	
    	return boutonFermer; 
    	
	}
}
