import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Visualisator {

    public static String generate(int length) {
        Random random = new Random();
        if (length == 0) {
            return Integer.toString(random.nextInt(100));
        }
        switch (random.nextInt(5)) {
            case 0:
                return Integer.toString(random.nextInt(100));
            case 1:
                return "(" + generate(length - 1) + ")";
            case 2:
                String binaryOperation;
                switch (random.nextInt(3)) {
                    case 0:
                        binaryOperation = "+";
                        break;
                    case 1:
                        binaryOperation = "-";
                        break;
                    default:
                        binaryOperation = "*";
                        break;
                }
                return generate(length - 1) + binaryOperation + generate(length - 1);
            case 3:
                return "-" + random.nextInt(100);
            case 4:
                return "\t" + generate(length);
            default:
                return "-(" + generate(length - 1) + ")";
        }
    }


    private static int counter = 0;

    public static void writeGraphvizData(Tree tree, String filePath) throws FileNotFoundException {
        counter = 0;
        PrintWriter writer = new PrintWriter(filePath);
        writer.println("digraph parseTree {");
        dfs(writer, tree);
        writer.println("}");
        writer.close();

    }

    private static void dfs(PrintWriter writer, Tree tree) {
        if (tree.getChildren() != null && (tree.getChildren().size() != 0)) {
            writer.println("\tv" + counter + " [label=\"" + tree.getNode() + "\"];");
            List<Integer> childIndexes = new ArrayList<>();
            int curCounter;
            curCounter = counter;
            for (Tree child : tree.getChildren()) {
                counter++;
                childIndexes.add(counter);
                dfs(writer, child);
            }
            for (Integer index : childIndexes) {
                writer.println("\tv" + curCounter + " -> v" + index);
            }
        } else {
            if (!tree.getNode().equals("ε")) {
                writer.println("\tv" + counter + " [label=\"" + tree.getNode() + "\" ; style=filled; fillcolor=pink];");
            } else {
                writer.println("\tv" + counter + " [label=\"" + tree.getNode() + "\"];");
            }
        }

    }

}
