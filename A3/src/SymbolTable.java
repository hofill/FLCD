public class SymbolTable {
    public HashTable hashTable;

    public SymbolTable() {
        this.hashTable = new HashTable();
    }

    public String addToTable(String element) {
        return this.hashTable.addToArray(element);
    }
}
