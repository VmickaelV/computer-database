package com.excilys.mviegas.speccdb.ui.cligui;

import com.excilys.mviegas.speccdb.controlers.IComputersListViewControler;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;

import java.util.List;
import java.util.Scanner;

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
		try {
			if ((mStart + mSize) < ComputerDao.getInstance().size()) {
				mStart += mSize;
			} else {
				System.err.println("Fin de liste atteinte !");
			}
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
			// TODO à refaire
			throw new RuntimeException(pE);
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
		List<Computer> computers;
		try {
			computers = ComputerDao.getInstance().findAll(mStart, mSize);
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
			// TODO a modifier
			throw new RuntimeException(pE);
		}

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
			Computer computer;
			try {
				computer = ComputerDao.getInstance().find(id);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO a modifier
				throw new RuntimeException(pE);
			}
			if (computer == null) {
				System.err.printf("Ordinateur avec l'ID n°%d introuvable%nVeuillez saisir un ID valide%n", id);
				continue;
			}

			try {
				if (!ComputerDao.getInstance().delete(computer)) {
					throw new RuntimeException();
				}
			} catch (DAOException pE) {
				// TODO a implémenter
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
		throw new UnsupportedOperationException("ComputersListView#addComputer not implemented yet.");
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.speccdb.ui.cligui.IComputersListView#searchById()
	 */
	@Override
	public void searchById() {
		int id;
		while (true) {
			System.out.println(MainMenuControleur.SEPARATOR);
			System.out.print("Voir le détail de quel ordinateur (Saisissez l'ID) ? > ");
			id = Integer.valueOf(MainMenuControleur.SCANNER.next());

			Computer computer;
			try {
				computer = ComputerDao.getInstance().find(id);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}

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
		throw new UnsupportedOperationException("ComputersListView#setPage not implemented yet.");
	}
	
	
	
	
}
