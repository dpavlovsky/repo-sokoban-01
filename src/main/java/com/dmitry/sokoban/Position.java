package main.java.com.dmitry.sokoban;

final public class Position {
	private final int m; // number of rows
	private final int n; // number of columns
	private final char[][] data;

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

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Position))
			return false;
		if (this.m != ((Position) o).m || this.n != ((Position) o).n)
			return false;

		byte i = -1, j = -1;

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

	public boolean isDeadlock() {
		for (int j = 0; j < data.length - 1; j++)
			for (int i = 0; i < data[j].length - 1; i++) {
				
				char[][] s = {	{data[j][i],	data[j][i + 1]},
								{data[j + 1][i],data[j + 1][i + 1]}	};
				if(
						(s[0][0]=='x' || s[0][0]=='1' || s[0][0]=='b') &&
						(s[0][1]=='x' || s[0][1]=='1' || s[0][1]=='b') &&
						(s[1][0]=='x' || s[1][0]=='1' || s[1][0]=='b') &&
						(s[1][1]=='x' || s[1][1]=='1' || s[1][1]=='b') ) {
					
				}
				else {
					continue;
				}
				
				
				
				int[][] a = new int[2][2];
				for(int m=0;m<s.length;m++)
					for(int n=0;n<s[m].length;n++) {						
						switch(s[m][n]) {
							case 'x': a[m][n]=0; break;
							case 'b': a[m][n]=7; break;
							case '1': a[m][n]=1;
						}
					}
				
				
				
				
				
				 
				
				
				/*
				// there is a box in current cell and this cell is not a goal
				if (data[j][i] == '1') {
					// is box in the corner?
					if (data[j][i - 1] == 'x' && data[j + 1][i] == 'x')
						return true;
					if (data[j][i + 1] == 'x' && data[j + 1][i] == 'x')
						return true;
					if (data[j][i - 1] == 'x' && data[j - 1][i] == 'x')
						return true;
					if (data[j][i + 1] == 'x' && data[j - 1][i] == 'x')
						return true;
				}
				*/
				
				/*
				// there is a box in current cell
				if (data[j][i] == '1' || data[j][i] == 'b') {
					// four boxes forming a square
					if (
					// if at least one of four boxes in not in the goal
					(data[j][i] == '1' || data[j][i + 1] == '1' || data[j + 1][i] == '1' || data[j + 1][i + 1] == '1')
							&&
							// right box
							(data[j][i + 1] == '1' || data[j][i + 1] == 'b') &&
							// bottom box
							(data[j + 1][i] == '1' || data[j + 1][i] == 'b') &&
							// right bottom box
							(data[j + 1][i + 1] == '1' || data[j + 1][i + 1] == 'b'))
						return true;
					// two boxes near wall (can be either two stones or stone and another box)
					if (
							// there is a box to the right
							(data[j][i + 1] == '1' || data[j][i + 1] == 'b') &&
							// there is a "wall" on top
							( 		
									// wall on the top
									((data[j - 1][i]=='x' && data[j - 1][i + 1]=='x') &&
									// and one of the two boxes not in the goal
									(data[j][i]=='1' || data[j][i+1]=='1')) ||
									// box and stone are on the top
									(((data[j - 1][i]=='1' || data[j - 1][i]=='b') && data[j - 1][i + 1]=='x') &&
									// and one of the three boxes not in the goal
									(data[j - 1][i]=='1' || data[j][i + 1] == '1' || data[j][i] == '1')) ||
									// stone and box are on the top
									((data[j - 1][i]=='x' && (data[j - 1][i + 1]=='1' || data[j - 1][i + 1]=='b')) &&
									// and one of the three boxes not in the goal
									(data[j - 1][i + 1]=='1' || data[j][i + 1]=='1' || data[j][i]=='1' ))
							)
						)
						return true;
						*/
		}

		return false;
	}
}