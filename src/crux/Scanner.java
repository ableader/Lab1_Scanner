package crux;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class Scanner implements Iterable<Token> {
	public static String studentName = "Andrew Leader";
	public static String studentID = "45136371";
	public static String uciNetID = "ableader";
	
	private int lineNum;  // current line count
	private int charPos;  // character offset for current line
	private int nextChar; // contains the next char (-1 == EOF)
	private Reader input;
	
	Scanner(Reader reader)
	{
		lineNum = 1;
		charPos = 0;
		input = reader;
		nextChar = readChar();
	}
	
	// helper function for reading a single char from input
	// can be used to catch and handle any IOExceptions,
	// advance the charPos or lineNum, etc.
	private int readChar() {
		int nextChar= -1;
		try {
			nextChar = input.read();
			if (33 <= nextChar && nextChar <= 126) charPos++;
			
			//System.out.println("currentChar is (" + nextChar + "), char is [ " + Character.toString((char)nextChar) + " ]");
			//System.out.println("currentChar == /n? : " + (currentChar == (int)'\n'));
			if (nextChar == 10 || nextChar == 13) {
				nextChar = input.read();
				charPos = 1;
				lineNum++;
			}
			else if (nextChar == (int)' ') {
				nextChar = input.read();
				charPos++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("currentChar is now: " + (char)currentChar);
		return nextChar;
	}
	
	private boolean multChar(String l) {
		return ( l.equals("a")
				|| l.equals("o") // OR
				|| l.equals("n") // NOT
				|| l.equals("l") // LET
				|| l.equals("v") // VAR
				|| l.equals("f") // FUNC or FALSE
				|| l.equals("i") // IF
				|| l.equals("e") // ELSE
				|| l.equals("w") // WHILE
				|| l.equals("t") // TRUE
				|| l.equals("r") // RETURN
				|| l.equals(">")
				|| l.equals("<")
				|| l.equals("!")
				|| l.equals("=")
				|| l.equals(":")
				|| ((47 < nextChar) && (nextChar < 58))
				|| l.equals("-"));
	}
	
	private String matchRest(String lexeme, String expect) {
		for (int i = 2; i < expect.length(); i++) {
			if (nextChar == expect.charAt(i)) {
				lexeme += expect.charAt(i);
				nextChar = readChar();
			}
			else { lexeme = "error"; break; }
			
		}
		return lexeme;
	}
	
	
	/* Invariants:
	 *  1. call assumes that nextChar is already holding an unread character
	 *  2. return leaves nextChar containing an untokenized character
	 */
	public Token next()
	{
		int startingCharPos = charPos;
		int startingLineNum = lineNum;
		String lexeme = "";
		//System.out.println("                   LineNum:" + lineNum + ", charPos:" + charPos);

		if (nextChar > 0) {
			lexeme = Character.toString((char)nextChar);
		}
		
		if (multChar(lexeme)) {
			nextChar = readChar();
			// EQUALS and ASSIGN
			if (lexeme.equals("=")) {
				if (nextChar == (int)'=') lexeme = "==";
				else lexeme = "=";
			}
			// AND and ARRAY
			else if (lexeme.equals("a")) {
				if (nextChar == (int)'n') {
					lexeme = "an";
					nextChar = readChar();
					lexeme = matchRest(lexeme, "and");
				}
				else if (nextChar == (int)'r') {
					lexeme = "ar";
					nextChar = readChar();
					lexeme = matchRest(lexeme, "array");
				}
				else lexeme = "error";
			}
			else if (lexeme.equals("o")) {
				lexeme = matchRest(lexeme, "or");
				// if (nextChar == (int)'r') lexeme = "or";
				// else lexeme = "error";
			}
			else if (lexeme.equals("n")) {
				lexeme = matchRest(lexeme, "not");
			}
			else if (lexeme.equals("l")) {
				lexeme = matchRest(lexeme, "let");
			}
			else if (lexeme.equals("v")) {
				lexeme = matchRest(lexeme, "var");
			}
			else if (lexeme.equals("f")) {
				if (nextChar == (int)'o') {
					lexeme = matchRest(lexeme, "for");
				}
				else if (nextChar == (int)'a') {
					lexeme = matchRest(lexeme, "false");
				}
			}
			else if (lexeme.equals("i")) {
				if (nextChar == (int)'f') lexeme = "if";
				else lexeme = "error";
			}
			else if (lexeme.equals("e")) {
				lexeme = matchRest(lexeme, "else");
			}
			/*
					|| l.equals("w") // WHILE
					|| l.equals("t") // TRUE
					|| l.equals("r") // RETURN
					|| l.equals(">")
					|| l.equals("<")
					|| l.equals("!")
					|| l.equals(":")
					|| ((47 < nextChar) && (nextChar < 58))
					|| l.equals("-"));
					*/
		}
		
		
		
			
		Token t = new Token(lexeme, startingLineNum, startingCharPos);
		nextChar = readChar();
		
		return t;
	}

	public Iterator<Token> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	// OPTIONAL: any other methods that you find convenient for implementation or testing
}
