import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        SymbolTable symbolTable = new SymbolTable();
        System.out.println(symbolTable.addToTable("caca"));
        System.out.println(symbolTable.addToTable("acac"));
        System.out.println(symbolTable.addToTable("maca"));
        System.out.println(symbolTable.addToTable("salam tza"));
    }
}
