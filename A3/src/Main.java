public class Main {

    public static void main(String[] args) {
        boolean presentFA = true;
        final String filesPath = "/Users/nitahoria/workspace/Facultate/FLCD/A3/files/";

        Grammar grammar = new Grammar(filesPath + "g1.txt");
        Parser parser = new Parser(grammar);
        System.out.println("---------------");
        parser.printFirst();
        System.out.println("---------------");
        parser.printFollow();
        System.out.println("---------------");
        parser.printParseTable();
        System.out.println("---------------");

        if (presentFA) {
            UI ui = new UI();
            ui.startUI();
        }

        if (!presentFA) {
            FileScanner scanner = new FileScanner();
            try {
                scanner.scanFile(filesPath + "p1.caca");
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
}
