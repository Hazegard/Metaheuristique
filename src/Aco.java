import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
/**
 * Created by Max on 12/04/2017.
 */
public class Aco {

static int MAX_CITIES = 30;
static final int MAX_DISTANCE =100;
//static int MAX_TOUR=MAX_CITIES*MAX_DISTANCE;
static final int MAX_ANTS = 30;

static final double ALPHA = 1.0;
static final double BETA = 5.0;
static final double RHO = 0.5;

static final int QVAL = 100;
static final int MAX_TOUR = 20;
static double INIT_PHEROMON = (1/MAX_CITIES);
static final double RANK_W = MAX_ANTS/2;
static Ants[] ants = new Ants[MAX_ANTS];
static Ants[] rankAnts = new Ants[MAX_ANTS];
static double[][] distance = new double[MAX_CITIES][MAX_CITIES];
static double[][] phero = new double[MAX_CITIES][MAX_CITIES];


static String csvFile ="D:\\Maxime\\Documents\\Java\\SearchTabu\\src\\SearchTabu\\villes2.csv";
static double best;// = MAX_TOUR;
static int bestIndex;
static int[] bestPath;

static void init(){
    //Ants[] ants = new Ants[MAX_ANTS];
    //double[][] distance = new double[MAX_CITIES][MAX_CITIES];
    //double[][] phero = new double[MAX_CITIES][MAX_CITIES];
    //Ants[] ants = new Ants[MAX_ANTS];
    Aco aco = new Aco();
        int to;
        int ant;


        List<City> Cities = new ArrayList<City>();
        String line = "";
        String csvSplitBy = ";";
        int nbCities = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                nbCities += 1;
                String[] coords = line.split(csvSplitBy);
                Cities.add(new City(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]), nbCities));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MAX_CITIES = nbCities;
        best = MAX_CITIES*100;
        INIT_PHEROMON=(double) 1/MAX_CITIES;
    for(int i=0;i<MAX_CITIES;i++){
        for(int j=0;j<MAX_CITIES;j++){
            phero[i][j]=INIT_PHEROMON;
        }
    }
    distance=aco.evalDistance(Cities,nbCities);

        to=0;
        for(ant=0;ant<MAX_ANTS;ant++){
            if(to==MAX_CITIES){
                to=0;
            }
            //ants[ant].setCurrentCity(to++);
            int[] tempTable = new int[MAX_CITIES];
            int[] tempPath = new int[MAX_CITIES];
            for(int from = 0; from <MAX_CITIES;from++){
                tempTable[from]=0;
                tempPath[from] = -1;
            }
            ants[ant]=new Ants(to++,-1,1,tempTable,tempPath,0);
            ants[ant].setOnePath(ants[ant].getCurrentCity(),0);
            ants[ant].setOneTable(1,ants[ant].getCurrentCity());
            //System.out.print("Bite\n");
        }
    //System.out.print("Couille\n");
}

static void resetAnts(){
    int to=0;
    for(int ant =0; ant<MAX_ANTS;ant++){
        if(ants[ant].getTourLength()<best){
            best = ants[ant].getTourLength();
            bestIndex = ant;
            bestPath = ants[ant].getPath().clone();
        }

        ants[ant].setNextCIty(-1);
        ants[ant].setTourLength(0);

        for(int i=0;i<MAX_CITIES;i++){
            ants[ant].setOnePath(-1,i);
            ants[ant].setOneTable(0,i);
        }

        if(to==MAX_CITIES){
            to=0;
        }

        ants[ant].setCurrentCity(to++);
        ants[ant].setPathIndex(1);
        ants[ant].setOnePath(ants[ant].getCurrentCity(),0);

        ants[ant].setOneTable(1,ants[ant].getCurrentCity());
    }

}

static double antProd(int from, int to){
    double test4 = distance[from][to];
    int test5=from;
    int test6=to;
    double test3 = 1/distance[from][to];
    double test1=Math.pow(phero[from][to],ALPHA);
    double test2=Math.pow(1/distance[from][to],BETA);
    double test =(Math.pow(phero[from][to],ALPHA)* Math.pow((1/distance[from][to]),BETA));
    return test;
}

static int selectNextCity(int ant){
    int from, to;
    double denom=0;

    from=ants[ant].getCurrentCity();

    for(to=0;to<MAX_CITIES;to++){
        if(ants[ant].getTable()[to]==0){
            denom+=antProd(from,to);
        }
    }

    while(true){
        double p;
        to++;
        //System.out.print("Chatte\n");
        if(to>=MAX_CITIES){
            to=0;
        }
        int test = ants[ant].getTable()[to];
        if(ants[ant].getTable()[to]==0){
            p=antProd(from,to)/denom;
            if(p>0.9999999999){
                int jk=50;
            }
            double x = Math.random()/30;
            //System.out.println("\n"+x+";"+p);
            if(x<p){
                break;
            }
        }
    }
    return to;
}

