import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public interface Tokenizer {
    /** Returns true if there is
     *  more token */
    boolean hasNextToken();

    /** Returns the next token
     *  in the input stream. */
    String peek();

    /** Consumes the next token
     *  from the input stream
     *  and returns it.
     *  effects: removes the next token
     *           from the input stream */
    String consume();
}

class ExprTokenizer implements Tokenizer {
    private String src, next;
    private int pos;
    public ExprTokenizer(String src) {
        this.src = src;  pos = 0;
        computeNext();
    }
    public boolean hasNextToken()
    { return next != null; }
    public void checkNextToken() {
        if (!hasNextToken()) throw new
                NoSuchElementException("no more tokens");
    }
    public String peek() {
        checkNextToken();
        return next;
    }
    public String consume() {
        checkNextToken();
        String result = next;
        computeNext();
        return result;
    }

    /** Returns true if
     *  the next token (if any) is s. */
    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }

    /** Consumes the next token if it is s.
     *  Throws SyntaxError otherwise.
     *  effects: removes the next token
     *           from input stream if it is s
     */
    public void consume(String s)
            throws Parser.SyntaxError {
        if (peek(s))
            consume();
        else
            throw new Parser.SyntaxError(s + " expected");
    }

    private void computeNext() {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && isSpace(src.charAt(pos))) pos++;  // ignore whitespace
        if (pos == src.length())
        { next = null;  return; }  // no more tokens

        char c = src.charAt(pos);

        if(isDigit(c)) {
            s.append(c);
            for (pos++; pos < src.length() && (isDigit(src.charAt(pos)) || src.charAt(pos) == '.'); pos++) {
                s.append(src.charAt(pos));
            }
        } else if (isChar(c)) {  // start of identifier
            s.append(c);
            for (pos++; pos < src.length() && (isChar(src.charAt(pos)) || isDigit(src.charAt(pos))); pos++) {
                s.append(src.charAt(pos));
            }
        } else if (c == '#') {  // comment, ignore everything until the end of the line
            for (pos++; pos < src.length() && src.charAt(pos) != '\0'; pos++)
                ;  // skip characters
            computeNext();  // recurse to find the next token after the comment
            return;
        }else if (c == '(' || c == ')' || c == '{' || c == '}') {
            s.append(c);
            pos++;
        } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
            s.append(c);
            pos++;
        } else if (c == '^') {
            s.append(c);
            pos++;
        } else if (c == '=') {
            s.append(c);
            pos++;
        } else if (c == '<') {
            // Check for <identifier> or <number>
            pos++;
            if (pos < src.length() && isChar(src.charAt(pos))) {
                s.append(c);
                s.append(src.charAt(pos));
                pos++;
                while (pos < src.length() && (isChar(src.charAt(pos)) || isDigit(src.charAt(pos)))) {
                    s.append(src.charAt(pos));
                    pos++;
                }
            } else if (pos < src.length() && isDigit(src.charAt(pos))) {
                s.append(c);
                s.append(src.charAt(pos));
                pos++;
                while (pos < src.length() && isDigit(src.charAt(pos))) {
                    s.append(src.charAt(pos));
                    pos++;
                }
            } else {
//                s.append('<');  // If it's not followed by an identifier or number, treat it as a single '<'
            }
        } else {
            s.append(c);
            pos++;
        }
        next = s.toString();

    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }
    private boolean isChar(char c) {return Character.isLetter(c) || c == '_';}
    private boolean isSpace(char c) {
        return Character.isWhitespace(c);
    }



}
