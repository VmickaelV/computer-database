package com.excilys.mviegas.speccdb.cligui;

import com.excilys.mviegas.speccdb.cligui.services.CompanyService;
import com.excilys.mviegas.speccdb.cligui.services.ComputerService;
import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controleur d'un Update ou d'ajout de {@link Computer}
 */
public class ComputerUpdate implements IComputerUpdateControler {

	//=============================================================
	// Attributs
	//=============================================================

	private CompanyService mCompanyService = CompanyService.INSTANCE;

	private ComputerService mComputerService = ComputerService.INSTANCE;

	private Computer mComputer;

	//=============================================================
	// Constructors
	//=============================================================

	private ComputerUpdate(Computer pComputer) {
		mComputer = pComputer;
	}

	//=============================================================
	// Override - IComputerUpdateControler
	//=============================================================

	/* (non-Javadoc)
	 * @see IComputerUpdateControler#launch()
	 */
	@Override
	public void launch() {
		if (mComputer == null) {
			MainMenuConsole.printBread("Création d'ordinateur");
			System.out.println("(Aide : la valeur entre parathèse est la valeur par défaut (saisissez vide)");
		} else {
			MainMenuConsole.printBread("Mise à jour de l'ordinateur " + mComputer.getId());
			System.out.println("(Aide : la valeur entre parathèse est l'ancienne (saisissez vide)");
		}

		String name = null;

		// Siasie nouveau nom
		do {
			if (mComputer == null) {
				System.out.print("> Nouveau nom : ");
			} else {
				System.out.printf("> Nouveau nom (ancien : %s) : ", mComputer.getName());
			}

			name = MainMenuControleur.SCANNER.nextLine();

			if (mComputer != null && name != null && name.equals("")) {
				name = mComputer.getName();
			} else if (name.isEmpty()) {
				System.err.println("Saisie d'un nom obligatoire !");
			}

		} while (name == null || name.equals(""));

		// saisie date introduction
		LocalDate dateIntroduced = null;
		final String formatDate = "yyyy/MM/dd";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

		do {
			if (mComputer == null || mComputer.getIntroducedDate() == null) {
				System.out.print("> Date Lancement (N/A) : ");
			} else {
				System.out.printf("> Date Lancement (ancien : %s) : ", mComputer.getIntroducedDate().toString());
			}

			String t = MainMenuControleur.SCANNER.nextLine();

			if (!t.isEmpty()) {
				try {
					dateIntroduced = LocalDate.parse(t, dateTimeFormatter);
				} catch (DateTimeParseException e) {
					System.err.println("Veuillez saisir une date dans le bon format (AAAA/MM/JJ)");
					continue;
				}
			}

			if (mComputer != null && t.isEmpty()) {
				dateIntroduced = mComputer.getIntroducedDate();
			}
			break;

		} while (true);

		// Saisie date fin
		LocalDate dateFinished = null;

		do {
			if (mComputer == null || mComputer.getDiscontinuedDate() == null) {
				System.out.print("> Fin Production (N/A) : ");
			} else {
				System.out.printf("> Fin Production (ancien : %s) : ", mComputer.getDiscontinuedDate().toString());
			}

			String t = MainMenuControleur.SCANNER.nextLine();

			if (!t.isEmpty()) {
				try {
					dateFinished = LocalDate.parse(t, dateTimeFormatter);
				} catch (DateTimeParseException e) {
					System.err.println("Veuillez saisir une date dans le bon format (AAAA/MM/JJ)");
					continue;
				}
			}

			if (mComputer != null && t.isEmpty()) {
				dateFinished = mComputer.getDiscontinuedDate();
			}
			break;

		} while (true);

		// Siasie fabriquant
		Company company = null;

		do {
			if (mComputer == null) {
				System.out.print("> ID du fabricant (N/A) : ");
			} else {
				if (mComputer.getManufacturer() == null) {
					System.out.printf("> ID du fabricant (ancien : (N/A) ) : ",
							mComputer.getDiscontinuedDate().toString());
				} else {
					System.out.printf("> ID du fabricant (ancien : %d (%s) ) : ", mComputer.getManufacturer().getId(),
							mComputer.getManufacturer().getName());
				}
			}

			String t = MainMenuControleur.SCANNER.nextLine();

			if (t.isEmpty()) {
				if (mComputer != null) {
					company = mComputer.getManufacturer();
				}
			} else {
				int id = -1;
				try {
					id = Integer.valueOf(t);
				} catch (NumberFormatException e) {
					System.err.println("Saisissez un ID valide");
					continue;
				}

				company = mCompanyService.find(id);

				if (company == null) {
					System.err.println("Saisissez un ID valide");
					continue;
				}
			}
			break;

		} while (true);

		if (mComputer == null) {
			Computer.Builder builder = new Computer.Builder();
			builder.setName(name).setIntroducedDate(dateIntroduced).setDiscontinuedDate(dateFinished).setManufacturer(company);

			mComputer = builder.build();

			mComputerService.create(mComputer);
		} else {
			mComputer.setName(name);
			mComputer.setIntroducedDate(dateIntroduced);

			mComputerService.update(mComputer);
		}
		
		valid();
	}
	
	

	@Override
	public void cancel() {
		throw new UnsupportedOperationException("ComputerUpdate#cancel not implemented yet.");
	}

	@Override
	public void valid() {
		System.out.printf("[!] Ordinateur %s (ID:%d) bien enregistrées%n", mComputer.getName(), mComputer.getId());
	}

	//=============================================================
	// Méthodes static - public
	//=============================================================

	/**
	 * Factory de ComputerUpdate
	 * @param pId Id du computeur à éditer
	 * @return Controleur, ou null si computer introuvable
	 */
	public static IComputerUpdateControler make(int pId) {
		Computer computer;
		computer = ComputerService.INSTANCE.find(pId);

		if (computer == null) {
			// throw new IllegalArgumentException();
			return null;
		} else {
			return new ComputerUpdate(computer);
		}
	}

	/**
	 * @see ComputerUpdate#make(int)
	 */
	public static IComputerUpdateControler make(Computer pComputer) {
		if (pComputer == null) {
			return null;
		}

		pComputer = ComputerService.INSTANCE.find(pComputer.getId());
		if (pComputer == null) {
			return null;
		}

		return new ComputerUpdate(pComputer);
	}

	/**
	 * Factory de ComputerUpdate pour un ajout d'ordinateur
	 * @return Controleur crée
	 */
	public static IComputerUpdateControler make() {
		return new ComputerUpdate(null);
	}

}
