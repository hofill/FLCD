import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

public class FiniteAutomata {

    String[] states;
    String[] inputs;
    String initialState;
    String[] finalStates;
    HashMap<String, HashMap<String, List<String>>> transitions;

    public FiniteAutomata(String fileName) {
        transitions = new HashMap<>();
        this.initialize(fileName);
    }

    private void initialize(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (lines.size() < 6) {
            return;
        }

        states = lines.get(0).split("=")[1].trim().split(" ");
        inputs = lines.get(1).split("=")[1].trim().split(" ");
        initialState = lines.get(2).split("=")[1].trim();
        finalStates = lines.get(3).split("=")[1].trim().split(" ");

        for (int i = 5; i < lines.size(); i++) {
            String line = lines.get(i);
            var components = Arrays.stream(line.split("->")).map(String::trim).toList();
            var source = components.get(0).substring(1, components.get(0).length() - 1).split(",")[0];
            var input = components.get(0).substring(1, components.get(0).length() - 1).split(",")[1];
            var destination = components.get(1).strip();
            var inputs = transitions.get(source);
            List<String> destinations;
            try {
                destinations = inputs.get(input);
            } catch (Exception ignored) {
                destinations = new ArrayList<>();
                inputs = new HashMap<>();
            }
            try {
                destinations.add(destination);
            } catch (Exception ignored) {
                destinations = new ArrayList<>();
                destinations.add(destination);
            }
            inputs.put(input, destinations);
            transitions.put(source, inputs);
        }
    }

    public boolean isDeterministic() {
        final boolean[] success = {true};
        transitions.forEach((k, inputs) -> {
            if (!success[0]) {
                return;
            }
            inputs.forEach((j, destinations) -> {
                if (!success[0]) return;
                success[0] = destinations.size() == 1;
            });
        });
        return success[0];
    }

    public boolean isAccepted(String sequence) {
        if (!isDeterministic()) return false;
        var characters = sequence.toCharArray();
        var currentState = initialState;
        for (var character : characters) {
            try {
                currentState = transitions.get(currentState).get("" + character).get(0);
            } catch (Exception ignored) {
                return false;
            }
        }
        return Arrays.stream(finalStates).toList().contains(currentState);
    }


}
