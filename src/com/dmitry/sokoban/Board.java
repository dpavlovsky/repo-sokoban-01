package com.dmitry.sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Board {

	private static final String FILENAME = "e:\\Work0\\workspace\\Sokoban_01\\src\\com\\dmitry\\sokoban\\board_test_02.txt";

	// HashSet<Pair<Byte, Byte>> reachable = new HashSet<>();

	// ArrayList<Position> positions = new ArrayList<>();

	Position fig;

	// char fig[][];

	HashMap<Position, HashMap<Character, HashMap<Character, Boolean>>> moves = new HashMap<>();

	public void getBoard() {
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			File f = new File(FILENAME);
			String line = br.readLine();
			byte x = (byte) line.length();
			byte y = (byte) ((f.length() + 2) / (x + 2));
			char temp[][] = new char[y][x];
			byte i = 0;
			while (line != null) {
				temp[i] = line.toCharArray();
				line = br.readLine();
				i++;
			}
			fig = new Position(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// for (byte j = 0; j < fig.getData().length; j++) {
		// for (byte i = 0; i < fig.getData()[j].length; i++) {
		// if (fig.getData()[j][i] == 'm' || fig.getData()[j][i] == 'c') {
		// fig.x = i;
		// fig.y = j;
		// }
		// }
		// }
	}

	// public void getReachable(byte x, byte y) {
	// reachable.add(new Pair<Byte, Byte>(x, y));
	// if (x > 0) {
	// Pair<Byte, Byte> l = new Pair<Byte, Byte>((byte) (x - 1), y);
	// if (!reachable.contains(l)
	// && (fig.getData()[l.getY()][l.getX()] == '0' ||
	// fig.getData()[l.getY()][l.getX()] == 'a')) {
	// reachable.add(l);
	// getReachable(l.getX(), l.getY());
	// }
	// }
	//
	// if (x < fig.getData()[0].length - 1) {
	// Pair<Byte, Byte> r = new Pair<Byte, Byte>((byte) (x + 1), y);
	// if (!reachable.contains(r)
	// && (fig.getData()[r.getY()][r.getX()] == '0' ||
	// fig.getData()[r.getY()][r.getX()] == 'a')) {
	// reachable.add(r);
	// getReachable(r.getX(), r.getY());
	// }
	// }
	//
	// if (y > 0) {
	// Pair<Byte, Byte> u = new Pair<Byte, Byte>(x, (byte) (y - 1));
	// if (!reachable.contains(u)
	// && (fig.getData()[u.getY()][u.getX()] == '0' ||
	// fig.getData()[u.getY()][u.getX()] == 'a')) {
	// reachable.add(u);
	// getReachable(u.getX(), u.getY());
	// }
	// }
	//
	// if (y < fig.getData().length - 1) {
	// Pair<Byte, Byte> d = new Pair<Byte, Byte>(x, (byte) (y + 1));
	// if (!reachable.contains(d)
	// && (fig.getData()[d.getY()][d.getX()] == '0' ||
	// fig.getData()[d.getY()][d.getX()] == 'a')) {
	// reachable.add(d);
	// getReachable(d.getX(), d.getY());
	// }
	// }
	//
	// }

	public void move() {// (boolean l, boolean r, boolean u, boolean d) {
		System.out.println(fig.toString());

		Position temp1 = fig.copy();
		if (!moves.containsKey(temp1)) {
			HashMap<Character, Boolean> direction = new HashMap<>();
			direction.put('l', true);
			direction.put('r', true);
			direction.put('u', true);
			direction.put('d', true);
			for (int i = 0; i < temp1.getData().length; i++)
				for (int j = 0; j < temp1.getData()[i].length; j++)
					if (temp1.getData()[i][j] >= '1' && temp1.getData()[i][j] <= '9') {
						HashMap<Character, HashMap<Character, Boolean>> boxes = new HashMap<>();
						boxes.put(temp1.getData()[i][j], direction);
						moves.put(temp1, boxes);
					}
		}
		
		Position temp = fig.copy();

		// if (positions.contains(fig))
		// return false;

		// positions.add(new Position(fig.getData()));

		// fig.initReachable(fig.y, fig.x);
		// fig.fillReachable(fig.y, fig.x);

		// disPositions();

		// getReachable(x, y);

		// disReachable();

		// temp.setReachable(fig.getReachable());

		// if (fig.solved()) {
		// System.out.println(temp.toString());
		// return;
		// }

		byte i = -1, j = -1;

		for (j = 0; j < fig.getData().length; j++)
			for (i = 0; i < fig.getData()[j].length; i++) {
				if (fig.getData()[j][i] >= '1' && fig.getData()[j][i] <= '9') {
					HashMap<Character, HashMap<Character, Boolean>> boxes = new HashMap<>();
					boxes = moves.get(temp);
					HashMap<Character, Boolean> direction = new HashMap<>();
					direction = boxes.get(temp.getData()[j][i]);

					// move left
					if (direction.get('l') && i > 0 && i < fig.getData()[0].length - 1
							&& (fig.getData()[j][i - 1] == '0' || fig.getData()[j][i - 1] == 'a'
									|| fig.getData()[j][i - 1] == 'c' || fig.getData()[j][i - 1] == 'm')
							&& (fig.getData()[j][i + 1] == 'm' || fig.getData()[j][i + 1] == 'c')) {

						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						fig.getData()[j][i - 1] = (fig.getData()[j][i - 1] == 'a') ? 'b' : '1';
						fig.getData()[j][i] = (fig.getData()[j][i] == 'b') ? 'c' : 'm';
						fig.getData()[j][i + 1] = (fig.getData()[j][i + 1] == 'c') ? 'c' : 'm';
						fig.initReachable();
						fig.fillReachable(j, i);
						// i--;
						if (fig.solved()) {
							System.out.println(fig.toString());
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						move();

						if (fig.solved()) {
							System.out.println(temp.toString());
							// l = r = u = d = false;
							return;
						}
						// l = false;
						// i++;
						// fig.x = (byte)(i+1);
						// fig.y = j;
						fig = temp;

						direction.put('l', false);
						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());
					} else
						direction.put('l', false);

					// move right
					if (direction.get('r') && i > 0 && i < fig.getData()[0].length - 1
							&& (fig.getData()[j][i + 1] == '0' || fig.getData()[j][i + 1] == 'a'
									|| fig.getData()[j][i + 1] == 'c' || fig.getData()[j][i + 1] == 'm')
							&& (fig.getData()[j][i - 1] == 'm' || fig.getData()[j][i - 1] == 'c')) {
						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						fig.getData()[j][i + 1] = (fig.getData()[j][i + 1] == 'a') ? 'b' : '1';
						fig.getData()[j][i] = (fig.getData()[j][i] == 'b') ? 'c' : 'm';
						fig.getData()[j][i - 1] = (fig.getData()[j][i - 1] == 'c') ? 'c' : 'm';
						fig.initReachable();
						fig.fillReachable(j, i);
						// i++;
						if (fig.solved()) {
							System.out.println(fig.toString());
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						move();
						if (fig.solved()) {
							System.out.println(temp.toString());
							// l = r = u = d = false;
							return;
						}
						// r = false;
						// i--;
						// fig.x = (byte)(i-1);
						// fig.y = j;
						fig = temp;
						direction.put('r', false);

						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());

					} else
						direction.put('r', false);

					// move up
					if (direction.get('u') && j > 0 && j < fig.getData().length - 1
							&& (fig.getData()[j - 1][i] == '0' || fig.getData()[j - 1][i] == 'a'
									|| fig.getData()[j - 1][i] == 'c' || fig.getData()[j - 1][i] == 'm')
							&& (fig.getData()[j + 1][i] == 'm' || fig.getData()[j + 1][i] == 'c')) {
						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						fig.getData()[j - 1][i] = (fig.getData()[j - 1][i] == 'a') ? 'b' : '1';
						fig.getData()[j][i] = (fig.getData()[j][i] == 'b') ? 'c' : 'm';
						fig.getData()[j + 1][i] = (fig.getData()[j + 1][i] == 'c') ? 'c' : 'm';
						fig.initReachable();
						fig.fillReachable(j, i);
						// j--;
						if (fig.solved()) {
							System.out.println(fig.toString());
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						move();

						if (fig.solved()) {
							System.out.println(temp.toString());
							// l = r = u = d = false;
							return;
						}
						// u = false;
						// j++;
						// fig.x = i;
						// fig.y = (byte)(j+1);
						fig = temp;
						direction.put('u', false);
						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());
					} else
						direction.put('u', false);

					// move down
					if (direction.get('d') && j > 0 && j < fig.getData().length - 1
							&& (fig.getData()[j + 1][i] == '0' || fig.getData()[j + 1][i] == 'a'
									|| fig.getData()[j + 1][i] == 'c' || fig.getData()[j + 1][i] == 'm')
							&& (fig.getData()[j - 1][i] == 'm' || fig.getData()[j - 1][i] == 'c')) {
						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						fig.getData()[j + 1][i] = (fig.getData()[j + 1][i] == 'a') ? 'b' : '1';
						fig.getData()[j][i] = (fig.getData()[j][i] == 'b') ? 'c' : 'm';
						fig.getData()[j - 1][i] = (fig.getData()[j - 1][i] == 'c') ? 'c' : 'm';
						fig.initReachable();
						fig.fillReachable(j, i);
						// j++;
						if (fig.solved()) {
							System.out.println(fig.toString());
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						move();

						if (fig.solved()) {
							System.out.println(temp.toString());
							// l = r = u = d = false;
							return;
						}
						// d = false;
						// j--;
						// fig.x = i;
						// fig.y = (byte)(j-1);
						fig = temp;
						direction.put('d', false);
						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());
					} else
						direction.put('d', false);

				}
			}
		// if (fig.solved())
		// System.out.println(temp.toString());
	}

	// public void disPositions() {
	// for (Position m : positions)
	// System.out.println(m.toString());
	// }
	//
	// public void disReachable() {
	// Iterator<Pair<Byte, Byte>> it = reachable.iterator();
	// while (it.hasNext()) {
	// System.out.println(it.next());
	// }
	// }
}
