/**
 * Type of Token
 * @author shiina mashiro
 *
 */
public enum TokenType {
    //all punctuation
    COMMA(0),
    COLON(1),
    SEMI(2),
    LPAREN(3),
    RPAREN(4),
    LBRACK(5),
    RBRACK(6),
    LBRACE(7),
    RBRACE(8),
    PERIOD(9),
    PLUS(10),
    MINUS(11),
    MULT(12),
    DIV(13),
    EQ(14),
    NEQ(15),
    LESSER(16),
    GREATER(17),
    LESSEREQ(18),
    GREATEREQ(19),
    AND(20),
    OR(21),
    ASSIGN(22),

    //keywords
    ARRAY(23),
    BREAK(24),
    DO(25),
    ELSE(26),
    END(27),
    FOR(28),
    FUNC(29),
    IF(30),
    IN(31),
    LET(32),
    OF(33),
    THEN(34),
    TO(35),
    TYPE(36),
    VAR(37),
    WHILE(38),
    ENDIF(39),
    BEGIN(40),
    ENDDO(41),
    RETURN(42),
    INT(43),
    FLOAT(44),
    //user-defined identifiers(), integer and float literals
    ID(45),
    INTLIT(46),
    FLOATLIT(47),
    /*
     * These are not real TokenTypes(), but they are useful to represent states
     */
    INITIAL(-1),
    ZERO(-1),
    ERROR(-1),
    COMMENTin(-1),
    COMMENTout(-1),
    FINAL(-1);

    public final int id;

    TokenType(int id){
        this.id = id;
    }
}
