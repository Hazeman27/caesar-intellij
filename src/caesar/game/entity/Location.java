package caesar.game.entity;

import caesar.game.map.Relief;
import org.jetbrains.annotations.Contract;

public class Location {

    private int x;
    private int y;
    private Relief relief;

    @Contract(pure = true)
    Location(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    void change(int deltaX, int deltaY) {

        this.x += deltaX;
        this.y += deltaY;
    }
    
    Relief getRelief() {
        return this.relief;
    }
    
    public void setRelief(Relief relief) {
        this.relief = relief;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }

    public static void main(String[] args) {
        
        Location location = new Location(0, 0);
        System.out.println(location);
    }
}