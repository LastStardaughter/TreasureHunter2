package util;

public class Coord{
    //this is almost more of a struct than a class, really. Has some basic helper methods though.
    public int x, y; //The whole point is making it easy to manipulate these ints, so they're public.

    public Coord(){
        x=-1;
        y=-1;
    }

    public Coord(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Coord(int[] pos){
        x=pos[0];
        y=pos[1];
    }

    public Coord(Coord pos){
        x=pos.x;
        y=pos.y;
    }

    public void add(Coord a){
        x+=a.x;
        y+=a.y;
    }

    public void add(int[] a){
        x+=a[0];
        y+=a[1];
    }

    public void add(int dx, int dy){
        x+=dx;
        y+=dy;
    }

    public int dir(){
        //This is only to be used on Coords containing orthagonal vectors.
        if (y==0){
            return (x>0)? 1 : 3;
        } else {return (y>0)? 2 : 0;}
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Coord)) { 
            return false; 
        } 
        Coord c = (Coord) o;
        return this.x == c.x && this.y == c.y;
    }

    @Override
    public int hashCode(){
        return (y<<16) | (x & 0b00000000000000001111111111111111);
    }
}