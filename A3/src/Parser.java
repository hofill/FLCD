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

        boolean ok = true;
        while (ok) {
            ok = false;
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonterminal : grammar.getNonTerminals()) {
                Map<Set<String>, Set<List<String>>> allProductions = grammar.getSetOfProductions();

                newColumn.put(nonterminal, new HashSet<>());
                Map<String, Set<List<String>>> nonTerminalProductions = new HashMap<String, Set<List<String>>>();

                allProductions.forEach((key, value) -> {
                    for (var eachProduction : value) {
                        if (eachProduction.contains(nonterminal)) {
                            String k = key.iterator().next();
                            if (!nonTerminalProductions.containsKey(k))
                                nonTerminalProductions.put(k, new HashSet<>());
                            nonTerminalProductions.get(k).add(eachProduction);
                        }
                    }
                });

                Set<String> addingSet = new HashSet<>(setFollow.get(nonterminal));

                nonTerminalProductions.forEach((key, value) -> {
                    for (var production : value) {
                        for (var i = 0; i < production.size(); ++i) {
                            if (production.get(i).equals(nonterminal)) {
                                if (i + 1 == production.size()) {
                                    addingSet.addAll(setFollow.get(key));
                                } else {
                                    String followSymbol = production.get(i + 1);
                                    if (grammar.E.contains(followSymbol))
                                        addingSet.add(followSymbol);
                                    else {
                                        for (var symbol : firstSet.get(followSymbol)) {
                                            if (!symbol.equals("epsilon"))
                                                addingSet.addAll(firstSet.get(followSymbol));
                                            else
                                                addingSet.addAll(setFollow.get(key));

                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                if (!addingSet.equals(setFollow.get(nonterminal))) {
                    ok = true;
                }
                newColumn.put(nonterminal, addingSet);
            }

            setFollow = newColumn;

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

