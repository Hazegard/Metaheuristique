//Classe Ordre, prend comme paramètre un tableau décrivant l'ordre suivi
public class Ordre {

	private int size;
    private int[] ordre;
	
    int getSize(){
		return this.size;
	}

	Ordre setOrdre(int[] Ordre, int Size){
		this.ordre=Ordre;
		this.size = Size;
		return this;
	}
	int[] getOrdre(){
		return this.ordre;
	}
	
	
}
