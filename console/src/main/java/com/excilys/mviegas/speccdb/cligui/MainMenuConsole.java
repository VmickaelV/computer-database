package com.excilys.mviegas.speccdb.cligui;

import com.excilys.mviegas.speccdb.C;
import com.excilys.mviegas.speccdb.controlers.IMainMenuControler;

public class MainMenuConsole {
	private IMainMenuControler mMainMenuControler;
	
	String[] menu = new String[] {"Voir la liste des companies", "Voir la liste des ordinateurs", "Ajouter un ordinateur"};

	protected MainMenuConsole(IMainMenuControler pMainMenuControler) {
		super();
		mMainMenuControler = pMainMenuControler;
	}
	
	public void display() {
		
		C.Loggers.RUNTIME.info("Lancement du programme");
		System.out.println("*******************************************************************");
		System.out.println("***                                                             ***");
		System.out.println("***                  Gestionaire d'ordinateurs                  ***");
		System.out.println("***                                                             ***");
		System.out.println("*******************************************************************");
		
		
		mainloop : while (true) {
    		printBread("MainMenu");
    		
    		for (int i = 0; i < menu.length; i++) {
    			String string = menu[i];
    			System.out.printf("%3d > %s\n", i, string);
    		}
    		
    		System.out.print("Menu ? ");
    		
		
		
			switch (MainMenuControleur.SCANNER.nextLine()) {
			case "0":
				mMainMenuControler.seeListComputers();
				break;
			case "a":
			case "2":
				mMainMenuControler.addComputer();
				break;
			case "q":
				break mainloop;
			default:
				System.err.println("Choix invalide.\nVeuillez ressaisir votre choix.");
				continue;
			}
		}
	}
	
	public void close() {
		System.out.println("\n\n-------------------------------------------------------------------");
		System.out.print("FIN");
		
		C.Loggers.RUNTIME.info("Exctinction du programme");
	}
	
	public static void printBread(String pLink) {
		System.out.println("");
		System.out.println(" > "+pLink);
		System.out.println("-------------------------------------------------------------------");
		
	}
	
	
}