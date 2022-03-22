package src;    

import java.util.*;

public class Main
{

	public static void simulation(Army army1, Army army2, int repetitions)
	{
		Army winner;
		int army1Win = 0;
		int army2Win = 0;
		for(int x = 0; x<repetitions; x++)
		{
			// Creature creature1Sim = new Creature("Tank", 3000, 100, 1, 3);
			// Creature creature2Sim = new Creature("Mage", 400, 300, 3, 4);
			// Creature creature3Sim = new Creature("Warrior", 400, 300, 3, 2);
			// Creature creature4Sim = new Creature("Turret", 5000, 10, 1, 1);

			Creature creature1Sim = new Creature("1", 500, 200, 3, 0);
			Creature creature2Sim = new Creature("2", 500, 200, 1, 0);	

			Army army1Sim = new Army(army1.getName());
			Army army2Sim = new Army(army2.getName());

			army1Sim.addCreature(creature1Sim, 5);
			// army1Sim.addCreature(creature3Sim, 20);
			// army1Sim.addCreature(creature4Sim, 1);

			army2Sim.addCreature(creature2Sim, 5);
			// army2Sim.addCreature(creature2Sim, 20);
			// army2Sim.addCreature(creature4Sim, 1);

			Battle battleSim = new Battle(army1Sim, army2Sim);

			winner = battleSim.fight();

			if(winner.getName() == "Army1"){army1Win++;}
			if(winner.getName() == "Army2"){army2Win++;}

		}

		System.out.println("\n\nRepetitions: " + repetitions + "\nArmy 1: " + army1Win + " wins"
							+ "\nArmy 2: " + army2Win + " wins");
		Army bigWinner = (army1Win>army2Win)?army1:army2;
		System.out.println("\n\nWINNER: " + bigWinner.getName());
	}
	public static void main(String[] args)
	{
		
		/*
		SPECIALS
		0. None
		1. Reflect 10% of dmg taken back
		2. Every 5th round increase your dmg by 20%
		3. Every 5th round become invincible
		4. Heal for 5% of dmg done
		*/

		Creature creature1 = new Creature("Tank", 3000, 100, 1, 3);
		Creature creature2 = new Creature("Mage", 250, 500, 3, 4);
		Creature creature3 = new Creature("Warrior", 500, 250, 3, 2);
		Creature creature4 = new Creature("Turret", 5000, 10, 1, 1);

		


		Army army1 = new Army("Army1");
		Army army2 = new Army("Army2");
		
		army1.addCreature(creature1, 5);
		army1.addCreature(creature3, 20);
		army1.addCreature(creature4, 1);

		army2.addCreature(creature1, 5);
		army2.addCreature(creature2, 20);
		army2.addCreature(creature4, 1);

	
		
		
		Battle battle = new Battle(army1, army2);
		
		simulation(army1, army2, 10000);
		

	}		
		
		










}
