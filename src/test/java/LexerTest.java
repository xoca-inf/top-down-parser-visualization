import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

public class LexerTest {

    @Test
    public void oneNumberTest() throws ParseException {
        String expressionString = "    \t \r \n   42   \t  \r   \n ";
        InputStream expressionStream = new ByteArrayInputStream(expressionString.getBytes(StandardCharsets.UTF_8));
        Parser parser = new Parser();
        List<String> tokens = parser.getTokenList();
        parser.parse(expressionStream);
        Assertions.assertEquals(tokens.get(0), Token.NUMBER.name());
        Assertions.assertEquals(tokens.get(1), Token.END.name());
    }

    @Test
    public void randomExpressionTest() throws ParseException {
        String expressionString = "(-(-((1 * -2 + 4  --  3))))";
        InputStream expressionStream = new ByteArrayInputStream(expressionString.getBytes(StandardCharsets.UTF_8));
        Parser parser = new Parser();
        List<String> tokens = parser.getTokenList();
        parser.parse(expressionStream);
        Assertions.assertEquals(tokens.get(0), Token.LPAREN.name());
        Assertions.assertEquals(tokens.get(1), Token.MINUS.name());
        Assertions.assertEquals(tokens.get(2), Token.LPAREN.name());
        Assertions.assertEquals(tokens.get(3), Token.MINUS.name());
        Assertions.assertEquals(tokens.get(4), Token.LPAREN.name());
        Assertions.assertEquals(tokens.get(5), Token.LPAREN.name());
        Assertions.assertEquals(tokens.get(6), Token.NUMBER.name());
        Assertions.assertEquals(tokens.get(7), Token.MUL.name());
        Assertions.assertEquals(tokens.get(8), Token.MINUS.name());
        Assertions.assertEquals(tokens.get(9), Token.NUMBER.name());
        Assertions.assertEquals(tokens.get(10), Token.PLUS.name());
        Assertions.assertEquals(tokens.get(11), Token.NUMBER.name());
        Assertions.assertEquals(tokens.get(12), Token.MINUS.name());
        Assertions.assertEquals(tokens.get(13), Token.MINUS.name());
        Assertions.assertEquals(tokens.get(14), Token.NUMBER.name());
        Assertions.assertEquals(tokens.get(15), Token.RPAREN.name());
        Assertions.assertEquals(tokens.get(16), Token.RPAREN.name());
        Assertions.assertEquals(tokens.get(17), Token.RPAREN.name());
        Assertions.assertEquals(tokens.get(18), Token.RPAREN.name());
        Assertions.assertEquals(tokens.get(19), Token.END.name());
    }

}