static int moveAnts(){
    int moving = 0;

    for(int k =0;k<MAX_ANTS;k++){
        if(ants[k].getPathIndex()<MAX_CITIES){
            ants[k].setNextCIty(selectNextCity(k));
            ants[k].setOneTable(1,ants[k].getNextCIty());
            ants[k].setOnePath(ants[k].getNextCIty(),ants[k].getPathIndex());
            ants[k].setPathIndex(ants[k].getPathIndex()+1);

            ants[k].setTourLength(ants[k].getTourLength()+distance[ants[k].getCurrentCity()][ants[k].getNextCIty()]);

            if(ants[k].getPathIndex()==MAX_CITIES){
                ants[k].setTourLength(ants[k].getTourLength()+distance[ants[k].getPath()[MAX_CITIES-1]][ants[k].getPath()[0]]);
            }

            ants[k].setCurrentCity(ants[k].getNextCIty());
            moving++;
        }
    }
    return moving;
}

static void sortAnts(){
    Ants tempAnt ;

    for(int i=0;i<MAX_CITIES;i++){
            rankAnts[i] = new Ants(ants[i].getCurrentCity(),ants[i].getNextCIty(),ants[i].getPathIndex(),ants[i].getTable(),ants[i].getPath(),ants[i].getTourLength());
    }
    //System.arraycopy(ants,0,rankAnts,1);

    for(int j =0; j<MAX_CITIES;j++){
        for(int k=j+1;k<MAX_CITIES;k++){
            if(rankAnts[j].getTourLength()>=rankAnts[k].getTourLength()){
                tempAnt = rankAnts[j];
                rankAnts[j] = rankAnts[k];
                rankAnts[k]=tempAnt;
            }
        }
    }
}

static void updateTrails(){
    for(int from=0;from<MAX_CITIES;from++){
        for(int to=0;to<MAX_CITIES;to++){
            if(from!=to){
                phero[from][to]*=(1-RHO);
                if(phero[from][to]<0){
                    phero[from][to]=INIT_PHEROMON;
                }
            }
        }
    }
    int from,to;
    for(int ant=0;ant<RANK_W-1;ant++){
        for(int i=0;i<MAX_CITIES;i++){
            if(i<MAX_CITIES-1){
                from = rankAnts[ant].getPath()[i];
                to = rankAnts[ant].getPath()[i+1];
            } else{
                from = rankAnts[ant].getPath()[i];
                to = rankAnts[ant].getPath()[0];
            }
            System.out.println(rankAnts[ant].getTourLength());
            System.out.println(from+";"+to+";"+rankAnts[ant].getPath()[i]);
            phero[from][to]+=(RANK_W-ant)*(QVAL/rankAnts[ant].getTourLength());
            phero[to][from]=phero[from][to];
        }
    }

    for(int i=0;i>MAX_CITIES;i++){
        if(i<MAX_CITIES-1){
            from = ants[bestIndex].getPath()[i];
            to = ants[bestIndex].getPath()[i+1];
        } else {
            from = ants[bestIndex].getPath()[i];
            to = ants[bestIndex].getPath()[0];
        }
        phero[from][to]+=QVAL/best;
        phero[to][from] = phero[from][to];
    }

    for(from =0;from<MAX_CITIES;from++){
        for(to=0;to<MAX_CITIES;to++){
            phero[from][to]*=RHO;
        }
    }
}

double[][] evalDistance(List<City> Villes,int nbCities) {
    double[][] Distance = new double[nbCities][nbCities];
    for (int i = 0; i < nbCities - 1; i++) {
        Distance[i][i] = 0;
        for (int j = i +1; j < nbCities; j++) {
            //System.out.println(Math.sqrt((Villes.get(i).getX() - Villes.get(j).getX())*(Villes.get(i).getX() - Villes.get(j).getX()) + (Villes.get(i).getY() - Villes.get(j).getY())*(Villes.get(i).getY() - Villes.get(j).getY())));
            Distance[i][j] = Math.sqrt((Villes.get(i).getX() - Villes.get(j).getX())*(Villes.get(i).getX() - Villes.get(j).getX()) + (Villes.get(i).getY() - Villes.get(j).getY())*(Villes.get(i).getY() - Villes.get(j).getY()));
            //System.out.println(Distance[i][j]);
            Distance[j][i] = Distance[i][j];
        }
    }
    return Distance;
}
 public static void main(String[] args){
    final int MAX_TIME = 100;
    int curTime=0;
    init();
    while(curTime++<MAX_TIME){
        System.out.println(curTime);
        if(curTime==14){
            int test=45;
        }
        if(moveAnts()==0){
            int jk=50;
            sortAnts();
            updateTrails();

            if(curTime!=MAX_TIME){
                resetAnts();
            }
            System.out.println("Current time : "+curTime+" \n"+best);
        }
    }
System.out.println("\nBest tour : "+best+"\n");
    System.out.println(Arrays.toString(bestPath));
 }

}
