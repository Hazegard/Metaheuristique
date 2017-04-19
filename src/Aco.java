import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

public class Aco {

    static int MAX_CITIES = 30;
    private static final int MAX_ANTS = 30;
    private static final double ALPHA = 1.0;
    private static final double BETA = 5.0;
    private static final double RHO = 0.5;
    private static final int QVAL = 100;
    private static double INIT_PHEROMON = (1 / MAX_CITIES);
    private static final double RANK_W = MAX_ANTS / 2;
    private static Ants[] ants = new Ants[MAX_ANTS];
    private static Ants[] rankAnts = new Ants[MAX_ANTS];
    private static double[][] distance = new double[MAX_CITIES][MAX_CITIES];
    private static double[][] phero = new double[MAX_CITIES][MAX_CITIES];
    private static double best;
    private static int bestIndex;
    private static int[] bestPath;

    private static void init() {
        //On récupère une liste des villes à partir d'un fichier CSV
        List<City> Cities = new ArrayList<>();
        String line;
        String csvSplitBy = ";";
        int nbCities = 0;
        File file = new File("./data/villes.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                nbCities += 1;
                String[] coords = line.split(csvSplitBy);
                Cities.add(new City(Float.parseFloat(coords[0]), Float.parseFloat(coords[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MAX_CITIES = nbCities;
        best = MAX_CITIES * 100;
        //On initialise la matrice des phéromones
        INIT_PHEROMON = (double) 1 / MAX_CITIES;
        for (int i = 0; i < MAX_CITIES; i++) {
            for (int j = 0; j < MAX_CITIES; j++) {
                phero[i][j] = INIT_PHEROMON;
            }
        }
        //Calcul de la matrice des distances
        distance = evalDistance(Cities, nbCities);
        //Initialisation des fourmis
        int next = 0;
        for (int ant = 0; ant < MAX_ANTS; ant++) {
            if (next == MAX_CITIES) {
                next = 0;
            }
            int[] tempTable = new int[MAX_CITIES];
            int[] tempPath = new int[MAX_CITIES];
            for (int prev = 0; prev < MAX_CITIES; prev++) {
                tempTable[prev] = 0;
                tempPath[prev] = -1;
            }
            ants[ant] = new Ants(next++, -1, 1, tempTable, tempPath, 0);
            ants[ant].setOnePath(ants[ant].getCurrentCity(), 0);
            ants[ant].setOneTable(1, ants[ant].getCurrentCity());
        }
    }

    private static void resetAnts() {
        //On extrait les meilleurs temps, ainsi que le parcours de
        //chaque fourmis avant de les réinitialiser
        //afin de poursuivre la boucle
        int next = 0;
        for (int ant = 0; ant < MAX_ANTS; ant++) {
            if (ants[ant].getTourLength() < best) {
                best = ants[ant].getTourLength();
                bestIndex = ant;
                bestPath = ants[ant].getPath().clone();
                System.out.println("====================");
                System.out.println("Best tour : " + best);
                System.out.println(Arrays.toString(bestPath));
                System.out.println("====================");
            }
            ants[ant].setNextCIty(-1);
            ants[ant].setTourLength(0);
            for (int i = 0; i < MAX_CITIES; i++) {
                ants[ant].setOnePath(-1, i);
                ants[ant].setOneTable(0, i);
            }
            //Si la fourmis a atteint sa dernière ville
            //ON réinitialise la ville
            if (next == MAX_CITIES) {
                next = 0;
            }
            ants[ant].setCurrentCity(next++);
            ants[ant].setPathIndex(1);
            ants[ant].setOnePath(ants[ant].getCurrentCity(), 0);
            ants[ant].setOneTable(1, ants[ant].getCurrentCity());
        }
    }

    private static int selectNextCity(int ant) {
        //Choix de la prochaine ville
        double denom = 0;
        int prev = ants[ant].getCurrentCity();
        for (int next = 0; next < MAX_CITIES; next++) {
            if (ants[ant].getTable()[next] == 0) {
                denom += (Math.pow(phero[prev][next], ALPHA) * Math.pow((1 / distance[prev][next]), BETA));
            }
        }
        int next = 0;
        while (true) {
            double p;
            next++;
            if (next >= MAX_CITIES) {
                next = 0;
            }
            if (ants[ant].getTable()[next] == 0) {
                p = (Math.pow(phero[prev][next], ALPHA) * Math.pow((1 / distance[prev][next]), BETA)) / denom;
                double x = Math.random() / 30;
                if (x < p) {
                    break;
                }
            }
        }
        return next;
    }

    private static int moveAnts() {
        //Déplacement des fourmis:
        //On met à jours les paramètres de chaque fourmis
        int moving = 0;
        for (int k = 0; k < MAX_ANTS; k++) {
            if (ants[k].getPathIndex() < MAX_CITIES) {
                ants[k].setNextCIty(selectNextCity(k));
                ants[k].setOneTable(1, ants[k].getNextCIty());
                ants[k].setOnePath(ants[k].getNextCIty(), ants[k].getPathIndex());
                ants[k].setPathIndex(ants[k].getPathIndex() + 1);
                ants[k].setTourLength(ants[k].getTourLength() + distance[ants[k].getCurrentCity()][ants[k].getNextCIty()]);
                //Gestion du cas d'un déplacement entre la dernière ville
                //et la première ville
                if (ants[k].getPathIndex() == MAX_CITIES) {
                    ants[k].setTourLength(ants[k].getTourLength() + distance[ants[k].getPath()[MAX_CITIES - 1]][ants[k].getPath()[0]]);
                }
                ants[k].setCurrentCity(ants[k].getNextCIty());
                moving++;
            }
        }
        return moving;
    }

    private static void sortAnts() {
        //On tri les fourmis afinde mieux pouvoir mettre à jour les phéromones par la suite
        Ants tempAnt;
        for (int i = 0; i < MAX_CITIES; i++) {
            rankAnts[i] = new Ants(ants[i].getCurrentCity(), ants[i].getNextCIty(), ants[i].getPathIndex(), ants[i].getTable(), ants[i].getPath(), ants[i].getTourLength());
        }
        for (int j = 0; j < MAX_CITIES; j++) {
            for (int k = j + 1; k < MAX_CITIES; k++) {
                if (rankAnts[j].getTourLength() >= rankAnts[k].getTourLength()) {
                    tempAnt = rankAnts[j];
                    rankAnts[j] = rankAnts[k];
                    rankAnts[k] = tempAnt;
                }
            }
        }
    }

    private static void updatePhero() {
        //Mise à jour des phéromones sur chaque ville
        for (int prev = 0; prev < MAX_CITIES; prev++) {
            for (int next = 0; next < MAX_CITIES; next++) {
                if (prev != next) {
                    phero[prev][next] *= (1 - RHO);
                    if (phero[prev][next] < 0) {
                        phero[prev][next] = INIT_PHEROMON;
                    }
                }
            }
        }
        int prev, next;
        for (int ant = 0; ant < RANK_W - 1; ant++) {
            for (int i = 0; i < MAX_CITIES; i++) {
                if (i < MAX_CITIES - 1) {
                    prev = rankAnts[ant].getPath()[i];
                    next = rankAnts[ant].getPath()[i + 1];
                } else {
                    prev = rankAnts[ant].getPath()[i];
                    next = rankAnts[ant].getPath()[0];
                }
                phero[prev][next] += (RANK_W - ant) * (QVAL / rankAnts[ant].getTourLength());
                phero[next][prev] = phero[prev][next];
            }
        }
        for (int i = 0; i < MAX_CITIES; i++) {
            if (i < MAX_CITIES - 1) {
                prev = ants[bestIndex].getPath()[i];
                next = ants[bestIndex].getPath()[i + 1];
            } else {
                prev = ants[bestIndex].getPath()[i];
                next = ants[bestIndex].getPath()[0];
            }
            phero[prev][next] += QVAL / best;
            phero[next][prev] = phero[prev][next];
        }
        for (prev = 0; prev < MAX_CITIES; prev++) {
            for (next = 0; next < MAX_CITIES; next++) {
                phero[prev][next] *= RHO;
            }
        }
    }

    private static double[][] evalDistance(List<City> Villes, int nbCities) {
        //Calcul une matrice des distance en ayant pour entrée la liste des villes avec leurs coordonnées
        double[][] Distance = new double[nbCities][nbCities];
        for (int i = 0; i < nbCities - 1; i++) {
            Distance[i][i] = 0;
            for (int j = i + 1; j < nbCities; j++) {
                Distance[i][j] = Math.sqrt((Villes.get(i).getX() - Villes.get(j).getX()) * (Villes.get(i).getX() - Villes.get(j).getX()) + (Villes.get(i).getY() - Villes.get(j).getY()) * (Villes.get(i).getY() - Villes.get(j).getY()));
                Distance[j][i] = Distance[i][j];
            }
        }
        return Distance;
    }

    public static void main(String[] args) {
        final int MAX_TIME = 100;
        int curTime = 0;
        init();
        while (curTime++ < MAX_TIME) {
            if (moveAnts() == 0) {
                sortAnts();
                updatePhero();
                if (curTime != MAX_TIME) {
                    resetAnts();
                }
            }
        }
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("\nBest tour : " + best + "\n");
        System.out.println(Arrays.toString(bestPath));
    }
}