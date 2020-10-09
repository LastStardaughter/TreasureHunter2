//As much as part of me wants to experiment with ultra-performant storage of a grid as bits or something,
//it's probably a waste for how little data we're working with here.
import util.*;
import java.util.Iterator;

public class TreasureHunter2 {
    static final int    MIN_X=0,
                        MAX_X=Integer.MAX_VALUE,
                        MIN_Y=0,
                        MAX_Y=Integer.MAX_VALUE;

    public static void main(final String[] args) {
        int[][] steps={{2, 3}, {1, 1}, {0, 2}, {1, 2}, {2, 4}, {3, 1}};
        int[] a=treasureHunt(2, 2, steps);
        System.out.println(a[0]+", "+a[1]);
        int[][] mines={{2,4}, {5,6}};
        a=treasureHunt(2, 2, steps, mines);
        System.out.println(a[0]+", "+a[1]);        
    }

    //Returns final coords, or [MIN_X-1, MIN_Y-1] if stepped out of bounds OR on a trap at any point.
    //Both methods can't use foreach anymore because of boundary checking.
    static int[] treasureHunt(int x, int y, int[][] steps, int[][] traps){
        Minefield mines = new Minefield(traps);
        int[] dest = {MIN_X-1, MIN_Y-1};
        Coord pos = new Coord(x,y), delta;

        for (int i=0; i<steps.length; i++){
            delta = new Coord(decode(steps[i]));
            if(!mines.walkSafe(pos, delta)){return dest;}
            pos.add(delta);
            if (y<0 || x < 0){return dest;}
            if (y>MAX_Y || x > MAX_X){return dest;}
        }
        dest[0]=pos.x;
        dest[1]=pos.y;
        return dest;
    }

    //Returns final coords, or [MIN_X-1, MIN_Y-1] if stepped out of bounds at any point.
    static int[] treasureHunt(int x, int y, int[][] steps){
        int[] delta, loc = {MIN_X-1, MIN_Y-1};
        for (int i=0; i<steps.length; i++){
            delta = decode(steps[i]);
            x+=delta[0];
            y+=delta[1];
            if (y<0 || x < 0){return loc;}
            if (y>MAX_Y || x > MAX_X){return loc;}
        }
        loc[0]=x;
        loc[1]=y;
        return loc;
    }

    static int[] decode(int[] step){
        int[] delta={0,0};
        switch(step[0]){
            case 0: delta[1]=-step[1];
            break;
            case 1: delta[0]=step[1];
            break;
            case 2: delta[1]=step[1];
            break;
            case 3: delta[0]=-step[1];
            break;
            default: System.out.println("Error: Invalid direction!");
        }
        return delta;
    }
    
}
