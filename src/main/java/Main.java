import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        String expressionString;
        Parser parser = new Parser();
        int counter = -1;
        Random random = new Random();
        String graphPath = "D:\\Лаба по МТ\\Top-Down-Parser\\graphs";
        try {
            System.out.println("Random expressions test:");
            for (int i = 0; i < 3; i++) {
                expressionString = Visualisator.generate(random.nextInt(42));
                System.out.println(i + ": " + expressionString);
                try {
                    Tree parseTree = parser.parse(new ByteArrayInputStream(expressionString.getBytes(StandardCharsets.UTF_8)));
                    Visualisator.writeGraphvizData(parseTree, "graphs/randomGraph" + i + ".dot");
                    Process p = Runtime.getRuntime().exec(String.format("dot %srandomGraph%s.dot -Tpng -o %srandomGraph%s.png", graphPath, i, graphPath, i));
                    p.waitFor(6L, TimeUnit.SECONDS);
                } catch (ParseException e) {
                    System.out.print("Exception: " + e.getMessage());
                    if (e.getErrorOffset() != -1) {
                        System.out.println("At position " + (e.getErrorOffset() - 1) + ".");
                    }
                }
            }
            BufferedReader testsReader = new BufferedReader(new FileReader("src/test/expressions.txt"));
            System.out.println("Expression examples test:");
            while ((expressionString = testsReader.readLine()) != null) {
                counter++;
                System.out.println(counter + ": " + expressionString);
                try {
                    Tree parseTree = parser.parse(new ByteArrayInputStream(expressionString.getBytes(StandardCharsets.UTF_8)));
                    Visualisator.writeGraphvizData(parseTree, "graphs/graph" + counter + ".dot");
                    Process p = Runtime.getRuntime().exec(String.format("dot %sgraph%s.dot -Tpng -o %sgraph%s.png", graphPath, counter, graphPath, counter));
                    p.waitFor(6L, TimeUnit.SECONDS);
                } catch (ParseException e) {
                    System.out.print("Exception: " + e.getMessage());
                    if (e.getErrorOffset() != -1) {
                        System.out.println("At position " + (e.getErrorOffset() - 1) + ".");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file path and/or name to expression examples or .dot file.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
