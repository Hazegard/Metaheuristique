import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

public class SearchTabu {
	// Definir les villes
	static int nbVilles = 14;
	static int nbTests = 1000;
	public static void main(String[] args) {
		City ville = new City();
		List<City> Villes = new ArrayList<>();
		List<Ordre> ordre = new ArrayList<>();
		double[][] Distance;
		int NumOrdreFinal[];
		String line;
		String csvSplitBy = ";";
		nbVilles=0;
		//On charge les données depuis un fichier CSV
        //Contenant les coordonnées séparées par des ";"
		File file = new File("./data/villes.csv");
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			while((line = br.readLine()) != null){ 
				nbVilles+=1;
				String[] coords = line.split(csvSplitBy);
				Villes.add(new City().setCity(nbVilles, Float.parseFloat(coords[0]), Float.parseFloat(coords[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Définition du permier ordre de la recherche
        //Il s'agit de l'ordre d'arrivée des données
		int[] ordre0 = new int[nbVilles];
		for(int i=0;i<nbVilles;i++){
			ordre0[i]=i;
		}
		ordre.add(new Ordre().setOrdre(ordre0,nbVilles));
		NumOrdreFinal = ordre0;
		//On évalue la valeur du parcours de l'ordre0
		Distance = evalDistance(Villes);
		double parcours = evalParcours(ordre.get(0).getOrdre(), Distance);
		System.out.println(parcours);

		//On boucle sur un certain nombre de test
		for (int j = 1; j < nbTests; j++) {
		    //On modifie l'ordre par un échange de deux villes
			Ordre testOrdre=swapCity(ordre,j-1);
			double tempParcours = evalParcours(testOrdre.getOrdre(), Distance);
			//On regarde si le nouveau temps de parcours est inférieur au meilleur temps
            //de parcours actuel
			if (parcours > tempParcours) {
			    //Si oui:
                //ON met à jours le meileur temps de parcours
                //On met à jour l'ordre associé au meilleurs temps de parcours
                //On ajoute cet ordre à la liste des ordres
				parcours = tempParcours;
				NumOrdreFinal = testOrdre.getOrdre();
				ordre.add(testOrdre);
				System.out.println("============================");
				System.out.println("Tour n= "+j+"\nDistance= "+parcours+"\nOrdre = "+Arrays.toString(NumOrdreFinal));
				System.out.println("============================");
			}else{
			    //Sinon, on reprend l'ordre précédent, que l'on ajoute à la liste
				ordre.add(ordre.get(j-1));
				}
		}
		System.out.println(parcours);
		System.out.println(Arrays.toString(NumOrdreFinal));
	}

	static Ordre swapCity(List<Ordre> ordre,int j) {
	    //Fonction qui échange deux villes à partir du j-ième élement de la liste
        //En s'assurant que le nouvel ordre n'a pas déjà été traité par la recherche
		boolean diff = false;
		boolean diff1=false;
		boolean controle=false;
		int cont=0;
		int id1 = 0;
		int id2 = 0;
		int[] ordreTemp =new int[ordre.get(j).getSize()];
		//On vérifie que les deux villes à échanger sont distinctes
		while (!(diff & diff1) | controle) {
			cont++;
			id1 = (int) (Math.random() * nbVilles);
			id2 = (int) (Math.random() * nbVilles);
			if (id1 != id2) {
				diff = true;
			}
			else{
				diff=false;
			}
			ordreTemp = ordre.get(j).getOrdre().clone();
			int temp1 = ordreTemp[id1];
			ordreTemp[id1]=ordreTemp[id2];
			ordreTemp[id2]=temp1;
			diff1=true;
			//on vérifie que l'ordre nouvellement crée n'est pas déjà existant dans
            //la liste
			for(int i=0; i<ordre.size()-1;i++){
				if(Arrays.equals(ordreTemp,ordre.get(i).getOrdre())){
					diff1=false;
					temp1=ordreTemp[id2];
					ordreTemp[id2]=ordreTemp[id1];
					ordreTemp[id1]=temp1;
				}
				else{
					diff1=true;
				}

			}
			//Si on tourne en boucle
			if(cont>50){
				controle = true;
			}
		}
		return new Ordre().setOrdre(ordreTemp,ordre.get(j).getSize());
	}

	static double[][] evalDistance(List<City> Villes) {
	    //Fonction qui prend comme entrée la liste des villes et qui renvoie
        //Un tableau contenant les distances entre les villes
		double[][] Distance = new double[nbVilles][nbVilles];
		for (int i = 0; i < nbVilles - 1; i++) {
			Distance[i][i] = 0;
			for (int j = i +1; j < nbVilles; j++) {
				Distance[i][j] = Math.sqrt((Villes.get(i).getX() - Villes.get(j).getX())*(Villes.get(i).getX() - Villes.get(j).getX()) + (Villes.get(i).getY() - Villes.get(j).getY())*(Villes.get(i).getY() - Villes.get(j).getY()));
				Distance[j][i] = Distance[i][j];
			}
		}
		return Distance;
	}

	// Faire fonction calcul du parcours
	static double evalParcours(int[] ordre, double distance[][]) {
	    //Fonction qui prend un ordre ainsi que la matrice des distances
        //et qui calcul le chemin parcouru pour l'ordre donné
		double parcours = 0;
		for (int i = 0; i < ordre.length-1; i++) {
			parcours += distance[ordre[i]][ordre[i + 1]];
		}
		return parcours;
	}
}