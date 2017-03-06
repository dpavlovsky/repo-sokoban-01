package com.dmitry.sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Board {

	private static final String FILENAME = "e:\\Work0\\workspace\\Sokoban_01\\src\\com\\dmitry\\sokoban\\board_17.txt";
	private static final int RECDEPTH = 30;
	
//	static int depth = 0;

	boolean solved = false;
	int step=0;
	int step2=0;
	
	HashMap<Position, HashMap<Pair<Byte, Byte>, HashMap<Character, Boolean>>> positions = new HashMap<>();

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

	}

	public void move(Position p) {
//		depth++;
		System.out.println(step2++ + "\n" + 
//depth + "\n"+ 
				p.toString());

		Position t2 = p.copy();
		if (!positions.containsKey(t2)) {
			HashMap<Pair<Byte, Byte>, HashMap<Character, Boolean>> boxes = new HashMap<>();
			for (int j = 0; j < t2.getData().length; j++)
				for (int i = 0; i < t2.getData()[j].length; i++)
					if (t2.getData()[j][i] == '1' || t2.getData()[j][i] == 'b') {
						HashMap<Character, Boolean> direction = new HashMap<>();
						direction.put('l', true);
						direction.put('r', true);
						direction.put('u', true);
						direction.put('d', true);
						boxes.put(new Pair<Byte, Byte>((byte) j, (byte) i), direction);
					}
			positions.put(t2, boxes);
		}

		Position t = p.copy();

		byte i = -1, j = -1;

		for (j = 0; j < t.getData().length; j++)
			for (i = 0; i < t.getData()[j].length; i++) {
				if (t.getData()[j][i] == '1' || t.getData()[j][i] == 'b') {
					HashMap<Pair<Byte, Byte>, HashMap<Character, Boolean>> boxes = new HashMap<>();
					boxes = positions.get(t);
					HashMap<Character, Boolean> direction = new HashMap<>();
					direction = boxes.get(new Pair<Byte, Byte>(j, i));

					// move left
					if (direction.get('l') && i > 0 && i < t.getData()[0].length - 1
							&& (t.getData()[j][i - 1] == '0' || t.getData()[j][i - 1] == 'a'
									|| t.getData()[j][i - 1] == 'c' || t.getData()[j][i - 1] == 'm')
							&& (t.getData()[j][i + 1] == 'm' || t.getData()[j][i + 1] == 'c')) {

						t.getData()[j][i - 1] = (t.getData()[j][i - 1] == 'a' || t.getData()[j][i - 1] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j][i + 1] = (t.getData()[j][i + 1] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);

						if (t.solved()) {
							System.out.println(step++);
							System.out.println(t.toString());
							solved = true;
							return;
						}

						if (//depth < RECDEPTH && 
//								!t.isDeadlock() &&
								!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(step++);
								System.out.println(t.toString());
								return;
							}
						}

						direction.put('l', false);
						t = p.copy();
					} else
						direction.put('l', false);

					// move up
					if (direction.get('u') && j > 0 && j < t.getData().length - 1
							&& (t.getData()[j - 1][i] == '0' || t.getData()[j - 1][i] == 'a'
									|| t.getData()[j - 1][i] == 'c' || t.getData()[j - 1][i] == 'm')
							&& (t.getData()[j + 1][i] == 'm' || t.getData()[j + 1][i] == 'c')) {
						t.getData()[j - 1][i] = (t.getData()[j - 1][i] == 'a' || t.getData()[j - 1][i] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j + 1][i] = (t.getData()[j + 1][i] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);
						if (t.solved()) {
							System.out.println(step++);
							System.out.println(t.toString());
							solved = true;
							return;
						}

						if (//depth < RECDEPTH && 
//								!t.isDeadlock() &&
								!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(step++);
								System.out.println(t.toString());
								return;
							}
						}
						direction.put('u', false);
						t = p.copy();
					} else
						direction.put('u', false);

					// move right
					if (direction.get('r') && i > 0 && i < t.getData()[0].length - 1
							&& (t.getData()[j][i + 1] == '0' || t.getData()[j][i + 1] == 'a'
									|| t.getData()[j][i + 1] == 'c' || t.getData()[j][i + 1] == 'm')
							&& (t.getData()[j][i - 1] == 'm' || t.getData()[j][i - 1] == 'c')) {
						t.getData()[j][i + 1] = (t.getData()[j][i + 1] == 'a' || t.getData()[j][i + 1] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j][i - 1] = (t.getData()[j][i - 1] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);
						if (t.solved()) {
							System.out.println(step++);
							System.out.println(t.toString());
							solved = true;
							return;
						}

						if (//depth < RECDEPTH && 
//								!t.isDeadlock() &&
								!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(step++);
								System.out.println(t.toString());
								return;
							}
						}

						direction.put('r', false);
						t = p.copy();

					} else
						direction.put('r', false);

					// move down
					if (direction.get('d') && j > 0 && j < t.getData().length - 1
							&& (t.getData()[j + 1][i] == '0' || t.getData()[j + 1][i] == 'a'
									|| t.getData()[j + 1][i] == 'c' || t.getData()[j + 1][i] == 'm')
							&& (t.getData()[j - 1][i] == 'm' || t.getData()[j - 1][i] == 'c')) {
						t.getData()[j + 1][i] = (t.getData()[j + 1][i] == 'a' || t.getData()[j + 1][i] == 'c') ? 'b'
								: '1';
						t.getData()[j][i] = (t.getData()[j][i] == 'b') ? 'c' : 'm';
						t.getData()[j - 1][i] = (t.getData()[j - 1][i] == 'c') ? 'c' : 'm';
						t.initReachable();
						t.fillReachable(j, i);
						if (t.solved()) {
							System.out.println(step++);
							System.out.println(t.toString());
							solved = true;
							return;
						}

						if (//depth < RECDEPTH &&
//								!t.isDeadlock() &&
								!positions.containsKey(t)) {
							move(t);
							if (solved) {
								System.out.println(step++);
								System.out.println(t.toString());
								return;
							}
						}
						direction.put('d', false);
						t = p.copy();
					} else
						direction.put('d', false);

				}
			}
//		depth--;
	}
}
