import java.util.*;

public class Parser {
    private final Grammar grammar;
    private HashMap<String, Set<String>> firstSet;
    private HashMap<String, Set<String>> setFollow;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.firstSet = new HashMap<>();
        this.setFollow = new HashMap<>();
        this.parseTable = new HashMap<>();

        generateFirst();
        generateFollow();
    }

    public void generateFirst() {
        // Initialize first set
        for (String nonTerminal : grammar.N) {
            // each non-terminal should have a set
            firstSet.put(nonTerminal, new HashSet<>());
            // get production for each non-terminal
            Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
            for (List<String> production : productionForNonTerminal) {
                // add if epsilon or is terminal
                if (grammar.E.contains(production.get(0)) || production.get(0).equals("epsilon"))
                    firstSet.get(nonTerminal).add(production.get(0));
            }
        }

        var isChanged = true;
        while (isChanged) {
            isChanged = false;
            // create new column
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonTerminal : grammar.N) {
                Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
                Set<String> toAdd = new HashSet<>(firstSet.get(nonTerminal));
                for (List<String> production : productionForNonTerminal) {
                }
            }
        }
    }

    public void generateFollow() {
        for (String nonterminal : grammar.getNonTerminals()) {
            setFollow.put(nonterminal, new HashSet<>());
        }
        setFollow.get(grammar.S).add("epsilon");

        var change = true;
        while (change) {
            change = false;
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonterminal : grammar.getNonTerminals()) {
                var allProductions = grammar.getSetOfProductions();

                newColumn.put(nonterminal, new HashSet<>());
                var nonTerminalProductions = new HashMap<String, Set<List<String>>>();

                allProductions.forEach((k, v) -> {
                    for (var eachProduction : v) {
                        if (eachProduction.contains(nonterminal)) {
                            var key = k.iterator().next();
                            if (!nonTerminalProductions.containsKey(key))
                                nonTerminalProductions.put(key, new HashSet<>());
                            nonTerminalProductions.get(key).add(eachProduction);
                        }
                    }
                });

                var toAdd = new HashSet<>(setFollow.get(nonterminal));
            }
        }

    }

    public String printFollow() {
        StringBuilder builder = new StringBuilder();
        setFollow.forEach((k, v) -> {
            builder.append(k).append(": ").append(v).append("\n");
        });
        return builder.toString();
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public List<List<String>> getProductionsRhs() {
        return productionsRhs;
    }
}

