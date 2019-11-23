package caesar.game.map;

import caesar.game.entity.Location;
import caesar.utility.RandomEnum;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Map {

    private final Relief[][] relief;
    private final int size;

    public Map(int size) {

        this.relief = new Relief[size][size];
        this.size = size;

        for (int x = 0; x < size; x++) {

            for (int y = 0; y < size; y++)
                this.relief[x][y] = RandomEnum.get(Relief.class, 1);
        }
    }

    @Contract(pure = true)
    public Relief getRelief(int x, int y) {

        if (x >= this.size || x < 0 || y >= this.size || y < 0)
            return Relief.UNKNOWN;

        return this.relief[x][y];
    }

    public Relief[] getReliefAround(@NotNull Location location) {
        
        int x = location.getX();
        int y = location.getY();

        Relief north        = this.getRelief(x, y + 1);
        Relief northwest    = this.getRelief(x - 1, y + 1);
        Relief northeast    = this.getRelief(x + 1, y + 1);
        Relief west         = this.getRelief(x - 1, y);
        Relief east         = this.getRelief(x + 1, y);
        Relief south        = this.getRelief(x, y - 1);
        Relief southwest    = this.getRelief(x - 1, y - 1);
        Relief southeast    = this.getRelief(x + 1, y - 1);

        return new Relief[] {
            north,
            northwest,
            northeast,
            west,
            east,
            south,
            southwest,
            southeast
        };
    }
    
    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        
        StringBuilder map = new StringBuilder();

        for (int x = 0; x < this.size; x++) {

            for (int y = 0; y < this.size; y++)
                map.append(this.relief[x][y]);

            map.append("\n");
        }

        return map.toString();
    }

    public static void main(String[] args) {

        Map reliefMap = new Map(20);
        System.out.println(reliefMap);
    }
}