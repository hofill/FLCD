import java.util.ArrayList;

public class HashTable {

    private ArrayList<ArrayList<String>> table;

    public HashTable() {
        this.table = new ArrayList<>(500);
        for (int i = 0; i < 500; i++) {
            this.table.add(i, new ArrayList<>());
        }
    }

    public ArrayList<ArrayList<String>> getTable() {
        return table;
    }

    private int hash(String element) {
        int hashSum = 0;
        for (int i : element.toCharArray()) {
            hashSum += i;
        }
        return hashSum % 500;
    }

    public String addToArray(String element) {
        int hashPos = hash(element);

        if (table.get(hashPos).size() != 0) {
            for (int i = 0; i < table.get(hashPos).size(); i++) {
                if (table.get(hashPos).get(i).equals(element)) {
                    return hashPos + "|" + i;
                }
            }
        }
        table.get(hashPos).add(element);
        return hashPos + "|" + (table.get(hashPos).size() - 1);
    }
}
