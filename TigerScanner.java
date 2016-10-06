import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * we wrote a hand-coded scanner
 * @author shiina mashiro & Gavin sun
 *
 */
public class TigerScanner {
    private final int N = 2048;                                 //half of buffer size
    private final int BUFFER_SIZE = 2 * N;                      //size of buffer
    private char[] Buffer = new char[BUFFER_SIZE];              //double-buffering
    private int char_pos = 0;                                   //position of current character
    private int fence = 0;                                      //limit of BUFFER, 0 or N
    private char c;                                             //place to store current character
    private int currentLine = 0;                                //lineNumber of current token
    private int lineNumber = 0;                                 //lineNumber
    private int nRead = 0;                                      //# of char read, -1 indicated EOF
    private StringBuilder lexeme = new StringBuilder();         //store current token
    /*
     * error code
     * 0 - invalid character
     * 1 - leading zero
     * 2 - invalid identifier
     * 3 - float start with period directly
     */
    private int error_code;
    private int error_pos;                                      //place where error occurs
    private String[] errorMsg = {", delete this invalid character",
                                 ", integers can't have leading 0",
                                 ", illegal identifier name"};
    private BufferedReader input = null;                        //input stream

    /*
     * In this scanner, TokenType is used to represent both TYPE of token and STATE!!!
     */
    private TokenType currentState = TokenType.INITIAL;
    private TokenType prevAcceptState = null;

    /*
     * ALL single character punctuations
     */
    private static final HashMap<Character,TokenType> punctuations = new HashMap<Character,TokenType>(){{
        put(',',TokenType.COMMA);
        put(':',TokenType.COLON);
        put(';',TokenType.SEMI);
        put('(',TokenType.LPAREN);
        put(')',TokenType.RPAREN);
        put('[',TokenType.LBRACK);
        put(']',TokenType.RBRACK);
        put('{',TokenType.LBRACE);
        put('}',TokenType.RBRACE);
        put('+',TokenType.PLUS);
        put('-',TokenType.MINUS);
        put('*',TokenType.MULT);
        put('/',TokenType.DIV);
        put('=',TokenType.EQ);
        put('<',TokenType.LESSER);
        put('>',TokenType.GREATER);
        put('&',TokenType.AND);
        put('|',TokenType.OR);
    }};

