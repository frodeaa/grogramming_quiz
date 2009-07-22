import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 
 * Class simulating the 'Goat' 'quandrum'.
 * <p/>
 * Suppose you're on a game show, and you're given the choice of three doors:<br>
 * </ul>
 * <li>Behind one door is a car</li>
 * <li>behind the others, goats.</li>
 * </ul> <br>
 * You pick a door, say <code>No.1</code>, and the host, who knows what's behind
 * the doors, opens another door, say <code>No.3</code>, which has a goat. He
 * then says to you, <em>"Do you
 * want to pick door <code>No.2</code>?"</em>
 * </p>
 * Is it to your advantage to switch your choice? (Whitaker 1990).</p>
 * 
 * @see 
 * <ul>
 *	<li><a href="http://en.wikipedia.org/wiki/Monty_Hall_problem">
 *               http://en.wikipedia.org/wiki/Monty_Hall_problem</a>
 *	<li><a href="http://mathforum.org/dr.math/faq/faq.monty.hall.html">
 *               http://mathforum.org/dr.math/faq/faq.monty.hall.html</a>
 *</ul>
 */

public class Goats {

	private static Random generator = new Random();
	private Map<Door, Boolean> doors = new HashMap<Door, Boolean>();
	private Map<Strategy, Integer> points = new HashMap<Strategy, Integer>();
	private Map<Strategy, Integer> trials = new HashMap<Strategy, Integer>();

	private Door choice1, choice2, revealed;
	private Strategy strategy;

	/** Door hiding goats? */
	enum Door {
		One, Two, Three
	}

	/** Strategies available. */
	enum Strategy {
		Do_not_switch_door, 
		Swith_to_the_other_door, 
		Random_chooose_one_of_the_to_remainding
	};

	public Goats() {
		for (Strategy strategy : Strategy.values()) {
			trials.put(strategy, 0);
			points.put(strategy, 0);
		}
	}

	/** Hide the goats. */
	public void initDooor() {
		for (Door door : Door.values()) {
			doors.put(door, false);

		}
		Door prize = randomDoor();
		doors.put(prize, true);
	}

	/** Choose a door randomly. */
	public void choose1() {
		choice1 = randomDoor();
	}

	/** Reveal a door with a goat. */
	public void reveal() {
		do {
			revealed = randomDoor();
		} while (revealed == choice1 || doors.get(revealed));
	}

	/** Reevaluate strategy selected. */
	public void choose2(Strategy strategy) {
		switch (strategy) {
		case Do_not_switch_door:
			choice2 = choice1;
			break;
		case Swith_to_the_other_door:
			for (Door door : Door.values()) {
				choice2 = door;
				if (choice2 != choice1 && choice2 != revealed)
					break;
			}
			break;
		case Random_chooose_one_of_the_to_remainding:
			do
				choice2 = randomDoor();
			while (revealed == choice2);
			break;
		}
	}

	/** Update Score and trials. */
	public void score(Strategy strategy) {
		trials.put(strategy, trials.get(strategy) + 1);
		points.put(strategy, points.get(strategy)
				+ (doors.get(choice2) ? 1 : 0));
	}

	/** Start the simulation. */
	public static void main(String[] args) {

		Goats goats = new Goats();

		while (goats.trials.get(Strategy.Do_not_switch_door) < 1000000) {
			goats.selecteStrategy();
			goats.initDooor();
			goats.choose1();
			goats.reveal();
			goats.choose2(goats.strategy);
			goats.score(goats.strategy);
		}
		System.out.println(goats);
	}

	private Door randomDoor() {
		return Door.values()[generator.nextInt(Door.values().length)];
	}

	private void selecteStrategy() {
		strategy = Strategy.values()[generator
				.nextInt(Strategy.values().length)];
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
                buffer.append("\n");
		for (Strategy strategy : Strategy.values()) {
                    buffer.append(String.format("\t· Strategy '%s' scored: %s", 
						strategy.toString().replaceAll("_"," "),
					          (double) points.get(strategy)
							/ (double) trials.get(strategy)) + "\n");
		}
		return buffer.toString();
	}
}
