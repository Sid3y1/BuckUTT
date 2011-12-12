package gui;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PageAide extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public PageAide() {
		super();
		
		setSize(MainFrame.width,MainFrame.height);
        setResizable(false);
        setTitle("Buckutt Aide");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(addPanel());
        
	}
	
	public JPanel addPanel() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(null);
		fenetre.setBounds(MainFrame.width, MainFrame.height, 0, 0);
		
		Color newCouleur = new Color(255, 164, 209);
    	
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
			fenetre.setBackground(new Color(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2])));
			
		} catch (FileNotFoundException e) {
			fenetre.setBackground(newCouleur);
			e.printStackTrace();
		} catch (IOException e) {
			fenetre.setBackground(newCouleur);
			e.printStackTrace();
		}
		
		fenetre.add(boutonFermeture());
		
		return fenetre;
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
