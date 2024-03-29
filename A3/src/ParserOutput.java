import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ParserOutput {
    private Parser parser;
    private List<Integer> productions;
    private Integer nodeNumber = 1;
    private Boolean hasErrors;
    private List<Node> nodeList = new ArrayList<>();
    private Node root;
    private String outputFile;

    public ParserOutput(Parser parser, List<String> sequence, String outputFile) {
        this.parser = parser;
        this.productions = parser.parseSequence(sequence);
        this.outputFile = outputFile;
        this.hasErrors = this.productions.contains(-1);
        generateTree();
    }

    public void generateTree() {
        if (hasErrors)
            return;

        Stack<Node> nodeStack = new Stack<>();
        var productionsIndex = 0;

        //root node
        Node node = new Node(0, parser.getGrammar().S, nodeNumber, 0, false);
        nodeNumber++;
        nodeStack.push(node);
        nodeList.add(node);
        this.root = node;

        while (productionsIndex < productions.size() && !nodeStack.isEmpty()) {
            Node currentNode = nodeStack.peek(); //father
            if (parser.getGrammar().E.contains(currentNode.getValue()) || currentNode.getValue().contains("epsilon")) {
                while (nodeStack.size() > 0 && !nodeStack.peek().getHasRight()) {
                    nodeStack.pop();
                }
                if (nodeStack.size() > 0)
                    nodeStack.pop();
                else
                    break;
                continue;
            }

            //children
            var production = parser.getProductionByOrderNumber(productions.get(productionsIndex));
            nodeNumber += production.size() - 1;
            for (var i = production.size() - 1; i >= 0; --i) {
                Node child = new Node(currentNode.getIndex(), production.get(i), nodeNumber, 0, false);
                child.setSibling(i == production.size() - 1 ? 0 : nodeNumber - i - 1);
                child.setHasRight(i != production.size() - 1);

                nodeNumber--;
                nodeStack.push(child);
                nodeList.add(child);
            }
            nodeNumber += production.size() + 1;
            productionsIndex++;
        }
    }

    public void printTree(String outputFile) {
        try {
            nodeList.sort(Comparator.comparing(Node::getIndex));
            File file = new File(outputFile);
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("---------------------------\n");
            bufferedWriter.write("nr | val | parent | sibling\n");
            bufferedWriter.write("---------------------------\n");
            for (Node node : nodeList) {
                bufferedWriter.write(String.format("%s | %s | %s | %s\n", node.getIndex(), node.getValue(), node.getParent(), node.getSibling()));
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
