package src;

public class Creature
{
	
	private String name;
	private int max_health;
	private int health;
	private int damage;
	private int speed;
	private int special;
	private Army army;
	
	//Constructor
	public Creature(String name, int health, int damage, int speed, int special)
	{
		this.name = name;
		this.max_health = health;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.special = special;
		this.army = null;
	}

	//Getters
	public String getName()
	{
		return this.name;
	}

	public int getHealth()
	{
		return this.health;
	}

	public int getMaxHealth()
	{
		return this.max_health;
	}

	public int getDamage()
	{
		return this.damage;
	}

	public int getSpeed()
	{
		return this.speed;
	}

	public int getSpecial()
	{
		return this.special;
	}

	public Army getArmy()
	{
		return this.army;
	}

	//Setter
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean setHealth(int health)
	{
		if(health<=max_health){this.health = health; return true;}
		return false;
	}

	public void setDamage(int damage)	
	{
		this.damage = damage;
	}

	public void setSpeed(int speed)	
	{
		this.speed = speed;
	}

	public void setSpecial(int special)
	{
		this.special = special;
	}

	public void setArmy(Army army)
	{
		this.army = army;
	}

	//Methods
	public void takeDamage(int damage)
	{
		this.setHealth(this.getHealth() - damage);
	}
	
	public void attackEnemyCreature(Creature enemyCreature, int specialChange)
	{
		enemyCreature.takeDamage(this.getDamage() + specialChange);
	}
	
	public void die()
	{
		this.army.getCreatures().remove(this);
	}			
				

}
