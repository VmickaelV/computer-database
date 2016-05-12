package com.excilys.mviegas.speccdb.cligui;

import com.excilys.mviegas.speccdb.cligui.services.ComputerService;
import com.excilys.mviegas.speccdb.controlers.IComputerDetailControler;
import com.excilys.mviegas.speccdb.data.Computer;

/**
 * Controleur d'affiche d'un {@link Computer} en Détail.
 */
public class ComputerDetail implements IComputerDetailControler {

	//=============================================================
	// Attributs
	//=============================================================

	private ComputerService mComputerService;

	private Computer mComputer;

	//=============================================================
	// Constructeurs
	//=============================================================

	private ComputerDetail(Computer pComputer) {
		mComputer = pComputer;
	}

	//=============================================================
	// Méthodes private
	//=============================================================

	private void printDetail() {
		MainMenuConsole.printBread("Ordinateur n°" + mComputer.getId() + " : " + mComputer.getName());
		
		System.out.printf("%15s : %d%n", "ID", mComputer.getId());
		System.out.printf("%15s : %s%n", "NOM", mComputer.getName());
		System.out.printf("%15s : %s%n", "DATE LANCEMENT", mComputer.getIntroducedDate() == null ? "(N/A)" : mComputer.getIntroducedDate());
		System.out.printf("%15s : %s%n", "FIN PRODUCTION", mComputer.getDiscontinuedDate() == null ? "(N/A)" : mComputer.getDiscontinuedDate());
		System.out.printf("%15s : %s%n", "FABRICANT", mComputer.getManufacturer() == null ? "(N/A)" : mComputer.getManufacturer().getName());
	}

	//=============================================================
	// Override - IComputerDetailControler
	//=============================================================
	
	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputerDetail#delete()
	 */
	@Override
	public void delete() {
		throw new UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputerDetail#update()
	 */
	@Override
	public void update() {
		ComputerUpdate.make(mComputer).launch();
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputerDetail#quit()
	 */
	@Override
	public void quit() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Computer getComputer() {
		return mComputer;
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputerDetail#launch()
	 */
	@Override
	public void launch() {
		printDetail();
		
		mainloop : while (true) {

			printChoices();

			switch (MainMenuControleur.SCANNER.nextLine()) {
			case "d":
				if (deleteComputer()) {
					break mainloop;
				} else {
					printDetail();
					break;
				}
			case "u":
				update();
				break;
			case "q":
				break mainloop;
			
				
			default:
				System.err.println("Choix invalide.\nVeuillez ressaisir votre choix.\n");
				//noinspection UnnecessaryContinue
				continue;
			}
		}
	}

	//=============================================================
	// Méthodes private
	//=============================================================

	private boolean deleteComputer() {
		System.out.println("Êtes-vous sur ? (O/n)");
		
		if (MainMenuControleur.SCANNER.next().equals("O")) {
			mComputerService.delete(mComputer);

			System.out.printf("[!] Ordinateur %s (n°%d) supprimé avec succès%n", mComputer.getName(), mComputer.getId());
			return true;
		}
		
		return false;
		
	}

	private void printChoices() {
		System.out.println(MainMenuControleur.SEPARATOR);
		System.out.print("(d : delete, q : quitter, u : update)\n? > ");
	}

	//=============================================================
	// Méthodes static - public
	//=============================================================

	public static IComputerDetailControler make(int pId) {
		Computer computer;
		computer = ComputerService.INSTANCE.find(pId);

		if (computer == null) {
			return null;
		} else {
			return new ComputerDetail(computer);
		}
	}
	
	public static IComputerDetailControler make(Computer pComputer) {
		if (pComputer == null) {
			return null;
		}

		pComputer = ComputerService.INSTANCE.find(pComputer.getId());
		if (pComputer == null) {
			return null;
		}
		
		return new ComputerDetail(pComputer);
	}

}
