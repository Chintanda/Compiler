import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/*
 * non-terminals <- Integer!
 * terminals     <- Token!
 * 
 * We store grammar rules in a list structure and use hashmap to access them.
 */
public class TigerParser {
    private Token token;
    private Object peak;
    private List<Object> rule;
    private Stack<Object> stack = new Stack<>();
    private TigerScanner scanner;
    
    public TigerParser(String file){
    	scanner = new TigerScanner(file);
    }
    
    public void push(List<Object> list){
    	for(int i = list.size()-1;i>=0;i--){
    		stack.push(list.get(i));
    	}
    }
    
	private final static List<List<Object>> grammar_rules = new ArrayList<List<Object>>(){
	{
    	add(Collections.emptyList());
    	add(Arrays.asList(TokenType.LET,2,TokenType.IN,18,TokenType.END));
    	add(Arrays.asList(3,4,5));
    	add(Arrays.asList(6,3));
    	add(Arrays.asList(9,4));
    	add(Arrays.asList(13,5));
    	add(Arrays.asList(TokenType.TYPE,TokenType.ID,TokenType.EQ,7,TokenType.SEMI));
    	add(Arrays.asList(8));
    	add(Arrays.asList(TokenType.ARRAY,TokenType.LBRACK,TokenType.INTLIT,TokenType.RBRACK,TokenType.OF,8));
    	add(Arrays.asList(TokenType.ID));
    	add(Arrays.asList(TokenType.INT));
    	add(Arrays.asList(TokenType.VAR,10,TokenType.COLON,7,12,TokenType.SEMI));
    	add(Arrays.asList(TokenType.ID));
    	add(Arrays.asList(TokenType.COMMA,10));
    	add(Arrays.asList(TokenType.ASSIGN,32));
    	add(Arrays.asList(TokenType.FUNC,TokenType.ID,TokenType.LPAREN,14,TokenType.RPAREN,16,TokenType.BEGIN,18,TokenType.END,TokenType.SEMI));
    	add(Arrays.asList(17,15));
    	add(Arrays.asList(17,15));
    	add(Arrays.asList(TokenType.COLON,7));
    	add(Arrays.asList(TokenType.ID,TokenType.COLON,7));
    	add(Arrays.asList(20,19));
    	add(Arrays.asList(18));
    	add(Arrays.asList(TokenType.ID,21));
    	add(Arrays.asList(TokenType.IF,23,TokenType.THEN,18,TokenType.ELSE,18,TokenType.ENDIF,TokenType.SEMI));
    	add(Arrays.asList(TokenType.WHILE,23,TokenType.DO,18,TokenType.ENDDO,TokenType.SEMI));
    	add(Arrays.asList(TokenType.FOR,TokenType.ID,TokenType.ASSIGN,23,TokenType.TO,23,TokenType.DO,18,TokenType.ENDDO,TokenType.SEMI));
    	add(Arrays.asList(TokenType.LPAREN,33,TokenType.RPAREN,TokenType.SEMI));
    	add(Arrays.asList(36,TokenType.ASSIGN,22));
    	add(Arrays.asList(23));
    	add(Arrays.asList(TokenType.BREAK,TokenType.SEMI));
    	add(Arrays.asList(TokenType.LET,2,TokenType.IN,18,TokenType.END));
    	add(Arrays.asList(25,24));
    	add(Arrays.asList(TokenType.AND,25,24));
    	add(Arrays.asList(TokenType.OR,25,24));
    	add(Arrays.asList(27,26));
    	add(Arrays.asList(TokenType.GREATEREQ,27,26));
    	add(Arrays.asList(TokenType.LESSEREQ,27,26));
    	add(Arrays.asList(TokenType.EQ,27,26));
    	add(Arrays.asList(TokenType.NEQ,27,26));
    	add(Arrays.asList(TokenType.GREATER,27,26));
    	add(Arrays.asList(TokenType.LESSER,27,26));
    	add(Arrays.asList(29,28));
    	add(Arrays.asList(TokenType.PLUS,29,28));
    	add(Arrays.asList(TokenType.MINUS,29,28));
    	add(Arrays.asList(31,30));
    	add(Arrays.asList(TokenType.MULT,31,30));
    	add(Arrays.asList(TokenType.DIV,31,30));
    	add(Arrays.asList(TokenType.LPAREN,23,TokenType.RPAREN));
    	add(Arrays.asList(32));
    	add(Arrays.asList(35));
    	add(Arrays.asList(TokenType.INTLIT));
    	add(Arrays.asList(TokenType.FLOATLIT));
    	add(Arrays.asList(23,34));
    	add(Arrays.asList(TokenType.COMMA,23,34));
    	add(Arrays.asList(TokenType.ID,36));
    	add(Arrays.asList(TokenType.LBRACK,23,TokenType.RBRACK));
    	add(Arrays.asList(20,TokenType.RETURN,23,TokenType.SEMI));
    	add(Arrays.asList(TokenType.FLOAT));
    	add(Arrays.asList(TokenType.ID,TokenType.LPAREN,33,TokenType.RPAREN,TokenType.SEMI));
    }};
    
