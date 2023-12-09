package src;

import java.util.*;

public class Simulation {

	private Battle battle;
	private Army army1, army2;
	int army1W = 0;
	int army2W = 0;
	static int simulation = 1;
	public static int type = 0;

	public Simulation(Battle battle, int repetition) {

		for (int i = 0; i < repetition; i++) {

			this.army1 = new Army(battle.getArmy1().getName());
			this.army2 = new Army(battle.getArmy2().getName());

			for (Creature creature : battle.getArmy1().getCreatures()) {
				Creature copy = new Creature(creature.getName(), creature.getHealth(),
						creature.getDamage(), creature.getSpeed(),
						creature.getSpecial(), creature.getType());
				copy.setArmy(this.army1);
				this.army1.addCreature(copy);
			}

			for (Creature creature : battle.getArmy2().getCreatures()) {
				Creature copy = new Creature(creature.getName(), creature.getHealth(),
						creature.getDamage(), creature.getSpeed(),
						creature.getSpecial(), creature.getType());
				copy.setArmy(this.army2);
				this.army2.addCreature(copy);
			}

			this.battle = new Battle(this.army1, this.army2);

			Army winner = this.battle.fight();

			if (winner.getName().equals("Army1")) {
				army1W++;
			} else {
				army2W++;
			}
		}
		int totalBattles = army1W + army2W;
		double army1WinPercentage = (totalBattles == 0) ? 0 : ((double) army1W / totalBattles) * 100;
		double army2WinPercentage = (totalBattles == 0) ? 0 : ((double) army2W / totalBattles) * 100;

		System.out.println("\n\n\n\n+++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("\n\t\tArmy 1: " + army1W + " wins | " + String.format("%.2f", army1WinPercentage) + "%");
		System.out.println("\t\tArmy 2: " + army2W + " wins | " + String.format("%.2f", army2WinPercentage) + "%");

		if (army1W > army2W) {
			System.out.println("\t\tAverage HP Army 1: "
					+ String.format("%.2f", calculateAverageArmyHealthRemaining(army1.getCreatures())) + " HP");
		} else {
			System.out.println("\t\tAverage HP Army 2: "
					+ String.format("%.2f", calculateAverageArmyHealthRemaining(army2.getCreatures())) + " HP");
		}
		System.out.println("\t\tUltimate Winner:");
		System.out.println("\n\t\t    " + ((army1W > army2W) ? "Army 1" : "Army 2"));
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++\n\n\n");

	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("\033[31m++++++++ WAR SIMULATOR ++++++++");
	}

	private double calculateAverageArmyHealthRemaining(ArrayList<Creature> armyCreatures) {
		int totalHealth = 0;
		for (Creature creature : armyCreatures) {
			totalHealth += creature.getHealth();
		}
		int totalCreatures = armyCreatures.size();
		return (totalCreatures == 0) ? 0 : ((double) totalHealth / totalCreatures);
	}

	public static void displayCreatures(ArrayList<Creature> array) {
		int i = 1;
		System.out.println("\n--- Creatures ---");
		System.out.printf("\n%7s %25s %10s %10s %10s %10s", "|Type|", "|Name|", "|HP|", "|DMG|", "|SPD|", "|POW|\n");
		for (Creature creature : array) {
			System.out.format("%7s %25s %10s %10s %10s %10s", i++, creature.getName(), creature.getHealth(),
					creature.getDamage(), creature.getSpeed(), creature.getSpecial());
			System.out.println();
		}
	}

	public static void displayArmy(ArrayList<Creature> array, int n) {
		int[] type = new int[n + 1];
		for (Creature creature : array) {
			int typeNumber = creature.getType();
			type[typeNumber]++;
		}
		System.out.printf("\n\n%7s %10s", "|Type|", "|AMOUNT|\n");
		for (int i = 1; i <= n; i++) {
			System.out.format("%7s %10s", i, type[i]);
			System.out.println();
		}
	}

	public static void displayArmyForOverview(ArrayList<Creature> array, ArrayList<Creature> inputCreatures) {
		Map<String, Integer> creatureCount = new HashMap<>();

		for (Creature creature : array) {
			String creatureName = creature.getName();
			creatureCount.put(creatureName, creatureCount.getOrDefault(creatureName, 0) + 1);
		}

		System.out.printf("\n\n%7s %10s", "|Name|", "|AMOUNT|\n");
		for (Creature creature : inputCreatures) {
			String creatureName = creature.getName();
			System.out.format("%7s %10s", creatureName, creatureCount.getOrDefault(creatureName, 0));
			System.out.println();
		}
	}

	public static void displayOverview(Army army, ArrayList<Creature> inputCreatures) {
		System.out.println("\n\n --- " + army.getName() + " ---");
		displayArmyForOverview(army.getCreatures(), inputCreatures);
	}

	public static void main(String[] args) {

		Scanner getInput = new Scanner(System.in);
		ArrayList<Creature> inputCreatures = new ArrayList<Creature>();

		clearScreen();
		int numOfTypes = 0;

		while (numOfTypes <= 0) {
			try {
				System.out.print("\033[100;0H~$> Different Type of Creatures: ");
				numOfTypes = getInput.nextInt();
				getInput.nextLine(); // Consume the newline character
				if (numOfTypes <= 0) {
					System.out.println("Please enter a positive integer.");
				}
			} catch (InputMismatchException e) {
				getInput.nextLine(); // Clear the input buffer
				System.out.println("Invalid input. Please enter a positive integer.");
			}
		}

		for (int i = 0; i < numOfTypes; i++) {
			clearScreen();
			System.out.print("\n--- Type " + (i + 1) + " ---"); // Use (i + 1) for Type numbering
			String creatureName = "";
			int creatureHealth = 0;
			int creatureDamage = 0;
			int creatureSpeed = 0;
			int creatureSpecial = -1;
			while (creatureName.isEmpty()) {
				System.out.print("\033[100;0H~$> Type Name: ");
				creatureName = getInput.nextLine();
				if (creatureName.isEmpty()) {
					System.out.println("Name cannot be empty.");
				}
			}
			clearScreen();
			System.out.print("\n--- Type " + (i + 1) + " ---");
			System.out.println("\n\nName: " + creatureName);
			while (creatureHealth <= 0) {
				System.out.print("\033[100;0H~$> Type Base Health: ");
				try {
					creatureHealth = getInput.nextInt();
					getInput.nextLine(); // Consume the newline character
					if (creatureHealth <= 0) {
						System.out.println("Please enter a positive integer for health.");
					}
				} catch (InputMismatchException e) {
					getInput.nextLine(); // Clear the input buffer
					System.out.println("Invalid input. Please enter a positive integer.");
				}
			}
			clearScreen();
			System.out.print("\n--- Type " + (i + 1) + " ---");
			System.out.println("\n\nName: " + creatureName);
			System.out.println("\nHP: " + creatureHealth);

			while (creatureDamage <= 0) {
				System.out.print("\033[100;0H~$> Type Base Damage: ");
				try {
					creatureDamage = getInput.nextInt();
					getInput.nextLine(); // Consume the newline character
					if (creatureDamage <= 0) {
						System.out.println("Please enter a positive integer for damage.");
					}
				} catch (InputMismatchException e) {
					getInput.nextLine(); // Clear the input buffer
					System.out.println("Invalid input. Please enter a positive integer.");
				}
			}
			clearScreen();
			System.out.print("\n--- Type " + (i + 1) + " ---");
			System.out.println("\n\nName: " + creatureName);
			System.out.println("\nHP: " + creatureHealth);
			System.out.println("\nDMG: " + creatureDamage);
			while (creatureSpeed <= 0) {
				System.out.print("\033[100;0H~$> Type Base Speed: ");
				try {
					creatureSpeed = getInput.nextInt();
					getInput.nextLine(); // Consume the newline character
					if (creatureSpeed <= 0) {
						System.out.println("Please enter a positive integer for speed.");
					}
				} catch (InputMismatchException e) {
					getInput.nextLine(); // Clear the input buffer
					System.out.println("Invalid input. Please enter a positive integer.");
				}
			}
			clearScreen();
			System.out.print("\n--- Type " + (i + 1) + " ---");
			System.out.println("\n\nName: " + creatureName);
			System.out.println("\nHP: " + creatureHealth);
			System.out.println("\nDMG: " + creatureDamage);
			System.out.println("\nSPD: " + creatureSpeed);
			while (creatureSpecial < 0 || creatureSpecial > 4) {
				System.out.print("\n\n\n\n\n\n\n" +
						"> SPECIALS <" +
						"\n\n0. None" +
						"\n1. Reflect between 1-15% of dmg taken back" +
						"\n2. Every 5th-15th round increase your dmg between 1-5%" +
						"\n3. Every 5th-15th round become invincible" +
						"\n4. Heal for 1-15% of dmg done\n");
				System.out.print("\033[100;0H~$> Type Special (0-4): ");
				try {
					creatureSpecial = getInput.nextInt();
					getInput.nextLine(); // Consume the newline character
					if (creatureSpecial < 0 || creatureSpecial > 4) {
						System.out.println("Please enter a number between 0 and 4.");
					}
				} catch (InputMismatchException e) {
					getInput.nextLine(); // Clear the input buffer
					System.out.println("Invalid input. Please enter a number between 0 and 4.");
				}
			}
			Creature inputCreature = new Creature(creatureName, creatureHealth, creatureDamage, creatureSpeed,
					creatureSpecial);
			inputCreature.setType(i + 1);
			inputCreatures.add(inputCreature);
		}

		clearScreen();
		System.out.print("\033[100;0HPress  ENTER  to continue... ");
		getInput.nextLine();

		Army army1 = new Army("Army1");
		Army army2 = new Army("Army2");

		while (true) {
			clearScreen();
			System.out.print("\n\nBUILD ARMY 1\n\n");
			displayCreatures(inputCreatures);
			System.out.print("\n\n\n --- ARMY 1 ---");
			displayArmy(army1.getCreatures(), numOfTypes);
			System.out.print("\n\n\n--- COMMANDS ---");
			System.out.print("\n1. ADD CREATURES");
			System.out.print("\n2. NEXT");
			System.out.print("\n~$> Choice: ");

			int choice = 0;

			try {
				choice = getInput.nextInt();
				getInput.nextLine();
			} catch (InputMismatchException e) {
				getInput.nextLine(); // Clear the input buffer
			}

			if (choice == 1) {
				while (true) {
					clearScreen();
					System.out.print("\n\nBUILD ARMY 1\n\n");
					displayCreatures(inputCreatures);
					System.out.print("\n\n\n --- ARMY 1 ---");
					displayArmy(army1.getCreatures(), numOfTypes);
					System.out.print("\033[80;0H~$> ADD TYPE: ");

					int choiceType = 0;

					try {
						choiceType = getInput.nextInt();
						getInput.nextLine();
					} catch (InputMismatchException e) {
						getInput.nextLine(); // Clear the input buffer
					}

					if (choiceType <= 0 || choiceType >= numOfTypes + 1) {
						;
						continue;
					}

					clearScreen();
					System.out.print("\n\nBUILD ARMY 1\n\n");
					displayCreatures(inputCreatures);
					System.out.print("\n\n\n --- ARMY 1 ---");
					displayArmy(army1.getCreatures(), numOfTypes);
					System.out.print("\033[80;0H~$> AMOUNT: ");

					int choiceAmount = 0;

					try {
						choiceAmount = getInput.nextInt();
						getInput.nextLine();
					} catch (InputMismatchException e) {
						getInput.nextLine(); // Clear the input buffer
					}

					if (choiceAmount <= 0) {
						System.out.println("Invalid amount. Please enter a positive number.");
						continue;
					}

					army1.addCreature(inputCreatures.get(choiceType - 1), choiceAmount);
					break;
				}
			} else if (choice == 2) {
				break;
			}
		}

		while (true) {
			clearScreen();
			System.out.print("\n\nBUILD ARMY 2\n");
			displayCreatures(inputCreatures);
			System.out.print("\n\n\n --- ARMY 2 ---");
			displayArmy(army2.getCreatures(), numOfTypes);
			System.out.print("\n\n\n--- COMMANDS ---");
			System.out.print("\n1. ADD CREATURES");
			System.out.print("\n2. NEXT");
			System.out.print("\n~$> Choice: ");

			int choice = 0;

			try {
				choice = getInput.nextInt();
				getInput.nextLine();
			} catch (InputMismatchException e) {
				getInput.nextLine();
			}

			if (choice == 1) {
				while (true) {
					clearScreen();
					System.out.print("\n\nBUILD ARMY 2\n");
					displayCreatures(inputCreatures);
					System.out.print("\n\n\n --- ARMY 2 ---");
					displayArmy(army2.getCreatures(), numOfTypes);
					System.out.print("\033[80;0H~$> ADD TYPE: ");

					int choiceType = 0;

					try {
						choiceType = getInput.nextInt();
						getInput.nextLine();
					} catch (InputMismatchException e) {
						getInput.nextLine();
					}

					if (choiceType <= 0 || choiceType >= numOfTypes + 1) {
						System.out.println("Invalid choice. Please enter a valid type.");
						continue;
					}

					clearScreen();
					System.out.print("\n\nBUILD ARMY 2\n");
					displayCreatures(inputCreatures);
					System.out.print("\n\n\n --- ARMY 2 ---");
					displayArmy(army2.getCreatures(), numOfTypes);
					System.out.print("\033[80;0H~$> AMOUNT: ");

					int choiceAmount = 0;

					try {
						choiceAmount = getInput.nextInt();
						getInput.nextLine();
					} catch (InputMismatchException e) {
						getInput.nextLine();
					}

					if (choiceAmount <= 0) {
						System.out.println("Invalid amount. Please enter a positive number.");
						continue;
					}

					army2.addCreature(inputCreatures.get(choiceType - 1), choiceAmount);
					break;
				}
			} else if (choice == 2) {
				break;
			}
		}

		clearScreen();
		displayOverview(army1, inputCreatures);
		displayOverview(army2, inputCreatures);
		System.out.print("\033[80;0HPress  ENTER  to continue... ");
		getInput.nextLine();

		while (true) {
			clearScreen();
			System.out.print("\n\n\n\n\n\t\t!BATTLE READY!");
			System.out.print("\n\n1. Fight\n2. Simulate\n3. Exit");
			System.out.print("\033[80;0H~$> Choice: ");

			int choice = 0;

			try {
				choice = getInput.nextInt();
				getInput.nextLine(); // Consume the newline character
			} catch (InputMismatchException e) {
				getInput.nextLine(); // Clear the input buffer
			}

			switch (choice) {
				case 1:
					Battle battlee = new Battle(army1, army2);
					clearScreen();
					battlee.fight();
					System.out.print("\033[80;0HPress  ENTER  to continue... ");
					getInput.nextLine();
					break;
				case 2:
					Battle battleee = new Battle(army1, army2);
					clearScreen();
					System.out.print("\033[80;0H~$> Number of Simulations: ");

					int choiceSimulation = 0;

					try {
						choiceSimulation = getInput.nextInt();
						getInput.nextLine(); // Consume the newline character
					} catch (InputMismatchException e) {
						getInput.nextLine(); // Clear the input buffer
					}

					Simulation simulation = new Simulation(battleee, choiceSimulation);
					System.out.print("\033[80;0HPress  ENTER  to continue... ");
					getInput.nextLine();
					break;
				case 3:
					// User chose to exit
					System.exit(0);
					break;
				default:
					// Invalid choice, do nothing and loop again
					break;
			}
		}

		/*
		 * SPECIALS
		 * 0. None
		 * 1. Reflect 10% of dmg taken back
		 * 2. Every 5th round increase your dmg by 20%
		 * 3. Every 5th round become invincible
		 * 4. Heal for 5% of dmg done
		 */

		// Creature creature1 = new Creature("Tank", 3000, 100, 1, 3);
		// Creature creature2 = new Creature("Mage", 250, 500, 3, 4);
		// Creature creature3 = new Creature("Warrior", 500, 250, 3, 2);
		// Creature creature4 = new Creature("Turret", 5000, 10, 1, 1);

		// Army army1 = new Army("Army1");
		// Army army2 = new Army("Army2");

		// army1.addCreature(creature1, 5);
		// army1.addCreature(creature3, 20);
		// army1.addCreature(creature4, 1);

		// army2.addCreature(creature1, 5);
		// army2.addCreature(creature2, 20);
		// army2.addCreature(creature4, 1);

		// Battle battle = new Battle(army1, army2);

		// int simulations = Integer.parseInt(args[0]);
		// Simulation simulation = new Simulation(battle, simulations);

	}

}
