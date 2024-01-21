import java.util.Arrays;
import java.util.List;

public class Tree {

    private final String node;
    private List<Tree> children;

    public String getNode() {
        return node;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}
