package gui;

import java.rmi.RemoteException;

class ThreadDelog extends Thread {

	int no_fin;

	// Constructeur

	ThreadDelog(int fin) {

		no_fin = fin;

	}

	// On redéfinit la méthode run()

	public void run () {
		
		
		
		
		//System.out.println("toto");
		try {
			while (true) {
				Thread.sleep(1000); // ms
				try {
					MainFrame.PBUY.isLoadedSeller();
					//int test = MainFrame.PBUY.isLoadedSeller();
					//MainFrame.showConnexionPanel();
					return;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		} catch (InterruptedException e) {
			
			return;
		}

		



	}
}