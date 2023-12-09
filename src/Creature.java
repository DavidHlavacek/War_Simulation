package src;

import java.util.*;

public class Creature {

	private String name;
	private int max_health;
	private int health;
	private int damage;
	private int speed;
	private int special;
	private Army army;
	private int type;
	private Random random;
	private Creature enemy; // Add a field to store the enemy


	// Constructor
	public Creature(String name, int health, int damage, int speed, int special) {
		this.name = name;
		this.max_health = health;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.special = special;
		this.army = null;
		this.type = Simulation.type++;

	}

	public Creature(String name, int health, int damage, int speed, int special, int type) {
		this.name = name;
		this.max_health = health;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.special = special;
		this.army = null;
		this.type = type++;
		this.random = new Random();
		randomizeStats(5);
	}

	// Getters
	public Creature getEnemy() {
        return enemy;
    }

	public String getName() {
		return this.name;
	}

	public int getHealth() {
		return this.health;
	}

	public int getMaxHealth() {
		return this.max_health;
	}

	public int getDamage() {
		return this.damage;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getSpecial() {
		return this.special;
	}

	public Army getArmy() {
		return this.army;
	}

	public int getType() {
		return type;
	}

	// Setter
	public void setEnemy(Creature enemy) {
        this.enemy = enemy;
    }
	
	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean setHealth(int health) {
		if (health <= this.max_health) {
			this.health = health;
			return true;
		}
		return false;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public void setArmy(Army army) {
		this.army = army;
	}

	// Methods
	
	private void randomizeStats(int chance) {
		// 1 in 'chance' chance to randomize each stat
		if (random.nextInt(chance) == 0) {
			this.health = Math.max(1, max_health + random.nextInt(101) - 50);
			// Randomize health within the range [max_health - 50, max_health + 50], but ensure it's at least 1
		}
	
		if (random.nextInt(chance) == 0) {
			this.damage = Math.max(1, damage + random.nextInt(101) - 50);
			// Randomize damage within the range [damage - 50, damage + 50], but ensure it's at least 1
		}
	
		if (random.nextInt(chance) == 0) {
			this.speed = Math.max(1, speed + random.nextInt(11) - 5);
			// Randomize speed within the range [speed - 5, speed + 5], but ensure it's at least 1
		}
	
		if (random.nextInt(10) == 0) {
			this.special = random.nextInt(5); // Randomize the ability to a value between 0 and 4
		}
	}
	

	public void takeDamage(int damage) {
		this.setHealth(this.getHealth() - damage);
	}

	public void attackEnemyCreature(Creature enemyCreature, int specialChange) {
		enemyCreature.takeDamage(this.getDamage() + specialChange);
	}

	public void die() {
		this.army.getCreatures().remove(this);
	}

}
