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

public class Buck1 extends JFrame {
	
	private JPanel[] cases = {null};
	private boolean joueur = true;
	private JLabel aQui = new JLabel();
	
	
	public Buck1() {
		super();
		
		setSize(MainFrame.width,MainFrame.height);
        setResizable(false);
        setTitle("Buckutt Popup");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cases = new JPanel[10];
        
        add(addPanel());
        
	}
	
	public JPanel addPanel() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(null);
		fenetre.setBounds(MainFrame.width, MainFrame.height, 0, 0);
		fenetre.setBackground(Color.GRAY);
		
		JPanel jeu = new JPanel();
		
		jeu.setBounds(MainFrame.width/2 - 150, MainFrame.height/2 - 150, 300, 300);
		jeu.setOpaque(false);
		jeu.setLayout(new GridLayout(0, 3, 0, 0));
		
		for (int i=0; i<9; i++) {
			cases[i] = new JPanel();
			cases[i].setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
			cases[i].setSize(100, 100);
			cases[i].setOpaque(false);
			
			final int copieI = i;
			JButton bouton = new JButton("?");
			bouton.setPreferredSize(new Dimension(100, 100));
			bouton.setFont(new Font("Arial", Font.BOLD, 30));
			bouton.setOpaque(false);    	
			bouton.setFocusPainted(false);
			bouton.setBorderPainted(false);
	    	bouton.setContentAreaFilled(false);
			bouton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	updateJeu(copieI);
                }
            });
			
			cases[i].add(bouton);
			
			jeu.add(cases[i]);
		}
		
		fenetre.add(jeu);
		
		fenetre.add(boutonFermeture());
		fenetre.add(boutonNew());
		fenetre.add(aQuiDeJouer());
		
		return fenetre;
	}
	
	private void updateJeu(int casesJouee) {
		cases[casesJouee].setVisible(false);
		cases[casesJouee].removeAll();
		
		JLabel remplir = new JLabel();
		
		if (joueur) {
			remplir.setText("X");
			aQui.setText("O");
			joueur = false;
		}
		else {
			remplir.setText("0");
			aQui.setText("X");
			joueur = true;
		}
		
		remplir.setFont(new Font("Arial", Font.BOLD, 30));
		cases[casesJouee].add(remplir);
		
		cases[casesJouee].revalidate();
		cases[casesJouee].setVisible(true);
	}

	private JLabel JLabel(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private JLabel aQuiDeJouer() {
		aQui.setBounds(MainFrame.width - 50, 0, 50, 50);
		aQui.setFont(new Font("Arial", Font.BOLD, 30));
		aQui.setText("X");
    	return aQui;
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
            	Buck1 buck = new Buck1();
        		buck.setLocationRelativeTo(null);
        		buck.setVisible(true);
            }
        });
    	
    	return boutonFermer; 
    	
	}
}
