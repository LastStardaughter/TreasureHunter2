package util;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

public class Minefield {
    private Set<Integer> minesx = new HashSet<Integer>(), minesy = new HashSet<Integer>();
    private ArrayList<Coord> mines = new ArrayList<Coord>();
    private boolean toss; //Throwaway return value from Set.add()

    public Minefield(int[][] list){
        for (int[] mine : list) {
            add(mine);
        }
    }

    public void add(Coord mine){
        //We store sets of which coords even HAVE mines for later optimizations.
        toss=minesx.add(mine.x);
        toss=minesy.add(mine.y);
        mines.add(mine);
    }

    public void add(int[] mine){
        add(new Coord(mine));
    }

    public boolean hasMine(Coord pos){
        if(!minesx.contains(pos.x)){return false;}
        if(!minesy.contains(pos.y)){return false;}
        return mines.contains(pos);
    }

    public boolean walkSafe(Coord origstart, Coord delta){
        Coord start = new Coord(0,0), end = new Coord(0,0);
        int dir=delta.dir();
        Iterator<Integer> screen; //screened list of x or y coords of mines on the same line as our movement.
        //To make the below code easier, we always rewrite things in terms of moving north or east.
        if (dir==0 || dir==3){
            start.add(origstart);
            start.add(delta);
            end.add(start);
            end.add(-delta.x, -delta.y);
            dir=(dir+2)%4;
        } else {
            start.add(origstart);
            end.add(start);
            end.add(delta);
        }
        System.out.println("Checking safe walk from ("+start.x+", "+start.y+"): ("+delta.x+", "+delta.y+")"); //delta is pre-flipping, oops
        if (dir==0){
            if (!minesx.contains(start.x)){return true;}
            //forEach won't work because we need to break the loop and return false if we step on a mine
            screen=getMinesy(start.x).iterator();
            while (screen.hasNext()){
                int miney=screen.next();
                System.out.println("Mine at position "+miney);
                if (miney >= start.y && miney <= end.y){
                    return false;
                }
            }
            return true;
        }
        //assuming dir==1 then
        if (!minesy.contains(start.y)){return true;}
        screen=getMinesx(start.y).iterator();
        while (screen.hasNext()){
            int minex=screen.next();
            System.out.println("Mine at position "+minex);
            if (minex >= start.x && minex <= end.x){
                return false;
            }
        }
        return true;
    }

    private Set<Integer> getMinesx(int y){
        Set<Integer> x = new HashSet<Integer>();
        mines.forEach((mine) -> {
            if (mine.y==y){
                x.add(mine.x);
            }
        });
        return x;
    }

    private Set<Integer> getMinesy(int x){
        Set<Integer> y = new HashSet<Integer>();
        mines.forEach((mine) -> {
            if (mine.x==x){
                y.add(mine.y);
            }
        });
        return y;
    }
    
}
