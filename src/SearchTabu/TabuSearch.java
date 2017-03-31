package SearchTabu;

import java.util.ArrayList;
import java.util.List;
import SearchTabu.City;
import java.lang.Math;
public class TabuSerach {
	
	//Définir les villes
	int nbVilles = 5 ;
	City ville = new City();

	public void main(){
	List<City> Villes = new ArrayList<City>();
	List<int[]> ordre = new ArrayList<int[]>();
	double[][] Distance = new double[nbVilles][nbVilles];
	int[] ordre0 = {0,1,2,3,4};
	//Ordre en liste de tableau de Int
	ordre.add(ordre0);
	for(int i=0; i<nbVilles;i++){
			int x=0;
			int y=0;
			Villes.add(ville.setCity(x,y,i));		
		}
	
		evalDistance(Villes);
	
	
	
	}
	
	List<City> swapCity(List<City> villes){
		boolean diff = true;
		int id1=0;
		int id2=0;
		while(diff){
		id1 = (int) (Math.random()*nbVilles);
		id2 = (int) (Math.random()*nbVilles);
			if (id1!=id2){
				diff=false;
			}
		}
		
		City temp = villes.get(id1);
		villes.set(id1, villes.get(id2));
		villes.set(id2, temp);
		return villes;
	}
	
	
	double[][] evalDistance(List<City> Villes){
		double[][] Distance = new double[nbVilles][nbVilles];
		for(int i=0;i<nbVilles-1;i++){
			Distance[i][i]=0;
			for(int j=i+1;j<nbVilles;j++){
				Distance[i][j]=Math.sqrt((Villes.get(i).getX()-Villes.get(j).getX())^2+(Villes.get(i).getY()*Villes.get(j).getY())^2);
				Distance[j][i]=Distance[i][j];
			}
		}
		
		return Distance;
	}
	
	//Faire fonction calcul du parcours
	double evalParcours(int[] ordre, double distance[][]){
		double parcours = 0;
		for(int i =0; i<ordre.length;i++){
		parcours+= distance[ordre[i]][ordre[i+1]];
		}
		return parcours;
	}
}
