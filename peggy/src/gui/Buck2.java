package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.FeatureDescriptor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Buck2 extends JFrame {
	
	private JLabel resultat = new JLabel();
	private JButton bouton = new JButton();
	
	public Buck2() {
		super();
		
		setSize(MainFrame.width,MainFrame.height);
        setResizable(false);
        setTitle("Buckutt Popup");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(addPanel());
        
	}
	
	public JPanel addPanel() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(null);
		fenetre.setBounds(MainFrame.width, MainFrame.height, 0, 0);
		fenetre.setBackground(Color.GRAY);
		
		resultat.setBounds(MainFrame.width/2 - 150, MainFrame.height/2, 300, 150);
		resultat.setFont(new Font("Arial", Font.BOLD, 40));
		resultat.setVerticalAlignment(SwingConstants.CENTER); //ça centre en haut...
		resultat.setHorizontalTextPosition(SwingConstants.CENTER);
		resultat.setHorizontalAlignment(SwingConstants.CENTER);
		
		bouton.setText("LANCER");
		bouton.setBounds(MainFrame.width/2 - 150, MainFrame.height/2 - 150, 300, 150);
		bouton.setFont(new Font("Arial", Font.BOLD, 40));
		bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (bouton.getText() == "") {
            		bouton.setText("LANCER");
            		resultat.setText("");
            	}
            	else {
            		updateJeu();
                	bouton.setText("");
            	}
            }
        });
		
		fenetre.add(resultat);
		fenetre.add(bouton);
		fenetre.add(boutonFermeture());
		
		return fenetre;
	}
	
	private void updateJeu() {		
		if (Math.random() > 0.5)
			resultat.setText("PILE");
		else
			resultat.setText("FACE");		
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
}
