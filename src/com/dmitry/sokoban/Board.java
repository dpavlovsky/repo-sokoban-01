package com.dmitry.sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Board {

	private static final String FILENAME = "d:\\Work0\\workspace\\Sokoban_01\\src\\com\\dmitry\\sokoban\\board_test_02.txt";

	boolean solved = false;

	// HashSet<Pair<Byte, Byte>> reachable = new HashSet<>();

	// ArrayList<Position> positions = new ArrayList<>();

	// Position fig;

	// char fig[][];

	HashMap<Position, HashMap<Character, HashMap<Character, Boolean>>> positions = new HashMap<>();

	public Position getBoard() {

		Position pos = null;

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
			pos = new Position(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pos;

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

	public void move(Position p) {// (boolean l, boolean r, boolean u, boolean
									// d) {
		System.out.println(p.toString());

		Position t2 = p.copy();
		if (!positions.containsKey(t2)) {
			HashMap<Character, Boolean> direction = new HashMap<>();
			direction.put('l', true);
			direction.put('r', true);
			direction.put('u', true);
			direction.put('d', true);
			for (int i = 0; i < t2.getData().length; i++)
				for (int j = 0; j < t2.getData()[i].length; j++)
					if (t2.getData()[i][j] >= '1' && t2.getData()[i][j] <= '9') {
						HashMap<Character, HashMap<Character, Boolean>> boxes = new HashMap<>();
						boxes.put(t2.getData()[i][j], direction);
						positions.put(t2, boxes);
					}
		}

		Position t = p.copy();

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

		for (j = 0; j < t.getData().length; j++)
			for (i = 0; i < t.getData()[j].length; i++) {
				if (t.getData()[j][i] >= '1' && t.getData()[j][i] <= '9') {
					HashMap<Character, HashMap<Character, Boolean>> boxes = new HashMap<>();
					boxes = positions.get(t);
					HashMap<Character, Boolean> direction = new HashMap<>();
					direction = boxes.get(t.getData()[j][i]);

					// move left
					if (direction.get('l') && i > 0 && i < t.getData()[0].length - 1
							&& (t.getData()[j][i - 1] == '0' || t.getData()[j][i - 1] == 'a'
									|| t.getData()[j][i - 1] == 'c' || t.getData()[j][i - 1] == 'm')
							&& (t.getData()[j][i + 1] == 'm' || t.getData()[j][i + 1] == 'c')) {

						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						t.getData()[j][i - 1] = (t.getData()[j][i - 1] == 'a' || t.getData()[j][i - 1] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j][i + 1] = (t.getData()[j][i + 1] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);

						// i--;
						if (t.solved()) {
							System.out.println(t.toString());
							solved = true;
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						if (!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(t.toString());
								// l = r = u = d = false;
								return;
							}
						}

						// l = false;
						// i++;
						// fig.x = (byte)(i+1);
						// fig.y = j;

						direction.put('l', false);
						t = p.copy();
						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());
					} else
						direction.put('l', false);

					// move up
					if (direction.get('u') && j > 0 && j < t.getData().length - 1
							&& (t.getData()[j - 1][i] == '0' || t.getData()[j - 1][i] == 'a'
									|| t.getData()[j - 1][i] == 'c' || t.getData()[j - 1][i] == 'm')
							&& (t.getData()[j + 1][i] == 'm' || t.getData()[j + 1][i] == 'c')) {
						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						t.getData()[j - 1][i] = (t.getData()[j - 1][i] == 'a' || t.getData()[j - 1][i] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j + 1][i] = (t.getData()[j + 1][i] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);
						// j--;
						if (t.solved()) {
							System.out.println(t.toString());
							// l = r = u = d = false;
							solved = true;
							return;
						}

						// move(l, r, u, d);
						if (!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(t.toString());
								// l = r = u = d = false;
								return;
							}
						}
						// u = false;
						// j++;
						// fig.x = i;
						// fig.y = (byte)(j+1);

						direction.put('u', false);
						t = p.copy();
						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());
					} else
						direction.put('u', false);

					// move right
					if (direction.get('r') && i > 0 && i < t.getData()[0].length - 1
							&& (t.getData()[j][i + 1] == '0' || t.getData()[j][i + 1] == 'a'
									|| t.getData()[j][i + 1] == 'c' || t.getData()[j][i + 1] == 'm')
							&& (t.getData()[j][i - 1] == 'm' || t.getData()[j][i - 1] == 'c')) {
						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						t.getData()[j][i + 1] = (t.getData()[j][i + 1] == 'a' || t.getData()[j][i + 1] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j][i - 1] = (t.getData()[j][i - 1] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);
						// i++;
						if (t.solved()) {
							System.out.println(t.toString());
							solved = true;
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						if (!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(t.toString());
								// l = r = u = d = false;
								return;
							}
						}
						// r = false;
						// i--;
						// fig.x = (byte)(i-1);
						// fig.y = j;

						direction.put('r', false);
						t = p.copy();

						// fig.x = temp.x;
						// fig.y = temp.y;
						// fig = new Position(temp.getData());
						// fig.setReachable(temp.getReachable());

					} else
						direction.put('r', false);

					// move down
					if (direction.get('d') && j > 0 && j < t.getData().length - 1
							&& (t.getData()[j + 1][i] == '0' || t.getData()[j + 1][i] == 'a'
									|| t.getData()[j + 1][i] == 'c' || t.getData()[j + 1][i] == 'm')
							&& (t.getData()[j - 1][i] == 'm' || t.getData()[j - 1][i] == 'c')) {
						// fig.getData()[fig.y][fig.x] =
						// (fig.getData()[fig.y][fig.x] == 'c') ? 'a' : '0';
						// fig.x = i;
						// fig.y = j;
						t.getData()[j + 1][i] = (t.getData()[j + 1][i] == 'a' || t.getData()[j + 1][i] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j - 1][i] = (t.getData()[j - 1][i] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);
						// j++;
						if (t.solved()) {
							System.out.println(t.toString());
							solved = true;
							// l = r = u = d = false;
							return;
						}

						// move(l, r, u, d);
						if (!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(t.toString());
								// l = r = u = d = false;
								return;
							}
						}
						// d = false;
						// j--;
						// fig.x = i;
						// fig.y = (byte)(j-1);

						direction.put('d', false);
						t = p.copy();
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