    //reserved keywords
    private static final HashMap<String,TokenType> keywords = new HashMap<String,TokenType>(){{
        put("array",TokenType.ARRAY);
        put("break",TokenType.BREAK);
        put("do",TokenType.DO);
        put("else",TokenType.ELSE);
        put("end",TokenType.END);
        put("for",TokenType.FOR);
        put("func",TokenType.FUNC);
        put("if",TokenType.IF);
        put("in",TokenType.IN);
        put("let",TokenType.LET);
        put("of",TokenType.OF);
        put("then",TokenType.THEN);
        put("to",TokenType.TO);
        put("type",TokenType.TYPE);
        put("var",TokenType.VAR);
        put("while",TokenType.WHILE);
        put("endif",TokenType.ENDIF);
        put("begin",TokenType.BEGIN);
        put("enddo",TokenType.ENDDO);
    }};
    /*
     * Constructor
     */
    public TigerScanner(String fileName){
        try{
            input = new BufferedReader(new FileReader(fileName));
            nRead = input.read(Buffer,0,N);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     * load next char into c
     */
    public boolean nextChar(){
        if((char_pos % N >= nRead) || (nRead == -1)){
            return false;
        }
        c = Buffer[char_pos];
        if(c == '\n'){
            lineNumber++;
        }
        char_pos = (char_pos+1) % (BUFFER_SIZE);
        if(char_pos % N == 0){
            try{
                nRead = input.read(Buffer, char_pos, N);
                fence = (char_pos + N) % BUFFER_SIZE;
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /*
     * roll back
     */
    public void rollback(){
        char_pos = (char_pos-1) % (BUFFER_SIZE);
        /*
         * In java, % is actually remainder rather than modular,
         * so we need to handle negative case
         */
        if(char_pos<0) char_pos+=BUFFER_SIZE;
    }

    /*
     * MAIN METHOD HERE!
     * - return next token
     * - return NULL if reach EOF
     */
    public Token nextToken(){
        /*
         * SET UP
         * - clear lexeme
         * - set up initial states
         */
        TokenType keyword;
        lexeme = new StringBuilder();
        currentState = TokenType.INITIAL;
        prevAcceptState = null;
        while(nextChar()){
            //Other than comments, we add char to token
            if(currentState != TokenType.COMMENTin && currentState != TokenType.COMMENTout) lexeme.append(c);
            /*
             * INITIAL STATE
             * -return all punctuation directly
             * -if input is literal, goto ID
             * -if input is digit,   goto INT
             * -TAKE CARE OF white spaces
             */
            if(currentState == TokenType.INITIAL){
                if(punctuations.get(c)!=null){
                    currentState = punctuations.get(c);
                    if(currentState == TokenType.DIV || currentState==TokenType.LESSER || currentState ==TokenType.GREATER || currentState==TokenType.COLON);
                    else break;
                }
                else if(Character.isLetter(c)){
                    currentState = TokenType.ID;
                }
                else if(c == '0'){
                    currentState = TokenType.ZERO;
                }
                else if('1'<=c && c<='9'){
                    currentState = TokenType.INTLIT;
                }
                else if(Character.isWhitespace(c)){
                    lexeme.setLength(lexeme.length()-1);
                }
                else{
                    error_code = 0;
                    error_pos = lexeme.length();
                    currentState = TokenType.ERROR;
                }
            }
            /*
             * < STATE
             * check <=
             * check <>
             */
            else if(currentState == TokenType.LESSER){
                if(c == '=') currentState = TokenType.LESSEREQ;
                else if(c=='>') currentState = TokenType.NEQ;
                else currentState = TokenType.FINAL;
                break;
            }
            /*
             * > STATE
             * check >=
             */
            else if(currentState == TokenType.GREATER){
                if(c == '=') currentState = TokenType.GREATEREQ;
                else currentState = TokenType.FINAL;
                break;
            }
            /*
             * : STATE
             * check :=
             */
            else if(currentState == TokenType.COLON){
                if(c == '=') currentState = TokenType.ASSIGN;
                else currentState = TokenType.FINAL;
                break;
            }
            /*
             * DIVIDE STATE
             * check /* comment
             */
            else if(currentState == TokenType.DIV){
                if(c == '*') currentState = TokenType.COMMENTin;
                else{
                    currentState = TokenType.FINAL;
                    break;
                }
            }
            else if(currentState == TokenType.COMMENTin){
                if(c == '*') currentState = TokenType.COMMENTout;
            }
            else if(currentState == TokenType.COMMENTout){
                if(c=='/'){
                    lexeme = new StringBuilder();
                    currentState = TokenType.INITIAL;
                }
                else currentState = TokenType.COMMENTin;
            }
            /*
             * FOR ALL states other than initial
             * punctuation | whitespace -> FINAL state
             */
            else if(punctuations.get(c) !=null | Character.isWhitespace(c)){
                currentState = TokenType.FINAL;
                break;
            }
            /*
             * ZERO STATE
             * if input is ".", goto FLOAT
             */
            else if(currentState == TokenType.ZERO){
                if(c == '.'){
                        currentState = TokenType.FLOATLIT;
                }
                else{
                    currentState = TokenType.ERROR;
                    error_pos = lexeme.length();
                    if(Character.isDigit(c)){
                        error_code = 1;
                    }
                    else if(Character.isLetter(c)){
                        error_code = 2;
                    }
                    else{
                        error_code = 0;
                    }
                }
            }
            /*
             * INTLIT STATE
             * -continue for DIGIT
             * -if ".", goto FLOATLIT
             */
            else if(currentState == TokenType.INTLIT){
                if(Character.isDigit(c));
                else if(c == '.'){
                    currentState = TokenType.FLOATLIT;
                }
                else{
                    currentState = TokenType.ERROR;
                    error_pos = lexeme.length();
                    if(Character.isLetter(c)){
                        error_code = 2;
                    }
                    else{
                        error_code = 0;
                    }
                }
            }
            /*
             * FLOATLIT STATE
             * continue for DIGIT
             */
            else if(currentState == TokenType.FLOATLIT){
                if(Character.isDigit(c));
                else{
                    currentState = TokenType.ERROR;
                    error_pos = lexeme.length();
                    if(Character.isLetter(c)) error_code = 2;
                    else error_code = 0;
                }
            }
            /*
             * IDENTIFIER STATE
             * continue for Literal | Digit | _
             */
            else if(currentState == TokenType.ID){
                if(Character.isLetterOrDigit(c) | c == '_');
                else{
                    currentState = TokenType.ERROR;
                    error_code = 0;
                    error_pos = lexeme.length();
                }
            }
            else if(currentState == TokenType.ERROR);
            else break;
            prevAcceptState = currentState;
        }
        //reach EOF
        if(currentState == TokenType.INITIAL || currentState == TokenType.COMMENTin || currentState == TokenType.COMMENTout) return null;
        currentLine = (c == '\n') ? lineNumber -1 : lineNumber;                 //compute lineNumber for currren token
        //recover state
        if(currentState == TokenType.FINAL){
            lexeme.deleteCharAt(lexeme.length()-1);
            rollback();
            currentState = prevAcceptState;
        }
        /*
         * handle keyword
         */
        if(currentState == TokenType.ID){
            keyword = keywords.get(lexeme.toString());
            if(keyword!=null) currentState = keyword;
        }
        /*
         * handle error messages
         */
        else if(currentState == TokenType.ERROR){
            lexeme.insert(error_pos-1,'"');
            if(error_code == 0){ //invalid character
                lexeme.insert(error_pos+1,'"');
            }
            else lexeme.insert(0, '"');
            System.out.println("Lexical Error: [" + lexeme.toString() + "] on line " + currentLine + errorMsg[error_code]);
        }
        return new Token(currentState, lexeme.toString(),currentLine);
    }

    public static void main(String[] args){
        Token token;
        TigerScanner scanner = new TigerScanner("tiger.txt");
        while((token = scanner.nextToken())!=null){
           System.out.println(token);
        }
    }
}
