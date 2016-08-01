package sat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Formula {
	
	private String formula;
	private char token;
	private int nextIndexToken = 0;
	private List<Character> listAtoms;

	public Formula(){
		this.listAtoms = new LinkedList<>();
	}
	
	public static Formula form(String formula){
		
		if(formula.isEmpty()){
			return null;
		}
		
		Formula f = new Formula();
		f.formula = formula;
		f.validarFormula();
		
		return f;
		
	}
	
	public char nextToken(){
		
		char nextToken;
		
		if(nextIndexToken < formula.length()){
			nextToken = formula.charAt(nextIndexToken++);
			while(nextToken == ' '){
				nextToken = formula.charAt(nextIndexToken++);
			}

			return nextToken;
		}
		return '\0';
	}

	public void validarFormula(){

		token = nextToken();
		

		if(token == '\0'){
			return;
		}else if (token == '(') {
			validarFormula();
		}else if (token == '¬') {
			not();
		}else if (token == 'v' || token == '^' || token == '-' || token == ')') {
			System.out.println("Erro! Expressão mal formada");
			return;
		}else{
			atomica();
		}
	}

	public void not(){

		token = nextToken();
		if(token == '('){
			validarFormula();
		}else if(token == '-' || token == 'v' || token == '^' || token == ')'){
			System.out.println("Erro! Expressão mal formada");
			return;
		}else {
			atomica();
		}
	}

	public void or(){

		token = nextToken();

		if (token == '(') {
			validarFormula();
		}else if (token == '¬') {
			not();
		}else if (token == 'v' || token == '^' || token == ')' || token == '-') {
			System.out.println("Erro! Expressão mal formada");
			return;
		}else{
			atomica();
		}
	}

	public void and(){

		token = nextToken();

		if(token == '('){
			validarFormula();
		}else if(token == '¬'){
			not();
		}else if (token == 'v' || token == '^' || token == ')' || token == '-') {
			System.out.println("Erro! Expressão mal formada");
			return;
		}else{
			atomica();
		}
	}

	public void implie(){

		token = nextToken();

		if (token == '(') {
			validarFormula();
		}else if(token == '¬'){
			not();
		}else if (token == '^' || token == 'v' || token == ')') {
			System.out.println("Erro! Expressão mal formada");
			return;
		}else{
			atomica();
		}
	}

	public void atomica(){

		listAtoms.add(token); //tratar para não repetir atômicas
		token = nextToken();

		if (token == '^') {
			and();
		}else if (token == 'v') {
			or();
		}else if (token == '-') {
			token = nextToken();
			if (token == '>') {
				implie();
			}
		}else if(token == '\0'){
			return;
		}else if(token == ')'){
			token = nextToken();
			while(token == ')'){
				token = nextToken();
			}
			if(token == 'v'){
				or();
			}else if(token == '^'){
				and();
			}else if(token == '-'){
				implie();
			}else if(token == '\0'){
				return;
			}
		}else{
			System.out.println("Erro!");
			return;
		}
	}
	
	public List<Character> getAtoms(){
		return this.listAtoms;
	}
	
	public String toString(){
		return this.formula;
	}

}
