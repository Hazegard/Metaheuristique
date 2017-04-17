/**
 * Created by Max on 12/04/2017.
 */
public class Ants {
    static int MAX_CITIES = Aco.MAX_CITIES;
    int currentCity;
    int nextCIty;
    int pathIndex;
    int[] table= new int [MAX_CITIES];
    int[] path = new int [MAX_CITIES];
    double tourLength;

    public Ants (int CurrentCity, int NextCity, int PathIndex, int[] Table, int[] Path, double TourLength){
        super();
        this.currentCity=CurrentCity;
        this.nextCIty=NextCity;
        this.pathIndex=PathIndex;
        this.table=Table;
        this.path=Path;
        this.tourLength = TourLength;
    }

    void setCurrentCity(int CurrentCity){
        this.currentCity=CurrentCity;
    }

    void setPath(int[] Path){
        this.path=Path;
    }

    void setTable(int[] Table){
        this.table = table;
    }
    void setNextCIty(int NextCity){
        this.nextCIty=NextCity;
    }
    void setPathIndex(int PathIndex){
        this.pathIndex=PathIndex;
    }

    void setTourLength(double TourLength){
        this.tourLength=TourLength;
    }

    void setOnePath(int Path, int index){
        this.path[index]=Path;
    }

    void setOneTable(int Table, int index){
        this.table[index]=Table;
    }

    int getCurrentCity(){
        return this.currentCity;
    }

    int getNextCIty(){
        return this.nextCIty;
    }

    int getPathIndex(){
        return this.pathIndex;
    }

    int[] getTable(){
        return this.table;
    }

    int[] getPath(){
        return this.path;
    }
    
    double getTourLength(){
        return tourLength;
    }
}

