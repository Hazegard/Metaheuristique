package SearchTabu;

public class Ordre {

	int size;
	int[] ordre;
	
	public void setSize(int Size){
		this.size=Size;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public Ordre setOrdre(int[] Ordre, int Size){
		this.ordre=Ordre;
		this.size = Size;
		return this;
	}
	public int[] getOrdre(){
		return this.ordre;
	}
	
	
}
