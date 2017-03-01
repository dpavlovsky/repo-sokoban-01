package com.dmitry.sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class Board {

	private static final String FILENAME = "e:\\Work0\\workspace\\Sokoban_01\\src\\com\\dmitry\\sokoban\\board_04.txt";

	byte x, y;

	HashSet<Pair<Byte, Byte>> reachable = new HashSet<>();
	
	TreeSet<Matrix> positions = new TreeSet<>();
	
//	Matrix fig;

	char fig[][];

	public void getBoard() {
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			File f = new File(FILENAME);
			String line = br.readLine();
			byte x = (byte) line.length();
			byte y = (byte) ((f.length() + 2) / (x + 2));
//			fig = new Matrix(y, x);
			fig = new char[y][x];
			byte i = 0;
			while (line != null) {
				fig[i] = line.toCharArray();
				line = br.readLine();
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (byte i = 0; i < fig.length; i++) {
			char[] cs = fig[i];
			for (byte j = 0; j < cs.length; j++) {
				if (cs[j] == 'm' || cs[j] == 'c') {
					y = i;
					x = j;
				}
			}
		}
	}

	public void getReachable(byte x, byte y) {
		reachable.add(new Pair<Byte, Byte>(x, y));
		if (x > 0) {
			Pair<Byte, Byte> l = new Pair<Byte, Byte>((byte) (x - 1), y);
			if (!reachable.contains(l) && (fig[l.getY()][l.getX()] == '0' || fig[l.getY()][l.getX()] == 'a')) {
				reachable.add(l);
				getReachable(l.getX(), l.getY());
			}
		}

		if (x < fig[0].length - 1) {
			Pair<Byte, Byte> r = new Pair<Byte, Byte>((byte) (x + 1), y);
			if (!reachable.contains(r) && (fig[r.getY()][r.getX()] == '0' || fig[r.getY()][r.getX()] == 'a')) {
				reachable.add(r);
				getReachable(r.getX(), r.getY());
			}
		}

		if (y > 0) {
			Pair<Byte, Byte> u = new Pair<Byte, Byte>(x, (byte) (y - 1));
			if (!reachable.contains(u) && (fig[u.getY()][u.getX()] == '0' || fig[u.getY()][u.getX()] == 'a')) {
				reachable.add(u);
				getReachable(u.getX(), u.getY());
			}
		}

		if (y < fig.length - 1) {
			Pair<Byte, Byte> d = new Pair<Byte, Byte>(x, (byte) (y + 1));
			if (!reachable.contains(d) && (fig[d.getY()][d.getX()] == '0' || fig[d.getY()][d.getX()] == 'a')) {
				reachable.add(d);
				getReachable(d.getX(), d.getY());
			}
		}

	}
	
	public void move() {
		
		if (positions.contains(fig)) return;
		positions.add(new Matrix(fig));
		
		getReachable(x, y);
		
		byte i=-1,j=-1;
		for (j = 0; j < fig.length; j++) {
			char[] cs = fig[j];
			for (i = 0; i < cs.length; i++) {
				if (cs[i]==1) {
					break;
				}				
			}			
		}
		// move left
		if (i > 0 && i < fig[0].length-1) {
			Pair<Byte, Byte> l = new Pair<Byte, Byte>((byte) (i - 1), j);
			Pair<Byte, Byte> r = new Pair<Byte, Byte>((byte) (i + 1), j);
			if ((fig[l.getY()][l.getX()]=='0' || fig[l.getY()][l.getX()]=='a') && reachable.contains(r)) {
				x = i;
				y = j;
				fig[j][i-1]	=(fig[j][i-1]=='a')?'b':'1';
				fig[j][i]	=(fig[j][i]	 =='b')?'c':'m';
				fig[j][i+1]	=(fig[j][i+1]=='c')?'a':'0';
				move();
			}
		}
	}
	
	public void disReachable() {
		Iterator<Pair<Byte, Byte>> it = reachable.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
