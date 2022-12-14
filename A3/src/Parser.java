import java.util.*;

public class Parser {
    private final Grammar grammar;
    private HashMap<String, Set<String>> firstMap;
    private HashMap<String, Set<String>> followMap;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.firstMap = new HashMap<>();
        this.followMap = new HashMap<>();

        first();
        follow();
    }

    public void first() {
        for (String nonTerminal : grammar.N) {
            firstMap.put(nonTerminal, new HashSet<>());
            Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
            for (List<String> production : productionForNonTerminal) {
                if (grammar.E.contains(production.get(0)) || production.get(0).equals("epsilon"))
                    firstMap.get(nonTerminal).add(production.get(0));
            }
        }

        var isChanged = true;
        while (isChanged) {
            isChanged = false;
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonTerminal : grammar.N) {
                Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
                Set<String> toAdd = new HashSet<>(firstMap.get(nonTerminal));
                for (List<String> production : productionForNonTerminal) {
                    List<String> rhsNonTerminals = new ArrayList<>();
                    String rhsTerminal = null;
                    for (String symbol : production)
                        if (this.grammar.N.contains(symbol))
                            rhsNonTerminals.add(symbol);
                        else {
                            rhsTerminal = symbol;
                            break;
                        }
                    toAdd.addAll(concatenate(rhsNonTerminals, rhsTerminal));
                }
                if (!toAdd.equals(firstMap.get(nonTerminal))) {
                    isChanged = true;
                }
                newColumn.put(nonTerminal, toAdd);
            }
            firstMap = newColumn;
        }
    }

    private Set<String> concatenate(List<String> nonTerminals, String terminal) {
        // check for zero size
        if (nonTerminals.size() == 0)
            return new HashSet<>();

        // check for size 1
        if (nonTerminals.size() == 1) {
            return firstMap.get(nonTerminals.iterator().next());
        }

        Set<String> concatenation = new HashSet<>();
        var step = 0;
        var allEpsilon = true;

        for (String nonTerminal : nonTerminals) {
            if (!firstMap.get(nonTerminal).contains("epsilon")) {
                allEpsilon = false;
            }
        }

        if (allEpsilon) {
            if (terminal != null) {
                concatenation.add(terminal);
            } else {
                concatenation.add("epsilon");
            }
        }

        while (step < nonTerminals.size()) {
            boolean thereIsOneEpsilon = false;
            for (String s : firstMap.get(nonTerminals.get(step)))
                if (s.equals("epsilon"))
                    thereIsOneEpsilon = true;
                else
                    concatenation.add(s);

            if (thereIsOneEpsilon)
                step++;
            else
                break;
        }
        return concatenation;
    }

    public void follow() {
        for (String nonterminal : grammar.N) {
            followMap.put(nonterminal, new HashSet<>());
        }

        followMap.get(grammar.S).add("epsilon");

        boolean ok = false;
        while (!ok) {
            ok = true;

            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonterminal : grammar.getNonTerminals()) {
                Map<Set<String>, Set<List<String>>> allProductions = grammar.getSetOfProductions();

                newColumn.put(nonterminal, new HashSet<>());
                Map<String, Set<List<String>>> nonTerminalProductions = new HashMap<>();

                for (var key : allProductions.keySet()) {
                    for (var prod : allProductions.get(key)) {
                        if (prod.contains(nonterminal)) {
                            String k = key.iterator().next();
                            if (!nonTerminalProductions.containsKey(k))
                                nonTerminalProductions.put(k, new HashSet<>());
                            nonTerminalProductions.get(k).add(prod);
                        }
                    }

                    Set<String> addingSet = new HashSet<>(followMap.get(nonterminal));


                    for (var keyN : nonTerminalProductions.keySet()) {
                        for (var prod : nonTerminalProductions.get(keyN)) {
                            for (var i = 0; i < prod.size(); ++i) {
                                if (prod.get(i).equals(nonterminal)) {
                                    if (i == prod.size() - 1) {
                                        addingSet.addAll(followMap.get(keyN));
                                    } else {
                                        String followSymbol = prod.get(i + 1);
                                        if (grammar.E.contains(followSymbol))
                                            addingSet.add(followSymbol);
                                        else {
                                            for (var symbol : firstMap.get(followSymbol)) {
                                                if (!symbol.equals("epsilon"))
                                                    addingSet.addAll(firstMap.get(followSymbol));
                                                else
                                                    addingSet.addAll(followMap.get(keyN));

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!addingSet.equals(followMap.get(nonterminal))) {
                        ok = false;
                    }
                    newColumn.put(nonterminal, addingSet);
                }

                followMap = newColumn;
            }
        }
    }


    public String printFollow() {
        StringBuilder builder = new StringBuilder();
        followMap.forEach((k, v) -> {
            builder.append(k).append("-> ").append(v).append("\n");
        });
        return builder.toString();
    }

    public void printFirst() {
        StringBuilder builder = new StringBuilder();
        firstMap.forEach((k, v) -> {
            builder.append(k).append("-> ").append(v).append("\n");
        });
        System.out.println(builder);
    }

}

