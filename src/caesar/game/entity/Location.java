package caesar.game.entity;

import java.util.Arrays;

public class Location {

    private int x;
    private int y;

    Location(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public void change(int deltaX, int deltaY) {

        this.x += deltaX;
        this.y += deltaY;
    }

    public int[] get() {
        return new int[] {this.x, this.y};
    }

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }

    public static void main(String[] args) {
        
        Location location = new Location(0, 0);
        System.out.println(Arrays.toString(location.get()));
    }
}