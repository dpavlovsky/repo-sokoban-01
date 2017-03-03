package com.dmitry.sokoban;

final public class Position {
	private final int m; // number of rows
	private final int n; // number of columns
	private final char[][] data; // M-by-N array
	// private Position reachable;

	// public byte x, y;

	// create M-by-N Position of 0's
	// public Position(int m, int n) {
	// this.m = m;
	// this.n = n;
	// data = new char[m][n];
	// }

	// create Position based on 2d array
	public Position(char[][] data) {

		byte mx = -1, my = -1;

		m = data.length;
		n = data[0].length;
		this.data = new char[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				this.data[i][j] = data[i][j];
				if (data[i][j] == 'm' || data[i][j] == 'c') {
					my = (byte) i;
					mx = (byte) j;
				}
			}
		initReachable();
		fillReachable(my, mx);
	}

	public Position copy() {
		return new Position(this.getData());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "";
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				s += data[i][j];
			s += "\n";
		}
		return s;
	}

	// does A = B exactly?
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Position))
			return false;
		if (this.m != ((Position) o).m || this.n != ((Position) o).n)
			return false;

		byte i = -1, j = -1;
		// outer1:
		// for (j = 0; j < m; j++)
		// for (i = 0; i < n; i++)
		// if (this.data[j][i] == 'm' || this.data[j][i] == 'c')
		// break outer1;

		// this.initReachable(j, i);
		// this.fillReachable(j, i);

		// outer2:
		// for (j = 0; j < m; j++)
		// for (i = 0; i < n; i++)
		// if (((Position) o).data[j][i] == 'm' || ((Position) o).data[j][i] ==
		// 'c')
		// break outer2;
		// ((Position) o).initReachable(j, i);
		// ((Position) o).fillReachable(j, i);

		for (j = 0; j < m; j++)
			for (i = 0; i < n; i++)
				if (data[j][i] != ((Position) o).data[j][i])
					return false;

		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hc = 1;
		for (int i = 0; i < data.length; i++)
			for (int j = 0; j < data[i].length; j++)
				hc = 7 * hc + data[i][j] % 3;
			
		return hc;
	}

	public boolean solved() {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++)
				if (data[i][j] == '1' || data[i][j] == 'a' || data[i][j] == 'c')
					return false;
		}
		return true;
	}

	public char[][] getData() {
		return data;
	}

	public void setData(byte j, byte i, char value) {
		data[j][i] = value;
	}

	// public void initReachable(byte j, byte i) {
	// reachable = new Position(data);
	// }

	// public void setReachable(Position p) {
	// reachable = new Position(p.getData());
	// }

	// public void fillReachable(byte j, byte i) {
	//
	// // this.setData(j, i, 'm');
	// if (i > 0) {
	// if (reachable.getData()[j][i - 1] == '0' || reachable.getData()[j][i - 1]
	// == 'a') {
	// fillReachable(j, (byte) (i - 1));
	// }
	// }
	//
	// if (i < data[j].length - 1) {
	// if (reachable.getData()[j][i + 1] == '0' || reachable.getData()[j][i + 1]
	// == 'a') {
	// fillReachable(j, (byte) (i + 1));
	// }
	// }
	//
	// if (j > 0) {
	// if (reachable.getData()[j - 1][i] == '0' || reachable.getData()[j - 1][i]
	// == 'a') {
	// fillReachable((byte) (j - 1), i);
	// }
	// }
	//
	// if (j < data.length - 1) {
	// if (reachable.getData()[j + 1][i] == '0' || reachable.getData()[j + 1][i]
	// == 'a') {
	// fillReachable((byte) (j + 1), i);
	// }
	// }
	// }
	public void initReachable() {
		for (int k = 0; k < data.length; k++)
			for (int l = 0; l < data[k].length; l++)
				switch (data[k][l]) {
				case 'm':
					data[k][l] = '0';
					break;
				case 'c':
					data[k][l] = 'a';
					break;
				}
	}

	public void fillReachable(byte j, byte i) {

		if (data[j][i] == 'a')
			data[j][i] = 'c';
		if (data[j][i] == '0')
			data[j][i] = 'm';

		if (i > 0)
			if (data[j][i - 1] == '0' || data[j][i - 1] == 'a')
				fillReachable(j, (byte) (i - 1));

		if (i < data[j].length - 1)
			if (data[j][i + 1] == '0' || data[j][i + 1] == 'a')
				fillReachable(j, (byte) (i + 1));

		if (j > 0) {
			if (data[j - 1][i] == '0' || data[j - 1][i] == 'a') {
				fillReachable((byte) (j - 1), i);
			}
		}

		if (j < data.length - 1) {
			if (data[j + 1][i] == '0' || data[j + 1][i] == 'a') {
				fillReachable((byte) (j + 1), i);
			}
		}
	}

	// public Position getReachable() {
	// return reachable;
	// }

}