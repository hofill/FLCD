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
        for (Pair<String, String> a : PIFList) {
            System.out.println(a.getFirst() + "|" + a.getSecond());
        }
    }
}
