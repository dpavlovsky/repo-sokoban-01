package com.dmitry.sokoban;

final public class Matrix {
	private final int m; // number of rows
	private final int n; // number of columns
	private final char[][] data; // M-by-N array

	// create M-by-N matrix of 0's
	public Matrix(int m, int n) {
		this.m = m;
		this.n = n;
		data = new char[m][n];
	}

	// create matrix based on 2d array
	public Matrix(char[][] data) {
		m = data.length;
		n = data[0].length;
		this.data = new char[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				this.data[i][j] = data[i][j];
	}

	// does A = B exactly?
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Matrix))
			return false;
		if (this.m != ((Matrix) o).m || this.n != ((Matrix) o).n)
			return false;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (this.data[i][j] != ((Matrix) o).data[i][j])
					return false;
		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hc = 1;
		for (int i = 0; i < data.length; i++) {
			hc = 31 * hc + data[i].hashCode();
		}
		return hc;
	}
	
	public boolean solved() {
		for (int i = 0; i < data.length; i++) {
			char[] cs = data[i];
			for (int j = 0; j < cs.length; j++) 
				if(cs[j]=='1' || cs[j]=='a' || cs[j]=='c') return false;
		}
		return true;
	}
}