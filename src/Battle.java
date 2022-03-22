package src;

import java.util.*;

public class Battle
{

	Army army1, army2;
	int rounds;
	private Random random = new Random();
	static long seed = 0L;


	//Constructor
	public Battle(Army army1, Army army2)
	{
		this.army1 = army1;
		this.army2 = army2;
		this.rounds = 0;
	}

	//Getters
	public Army getArmy1()
	{
		return this.army1;
	}

	public Army getArmy2()
	{
		return this.army2;
	}
	
	public int getRounds()
	{
		return this.rounds;
	}

	//Setters	
	public void setArmy1(Army army1)
	{
		this.army1 = army1;
	}

	public void setArmy2(Army army2)
	{
		this.army2 = army2;
	}
	
	public void setRounds(int rounds)
	{
		this.rounds = rounds;
	}

	//Methods
	public Creature getRandomCreatureArmy1()
	{
		ArrayList<Creature> array = army1.getCreatures();
		if(!array.isEmpty())
		{	
			int index = random.nextInt(array.size());
			Creature creature = array.get(index);
			return creature;
		}
		return null;
	}

	public Creature getRandomCreatureArmy2()
	{
		ArrayList<Creature> array = army2.getCreatures();
		if(!array.isEmpty())
		{
			int index = random.nextInt(array.size());
			Creature creature = array.get(index);
			return creature;
		}
		return null;
	}

	public int getPriority(Creature c1, Creature c2)
	{
		if(c1.getSpeed() == c2.getSpeed()){return (random.nextInt(3-1)+1);}
		return (c1.getSpeed() > c2.getSpeed()) ? 1:2;
	}

	public boolean check(Creature first, Creature second)
	{
		if(first.getHealth()<1){first.die();return false;}
		if(second.getHealth()<1){second.die();return false;}
		return true;
	}

	public void duel(Creature first, Creature second)
	{		
		int specialChange = 0;
		boolean invincible = false;
		boolean heal = false;
		if(this.check(first, second))
		{
			switch(first.getSpecial())
			{
				case 0:
					specialChange = 0;
					invincible=false;
					break;
				case 2:
					if(this.getRounds() % 5 == 0){specialChange = (int)(first.getDamage()*(20.0f/100.0f));}
					else{specialChange=0;}
					break;
				case 4:
					heal = first.setHealth(first.getHealth()+(int)(first.getDamage()*(5.0f/100.0f)));
					break;
				default:
					break;
			}

			switch(second.getSpecial())
			{
				case 0:
					specialChange = 0;
					invincible=false;
					break;
				case 3:
					if(this.getRounds() % 5 == 0){
						invincible=true;
						if(first.getSpecial()==4 && heal){first.setHealth(first.getHealth()-(int)(first.getDamage()*(5.0f/100.0f)));}

					}
					else{invincible=false;}
					break;
				default:
					break;
			}

			if(!invincible)
			{first.attackEnemyCreature(second, specialChange);}
			
			if(second.getSpecial()==1){first.setHealth(first.getHealth()-(int)(first.getDamage()*(10.0f/100.0f)));}

		}	
		specialChange = 0;
		invincible = false;

		if(this.check(first, second))
		{
			switch(second.getSpecial())
			{
				case 0:
					specialChange = 0;
					invincible=false;
					break;
				case 2:
					if(this.getRounds() % 5 == 0){specialChange = (int)(second.getDamage()*(20.0f/100.0f));}
					else{specialChange=0;}
					break;
				case 4:
					heal = second.setHealth(second.getHealth()+(int)(second.getDamage()*(5.0f/100.0f)));
					break;
				default:
					break;
			}

			switch(first.getSpecial())
			{
				case 0:
					specialChange = 0;
					invincible=false;
					break;
				case 3:
					if(this.getRounds() % 5 == 0){
						invincible=true;
						if(second.getSpecial()==4 && heal){second.setHealth(second.getHealth()-(int)(second.getDamage()*(5.0f/100.0f)));}

					}
					else{invincible=false;}
					break;
				default:
					break;
			}

			if(!invincible)
			{second.attackEnemyCreature(first, specialChange);}
		
			if(first.getSpecial()==1){second.setHealth(second.getHealth()-(int)(second.getDamage()*(10.0f/100.0f)));}

		}	
	}

	public Army fight()
	{
		while(!(army1.getCreatures().isEmpty()) && !(army2.getCreatures().isEmpty()))
		{
			Creature randomCreatureArmy1 = getRandomCreatureArmy1();
			Creature randomCreatureArmy2 = getRandomCreatureArmy2();
			
			int priority = getPriority(randomCreatureArmy1, randomCreatureArmy2);
			
			Creature first, second;

			if(priority == 1)
			{
				first = randomCreatureArmy1;
				second = randomCreatureArmy2;
			}
			else
			{
				first = randomCreatureArmy2;
				second = randomCreatureArmy1;
			}
			
			this.duel(first, second);
			
			this.setRounds(getRounds()+1);		
		}	
		Army winner = (army1.getSize()>army2.getSize())?army1:army2;
		System.out.println("Winner: " + winner.getName());
		System.out.println("Rounds: " + this.getRounds());
		int count = 0;
		System.out.println("\nAlive: ");
		for(Creature creature: winner.getCreatures())
		{
			System.out.println(creature.getName() + " HP: " + creature.getHealth());

			count++;
		}
		System.out.println("Total: " + count);
		return winner;

		
	}

	

}
