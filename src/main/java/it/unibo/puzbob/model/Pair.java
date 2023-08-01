package it.unibo.puzbob.model;

/*
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString well implemented. 
 */

public class Pair<X,Y> {
	
	private final X x;
	private final Y y;
	
	public Pair(X x, Y y) {
		super();
		this.x = x;
		this.y = y;
	}

	public X getX() {
		return x;
	}

	public Y getY() {
		return y;
	}

	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}

}
