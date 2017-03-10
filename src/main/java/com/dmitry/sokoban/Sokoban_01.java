package main.java.com.dmitry.sokoban;

public class Sokoban_01 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solver.Init();
		Solver.move(Solver.getBoard());
		System.out.println(Solver.getSteps());
	}
}
