package gui;

import java.rmi.RemoteException;

public class PopupError {

	public static boolean retourMethode(int codeErreur)
	{
		if (codeErreur == 1)
			return true;
		else
		{
			affichePopup(codeErreur);
			
			return false;
		}
	}
	
	private static void affichePopup(int codeErreur)
	{
		try {
			String[] result = ComplexData.getData(MainFrame.PBUY.getErrorDetail(codeErreur));	
        	
        	Popup fenetreAide = new Popup(result[2], result[0]);
        	fenetreAide.setLocationRelativeTo(null);
        	fenetreAide.setVisible(true);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
