package caesar.game.player;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public Location() {

        this.x = 0;
        this.y = 0;
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
        
        Location location = new Location();
        System.out.println(location.get());
    }
}