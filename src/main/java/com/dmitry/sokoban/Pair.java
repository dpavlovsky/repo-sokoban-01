package main.java.com.dmitry.sokoban;

public class Pair<X, Y> {

	private final X x;
	private final Y y;

	public Pair(X first, Y second) {
		this.x = first;
		this.y = second;
	}

	public X getX() {
		return x;
	}

	public Y getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return x.hashCode() ^ y.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair))
			return false;
		
		@SuppressWarnings("unchecked")
		Pair<X, Y> pairo = (Pair<X, Y>) o;
		return this.x.equals(pairo.getX()) && this.y.equals(pairo.getY());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return y + "" + x;
	}
}