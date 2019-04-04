import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class All
{
	//==========================================
	//	La fonction principal
	//==========================================
	public static void main(String[] args) throws IOException 
	{
		CNF cnf	=	new	CNF();
		
		cnf.createformule();
		int		leChoix;
		
		@SuppressWarnings("resource")
		Scanner s	=	new Scanner(System.in);
		
		do
		{
			
			System.out.printf("\n\n\t\tvoilà les options qu'il existe:\n");
			System.out.println("0-arrêter le programme:");
			System.out.println("1-afficher la formule:");
			System.out.println("2-parcours en largeur:");
			System.out.println("3-parcours en profondeur:");
			System.out.println("4-génération des solutions aléatoire:");
			
			System.out.println("quel est votre choix ?");
			leChoix		=	s.nextInt();
			
			
			
			switch(leChoix)
			{
				case 0:	
					System.out.println("au revoir !");
					System.exit(0);
					break;
				case 1:	
					cnf.showFormule();
					break;
						
				case 2:	
					System.out.println("parcour par largeur :");
					cnf.rechLargeur();
					break;
				
				case 3:	
					System.out.println("parcour par profondeur :");
					cnf.rechProfondeur(new ArrayList<Integer>());
					break;
				
				case 4:
					System.out.println("recherche Aléatoire :");
					cnf.rechercheAléatoire();
					break;
				default:
					System.out.println("ce choix n'existe plus");
			}
		}while(true);	
		
		
		
		
	}
	
	
}
