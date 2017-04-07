package SearchTabu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import SearchTabu.City;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

public class TabuSearch1 {

	// Définir les villes
	static int nbVilles = 5;
	static int nbTests = 100;
	@SuppressWarnings("null")
	public static void main(String[] args) {
		TabuSearch tabuSearch = new TabuSearch();
		String csvFile = "D:\\Maxime\\Documents\\Java\\SearchTabu\\src\\SearchTabu\\villes.csv";
		City ville = new City();
		List<City> Villes = new ArrayList<City>();
		List<Ordre> ordre = new ArrayList<Ordre>();
		double[][] Distance = new double[nbVilles][nbVilles];
		int NumOrdreFinal[] = new int[nbVilles];
		int[] ordre0 = null;

		String line = "";
		String csvSplitBy = ";";
		nbVilles=0;
		try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
			while((line = br.readLine()) != null){ 
				nbVilles+=1;
				String[] coords = line.split(csvSplitBy);
				Villes.add(new City().setCity(nbVilles, (int) Float.parseFloat(coords[0]), (int)Float.parseFloat(coords[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0;i<nbVilles;i++){
			ordre0[i]=i;
		}
		
		
		
		ordre.add(new Ordre().setOrdre(ordre0,nbVilles));
		Distance = tabuSearch.evalDistance(Villes);
		double parcours = tabuSearch.evalParcours(ordre.get(0).getOrdre(), Distance);
		for (int j = 1; j < nbTests; j++) {
			ordre.add(tabuSearch.swapCity(ordre,j-1));
			double tempParcours = tabuSearch.evalParcours(ordre.get(j).getOrdre(), Distance);
			if (parcours > tempParcours) {
				parcours = tempParcours;
				NumOrdreFinal = ordre.get(j).getOrdre();
			}
			
		}
		System.out.println(parcours);
		System.out.println(Arrays.toString(NumOrdreFinal));
	}

	Ordre swapCity(List<Ordre> ordre,int j) {
		//List<Ordre> test = ordre;
		boolean diff = false;
		boolean diff1=false;
		boolean controle=false;
		int cont=0;
		int id1 = 0;
		int id2 = 0;
		int[] ordreTemp =new int[ordre.get(j).getSize()];
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
			for(int i=0; i<ordre.size()-1;i++){
				if(ordreTemp.equals(ordre.get(i))){
					diff1=false;
					ordreTemp[id2]=ordreTemp[id1];
					ordreTemp[id1]=temp1;
				}
				else{
					diff1=true;
				}
			
			}
			if(cont>50){
				controle = true;
			}
		}
		return new Ordre().setOrdre(ordreTemp,ordre.get(j).getSize());
	}

	double[][] evalDistance(List<City> Villes) {
		double[][] Distance = new double[nbVilles][nbVilles];
		for (int i = 0; i < nbVilles - 1; i++) {
			Distance[i][i] = 0;
			for (int j = i +1; j < nbVilles; j++) {
				//System.out.println(Math.sqrt((Villes.get(i).getX() - Villes.get(j).getX())*(Villes.get(i).getX() - Villes.get(j).getX()) + (Villes.get(i).getY() - Villes.get(j).getY())*(Villes.get(i).getY() - Villes.get(j).getY())));
				Distance[i][j] = Math.sqrt((Villes.get(i).getX() - Villes.get(j).getX())*(Villes.get(i).getX() - Villes.get(j).getX()) + (Villes.get(i).getY() - Villes.get(j).getY())*(Villes.get(i).getY() - Villes.get(j).getY()));
				//System.out.println(Distance[i][j]);
				Distance[j][i] = Distance[i][j];
			}
		}
		return Distance;
	}

	// Faire fonction calcul du parcours
	double evalParcours(int[] ordre, double distance[][]) {
		double parcours = 0;
		for (int i = 0; i < ordre.length-1; i++) {
			parcours += distance[ordre[i]][ordre[i + 1]];
		}
		return parcours;
	}
}
