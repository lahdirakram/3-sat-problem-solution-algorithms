
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class All {
	static ArrayList<ArrayList<Integer>> sols = new ArrayList<ArrayList<Integer>>();
	static int[][] formule=new int[91][3];
	static String cnf="4 -18 19 03 18 -5 0-5 -8 -15 0-20 7 -16 010 -13 -7 0-12 -9 17 017 19 5 0-16 9 15 011 -5 -14 018 -10 13 0-3 11 12 0-6 -17 -8 0-18 14 1 0-19 -15 10 012 18 -19 0-8 4 7 0-8 -9 4 07 17 -15 012 -7 -14 0-10 -11 8 02 -15 -11 09 6 1 0-11 20 -17 09 -15 13 012 -7 -17 0-18 -2 20 020 12 4 019 11 14 0-16 18 -4 0-1 -17 -19 0-13 15 10 0-12 -14 -13 012 -14 -7 0-7 16 10 06 10 7 020 14 -16 0-19 17 11 0-7 1 -20 0-5 12 15 0-4 -9 -13 012 -11 -7 0-5 19 -8 01 16 17 020 -14 -15 013 -4 10 014 7 10 0-5 9 20 010 1 -19 0-16 -15 -1 016 3 -11 0-15 -10 4 04 -15 -3 0-10 -16 11 0-8 12 -5 014 -6 12 01 6 11 0-13 -5 -1 0-7 -2 12 01 -20 19 0-2 -13 -8 015 18 4 0-11 14 9 0-6 -15 -2 05 -12 -15 0-6 17 5 0-13 5 -19 020 -1 14 09 -17 15 0-5 19 -18 0-12 8 -10 0-18 14 -4 015 -9 13 09 -5 -1 010 -19 -14 020 9 4 0-9 -2 19 0-5 13 -17 02 -10 -18 0-18 3 11 07 -9 17 0-15 -6 -3 0-2 3 -13 012 3 -2 0-2 -3 17 020 -15 -16 0-5 -17 -19 0-20 -18 11 0-9 1 -5 0-19 9 17 012 -2 17 04 -16 -5 0%0";
	
	//function that creates a formula structure by reading the cnf file up here ^^
	static void createformule(){
		int i=0,j=0;
		String temp;
		for (int z = 0; z < cnf.length(); z++) {
			if(cnf.charAt(z)=='0' && cnf.charAt(z+1)=='%' && cnf.charAt(z+2)=='0') z=cnf.length();
			else 
				if(cnf.charAt(z)=='0'){
					i++;
					//System.out.println("");
					j=0;
				}else{
					if(cnf.charAt(z)!=0){
						temp="";
						while(cnf.charAt(z)!=' ' && z <= cnf.length()){
							temp=temp+cnf.charAt(z);
							z++;
						}
						formule[i][j]=Integer.parseInt(temp);
						//System.out.print(formule[i][j]+" ");
						j++;
					}
				}
			
		}
	}
	//function that prints the fomula 
	public static void showFormule(){
		for (int i = 0; i < formule.length; i++) {
			for (int j = 0; j < 3; j++) {
				//System.out.print(formule[i][j]+" ");
			}
			//System.out.println("");
		}
	}
	//function that validates the solution if it is used before or not
	public static Boolean isValid(ArrayList<Integer> sol){
		if(sols.containsAll(sol)){
		//	System.out.print("   bad\n");
			return true;
		}else{
		//	System.out.println("   good\n");
			return false;
		}
	}
	//function that generates a random solution
	public static ArrayList<Integer> genSolution(){
		ArrayList<Integer> sol = new ArrayList<Integer>();
		Random r = new Random();
		do{
			sol.clear();
			for (int i = 0; i < 20; i++) {
				sol.add(r.nextInt(2));
				System.out.print(sol.get(i)+"|");
			}
		}while(isValid(sol));
		System.out.println("");
		sols.add(sol);
		return sol;
	}
	//function that verifies if a solution in correct or false
	public static Boolean isCorrectSolution(ArrayList<Integer> sol){
		int lit1,lit2,lit3;
		
		for (int i = 0; i < formule.length; i++) {
			lit1=Math.abs(formule[i][0])-1;
			lit2=Math.abs(formule[i][1])-1;
			lit3=Math.abs(formule[i][2])-1;
			int lit1state = (formule[i][0]>0)? 1 :0;
			int lit2state = (formule[i][1]>0)? 1 :0;
			int lit3state = (formule[i][2]>0)? 1 :0;
			
			if(sol.get(lit1) != lit1state && sol.get(lit2) != lit2state && sol.get(lit3) != lit3state)return false;
		}
		System.out.println("successsssssss");
		for (int i = 0; i < sol.size(); i++) {
			System.out.print(sol.get(i)+" ");
		}
		return true;
	}
	//deep search
	public static Boolean rechProfondeur(ArrayList<Integer> sol){
		ArrayList<Integer> sol1 = new ArrayList<Integer>();
		ArrayList<Integer> sol2 = new ArrayList<Integer>();
		sol1.addAll(sol);
		sol2.addAll(sol);
		
		if(sol.size()==20){
			return isCorrectSolution(sol);
		}else{
			sol1.add(1);
			sol2.add(0);
			return rechProfondeur(sol1)||rechProfondeur(sol2);
		}
		
	}
	//wide search
	static Queue<ArrayList<Integer>> largeurQueue=new LinkedList<>();
	public static Boolean rechLargeur(){
		ArrayList<Integer> sol = new ArrayList<Integer>();
		ArrayList<Integer> sol1 = new ArrayList<Integer>();
		ArrayList<Integer> sol2 = new ArrayList<Integer>();
		if(!largeurQueue.isEmpty())sol.addAll(largeurQueue.poll());
		sol1.addAll(sol);
		sol2.addAll(sol);
		
		if(sol.size()==20){
			return isCorrectSolution(sol);
		}else{
			sol1.add(1);
			sol2.add(0);
			largeurQueue.add(sol1);
			largeurQueue.add(sol2);
			return rechLargeur();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		createformule();
		//showFormule();
		
		//System.out.println("parcour par profondeur :");
		//rechProfondeur(new ArrayList<Integer>());
		
		//System.out.println("parcour par largeur :");
		//rechLargeur();
		
		int cpt=0;
		while(!isCorrectSolution(genSolution())){
			cpt++;
		}
		System.out.println("\n"+cpt+" solutions tried");
	}
}
