import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class UI {

    private Grammar grammar;
    final String filesPath = "/Users/nitahoria/workspace/Facultate/FLCD/A3/files/";

    public UI() {
        this.grammar = new Grammar(filesPath + "g1.txt");
    }

    public void startUI() {

        while (true) {
            this.printMenu();

            Scanner myInput = new Scanner(System.in);
            var command = myInput.nextInt();
            myInput.nextLine();
            switch (command) {
                case 1: {
//                    this.loadNewFA
                    System.out.println("Loaded new Grammar");
                    break;
                }
                case 2: {
                    System.out.println(this.grammar.getNonTerminals());
                    break;
                }
                case 3: {
                    System.out.println(this.grammar.getTerminals());
                    break;
                }
                case 4: {
                    System.out.println(this.grammar.getSetOfProductions());
                    break;
                }
                case 5: {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("How many terminals : ");
                    int n = scanner.nextInt();

                    var array = new HashSet<String>();
                    for(int i = 0; i < n; ++i){
                        var terminal = scanner.next();
                        array.add(terminal);
                    }
                    System.out.println(this.grammar.getProductionsForNonTerminal(array));
                    break;
                }
                case 6: {
                    if(this.grammar.checkCFG())
                        System.out.println("Correct");
                    else
                        System.out.println("Incorrect");
                    break;
                }
                default: {
                    return;
                }
            }

        }
    }

    private void printMenu() {
        System.out.println("""
                1. Load new file for Grammar
                2. Show set of non terminals
                3. Show set of terminals
                4. Show set of productions
                5. Show productions for a given non terminal
                6. CFG check
                """);
    }


}
