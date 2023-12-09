package src;

import java.util.*;
import java.util.logging.Logger;

public class Battle {
	private static final Logger LOGGER = Logger.getLogger(Battle.class.getName());

	Army army1, army2;
	int rounds;
	private Random random = new Random();

	private static final int SPECIAL_ABILITY_1 = 1;
	private static final int SPECIAL_ABILITY_2 = 2;
	private static final int SPECIAL_ABILITY_3 = 3;
	private static final int SPECIAL_ABILITY_4 = 4;

	private static int SPECIAL_REFLECT_DAMAGE_MIN = 1;
	private static int SPECIAL_REFLECT_DAMAGE_MAX = 15;

	private static int SPECIAL_INVINCIBLE_ROUNDS_MIN = 5;
	private static int SPECIAL_INVINCIBLE_ROUNDS_MAX = 15;

	private static int SPECIAL_INCREASE_DAMAGE_ROUNDS_MIN = 5;
	private static int SPECIAL_INCREASE_DAMAGE_ROUNDS_MAX = 15;
	private static int SPECIAL_INCREASE_DAMAGE_MIN = 1;
	private static int SPECIAL_INCREASE_DAMAGE_MAX = 5;

	private static int SPECIAL_HEAL_MIN = 1;
	private static int SPECIAL_HEAL_MAX = 15;

	// Constructor
	public Battle(Army army1, Army army2) {
		this.army1 = army1;
		this.army2 = army2;
		this.rounds = 0;

	}

	// Getters
	public Army getArmy1() {
		return this.army1;
	}

	public Army getArmy2() {
		return this.army2;
	}

	public int getRounds() {
		return this.rounds;
	}

	// Setters
	public void setArmy1(Army army1) {
		this.army1 = army1;
	}

