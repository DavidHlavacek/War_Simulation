package src;    

import java.util.*;

public class Simulation
{

	private Battle battle;
	private Army army1, army2;
	private ArrayList<Creature> army1C, army2C;
	int army1W = 0;
	int army2W = 0;

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
						creature.getSpecial());
				copy.setArmy(this.army1);
				this.army1.addCreature(copy);
			}
			
			for(Creature creature: battle.getArmy2().getCreatures())
			{
				Creature copy = new Creature(creature.getName(), creature.getHealth(),
							creature.getDamage(), creature.getSpeed(),
						creature.getSpecial());
				copy.setArmy(this.army2);
				this.army2.addCreature(copy);
			}
			
			this.battle = new Battle(this.army1, this.army2);

			Army winner = this.battle.fight();

			if(winner.getName()=="Army1"){army1W++;}
			else{army2W++;}

				
		}
		System.out.println("\n\nArmy 1: " + army1W + " wins");
		System.out.println("Army 2: " + army2W + " wins");
		
	}
	
	public static void main(String[] args){
		
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
		// army1.addCreature(creature3, 20);
		// army1.addCreature(creature4, 1);

		army2.addCreature(creature1, 5);
		// army2.addCreature(creature2, 20);
		// army2.addCreature(creature4, 1);

	
		
		
		Battle battle = new Battle(army1, army2);
		
		int simulations = Integer.parseInt(args[0]);
		Simulation simulation = new Simulation(battle, simulations);
		

	}		
		
		










}
