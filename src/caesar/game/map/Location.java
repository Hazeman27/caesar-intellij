package caesar.game.map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Location {

    private int x;
    private int y;
    private Relief relief;

    @Contract(pure = true)
    public Location(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public void change(int deltaX, int deltaY) {

        this.x += deltaX;
        this.y += deltaY;
    }
    
    public Vector calcVector(@NotNull Location location) {
        
        return new Vector(
            this.x,
            this.y,
            location.x,
            location.y
        );
    }
    
    public Relief getRelief() {
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