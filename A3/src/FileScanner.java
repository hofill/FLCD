import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileScanner {

    private Scanner fileReader;
    private final PIF pif;
    private final SymbolTable symbolTable;
    private final ArrayList<String> tokens;
    private ErrorList lastError;
    private int errorLineNumber;
    private String errorToken;

    public FileScanner() {
        this.lastError = ErrorList.NONE;
        this.pif = new PIF();
        this.symbolTable = new SymbolTable();
        this.tokens = new ArrayList<>();
        this.errorLineNumber = 0;
        this.errorToken = "";
        try {
            File programFile = new File(System.getProperty("user.dir") + "/files/tokens.in");
            Scanner sc = new Scanner(programFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                this.tokens.add(line);
            }
        } catch (Exception ignored) {
        }

    }

    private void openFile(String fileName) throws FileNotFoundException {
        File programFile = new File(fileName);
        this.fileReader = new Scanner(programFile);
    }

    private String getTokenBetweenIndexes(int firstIndex, int secondIndex, String line) {
        StringBuilder token = new StringBuilder();
        for (int i = firstIndex; i < secondIndex; i++) {
            token.append(line.charAt(i));
        }
        return token.toString();
    }

    private List<String> tokenizeLine(String line) {
        ArrayList<String> tempTokens = new ArrayList<>();
        int lastIndex = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                String newToken = getTokenBetweenIndexes(lastIndex, i, line).strip();
                if (newToken.length() != 0) {
                    tempTokens.add(newToken);
                }
                lastIndex = i+1;
            }
            else if (tokens.contains(Character.toString(line.charAt(i)))) {
                if (lastIndex != i) {
                    String newToken = getTokenBetweenIndexes(lastIndex, i, line).strip();
                    if (newToken.length() != 0) {
                        tempTokens.add(newToken);
                    }
                }
                tempTokens.add(Character.toString(line.charAt(i)));
                lastIndex = i+1;
            }
        }
        return tempTokens;
    }

    private boolean checkConstant(String token) {
        Pattern numberPattern = Pattern.compile("^\\d*$");
        Matcher numberMatcher = numberPattern.matcher(token);
        Pattern stringPattern = Pattern.compile("^\".*\"$");
        Matcher stringMatcher = stringPattern.matcher(token);
        return numberMatcher.find() || stringMatcher.find();
    }

    private boolean checkIdentifier(String token) {
        Pattern identifierPattern = Pattern.compile("^[a-zA-Z]+[0-9]*$");
        Matcher identifierMatcher = identifierPattern.matcher(token);
        return identifierMatcher.find();
    }

    private void getDataFromLine(String line) {
        if (line.equals("#")) {
            pif.addElement("#", "-1");
            return;
        }
        for (var token : tokenizeLine(line)) {
            if (token.equals("\t") || token.getBytes().length == 0) {
                continue;
            }
            if (tokens.contains(token)) {
                pif.addElement(token, "-1");
            } else if (checkConstant(token)) {
                String position = symbolTable.addToTable(token);
                pif.addElement("const", position);
            } else if (checkIdentifier(token)) {
                String position = symbolTable.addToTable(token);
                pif.addElement(token, position);
            } else {
                lastError = ErrorList.LEXICAL_ERROR;
                errorToken = token;
                return;
            }
        }
    }

    public void scanFile(String fileName) throws FileNotFoundException {
        this.openFile(fileName);
        int lineNumber = 0;
        while (this.fileReader.hasNextLine()) {
            String data = this.fileReader.nextLine();
            getDataFromLine(data);
            if (lastError != ErrorList.NONE) {
                errorLineNumber = lineNumber;
                break;
            }
            lineNumber += 1;
        }
    }

    public Pair<Integer, String> getErrorLocation() {
        return new Pair<>(errorLineNumber, errorToken);
    }

    public ErrorList getLastError() {
        return lastError;
    }

    public void printPIF() {
        pif.printElements();
    }

    public void printST() {
        symbolTable.printElements();
    }

}
