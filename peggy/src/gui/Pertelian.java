package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author ZANETTI Boris
 * @version 1.0
 */
public class Pertelian {
	  /**
     * Satus du pertelian.
     * <p>
     * Cette fonction retourne true si le pertelian est demarrer 
     * et retourne false si il est arreter
     * nessesite l'installation du packet pertd 
     * </p>
     */
	public static boolean GetStatus(){
		Process p;
		try {
			p = Runtime.getRuntime().exec("sh /etc/init.d/pertd status");
			BufferedReader d = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String toto=d.readLine();
			if (toto.equals("demarrer")){
				return true;
			}else{
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
    /**
     * Clear du pertelian
     * <p>
     * Cette fonction efface l'integralité de l'ecran du pertelian
     * </p>
     */
	public static void Clear(){
		try {
			for (int i = 1; i<5;i++){
				FileWriter fstream = new FileWriter("/pertd/line" + i);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(" ");
				out.close();
			}
			FileWriter fstream = new FileWriter("/pertd/refresh");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(" ");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   /**
    * Ecrit sur le pertelian
    * <p>
    * Cette fonction permet de metre en memoire du texte
    * (en fonction de la ligne).
    * Il faut appeler Update() pour mettre a jour l'affichage 
    * @param text
    *            le texte a afficher sur la ligne 'line'
    * @param line
    *            numero de la ligne sur laquel il faut ercire (0<line<=4)
    *            
    * @see Pertelian#Update()
    */

	public static boolean Write(String text, int line){
		if (line > 0){
			try {
				FileWriter fstream = new FileWriter("/pertd/line" + line);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(text + " ");
				out.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			return false;
		}
		return false;
	}
    
	/**
     * Mise à jour de l'affichage du pertelian
     * <p>
     * Cette fonction affiche sur le pertelian le texte 
     * ecrit par la methode Write
     * </p>
     * @see Pertelian#Write(String , int)
     */
	public static void Update(){
		try {
			FileWriter fstream = new FileWriter("/pertd/refresh");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(" ");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
