package it.unibo.puzbob.model;

/**
 * This is a generic class of pair.
 *
 * @param <X> The type of the first value
 * @param <Y> The type of the second value
 */
public class Pair<X,Y> {

	private final X x;
	private final Y y;
	
	/**
	 * Constructor of Pair
	 * @param x first value
	 * @param y second value
	 */
	public Pair(X x, Y y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter first value
	 * @return a generic type
	 */
	public X getX() {
		return x;
	}

	/**
	 * Getter second value
	 * @return a generic type
	 */
	public Y getY() {
		return y;
	}

	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}

}
