package WarSim;

import java.util.*;

public class Army
{
	
	private String name;
	private ArrayList<Creature> creatures;

	//Constructor
	public Army(String name)
	{
		this.name = name;
		this.creatures = new ArrayList<Creature>();
	}

	//Getters
	public String getName()
	{
		return this.name;
	}

	public ArrayList<Creature> getCreatures()
	{
		return this.creatures;
	}

	public int getSize()
	{
		return this.getCreatures().size();
	}

	//Setters
	public void setName(String name)
	{
		this.name = name;
	}

	public void setCreatures(ArrayList<Creature> creatures)
	{
		this.creatures = creatures;
	}

	//Methods
	public void addCreature(Creature creature)
	{
		creature.setArmy(this);
		this.creatures.add(creature);
	}

	public void addCreature(Creature creature, int copies)
	{
		for(int x = 0; x < copies; x++)
		{
			Creature shallowCopy = new Creature(creature.getName(), creature.getHealth(), 
								creature.getDamage(), creature.getSpeed(), creature.getSpecial());
			shallowCopy.setArmy(this);
			this.creatures.add(shallowCopy);
		}
	}	

}