	public void setArmy2(Army army2) {
		this.army2 = army2;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	// Methods

	public Creature getRandomCreatureArmy1() {
		ArrayList<Creature> array = army1.getCreatures();
		if (!array.isEmpty()) {
			int index = random.nextInt(array.size());
			Creature creature = array.get(index);
			return creature;
		}
		return null;
	}

	public Creature getRandomCreatureArmy2() {
		ArrayList<Creature> array = army2.getCreatures();
		if (!array.isEmpty()) {
			int index = random.nextInt(array.size());
			Creature creature = array.get(index);
			return creature;
		}
		return null;
	}

	public int getPriority(Creature c1, Creature c2) {
		if (c1.getSpeed() == c2.getSpeed()) {
			return (random.nextInt(3 - 1) + 1);
		}
		return (c1.getSpeed() > c2.getSpeed()) ? 1 : 2;
	}

	public boolean check(Creature first, Creature second) {
		if (first.getHealth() < 1) {
			first.die();
			return false;
		}
		if (second.getHealth() < 1) {
			second.die();
			return false;
		}
		return true;
	}

	public void duel(Creature first, Creature second) {
		// Log information about the start of the duel
		// LOGGER.info("Start round: " + this.rounds);
		// logCreatureStats("First Creature", first);
		// logCreatureStats("Second Creature", second);
		// logCreatureStats("Initial Stats", first, second);

		// Check if it's the 5th round and the first creature has special ability 3
		int rounds_for_invincibility_first = random.nextInt(SPECIAL_INVINCIBLE_ROUNDS_MAX
				- SPECIAL_INVINCIBLE_ROUNDS_MIN + 1) + SPECIAL_INVINCIBLE_ROUNDS_MIN;
		boolean firstCreatureInvincible = (this.rounds % rounds_for_invincibility_first == 0 && first.getSpecial() == 3);
		if (this.check(first, second)) {
			if (!firstCreatureInvincible) {
				// Simulate the first creature attacking the second creature
				int specialChange = calculateSpecialChange(first);
				first.attackEnemyCreature(second, specialChange);

				// Log information after the first creature attacks
				// logCreatureStats("After " + first.getName() + " attacks", first, second);

				// Check if the second creature has died
				if (!check(first, second)) {
					return; // If the second creature has died, exit the method
				}
			}
		}

		// Check if it's the 5th round and the second creature has special ability 3
		int rounds_for_invincibility_second = random.nextInt(SPECIAL_INVINCIBLE_ROUNDS_MAX
				- SPECIAL_INVINCIBLE_ROUNDS_MIN + 1) + SPECIAL_INVINCIBLE_ROUNDS_MIN;
		boolean secondCreatureInvincible = (this.rounds % rounds_for_invincibility_second == 0
				&& second.getSpecial() == 3);
		if (this.check(first, second)) {

			if (!secondCreatureInvincible) {
				// Simulate the second creature attacking the first creature
				int specialChange = calculateSpecialChange(second);
				second.attackEnemyCreature(first, specialChange);

				// Log information after the second creature attacks
				// logCreatureStats("After " + second.getName() + " attacks", first, second);

				// Check if the first creature has died after the second attack
				check(first, second);
			}
		}
		// Increment rounds
		this.check(first, second);
		// LOGGER.info("End round: " + this.rounds);
	}

	private int calculateSpecialChange(Creature creature) {
		int specialChange = 0;
		int creatureSpecial = creature.getSpecial();

		if (creatureSpecial == SPECIAL_ABILITY_1) {
			// Apply special ability 1: Reflect between 1% and 15% of damage taken back
			specialChange = random.nextInt(SPECIAL_REFLECT_DAMAGE_MAX - SPECIAL_REFLECT_DAMAGE_MIN + 1)
					+ SPECIAL_REFLECT_DAMAGE_MIN;
			specialChange = (int) (specialChange / 100.0 * creature.getEnemy().getDamage());
		} else if (creatureSpecial == SPECIAL_ABILITY_2) {
			// Apply special ability 2: Every 5th round increase your damage by a random
			// value between 1% and SPECIAL_INCREASE_DAMAGE_PERCENTAGE
			int rounds_for_increase_damage = random.nextInt(SPECIAL_INCREASE_DAMAGE_ROUNDS_MAX
					- SPECIAL_INCREASE_DAMAGE_ROUNDS_MIN + 1) + SPECIAL_INCREASE_DAMAGE_ROUNDS_MIN;
			if (this.rounds % rounds_for_increase_damage == 0) {
				int randomIncrease = random.nextInt(SPECIAL_INCREASE_DAMAGE_MAX - SPECIAL_INCREASE_DAMAGE_MIN + 1)
						+ SPECIAL_INCREASE_DAMAGE_MIN;
				specialChange = (int) (randomIncrease / 100.0 * creature.getDamage());
			}
		} else if (creatureSpecial == SPECIAL_ABILITY_4) {
			// Apply special ability 4: Heal for between 1% and 15% of damage done
			specialChange = random.nextInt(SPECIAL_HEAL_MAX - SPECIAL_HEAL_MIN + 1) + SPECIAL_HEAL_MIN;
			specialChange = (int) (specialChange / 100.0 * (creature.getDamage() + specialChange));
		}

		return specialChange;
	}

	private void logCreatureStats(String message, Creature... creatures) {
		for (Creature creature : creatures) {
			StringBuilder stats = new StringBuilder(message + " - " + creature.getName() +
					" HP: " + creature.getHealth() +
					" | DMG: " + creature.getDamage() +
					" | Speed: " + creature.getSpeed() +
					" | Special: " + creature.getSpecial());
		}
	}
	
	

	public Army fight() {
		// LOGGER.info("Starting fight between " + army1.getName() + " and " +
		// army2.getName());

		while (!(army1.getCreatures().isEmpty()) && !(army2.getCreatures().isEmpty()) && getRounds() < 10000) {
			Creature randomCreatureArmy1 = getRandomCreatureArmy1();
			Creature randomCreatureArmy2 = getRandomCreatureArmy2();

			int priority = getPriority(randomCreatureArmy1, randomCreatureArmy2);

			Creature first, second;

			if (priority == 1) {
				first = randomCreatureArmy1;
				second = randomCreatureArmy2;
			} else {
				first = randomCreatureArmy2;
				second = randomCreatureArmy1;
			}

			randomCreatureArmy1.setEnemy(randomCreatureArmy2);
			randomCreatureArmy2.setEnemy(randomCreatureArmy1);

			this.duel(first, second);

			this.setRounds(getRounds() + 1);

		}
		Army winner = (army1.getSize() > army2.getSize()) ? army1 : army2;
		System.out.println("\n\n------------" + "Simulation " + Simulation.simulation++ + "------------\n");
		System.out.println("\tWinner: " + winner.getName());
		System.out.println("\tRounds: " + this.getRounds());
		int count = 0;
		System.out.println("\n\n\t   Alive: \n");
		for (Creature creature : winner.getCreatures()) {
			System.out.println("\t" + creature.getName() + " | HP: " + creature.getHealth());

			count++;
		}
		System.out.println("\n\tTotal: " + count);
		System.out.println("\n-----------------------------------------\n\n");
		// LOGGER.info("Finished fight between " + army1.getName() + " and " +
		// army2.getName());

		return winner;

	}

}