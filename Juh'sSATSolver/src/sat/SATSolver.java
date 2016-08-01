package sat;

import java.util.ArrayList;
import java.util.HashMap;

public class SATSolver {

	private static Formula phi;
	private static int nextTokenIndex = 0;
	private static char token;
	private static boolean valor;
	
	public static boolean TTSat(Formula phi){
		
		SATSolver.phi = phi;

		HashMap<Character, Boolean> val = new HashMap<>();
		ArrayList<Character> atoms = new ArrayList<Character>();
		
		for (Character atom: phi.getAtoms()){
			atoms.add(atom);
		}
		
		return TTSatCheck(phi, atoms, val);
	}
	
	public static boolean TTSatCheck(Formula phi, ArrayList<Character> atomsList, HashMap<Character, Boolean> val){
		
		
		if(atomsList.isEmpty()){
			if(v(phi, val)){
				return true;
			}
			return false;
		}
		Character p = atomsList.get(0);
		atomsList.remove(0);
		ArrayList<Character> rest = (ArrayList<Character>) atomsList.clone();
		
		HashMap<Character, Boolean> val1 = (HashMap<Character, Boolean>) val.clone();
		val1.put(p, true);
		
		HashMap<Character, Boolean> val2 = (HashMap<Character, Boolean>) val.clone();
		val2.put(p, false);
		
		boolean check1 = TTSatCheck(phi, rest, val1);
		boolean check2 = TTSatCheck(phi, rest, val2);
		
		return check1 || check2;
				
	}
	
	private static boolean isAtom(Character token, HashMap<Character, Boolean> val){
		
		return val.containsKey(token);
	}

	public static char nextToken(){

		String formula = phi.toString();
		char nextToken;

		if(nextTokenIndex < formula.length()){
			nextToken = formula.charAt(nextTokenIndex++);
			while(nextToken == ' '){
				nextToken = formula.charAt(nextTokenIndex++);
			}
			return nextToken;
		}
		return '\0';
	}

	public static boolean v(Formula phi, HashMap<Character, Boolean> val){
		
		return expression(val);
	}
	
		
	public static boolean expression(HashMap<Character, Boolean> val){
		
		token = nextToken();
		
		if(token == '¬'){
			token = nextToken();
			if(token == '('){
				valor = !expression(val);
			}else if(isAtom(token, val)){
				valor = !val.get(token);
			}
		}else if(token == '('){
			valor = expression(val);
		}else if(token == '-'){
			token = nextToken();
			if(token == '>'){
				boolean p = valor;
				boolean q = expression(val);
				valor = !(p && !q);
			}
			
		}else if (token == 'v'){
			boolean p = valor;
			boolean q = expression(val);
			valor = p || q;
			
		}else if(token == '^'){
			boolean p = valor;
			boolean q = expression(val);
			valor = p && q;
			
		}else if(isAtom(token, val)){
			valor = val.get(token);
		}else if(token == '\0' || token == ')'){
			return valor;
		}
		
		return expression(val);
		
	}	
	
}
