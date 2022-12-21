public class Node {
    private int parent;
    private String value;
    private int index;
    private int sibling;
    private boolean hasRight;

    public Node() {

    }

    public Node(int parent, String value, int index, int sibling, boolean hasRight) {
        this.parent = parent;
        this.value = value;
        this.index = index;
        this.sibling = sibling;
        this.hasRight = hasRight;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSibling() {
        return sibling;
    }

    public void setSibling(int sibling) {
        this.sibling = sibling;
    }

    public boolean getHasRight() {
        return hasRight;
    }

    public void setHasRight(boolean hasRight) {
        this.hasRight = hasRight;
    }
}
