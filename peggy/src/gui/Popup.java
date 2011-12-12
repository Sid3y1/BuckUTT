package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class Popup extends JFrame {

	private static final long serialVersionUID = 1L;
	private String message;
	private String codeErreur;
	
	public Popup(String message, String codeErreur) {
		super();
		
		this.message = message;
		this.codeErreur = codeErreur;
		
		setSize(MainFrame.width,MainFrame.height);
        setResizable(false);
        setTitle("Buckutt Popup");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(addPanel());
	}
	
	public JPanel addPanel() {
		JPanel test = new JPanel();
		test.setLayout(null);
		test.setBounds(MainFrame.width, MainFrame.height, 0, 0);
		test.setBackground(Color.BLACK);
		
		JLabel labelMessage = new JLabel("Erreur " + codeErreur + " : " + message);
		labelMessage.setBounds(0, 0, 300, 100);
		labelMessage.setBounds(0, (MainFrame.height/2 - 100), MainFrame.width, 100);
        labelMessage.setFont(new Font("Arial", Font.BOLD, 20));
        labelMessage.setForeground(Color.WHITE);
        labelMessage.setVerticalAlignment(SwingConstants.CENTER); //ça centre en haut...
        labelMessage.setHorizontalTextPosition(SwingConstants.CENTER);
        labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton bouton = new JButton("OK");
        bouton.setBounds((MainFrame.width/2 - 150), (MainFrame.height/2 + 50), 300, 100);
        bouton.setFocusable(false);
        bouton.setFont(new Font("Arial", Font.BOLD, 30));
        bouton.setBackground(Color.DARK_GRAY);
        bouton.setForeground(Color.WHITE);
        bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        
        test.add(labelMessage);
        test.add(bouton);
		
        

        
		return test;
	}
	
}
