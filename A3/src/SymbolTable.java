import java.util.ArrayList;

public class SymbolTable {
    public HashTable hashTable;

    public SymbolTable() {
        this.hashTable = new HashTable();
    }

    public String addToTable(String element) {
        return this.hashTable.addToArray(element);
    }

    public void printElements() {
        for (ArrayList<String> a : hashTable.getTable()) {
            int fI = hashTable.getTable().indexOf(a);
            int sI = 0;
            for (String b: a) {
                System.out.println(b + "|" + fI + "|" + sI);
            }
        }
    }
}
