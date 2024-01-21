import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class LexicalAnalyzer {
    private final InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;
    private String curValue;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                break;
            case '+':
                nextChar();
                curToken = Token.PLUS;
                break;
            case '-':
                nextChar();
                curToken = Token.MINUS;
                break;
            case '*':
                nextChar();
                curToken = Token.MUL;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                if (curChar >= '0' && curChar <= '9') {
                    curValue = "";
                    while (curChar >= '0' && curChar <= '9') {
                        curValue += (String.valueOf((char) curChar));
                        nextChar();
                    }
                    curToken = Token.NUMBER;

                } else {
                    throw new ParseException("Illegal Character '" + (char) curChar + "'. ", curPos);
                }

        }
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException("IOException: " + e.getMessage(), curPos);
        }
    }

    public Token getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }

    public String getCurValue() {
        return curValue;
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

}
