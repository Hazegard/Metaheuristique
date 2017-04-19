//Classe City:
//Prend comme oaramètre un identifiant ainsi que les cordonnées

public class City {
    private float x;
    private float y;

    public City (float X, float Y){
        super();
        this.x=X;
        this.y=Y;
    }

    float getX(){
        return this.x;
    }

    float getY(){
        return this.y;
    }
	City setCity(float X, float Y){
		this.x=X;
		this.y=Y;
		return this;
    }
}
