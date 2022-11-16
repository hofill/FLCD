import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        createFile();
        try {
            FileWriter myWriter = new FileWriter("ST.txt");
            for (ArrayList<String> a : hashTable.getTable()) {
                int fI = hashTable.getTable().indexOf(a);
                int sI = 0;
                for (String b : a) {
                    myWriter.write(b + "|" + fI + "|" + sI + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile() {
        try {
            File myObj = new File("ST.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
