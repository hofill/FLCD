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

        first();
        follow();
    }

    public void first() {
        for (String nonTerminal : grammar.N) {
            firstSet.put(nonTerminal, new HashSet<>());
            Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
            for (List<String> production : productionForNonTerminal) {
                if (grammar.E.contains(production.get(0)) || production.get(0).equals("epsilon"))
                    firstSet.get(nonTerminal).add(production.get(0));
            }
        }

        var isChanged = true;
        while (isChanged) {
            isChanged = false;
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonTerminal : grammar.N) {
                Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
                Set<String> toAdd = new HashSet<>(firstSet.get(nonTerminal));
                for (List<String> production : productionForNonTerminal) {
                }
            }
        }
    }

    public void follow() {
        for (String nonterminal : grammar.N) {
            setFollow.put(nonterminal, new HashSet<>());
        }

        setFollow.get(grammar.S).add("epsilon");

        boolean ok = false;
        while (!ok) {
            ok = true;

            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonterminal : grammar.getNonTerminals()) {
                Map<Set<String>, Set<List<String>>> allProductions = grammar.getSetOfProductions();

                newColumn.put(nonterminal, new HashSet<>());
                Map<String, Set<List<String>>> nonTerminalProductions = new HashMap<String, Set<List<String>>>();

                for (var key : allProductions.keySet()) {
                    for (var prod : allProductions.get(key)) {
                        if (prod.contains(nonterminal)) {
                            String k = key.iterator().next();
                            if (!nonTerminalProductions.containsKey(k))
                                nonTerminalProductions.put(k, new HashSet<>());
                            nonTerminalProductions.get(k).add(prod);
                        }
                    }

                    Set<String> addingSet = new HashSet<>(setFollow.get(nonterminal));


                    for (var keyN : nonTerminalProductions.keySet()) {
                        for (var prod : nonTerminalProductions.get(keyN)) {
                            for (var i = 0; i < prod.size(); ++i) {
                                if (prod.get(i).equals(nonterminal)) {
                                    if (i  == prod.size() - 1) {
                                        addingSet.addAll(setFollow.get(keyN));
                                    } else {
                                        String followSymbol = prod.get(i + 1);
                                        if (grammar.E.contains(followSymbol))
                                            addingSet.add(followSymbol);
                                        else {
                                            for (var symbol : firstSet.get(followSymbol)) {
                                                if (!symbol.equals("epsilon"))
                                                    addingSet.addAll(firstSet.get(followSymbol));
                                                else
                                                    addingSet.addAll(setFollow.get(keyN));

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!addingSet.equals(setFollow.get(nonterminal))) {
                        ok = false;
                    }
                    newColumn.put(nonterminal, addingSet);
                }

                setFollow = newColumn;
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

