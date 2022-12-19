import java.io.File;
import java.io.IOException;
import java.util.*;

public class Grammar {

    public Set<String> N;
    public Set<String> E;
    public String S;
    public HashMap<Set<String>, Set<List<String>>> P = new HashMap<>();


    public Grammar(String filePath) {
        this.N = new HashSet<>();
        this.E = new HashSet<>();
        this.S = "";
        this.initialize(filePath);
    }

    public void initialize(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (IOException ignored) {
            return;
        }
        if (lines.size() < 6) {
            return;
        }

        var nSet = Arrays.stream(lines.get(0).trim().split("=")[1].trim().split(" ")).map(String::trim).filter(a -> !a.equals("{") && !a.equals("}")).toList();
        N = new HashSet<>(nSet);
        var eSet = Arrays.stream(lines.get(1).trim().split("=")[1].trim().split(" ")).map(String::trim).filter(a -> !a.equals("{") && !a.equals("}")).toList();
        E = new HashSet<>(eSet);
        S = lines.get(2).split("=")[1].trim();
        System.out.println(N);
        System.out.println(E);
        System.out.println(S);
        lines = lines.subList(4, lines.size() - 1);

        P = new HashMap<>();

        for (var line : lines) {
            var pSet = Arrays.stream(line.split("->")[0].trim().split(" ")).toList();
            P.put(new HashSet<>(pSet), new HashSet<>());

            var tokens = line.split("->")[1].trim().split("\\|");
            for (var token : tokens) {
                ArrayList<String> productionElements = new ArrayList<>();
                var elements = token.strip().split(" ");
                for (var r : elements) {
                    productionElements.add(r.strip());
                }
                P.get(new HashSet<>(pSet)).add(productionElements);
            }
        }

        System.out.println(P);
    }

    public boolean isTerminal(String symbol){
        return E.contains(symbol);
    }

    public boolean isNonTerminal(String symbol){
        return N.contains(symbol);
    }

    public Set<String> getNonTerminals() {
        return N;
    }

    public Set<String> getTerminals() {
        return E;
    }

    public Set<List<String>> getProductionsForNonTerminal(Set<String> set) {
        return P.get(set);
    }

    public HashMap<Set<String>, Set<List<String>>> getSetOfProductions() {
        return P;
    }

    public boolean checkCFG() {
        // Check if we have a starting symbol in the LHS
        var hasStartingSymbol = false;
        for (Set<String> left : P.keySet())
            if (left.contains(S)) {

                hasStartingSymbol = true;
                break;
            }
        if (!hasStartingSymbol)
            return false;

        // Check LHS not empty or N contains left then search RHS
        for (Set<String> left : P.keySet()) {
            if (left.size() > 1)
                return false;
            else if (!N.contains(left.iterator().next()))
                return false;

            Set<List<String>> right = P.get(left);

            for (List<String> rh : right) {
                for (String r : rh) {
                    // check for valid symbol
                    if (!(N.contains(r) || E.contains(r) || r.equals("epsilon")))
                        return false;
                }
            }
        }
        return true;
    }

    public Set<List<String>> getProduction(String nonterminal) {
        return P.get(new HashSet<>(Collections.singleton(nonterminal)));
    }

}
