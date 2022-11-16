import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PIF {
    private final ArrayList<Pair<String, String>> PIFList;

    public PIF() {
        this.PIFList = new ArrayList<>();
    }

    public void addElement(String keyword, String value) {
        Pair<String, String> newPair = new Pair<>(keyword, value);
        this.PIFList.add(newPair);
    }

    public void printElements() {
        createFile();
        try {
            FileWriter myWriter = new FileWriter("PIF.txt");
            for (Pair<String, String> a : PIFList) {
                myWriter.write(a.getFirst() + "|" + a.getSecond() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile() {
        try {
            File myObj = new File("PIF.txt");
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
