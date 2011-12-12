package gui;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

//fait un dégradé tout joli

class ContentPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	ContentPanel() {
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
			setBackground(new Color(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2])));
			
		} catch (FileNotFoundException e) {
			setBackground(newCouleur);
			e.printStackTrace();
		} catch (IOException e) {
			setBackground(newCouleur);
			e.printStackTrace();
		}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if ( !isOpaque() ) {
            super.paintComponent(g);
            return;
        }
        
        Color color1 = getBackground();
        Color color2 = color1.darker().darker().darker();
        
        Graphics2D g2d = (Graphics2D)g;
        
        int w = getWidth();
        int h = getHeight();
        
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }
    
}
