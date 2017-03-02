package SearchTabu;

public class City {
	int id;
	int x;
	int y;
	
City setCity(int Id, int X, int Y){
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

void setX(int X){
	x=X;
}
void setY(int Y){
	y=Y;
}

int getX(){
	return x;
}

int getY(){
	return y;
}
}
