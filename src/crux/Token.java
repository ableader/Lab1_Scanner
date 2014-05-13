package crux;

public class Token {
	
	public static enum Kind {
		AND("and"),
		OR("or"),
		NOT("not"),
		LET("let"),
		VAR("var"),
		ARRAY("array"),
		FUNC("func"),
		IF("if"),
		ELSE("else"),
		WHILE("while"),
		TRUE("true"),
		FALSE("false"),
		RETURN("return"),
		
		OPEN_PAREN("("),
		CLOSE_PAREN(")"),
		OPEN_BRACE("{"),
		CLOSE_BRACE("}"),
		OPEN_BRACKET("["),
		CLOSE_BRACKET("]"),
		ADD("+"),
		SUB("-"),
		MUL("*"),
		DIV("/"),
		GREATER_EQUAL(">="),
		LESSER_EQUAL("<="),
		NOT_EQUAL("!="),
		EQUAL("=="),
		GREATER_THAN(">"),
		LESS_THAN("<"),
		ASSIGN("="),
		COMMA(","),
		SEMICOLON(";"),
		COLON(":"),
		CALL("::"),

		IDENTIFIER(),	// ("_" | letter) { "_" | letter | digit }
		INTEGER(), 		// digit {digit}
		FLOAT(),	  	// digit {digit} "." {digit}
		ERROR(),		// any char sequence not otherwise reserved
		EOF();			// EOF marker
		
		// TODO: finish the list of possible tokens (the last 5)
		
		private String default_lexeme;
		
		Kind()
		{
			default_lexeme = "";
		}
		
		Kind(String lexeme)
		{
			default_lexeme = lexeme;
		}
		
		public boolean hasStaticLexeme()
		{
			return default_lexeme != null;
		}
		
		public boolean matches(String lexeme)
		{ return default_lexeme.equals(lexeme); }
		
		public boolean isKind(Kind kind)
		{ return kind.equals(this); }
		
		// OPTIONAL: if you wish to also make convenience functions, feel free
		//           for example, boolean matches(String lexeme)
		//           can report whether a Token.Kind has the given lexeme
	}
	
	private int lineNum;
	private int charPos;
	Kind kind;
	private String lexeme = "";
	
	
	// OPTIONAL: implement factory functions for some tokens, as you see fit
	
	public static Token EOF(int linePos, int charPos)
	{
		Token tok = new Token(linePos, charPos);
		tok.kind = Kind.EOF;
		return tok;
	}

	private Token(int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
		
		// if we don't match anything, signal error
		this.kind = Kind.ERROR;
		this.lexeme = "No Lexeme Given";
	}
	
	public Token(String lexeme, int lineNum, int charPos)
	{
		this.lineNum = lineNum;
		this.charPos = charPos;
		if (lexeme == null) { new Token(lineNum, charPos); }
		else { this.lexeme = lexeme; }
		
		// TODO: based on the given lexeme determine and set the actual kind
		/*if lexeme is an Identifier
			set kind to Identifier
			save lexeme into token
		else if lexeme is an Integer
			set kind to Integer
			save lexeme value into token
		else if lexeme is a Float
			set kind to Float
			save lexeme value into token*/
		if (lexeme.equals("and")) {this.kind = Kind.AND;}
		else if (lexeme.equals("or")) {this.kind = Kind.OR;}
		else if (lexeme.equals("not")) {this.kind = Kind.NOT;}
		else if (lexeme.equals("let")) {this.kind = Kind.LET;}
		else if (lexeme.equals("var")) {this.kind = Kind.VAR;}
		else if (lexeme.equals("array")) {this.kind = Kind.ARRAY;}
		else if (lexeme.equals("func")) {this.kind = Kind.FUNC;}
		else if (lexeme.equals("if")) {this.kind = Kind.IF;}
		else if (lexeme.equals("else")) {this.kind = Kind.ELSE;}
		else if (lexeme.equals("while")) {this.kind = Kind.WHILE;}
		else if (lexeme.equals("true")) {this.kind = Kind.TRUE;}
		else if (lexeme.equals("false")) {this.kind = Kind.FALSE;}
		else if (lexeme.equals("return")) {this.kind = Kind.RETURN;}
		
		else if (lexeme.equals("(")) {this.kind = Kind.OPEN_PAREN;}
		else if (lexeme.equals(")")) {this.kind = Kind.CLOSE_PAREN;}
		else if (lexeme.equals("{")) {this.kind = Kind.OPEN_BRACE;}
		else if (lexeme.equals("}")) {this.kind = Kind.CLOSE_BRACE;}
		else if (lexeme.equals("[")) {this.kind = Kind.OPEN_BRACKET;}
		else if (lexeme.equals("]")) {this.kind = Kind.CLOSE_BRACKET;}
		else if (lexeme.equals("+")) {this.kind = Kind.ADD;}
		else if (lexeme.equals("-")) {this.kind = Kind.SUB;}
		else if (lexeme.equals("*")) {this.kind = Kind.MUL;}
		else if (lexeme.equals("/")) {this.kind = Kind.DIV;}
		else if (lexeme.equals(",")) {this.kind = Kind.COMMA;}
		else if (lexeme.equals(";")) {this.kind = Kind.SEMICOLON;}

		else if (lexeme.equals(">")) {this.kind = Kind.GREATER_THAN;}
		else if (lexeme.equals(">=")) {this.kind = Kind.GREATER_EQUAL;}
		else if (lexeme.equals("<")) {this.kind = Kind.LESS_THAN;}
		else if (lexeme.equals("<=")) {this.kind = Kind.LESSER_EQUAL;}
		else if (lexeme.equals("=")) {this.kind = Kind.ASSIGN;}
		else if (lexeme.equals("==")) {this.kind = Kind.EQUAL;}
		else if (lexeme.equals(":")) {this.kind = Kind.COLON;}
		else if (lexeme.equals("::")) {this.kind = Kind.CALL;}

		else if (lexeme.equals("!=")) {this.kind = Kind.NOT_EQUAL;}
		
		else if (lexeme.equals("")) {this.kind = Kind.EOF;} 
		
		// if we don't match anything, signal error
		if (false) {	// TODO: if(no match)
			this.kind = Kind.ERROR;
			this.lexeme = "Unrecognized lexeme: " + lexeme;
		}
	}

	public int lineNumber()
	{
		return lineNum;
	}
	
	public int charPosition()
	{
		return charPos;
	}
	
	// Return the lexeme representing or held by this token
	public String lexeme()
	{
		return this.lexeme;
	}
	
	public String kind()
	{ return this.kind.name(); }
	
	public String toString()
	{
		return kind + "(lineNum:" + lineNum + ", charPos:" + charPos + ")";
	}
	
	public boolean is(Token.Kind kind)
	{
		return this.kind.equals(kind);
	}
	
	// OPTIONAL: add any additional helper or convenience methods
	//           that you find make for a clean design
}
