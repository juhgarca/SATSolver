package main;

import java.util.Scanner;

import sat.Formula;
import sat.SATSolver;

public class Main {

	public static void main(String[] args) {
		
		while (true){

			Scanner s = new Scanner(System.in);
			System.out.println("------------------------------------\n"
					+ "|  And = ^ ; Or = v; Implies = ->  |\n"
					+ "------------------------------------\n"
					+ "DIGITE UMA FÓRMULA");
			String phi = s.nextLine();

			//colocar cálculo de tempo
			
			Formula f = Formula.form(phi);
			boolean isSAT = SATSolver.TTSat(f);
			
			if(isSAT){
				System.out.println("A fórmula é satisfatível\n");
			}else{
				System.out.println("A fórmula é insatisfatível\n");
			}

		}
	}
}
