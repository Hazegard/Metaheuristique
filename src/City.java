//Classe City:
//Prend comme oaramètre un identifiant ainsi que les cordonnées

public class City {
	private float x;
	private float y;

	City setCity(float X, float Y){
		this.x=X;
		this.y=Y;
		return this;
	}
    float getX(){
	    return x;
	}

	float getY(){
        return y;
    }
}
