package caesar.military.rome;

import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;
import caesar.ui.Printer;

public class Legion extends Troop {

    private int number;
    
    public Legion() {
        super(TroopType.LEGION);
    }

    public Legion(Troop parentTroop) {
        super(TroopType.LEGION, parentTroop);
    }

    @Override
    public void printSymbol(boolean addSpace, boolean addNewLine) {

        Printer.print(
            "[>" + (this.number != 0 ? this.number : "") + "<]" + 
            (addSpace ? " " : "") + 
            (addNewLine ? "\n" : "")
        );
    }

    @Override
    public void printSymbol(boolean addSpace) {
    
        Printer.print(
            "[>" + (this.number != 0 ? this.number : "") + "<]" + 
            (addSpace ? " " : "")
        );
    }

    @Override
    public void printSymbol() {
        Printer.print("[>" + (this.number != 0 ? this.number : "") + "<]");
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}