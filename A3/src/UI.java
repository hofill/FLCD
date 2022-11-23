import java.util.Arrays;
import java.util.Scanner;

public class UI {

    private FiniteAutomata FA;
    final String filesPath = "/Users/nitahoria/workspace/Facultate/FLCD/A3/files/";

    public UI() {
        this.FA = new FiniteAutomata(filesPath + "fa-int.in");
    }

    public void startUI() {

        while (true) {
            this.printMenu();

            Scanner myInput = new Scanner(System.in);
            var command = myInput.nextInt();
            myInput.nextLine();
            switch (command) {
                case 1: {
                    this.loadNewFA();
                    System.out.println("Loaded new FA");
                    break;
                }
                case 2: {
                    System.out.println(Arrays.toString(this.FA.states));
                    break;
                }
                case 3: {
                    System.out.println(Arrays.toString(this.FA.inputs));
                    break;
                }
                case 4: {
                    System.out.println(this.FA.transitions);
                    break;
                }
                case 5: {
                    System.out.println(this.FA.initialState);
                    break;
                }
                case 6: {
                    System.out.println(Arrays.toString(this.FA.finalStates));
                    break;
                }
                case 7: {
                    System.out.print("> ");
                    var toTest = myInput.nextLine().trim();
                    System.out.println(this.FA.isAccepted(toTest));
                    break;
                }
                default: {
                    return;
                }
            }

        }
    }

    private void loadNewFA() {
        Scanner myInput = new Scanner(System.in);
        var fileName = myInput.nextLine().trim();
        FA = new FiniteAutomata(filesPath + fileName);
    }

    private void printMenu() {
        System.out.println("""
                1. Load new file for FA
                2. Show set of states
                3. Show the alphabet
                4. Show the transitions
                5. Show initial state
                6. Show final states
                7. Check if input is accepted
                """);
    }


}
