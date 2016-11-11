package com.excilys.mviegas.computer_database.cligui;

import com.excilys.mviegas.computer_database.cligui.services.ComputerService;
import com.excilys.mviegas.computer_database.controlers.IComputersListViewControler;
import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.persistence.Paginator;

import java.util.List;

/**
 * View d'une liste de Computer en CLI.
 */
@SuppressWarnings("WeakerAccess")
public enum ComputersListView implements IComputersListViewControler {

	INSTANCE;

	public static final String FORMAT_LINE = "%3s | %15.15s | %10s | %10s | %10s%n";

	//=============================================================
	// Attributs
	//=============================================================

	private int mStart = 0;
	private int mSize = 20;

	private Paginator<Computer> mPaginator;

	private ComputerService mComputerService;

	//=============================================================
	// Méthodes - private
	//=============================================================

	private void printHeaderList() {
		System.out.println();
		System.out.printf(FORMAT_LINE, "ID", "NOM", "DATE CREATION", "DATE FIN", "FABRICANT");
		System.out.println("-------------------------------------------------------------------");
	}

	private void printPage() {
		printHeaderList();
		List<Computer> computers;

		computers = mPaginator.values;
		if (computers.size() == 0) {
			System.out.println("Aucune donnée.");
		} else {
			for (Computer computer : computers) {
				System.out.printf(FORMAT_LINE, computer.getId(), computer.getName(), computer.getIntroducedDate() == null ?  "" : computer.getIntroducedDate(), computer.getDiscontinuedDate() == null ? "" : computer.getDiscontinuedDate(), computer.getManufacturer() != null ? computer.getManufacturer().getName() : "");
//				System.out.printf(FORMAT_LINE, computer.getId(), computer.getName(), computer.getIntroducedDate() == null ?  "" : computer.getIntroducedDate().toString(), computer.getDiscontinuedDate() == null ? "" : computer.getDiscontinuedDate()., computer.getIdCompany());
			}
		}
	}

	private void printChoices() {
		System.out.print("(n : nextPage, p : previousPage, q : quit, id : view specific computer)\n? > ");
	}

	private void invalidatePaginator() {
		mPaginator = ComputerService.INSTANCE.findAllWithPaginator(mStart, mSize);
	}

	//=============================================================
	// Override - IComputersListViewControler
	//=============================================================
	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#nextPage()
	 */
	@Override
	public void nextPage() {
		if (mPaginator.currentPage >= mPaginator.nbPages) {
			System.err.println("Fin de liste atteinte !");
		} else {
			mStart += mSize;
		}

		invalidatePaginator();
		printPage();
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#previousPage()
	 */
	@Override
	public void previousPage() {
		mStart -= mSize;
		if (mStart < 0) {
			mStart = 0;
			System.err.println("Début de liste atteinte !");
		}

		invalidatePaginator();
		printPage();
	}


	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#setSize(int)
	 */
	@Override
	public void setSize(int pSize) {
		mSize = pSize;
		invalidatePaginator();
		printPage();
	}


	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#launch()
	 */
	@Override
	public void launch() {
		invalidatePaginator();

		MainMenuConsole.printBread("Liste des ordinateurs");

		printPage();

		mainloop : while (true) {

			printChoices();

			switch (MainMenuControleur.SCANNER.nextLine()) {
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
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#deleteComputer()
	 */
	@Override
	public void deleteComputer() {
		
		int id;
		while (true) {
			System.out.println(MainMenuControleur.SEPARATOR);
			System.out.print("Supprimer quel ordinateur (Saisissez l'ID) ? > ");
			
			id = MainMenuControleur.SCANNER.nextInt();
			Computer computer;
			computer = ComputerService.INSTANCE.find(id);
			if (computer == null) {
				System.err.printf("Ordinateur avec l'ID n°%d introuvable%nVeuillez saisir un ID valide%n", id);
				continue;
			}

			ComputerService.INSTANCE.delete(computer);

			printPage();
			
			System.out.printf("[!] Ordinateur %s (n°%d) supprimé avec succès%n", computer.getName(), computer.getId());

			break;
		}	
		
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#addComputer()
	 */
	@Override
	public void addComputer() {
		throw new UnsupportedOperationException("ComputersListView#addComputer not implemented yet.");
	}

	/* (non-Javadoc)
	 * @see com.excilys.mviegas.computer_database.ui.cligui.IComputersListView#searchById()
	 */
	@Override
	public void searchById() {
		int id;
		while (true) {
			System.out.println(MainMenuControleur.SEPARATOR);
			System.out.print("Voir le détail de quel ordinateur (Saisissez l'ID) ? > ");
			id = Integer.valueOf(MainMenuControleur.SCANNER.next());

			Computer computer;
			computer = ComputerService.INSTANCE.find(id);

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
