import java.util.*;

public class Parser {
    private final Grammar grammar;
    private final HashMap<Pair<String, String>, Pair<String, Integer>> parseTable;
    private HashMap<String, Set<String>> firstMap;
    private HashMap<String, Set<String>> followMap;
    private List<List<String>> productions;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.firstMap = new HashMap<>();
        this.followMap = new HashMap<>();
        this.parseTable = new HashMap<>();
        this.productions = new ArrayList<>();

        first();
        follow();

        initializeParseTable();
        this.generateParseTable();
    }

    public void first() {
        for (String nonTerminal : grammar.N) {
            firstMap.put(nonTerminal, new HashSet<>());
            Set<List<String>> productionForNonTerminal = grammar.getProduction(nonTerminal);
            for (List<String> production : productionForNonTerminal) {
                if (grammar.isTerminal(production.get(0)) || production.get(0).equals("epsilon"))
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
                        if (this.grammar.isNonTerminal(symbol))
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

        var ok = false;
        while (!ok) {
            ok = true;
            HashMap<String, Set<String>> column = new HashMap<>();

            for (String nonterminal : grammar.N) {
                column.put(nonterminal, new HashSet<>());
                Map<String, Set<List<String>>> productionsWithNonterminalInRhs = new HashMap<>();
                Map<Set<String>, Set<List<String>>> allProductions = grammar.P;
                for (var key : allProductions.keySet()) {
                    for (var eachProduction : allProductions.get(key)) {
                        if (eachProduction.contains(nonterminal)) {
                            String entry = key.iterator().next();
                            if (!productionsWithNonterminalInRhs.containsKey(entry))
                                productionsWithNonterminalInRhs.put(entry, new HashSet<>());
                            productionsWithNonterminalInRhs.get(entry).add(eachProduction);
                        }
                    }
                }

                Set<String> toAdd = new HashSet<>(followMap.get(nonterminal));

                for (var key : productionsWithNonterminalInRhs.keySet()) {
                    for (var production : productionsWithNonterminalInRhs.get(key)) {
                        for (var indexOfNonterminal = 0; indexOfNonterminal < production.size(); ++indexOfNonterminal)
                            if (production.get(indexOfNonterminal).equals(nonterminal)) {
                                if (indexOfNonterminal + 1 == production.size()) {
                                    toAdd.addAll(followMap.get(key));
                                } else {
                                    String followSymbol = production.get(indexOfNonterminal + 1);
                                    if (grammar.isTerminal(followSymbol))
                                        toAdd.add(followSymbol);
                                    else {
                                        for (var symbol : firstMap.get(followSymbol)) {
                                            if (symbol.equals("epsilon"))
                                                toAdd.addAll(followMap.get(key));
                                            else
                                                toAdd.addAll(firstMap.get(followSymbol));
                                        }
                                    }
                                }
                            }
                    }
                }

                if (!toAdd.equals(followMap.get(nonterminal))) {
                    ok = false;
                }
                column.put(nonterminal, toAdd);
            }

            followMap = column;
        }
    }

    public void generateParseTable() {
        this.productions = new ArrayList<>();
        initializeProductionsOnRightSide();

        for (var key : grammar.P.keySet()) {
            String entry = key.iterator().next();
            for (var p : grammar.P.get(key)) {
                String frst = p.get(0);
                if (grammar.isTerminal(frst))
                    if (isErr(entry, frst))
                        addToParseTable(entry, frst, spacedString(p), productions.indexOf(p) + 1);
                    else
                        throw new RuntimeException("parsing was done wrong");
                else if (grammar.isNonTerminal(frst)) {
                    if (p.size() == 1)
                        for (var symbol : firstMap.get(frst))
                            if (isErr(entry, symbol))
                                addToParseTable(entry, symbol, spacedString(p), productions.indexOf(p) + 1);
                            else
                                throw new RuntimeException("parsing was done wrong");
                    else {
                        String next = p.get(1);
                        Set<String> firstSet = firstMap.get(frst);
                        int index = 1;
                        while (grammar.isNonTerminal(next) &&
                                index < p.size()) {
                            Set<String> firstNext = firstMap.get(next);

                            if (firstSet.contains("epsilon")) {
                                firstSet.remove("epsilon");
                                firstSet.addAll(firstNext);
                            }
                            index++;
                            if (index < p.size())
                                next = p.get(index);
                        }

                        for (var symbol : firstSet) {
                            if (symbol.equals("epsilon"))
                                symbol = "$";
                            if (isErr(entry, symbol))
                                addToParseTable(entry, symbol, spacedString(p), productions.indexOf(p) + 1);
                            else
                                throw new RuntimeException("parsing was done wrong");
                        }
                    }
                } else {
                    Set<String> follow = followMap.get(entry);
                    for (var symbol : follow) {
                        if (symbol.equals("epsilon")) {
                            if (isErr(entry, "$")) {
                                var prod = new ArrayList<>(List.of("epsilon", key));
                                addToParseTable(entry, "$", "epsilon", productions.indexOf(prod) + 1);
                            } else
                                throw new RuntimeException("parsing was done wrong");
                        } else if (isErr(entry, symbol)) {
                            var prod = new ArrayList<>(List.of("epsilon", entry));
                            addToParseTable(entry, symbol, "epsilon", productions.indexOf(prod) + 1);
                        } else
                            throw new RuntimeException("parsing was done wrong");
                    }
                }
            }

        }
    }

    private void initializeProductionsOnRightSide() {
        for (var key : grammar.P.keySet()) {
            String nont = key.iterator().next();
            for (var p : grammar.P.get(key))
                if (p.get(0).equals("epsilon"))
                    productions.add(new ArrayList<>(List.of("epsilon", nont)));
                else
                    productions.add(p);
        }
    }

    public void printParseTable() {
        StringBuilder builder = new StringBuilder();
        parseTable.forEach((k, v) -> {
            builder.append(k).append(" -> ").append(v).append("\n");
        });
        System.out.println(builder);
    }

    public void printFollow() {
        StringBuilder builder = new StringBuilder();
        followMap.forEach((k, v) -> {
            builder.append(k).append("-> ").append(v).append("\n");
        });
        System.out.println(builder);
    }

    public void printFirst() {
        StringBuilder builder = new StringBuilder();
        firstMap.forEach((k, v) -> {
            builder.append(k).append("-> ").append(v).append("\n");
        });
        System.out.println(builder);
    }

    private void initializeParseTable() {
        List<String> rows = new ArrayList<>(grammar.N);
        rows.addAll(grammar.E);
        rows.add("$");

        List<String> columns = new ArrayList<>(grammar.E);
        columns.add("$");

        for (var row : rows)
            for (var col : columns) {
                addToParseTable(row, col, "err", -1);
                addToParseTable(col, col, "pop", -1);
            }

        addToParseTable("$", "$", "acc", -1);
    }

    private boolean isErr(String key, String value) {
        return parseTable.get(new Pair<>(key, value)).getFirst().equals("err");
    }

    private void addToParseTable(String key, String value, String symbol, Integer svalue) {
        parseTable.put(new Pair<>(key, value), new Pair<>(symbol, svalue));
    }

    private String spacedString(List<String> p) {
        return String.join(" ", p);
    }

}

