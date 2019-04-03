import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CNF 
{
	 ArrayList<ArrayList<Integer>> Fermer = new ArrayList<ArrayList<Integer>>();
	 int[][]	formule	=	new int[91][3];
	 String		nomF	=	"/home/dell/Documents/Documents/workspace/énoncer des TPs/MetaH/uf20-91/uf20-01.cnf";
	//=========================================================
	//	fonction pour lire le fichier
	//=========================================================
	@SuppressWarnings("null")
	public void createformule() throws IOException
	{
		FileInputStream 	fis	=	null;
		InputStreamReader	isr	=	null;
		BufferedReader 		br	=	null;
		try 
		{
			fis		=	new FileInputStream(nomF);
			isr		=	new InputStreamReader(fis);
			br		=	new BufferedReader(isr);
			
			String 		ligne;
			String[]	tabString;
			//lire les lignes qui commence avec la lettre C+1
			while ((ligne=br.readLine())!=null	&&	ligne.startsWith("c"));
			
			int laLigne=0;
			while ((ligne=br.readLine())!=null	&&	!ligne.startsWith("%"))
			{
				//découper la ligne
				tabString	=	ligne.trim().split(" ");
				
				//recopier les valeurs
				for (int laColonne = 0; laColonne < 3; laColonne++) 
				{
					formule[laLigne][laColonne]	=	Integer.parseInt(tabString[laColonne]);
				}
				//incrémenter la ligne pour la prochaine itération
				laLigne++;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			br.close();
			e.printStackTrace();
		}
	}
	

	//==========================================
	//	fonction pour afficher la formule
	//==========================================
	public  void showFormule()
	{
		for (int i = 0; i < formule.length; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				System.out.printf("%3d\t",formule[i][j]);
			}
			System.out.println("");
		}
	}
	
	
	
	//==============================================
	//	fonction pour tester la solution potentiel
	//==============================================
	public  Boolean isCorrectSolution(ArrayList<Integer> sol)
	{
		int lit1,lit2,lit3;
		
		for (int i = 0; i < formule.length; i++)
		{
			lit1=Math.abs(formule[i][0])-1;
			lit2=Math.abs(formule[i][1])-1;
			lit3=Math.abs(formule[i][2])-1;
			int lit1state = (formule[i][0]>0)? 1 :0;
			int lit2state = (formule[i][1]>0)? 1 :0;
			int lit3state = (formule[i][2]>0)? 1 :0;
			
			if(sol.get(lit1) != lit1state && sol.get(lit2) != lit2state && sol.get(lit3) != lit3state)
			{
				//System.out.println("Failed");
				return false;
			}
				
		}
		System.out.println("successsssssss");
		for (int i = 0; i < sol.size(); i++)
		{
			System.out.print(sol.get(i)+" ");
		}
		return true;
	}
	

	
	
	
	
	
	//==========================================
	//	recherche en profondeur d'abord
	//==========================================
	public  Boolean rechProfondeur(ArrayList<Integer> sol)
	{
		ArrayList<Integer> sol1 = new ArrayList<Integer>();
		ArrayList<Integer> sol2 = new ArrayList<Integer>();
		sol1.addAll(sol);
		sol2.addAll(sol);
		
		if(sol.size()==20)
		{
			return isCorrectSolution(sol);
		}
		else
		{
			sol1.add(1);
			sol2.add(0);
			return rechProfondeur(sol1)||rechProfondeur(sol2);
		}
		
	}
	
	//==========================================
	//	recherche en largeur d'abord
	//==========================================
	
	Queue<ArrayList<Integer>> Ouvert	=	new LinkedList<>();
	public void rechLargeur()
	{
		ArrayList<Integer> sol 	= new ArrayList<Integer>();
		ArrayList<Integer> sol1 = new ArrayList<Integer>();
		ArrayList<Integer> sol2 = new ArrayList<Integer>();
		sol1.add(1);	Ouvert.add(sol1);
		sol2.add(0);	Ouvert.add(sol2);
		//System.out.println(sol1);
		while(!Ouvert.isEmpty())
		{
			//System.out.println("Ouvert\t"+Ouvert.size());
			//System.out.println("Ouvert.poll()\t"+Ouvert.poll());
			sol	=	Ouvert.poll();
			if(sol.size()==20)
			{
				if(isCorrectSolution(sol))
					break;
			}
			else
			{
				//System.out.println("sol\t"+sol);
				sol1	=	new	ArrayList<Integer>(sol);
				//System.out.println("sol1\t"+sol1);
				sol2	=	new	ArrayList<Integer>(sol);
				//System.out.println("sol2\t"+sol2);
				sol1.add(1);
				//System.out.println("sol1\t"+sol1);
				sol2.add(0);
				//System.out.println("sol2\t"+sol2);
				Ouvert.add(sol1);
				Ouvert.add(sol2);
				//System.out.println("===========");
			}
		}
		
		
		
		
	}
	//==========================================
	//	recherche Aléatoire
	//==========================================
	public  void rechercheAléatoire()
	{
		int cpt=0;
		while(!isCorrectSolution(genSolution()))
		{
			cpt++;
		}
		System.out.println("\n"+cpt+" solutions tried");
	}
	
	//==========================================
	//	fonction qui génère un solution aléatoire
	//==========================================
	public  ArrayList<Integer> genSolution()
	{
		ArrayList<Integer> sol = new ArrayList<Integer>();
		Random r = new Random();
		do
		{
			sol.clear();
			for (int i = 0; i < 20; i++)
			{
				sol.add(r.nextInt(2));
					//System.out.print(sol.get(i)+"|");
			}
		}
		while(déjàEssayé(sol));
		
		//System.out.println("");
		Fermer.add(sol);
		return sol;
	}
		
	//==============================================================================================================================
	//	fonction qui recherche dans la structure "fermer" pour savoir si la solution est déjà utilisée au pas
	//===========================================================================================================
	@SuppressWarnings("unlikely-arg-type")
	
	public  Boolean déjàEssayé(ArrayList<Integer> sol)
	{
		if(Fermer.containsAll(sol))
		{
		//	System.out.print("   bad\n");
			return true;
		}
		else
		{
		//	System.out.println("   good\n");
			return false;
		}
	}


}
