package src;    

import java.util.*;

public class Main
{

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

		Creature creature1 = new Creature("Tank", 20000, 50, 1, 3);
		Creature creature2 = new Creature("Mage", 500, 500, 3, 4);
		Creature creature3 = new Creature("Warrior", 5000, 250, 3, 2);
		Creature creature4 = new Creature("Turret", 10000, 10, 1, 1);

		


		Army army1 = new Army("Army1");
		Army army2 = new Army("Army2");
		
		army2.addCreature(creature4, 1);
		army1.addCreature(creature2, 1);
		
		
		
		Battle battle = new Battle(army1, army2);
		battle.fight();	
	}		
		
		










}