    private final static Map<key,List<Object>> parse_table = new HashMap<key,List<Object>>(){{
    	put(new key(1,TokenType.LET),grammar_rules.get(1));
    	put(new key(2,TokenType.FUNC),grammar_rules.get(2));
    	put(new key(2,TokenType.IN),grammar_rules.get(2));
    	put(new key(2,TokenType.TYPE),grammar_rules.get(2));
    	put(new key(2,TokenType.VAR),grammar_rules.get(2));
    	put(new key(3,TokenType.FUNC),grammar_rules.get(0));
    	put(new key(3,TokenType.IN),grammar_rules.get(0));
    	put(new key(3,TokenType.TYPE),grammar_rules.get(3));
    	put(new key(3,TokenType.VAR),grammar_rules.get(0));
    	put(new key(4,TokenType.FUNC),grammar_rules.get(0));
    	put(new key(4,TokenType.IN),grammar_rules.get(0));
    	put(new key(4,TokenType.VAR),grammar_rules.get(4));
    	put(new key(5,TokenType.FUNC),grammar_rules.get(5));
    	put(new key(5,TokenType.IN),grammar_rules.get(0));
    	put(new key(6,TokenType.TYPE),grammar_rules.get(6));
    	put(new key(7,TokenType.ARRAY),grammar_rules.get(8));
    	put(new key(7,TokenType.INT),grammar_rules.get(7));
    	put(new key(7,TokenType.FLOAT),grammar_rules.get(7));
    	put(new key(7,TokenType.ID),grammar_rules.get(9));
    	put(new key(8,TokenType.INT),grammar_rules.get(10));
    	put(new key(8,TokenType.FLOAT),grammar_rules.get(58));
    	put(new key(9,TokenType.VAR),grammar_rules.get(11));
    	put(new key(10,TokenType.ID),grammar_rules.get(12));
    	put(new key(11,TokenType.COMMA),grammar_rules.get(13));
    	put(new key(11,TokenType.COLON),grammar_rules.get(0));
    	put(new key(12,TokenType.COLON),grammar_rules.get(14));
    	put(new key(12,TokenType.SEMI),grammar_rules.get(0));
    	put(new key(13,TokenType.FUNC),grammar_rules.get(15));
    	put(new key(14,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(14,TokenType.ID),grammar_rules.get(16));
    	put(new key(15,TokenType.COMMA),grammar_rules.get(17));
    	put(new key(15,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(16,TokenType.COLON),grammar_rules.get(18));
    	put(new key(16,TokenType.BEGIN),grammar_rules.get(0));
    	put(new key(17,TokenType.ID),grammar_rules.get(19));
    	put(new key(18,TokenType.BREAK),grammar_rules.get(20));
    	put(new key(18,TokenType.FOR),grammar_rules.get(20));
    	put(new key(18,TokenType.IF),grammar_rules.get(20));
    	put(new key(18,TokenType.LET),grammar_rules.get(20));
    	put(new key(18,TokenType.WHILE),grammar_rules.get(20));
    	put(new key(18,TokenType.RETURN),grammar_rules.get(20));
    	put(new key(18,TokenType.ID),grammar_rules.get(20));
    	put(new key(19,TokenType.BREAK),grammar_rules.get(21));
    	put(new key(19,TokenType.ELSE),grammar_rules.get(0));
    	put(new key(19,TokenType.END),grammar_rules.get(0));
    	put(new key(19,TokenType.FOR),grammar_rules.get(21));
    	put(new key(19,TokenType.IF),grammar_rules.get(21));
    	put(new key(19,TokenType.LET),grammar_rules.get(21));
    	put(new key(19,TokenType.WHILE),grammar_rules.get(21));
    	put(new key(19,TokenType.ENDIF),grammar_rules.get(0));
    	put(new key(19,TokenType.ENDDO),grammar_rules.get(0));
    	put(new key(19,TokenType.RETURN),grammar_rules.get(21));
    	put(new key(19,TokenType.ID),grammar_rules.get(21));
    	put(new key(20,TokenType.BREAK),grammar_rules.get(29));
    	put(new key(20,TokenType.FOR),grammar_rules.get(25));
    	put(new key(20,TokenType.IF),grammar_rules.get(23));
    	put(new key(20,TokenType.LET),grammar_rules.get(30));
    	put(new key(20,TokenType.WHILE),grammar_rules.get(24));
    	put(new key(20,TokenType.RETURN),grammar_rules.get(57));
    	put(new key(20,TokenType.ID),grammar_rules.get(22));
    	put(new key(21,TokenType.COLON),grammar_rules.get(27));
    	put(new key(21,TokenType.LPAREN),grammar_rules.get(26));
    	put(new key(21,TokenType.LBRACK),grammar_rules.get(27));
    	put(new key(23,TokenType.LPAREN),grammar_rules.get(31));
    	put(new key(23,TokenType.ID),grammar_rules.get(31));
    	put(new key(23,TokenType.INTLIT),grammar_rules.get(31));
    	put(new key(23,TokenType.FLOATLIT),grammar_rules.get(31));
    	put(new key(24,TokenType.COMMA),grammar_rules.get(0));
    	put(new key(24,TokenType.SEMI),grammar_rules.get(0));
    	put(new key(24,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(24,TokenType.RBRACK),grammar_rules.get(0));
    	put(new key(24,TokenType.AND),grammar_rules.get(32));
    	put(new key(24,TokenType.OR),grammar_rules.get(33));
    	put(new key(24,TokenType.DO),grammar_rules.get(0));
    	put(new key(24,TokenType.THEN),grammar_rules.get(0));
    	put(new key(24,TokenType.TO),grammar_rules.get(0));
    	put(new key(25,TokenType.LPAREN),grammar_rules.get(34));
    	put(new key(25,TokenType.ID),grammar_rules.get(34));
    	put(new key(25,TokenType.INTLIT),grammar_rules.get(34));
    	put(new key(25,TokenType.FLOATLIT),grammar_rules.get(34));
    	put(new key(26,TokenType.COMMA),grammar_rules.get(0));
    	put(new key(26,TokenType.SEMI),grammar_rules.get(0));
    	put(new key(26,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(26,TokenType.RBRACK),grammar_rules.get(0));
    	put(new key(26,TokenType.EQ),grammar_rules.get(37));
    	put(new key(26,TokenType.NEQ),grammar_rules.get(38));
    	put(new key(26,TokenType.LESSER),grammar_rules.get(40));
    	put(new key(26,TokenType.GREATER),grammar_rules.get(39));
    	put(new key(26,TokenType.LESSEREQ),grammar_rules.get(36));
    	put(new key(26,TokenType.GREATEREQ),grammar_rules.get(35));
    	put(new key(26,TokenType.AND),grammar_rules.get(0));
    	put(new key(26,TokenType.OR),grammar_rules.get(0));
    	put(new key(26,TokenType.DO),grammar_rules.get(0));
    	put(new key(26,TokenType.THEN),grammar_rules.get(0));
    	put(new key(26,TokenType.TO),grammar_rules.get(0));
    	put(new key(27,TokenType.LPAREN),grammar_rules.get(41));
    	put(new key(27,TokenType.ID),grammar_rules.get(41));
    	put(new key(27,TokenType.INTLIT),grammar_rules.get(41));
    	put(new key(27,TokenType.FLOATLIT),grammar_rules.get(41));
    	put(new key(28,TokenType.COMMA),grammar_rules.get(0));
    	put(new key(28,TokenType.SEMI),grammar_rules.get(0));
    	put(new key(28,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(28,TokenType.RBRACK),grammar_rules.get(0));
    	put(new key(28,TokenType.PLUS),grammar_rules.get(42));
    	put(new key(28,TokenType.MINUS),grammar_rules.get(43));
    	put(new key(28,TokenType.EQ),grammar_rules.get(0));
    	put(new key(28,TokenType.NEQ),grammar_rules.get(0));
    	put(new key(28,TokenType.LESSER),grammar_rules.get(0));
    	put(new key(28,TokenType.GREATER),grammar_rules.get(0));
    	put(new key(28,TokenType.LESSEREQ),grammar_rules.get(0));
    	put(new key(28,TokenType.GREATEREQ),grammar_rules.get(0));
    	put(new key(28,TokenType.AND),grammar_rules.get(0));
    	put(new key(28,TokenType.OR),grammar_rules.get(0));
    	put(new key(28,TokenType.DO),grammar_rules.get(0));
    	put(new key(28,TokenType.THEN),grammar_rules.get(0));
    	put(new key(28,TokenType.TO),grammar_rules.get(0));
    	put(new key(29,TokenType.LPAREN),grammar_rules.get(44));
    	put(new key(29,TokenType.ID),grammar_rules.get(44));
    	put(new key(29,TokenType.INTLIT),grammar_rules.get(44));
    	put(new key(29,TokenType.FLOATLIT),grammar_rules.get(44));
    	put(new key(30,TokenType.COMMA),grammar_rules.get(0));
    	put(new key(30,TokenType.SEMI),grammar_rules.get(0));
    	put(new key(30,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(30,TokenType.RBRACK),grammar_rules.get(0));
    	put(new key(30,TokenType.PLUS),grammar_rules.get(0));
    	put(new key(30,TokenType.MINUS),grammar_rules.get(0));
    	put(new key(30,TokenType.MULT),grammar_rules.get(45));
    	put(new key(30,TokenType.DIV),grammar_rules.get(46));
    	put(new key(30,TokenType.EQ),grammar_rules.get(0));
    	put(new key(30,TokenType.NEQ),grammar_rules.get(0));
    	put(new key(30,TokenType.LESSER),grammar_rules.get(0));
    	put(new key(30,TokenType.GREATER),grammar_rules.get(0));
    	put(new key(30,TokenType.LESSEREQ),grammar_rules.get(0));
    	put(new key(30,TokenType.GREATEREQ),grammar_rules.get(0));
    	put(new key(30,TokenType.AND),grammar_rules.get(0));
    	put(new key(30,TokenType.OR),grammar_rules.get(0));
    	put(new key(30,TokenType.DO),grammar_rules.get(0));
    	put(new key(30,TokenType.THEN),grammar_rules.get(0));
    	put(new key(30,TokenType.TO),grammar_rules.get(0));
    	put(new key(31,TokenType.LPAREN),grammar_rules.get(47));
    	put(new key(31,TokenType.ID),grammar_rules.get(49));
    	put(new key(31,TokenType.INTLIT),grammar_rules.get(48));
    	put(new key(31,TokenType.FLOATLIT),grammar_rules.get(48));
    	put(new key(32,TokenType.INTLIT),grammar_rules.get(50));
    	put(new key(32,TokenType.FLOATLIT),grammar_rules.get(51));
    	put(new key(33,TokenType.LPAREN),grammar_rules.get(52));
    	put(new key(33,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(33,TokenType.ID),grammar_rules.get(52));
    	put(new key(33,TokenType.INTLIT),grammar_rules.get(52));
    	put(new key(33,TokenType.FLOATLIT),grammar_rules.get(52));
    	put(new key(34,TokenType.COMMA),grammar_rules.get(53));
    	put(new key(34,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(35,TokenType.ID),grammar_rules.get(0));
    	put(new key(36,TokenType.COMMA),grammar_rules.get(0));
    	put(new key(36,TokenType.COLON),grammar_rules.get(0));
    	put(new key(36,TokenType.SEMI),grammar_rules.get(0));
    	put(new key(36,TokenType.RPAREN),grammar_rules.get(0));
    	put(new key(36,TokenType.LBRACK),grammar_rules.get(0));
    	put(new key(36,TokenType.RBRACK),grammar_rules.get(0));
    	put(new key(36,TokenType.PLUS),grammar_rules.get(0));
    	put(new key(36,TokenType.MINUS),grammar_rules.get(0));
    	put(new key(36,TokenType.MULT),grammar_rules.get(0));
    	put(new key(36,TokenType.DIV),grammar_rules.get(0));
    	put(new key(36,TokenType.EQ),grammar_rules.get(0));
    	put(new key(36,TokenType.NEQ),grammar_rules.get(0));
    	put(new key(36,TokenType.LESSER),grammar_rules.get(0));
    	put(new key(36,TokenType.GREATER),grammar_rules.get(0));
    	put(new key(36,TokenType.LESSEREQ),grammar_rules.get(0));
    	put(new key(36,TokenType.GREATEREQ),grammar_rules.get(0));
    	put(new key(36,TokenType.AND),grammar_rules.get(0));
    	put(new key(36,TokenType.OR),grammar_rules.get(0));
    	put(new key(36,TokenType.DO),grammar_rules.get(0));
    	put(new key(36,TokenType.THEN),grammar_rules.get(0));
    	put(new key(36,TokenType.TO),grammar_rules.get(0));
    }};
    
    public void parser(){
    	//push EOF and start symbol
    	token = scanner.nextToken();
    	stack.push(-1);
    	stack.push(1);
    	peak = stack.peek();
    	while(true){
    		if(peak.equals(-1) && token == null){
    			System.out.println("Successful Parsing");
    		}
    		else if(peak instanceof TokenType || peak.equals(-1)){
    			if(token.type == peak){
    				stack.pop();
    				token = scanner.nextToken();
    			}
    			else{
    				System.out.println(token.lexeme + "on line " + token.lineNumber + " " + peak + " is expected");
    				break;
    			}
    		}
    		else{
    			if((Integer)peak == 22 && token.type != TokenType.ID){//LL(2)
    				rule = null;
    			} else{
    				rule = parse_table.get(new key((Integer)peak,token.type));
    			}
    			if(rule != null){
    				stack.pop();
    				push(rule);
    			}
    			else{
    				System.out.println("Can not find rule to expand " + token.lexeme + " on line " + token.lineNumber );
    				break;
    			}
    		}
    		peak = stack.peek();
    	}
    }
    
    public static void main(String[] args){
    	TigerParser parser = new TigerParser("test1.txt");
    	parser.parser();
    }

}
