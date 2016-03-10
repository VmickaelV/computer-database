package com.excilys.mviegas.speccdb.ui.cligui;

import java.util.List;
import java.util.Scanner;

import com.excilys.mviegas.speccdb.controlers.IComputersListViewControler;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;

public enum ComputersListView implements IComputersListViewControler {

	INSTANCE;

	public static final String FORMAT_LINE = "%3s | %15.15s | %10s | %10s | %10s%n";
	private int mStart = 0;
	private int mSize = 20;
	
	

	private void printHeaderList() {
		System.out.println();
		System.out.printf(FORMAT_LINE, "ID", "NOM", "DATE CREATION", "DATE FIN", "FABRICANT");
		System.out.println("-------------------------------------------------------------------");
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#nextPage()
	 */
	@Override
	public void nextPage() {
		if ((mStart + mSize) < ComputerDao.INSTANCE.size()) {
			mStart += mSize;
		} else {
			System.err.println("Fin de liste atteinte !");
		}
		printPage();
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#previousPage()
	 */
	@Override
	public void previousPage() {
		mStart -= mSize;
		if (mStart < 0) {
			mStart = 0;
			System.err.println("Début de liste atteinte !");
		}
		printPage();
	}

	
	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#setSize(int)
	 */
	@Override
	public void setSize(int pSize) {
		mSize = pSize;
		printPage();
	}

	private void printPage() {
		printHeaderList();
		List<Computer> computers = ComputerDao.INSTANCE.findAll(mStart, mSize);
		
		if (computers.size() == 0) {
			System.out.println("Aucune donnée.");
		} else {
			for (Computer computer : computers) {
				System.out.printf(FORMAT_LINE, computer.getId(), computer.getName(), computer.getIntroducedDate() == null ?  "" : computer.getIntroducedDate(), computer.getDiscontinuedDate() == null ? "" : computer.getDiscontinuedDate(), computer.getManufacturer() != null ? computer.getManufacturer().getName() : "");
			}
		}
	}

	private void printChoices() {
		System.out.print("(n : nextPage, p : previousPage, q : quit, id : view specific computer)\n? > ");
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#launch()
	 */
	@Override
	public void launch() {
		
		MainMenuConsole.printBread("Liste des ordinateurs");

		Scanner a = new Scanner(System.in);

		printPage();

		mainloop : while (true) {

			printChoices();

			switch (a.nextLine()) {
			case "n":
				nextPage();
				break;
			case "p":
				previousPage();
				break;
			case "id":
				searchById();
				break;
			case "a":
				addComputer();
				break;
			case "d":
				deleteComputer();
				break;
			case "q":
				break mainloop;
			default:
				System.err.println("Choix invalide.\nVeuillez ressaisir votre choix.\n");
				continue;
			}
		}
		
		a.close();
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#deleteComputer()
	 */
	@Override
	public void deleteComputer() {
		
		int id;
		while (true) {
			System.out.println(MainMenuControleur.SEPARATOR);
			System.out.print("Supprimer quel ordinateur (Saisissez l'ID) ? > ");
			
			id = MainMenuControleur.SCANNER.nextInt();
			Computer computer = ComputerDao.INSTANCE.find(id);
			if (computer == null) {
				System.err.printf("Ordinateur avec l'ID n°%d introuvable%nVeuillez saisir un ID valide%n", id);
				continue;
			}
			
			if (!ComputerDao.INSTANCE.delete(computer)) {
				throw new RuntimeException();
			}
			
			printPage();
			
			
			
			System.out.printf("[!] Ordinateur %s (n°%d) supprimé avec succès%n", computer.getName(), computer.getId());
			
			
			break;
		}	
		
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#addComputer()
	 */
	@Override
	public void addComputer() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputersListView#addComputer not implemented yet.");
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#searchById()
	 */
	@Override
	public void searchById() {
		// TODO Auto-generated method stub
		int id;
		while (true) {
			System.out.println(MainMenuControleur.SEPARATOR);
			System.out.print("Voir le détail de quel ordinateur (Saisissez l'ID) ? > ");
			id = Integer.valueOf(MainMenuControleur.SCANNER.next());
			
			Computer computer = ComputerDao.INSTANCE.find(id);
			if (computer == null) {
				System.err.printf("Ordinateur avec l'ID n°%d introuvable%nVeuillez saisir un ID valide%n", id);
				continue;
			}
			
			ComputerDetail.make(computer).launch();
			
			printPage();
			
			break;
		}
		
	}

	@Override
	public void setPage(int pPage) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputersListView#setPage not implemented yet.");
	}
	
	
	
	
}
