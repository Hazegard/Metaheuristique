//Classe City:
//Prend comme oaramètre un identifiant ainsi que les cordonnées

public class City {
	int id;
	float x;
	float y;
	
City setCity(int Id, float X, float Y){
		this.x=X;
		this.y=Y;
		this.id=Id;
		return this;
	}

void setId(int Id){
		id = Id;
}

int getId(){
	return id;
}

void setX(float X){
	x=X;
}
void setY(float Y){
	y=Y;
}

float getX(){
	return x;
}

float getY(){
	return y;
}
}
