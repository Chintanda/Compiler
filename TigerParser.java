import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * non-terminals <- Integer!
 * terminals     <- Token!
 */
public class TigerParser {
    private Token token;
    private Object peak;

    private final Map<key,ArrayList<Object>> parse_table = new HashMap<key,ArrayList<Object>>(){{
        put(new key(1,32),new ArrayList<>(Arrays.asList(TokenType.LET,2,TokenType.IN,27,TokenType.END)));
    }};


}
