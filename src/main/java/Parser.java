import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private LexicalAnalyzer lexer;
    private final List<String> tokenList = new ArrayList<>();

    public List<String> getTokenList() {
        return tokenList;
    }

    private Tree F() throws ParseException {
        switch (lexer.getCurToken()) {
            case NUMBER:
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree numberTerminal = new Tree(lexer.getCurValue());
                return new Tree("F", numberTerminal);
            case MINUS:
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree right = F();
                Tree left = new Tree("-");
                return new Tree("F", left, right);
            case LPAREN:
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree mid = E();
                if (lexer.getCurToken() != Token.RPAREN) {
                    throw new ParseException("Invalid token: " + lexer.getCurToken().toString() + ". ')' expected. ", lexer.getCurPos() - 1);
                }
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree lparenTree = new Tree("(");
                Tree rparenTree = new Tree(")");
                return new Tree("F", lparenTree, mid, rparenTree);
            default:
                throw new ParseException("Invalid token: " + lexer.getCurToken().toString() + ". ", lexer.getCurPos() - 1);
        }
    }

    private Tree T() throws ParseException {
        Tree left = F();
        Tree right = TPrime();
        return new Tree("T", left, right);
    }

    private Tree TPrime() throws ParseException {
        switch (lexer.getCurToken()) {
            case MUL:
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree left = F();
                Tree right = TPrime();
                return new Tree("T'", new Tree("*"), left, right);
            case PLUS:
            case MINUS:
            case RPAREN:
            case END:
                return new Tree("T'", new Tree("ε"));
            default:
                throw new ParseException("Invalid token: " + lexer.getCurToken().toString() + ". ", lexer.getCurPos() - 1);
        }
    }

    private Tree E() throws ParseException {
        Tree left = T();
        Tree right = EPrime();
        return new Tree("E", left, right);
    }

    private Tree EPrime() throws ParseException {
        switch (lexer.getCurToken()) {
            case MINUS:
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree left = T();
                Tree right = EPrime();
                return new Tree("E'", new Tree("-"), left, right);
            case PLUS:
                lexer.nextToken();
                tokenList.add(lexer.getCurToken().name());
                Tree leftt = T();
                Tree rightt = EPrime();
                return new Tree("E'", new Tree("+"), leftt, rightt);
            case RPAREN:
            case END:
                return new Tree("E'", new Tree("ε"));
            default:
                throw new ParseException("Invalid token: " + lexer.getCurToken().toString() + ". ", lexer.getCurPos() - 1);
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lexer = new LexicalAnalyzer(is);
        tokenList.clear();
        lexer.nextToken();
        tokenList.add(lexer.getCurToken().name());
        Tree result = E();

        if (lexer.getCurToken() != Token.END) {
            throw new ParseException("Invalid sequence of tokens.\n", -1);
        }

        return result;
    }
}
