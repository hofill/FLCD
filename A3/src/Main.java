import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        FileScanner scanner = new FileScanner();
        try {
            scanner.scanFile("/Users/nitahoria/workspace/Facultate/FLCD/A3/files/p1err.caca");
            if (scanner.getLastError() != ErrorList.NONE) {
                System.out.println(scanner.getLastError());
                System.out.println("Line: " + scanner.getErrorLocation().getFirst());
                System.out.println("Token: " + scanner.getErrorLocation().getSecond());
            } else {
                System.out.println("Lexically correct");
                scanner.printST();
                scanner.printPIF();
            }
        } catch (Exception ignored) {
        }
    }
}
