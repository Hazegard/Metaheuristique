/**
 * Created by Max on 12/04/2017.
 */
public class City {
    float x;
    float y;
    int id;

    public City (float X, float Y, int Id){
        super();
        this.x=X;
        this.y=Y;
        this.id=Id;
    }

    float getX(){
        return this.x;
    }

    float getY(){
        return this.y;
    }

    int getId(){
        return this.id;
    }
}
