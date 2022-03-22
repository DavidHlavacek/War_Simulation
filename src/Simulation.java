package src;    

import java.util.*;

public class Simulation
{

	private Battle battle;
	private Army army1, army2;
	private ArrayList<Creature> army1C, army2C;
	int army1W = 0;
	int army2W = 0;
	static int simulation = 1;
	public static int type = 0;

	public Simulation(Battle battle, int repetition)
	{

		this.army1C = new ArrayList<Creature>();
		this.army2C = new ArrayList<Creature>();

		for(int i = 0; i<repetition; i++)
		{
			
			this.army1 = new Army(battle.getArmy1().getName());
			this.army2 = new Army(battle.getArmy2().getName());

			for(Creature creature: battle.getArmy1().getCreatures())
			{
				Creature copy = new Creature(creature.getName(), creature.getHealth(),
							creature.getDamage(), creature.getSpeed(),
						creature.getSpecial(), creature.getType());
				copy.setArmy(this.army1);
				this.army1.addCreature(copy);
			}
			
			for(Creature creature: battle.getArmy2().getCreatures())
			{
				Creature copy = new Creature(creature.getName(), creature.getHealth(),
							creature.getDamage(), creature.getSpeed(),
						creature.getSpecial(), creature.getType());
				copy.setArmy(this.army2);
				this.army2.addCreature(copy);
			}
			
			this.battle = new Battle(this.army1, this.army2);

			Army winner = this.battle.fight();

			if(winner.getName()=="Army1"){army1W++;}
			else{army2W++;}

				
		}
		System.out.println("\n\n\n\n+++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("\n\t\tArmy 1: " + army1W + " wins");
		System.out.println("\t\tArmy 2: " + army2W + " wins");
		System.out.println("\t\tUltimate Winner:");
		System.out.println("\n\t\t    " + ((army1W>army2W)?"Army 1":"Army 2"));
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++\n\n\n");		
	}

	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush(); 
		System.out.println("\033[31m++++++++ WAR SIMULATOR ++++++++"); 
	}  
	public static void displayCreatures(ArrayList<Creature> array) {  
		int i = 0;
		System.out.println("\n\n--- Creatures ---");
		System.out.printf("\n%7s %25s %10s %10s %10s %10s" ,"|Type|", "|Name|", "|HP|", "|DMG|", "|SPD|", "|POW|\n");
		for(Creature creature: array)
		{
			System.out.println();	
			System.out.format("%7s %25s %10s %10s %10s %10s", i++, creature.getName(), creature.getHealth(), 
			creature.getDamage(), creature.getSpeed(), creature.getSpecial());	
			System.out.println();
		}
	} 
	public static void displayArmy(ArrayList<Creature> array, int n) {  
		int[] type = new int[n];
		for(Creature creature: array)
		{
			int typeNumber = creature.getType();
			type[typeNumber]++;
		}
		System.out.printf("\n\n%7s %10s" ,"|Type|", "|AMOUNT|\n");
		for(int i = 0; i<n; i++)
		{
			System.out.println();	
			System.out.format("%7s %10s", i, type[i]);
			System.out.println();
		}
		
	}  	
	public static void main(String[] args){
	
				
		Scanner getInput = new Scanner(System.in);	
		ArrayList<Creature> inputCreatures = new ArrayList<Creature>();
		
		clearScreen();	
		System.out.print("\033[100;0H$> Different Type of Creatures: ");	
		int numOfTypes = getInput.nextInt();
		getInput.nextLine();

		for(int i = 0; i<numOfTypes; i++)
		{
			clearScreen();
			System.out.print("\n--- Type " + i +" ---");
			System.out.print("\033[100;0H$>Type Name: ");
			String creatureName = getInput.nextLine();
			clearScreen();
			System.out.print("\n--- Type " + i +" ---");
			System.out.println("\n\nName: " + creatureName);	
			System.out.print("\033[100;0H$>Type Health: ");
			int creatureHealth = getInput.nextInt();
			getInput.nextLine();
			clearScreen();
			System.out.print("\n--- Type " + i +" ---");
			System.out.println("\n\nName: " + creatureName);	
			System.out.println("\nHP: " + creatureHealth);
			System.out.print("\033[100;0H$>Type Damage: ");
			int creatureDamage = getInput.nextInt();
			getInput.nextLine();
			clearScreen();
			System.out.print("\n--- Type " + i +" ---");
			System.out.println("\n\nName: " + creatureName);	
			System.out.println("\nHP: " + creatureHealth);
			System.out.println("\nDMG: " + creatureDamage);
			System.out.print("\033[100;0H$>Type Speed: ");
			int creatureSpeed = getInput.nextInt();
			getInput.nextLine();
			clearScreen();
			System.out.print("\n--- Type " + i +" ---");
			System.out.println("\n\nName: " + creatureName);	
			System.out.println("\nHP: " + creatureHealth);
			System.out.println("\nDMG: " + creatureDamage);
			System.out.println("\nSPD: " + creatureSpeed);
			System.out.print("\n\n\n\n\n\n\n\n\n\n\n" +
								"> SPECIALS <" +
								"\n\n0. None" +
								"\n1. Reflect 10% of dmg taken back" +
								"\n2. Every 5th round increase your dmg by 20%" + 
								"\n3. Every 5th round become invincible" +
								"\n4. Heal for 5% of dmg done");
			System.out.print("\033[100;0H$>Type Special: ");
			int creatureSpecial = getInput.nextInt();
			getInput.nextLine();
			Creature inputCreature = new Creature(creatureName, creatureHealth, creatureDamage, creatureSpeed, creatureSpecial);
            inputCreatures.add(inputCreature);
			
		}				

		clearScreen();
		System.out.print("\033[100;0HPress  ENTER  to continue...");
		getInput.nextLine();

		Army army1 = new Army("Army1");
		Army army2 = new Army("Army2");
		
		while(true)
		{
			clearScreen();	
			System.out.print("\n\nBUILD ARMY 1\n\n");
			displayCreatures(inputCreatures);
			System.out.print("\n\n\n --- ARMY 1 ---");
			displayArmy(army1.getCreatures(), numOfTypes);
			System.out.print("\n\n\n--- COMMANDS ---");	
			System.out.print("\n\n1. ADD CREATURES");
			System.out.print("\n2. EXIT");
			System.out.print("\033[80;0H$>Choice: ");
			int choice = getInput.nextInt();
			getInput.nextLine();
			if(choice==1)
			{
				clearScreen();
				System.out.print("\n\nBUILD ARMY 1\n\n");	
				displayCreatures(inputCreatures);
				System.out.print("\n\n\n --- ARMY 1 ---");
				displayArmy(army1.getCreatures(), numOfTypes);
				System.out.print("\033[80;0H$>ADD TYPE: ");
				int choiceType = getInput.nextInt();
				getInput.nextLine();
				clearScreen();
				System.out.print("\n\nBUILD ARMY 1\n\n");	
				displayCreatures(inputCreatures);
				System.out.print("\n\n\n --- ARMY 1 ---");
				displayArmy(army1.getCreatures(), numOfTypes);
				System.out.print("\033[80;0H$>AMOUNT: ");
				int choiceAmount = getInput.nextInt();
				getInput.nextLine();

				army1.addCreature(inputCreatures.get(choiceType), choiceAmount);

			}
			else {break;}

		}
		
		while(true)
		{
			clearScreen();
			System.out.print("\n\nBUILD ARMY 2\n\n");	
			displayCreatures(inputCreatures);
			System.out.print("\n\n\n --- ARMY 2 ---");
			displayArmy(army2.getCreatures(), numOfTypes);
			System.out.print("\n\n\n--- COMMANDS ---");	
			System.out.print("\n\n1. ADD CREATURES");
			System.out.print("\n2. EXIT");
			System.out.print("\033[80;0H$>Choice: ");
			int choice = getInput.nextInt();
			getInput.nextLine();
			if(choice==1)
			{
				clearScreen();
				System.out.print("\n\nBUILD ARMY 2\n\n");	
				displayCreatures(inputCreatures);
				System.out.print("\n\n\n --- ARMY 2 ---");
				displayArmy(army2.getCreatures(), numOfTypes);
				System.out.print("\033[80;0H$>ADD TYPE: ");
				int choiceType = getInput.nextInt();
				getInput.nextLine();
				clearScreen();
				System.out.print("\n\nBUILD ARMY 2\n\n");	
				displayCreatures(inputCreatures);
				System.out.print("\n\n\n --- ARMY 2 ---");
				displayArmy(army2.getCreatures(), numOfTypes);
				System.out.print("\033[80;0H$>AMOUNT: ");
				int choiceAmount = getInput.nextInt();
				getInput.nextLine();

				army2.addCreature(inputCreatures.get(choiceType), choiceAmount);

			}
			else {break;}

		}

		clearScreen();
		System.out.print("\033[80;0HPress  ENTER  to continue...");
		getInput.nextLine();

		while(true)
		{
			clearScreen();
			System.out.print("\n\n\n\n\n\t\t!BATTLE READY!");	
			System.out.print("\n\n1.Fight\n2.Simulate\n3.Exit");
			System.out.print("\033[80;0H$>Choice: ");
			int choice = getInput.nextInt();
			getInput.nextLine();
			if(choice == 1)
			{
				Battle battlee = new Battle(army1, army2);
				clearScreen();
				battlee.fight();
				System.out.print("\033[80;0HPress  ENTER  to continue...");
				getInput.nextLine();
				break;
			}	
			else if(choice == 2)
			{
				Battle battleee = new Battle(army1, army2);
				clearScreen();	
				System.out.print("\033[80;0H$>Number of Simulations: ");
				int choiceSimulation = getInput.nextInt();
				getInput.nextLine();
				Simulation simulation = new Simulation(battleee, choiceSimulation);
				System.out.print("\033[80;0HPress  ENTER  to continue...");
				getInput.nextLine();	
				break;
			}
			else {break;}
			
		}
		/*
		SPECIALS
		0. None
		1. Reflect 10% of dmg taken back
		2. Every 5th round increase your dmg by 20%
		3. Every 5th round become invincible
		4. Heal for 5% of dmg done
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
