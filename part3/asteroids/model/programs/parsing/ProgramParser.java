package asteroids.model.programs.parsing;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ProgramParser<E, S, T> {
  private ProgramFactory<E, S, T> factory;
  private Map<String, T> globals = null;
  private List<String> errors = new ArrayList<String>();
  private S statement = null;

  public ProgramParser(ProgramFactory<E, S, T> factory) {
    this.factory = factory;
  }
  
  public void parse(String text) throws RecognitionException {
    globals = null;
    statement = null;
    CharStream cs = new ANTLRInputStream(text);
    AsteroidsParserLexer lexer = new AsteroidsParserLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AsteroidsParserParser parser = new AsteroidsParserParser(tokens);
    parser.setBuildParseTree(true);
    parser.addErrorListener(new ANTLRErrorListener() {
      
      @Override
      public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int column, String msg, RecognitionException e) {
        ProgramParser.this.errors.add(line + ":" + column + " syntax error: " + msg);
        
      }
      
      @Override
      public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, ATNConfigSet arg4) {
        
      }
      
      @Override
      public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, ATNConfigSet arg4) {
        
      }
      
      @Override
      public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4, ATNConfigSet arg5) {
        
      }
    });
    ParserRuleContext tree = parser.eval();
    if(! errors.isEmpty())
      return;
    AsteroidsParserMyListener<E, S, T> listener = new AsteroidsParserMyListener<E, S, T>(factory);
    
    ParseTreeWalker.DEFAULT.walk(listener, tree);
    errors = listener.getErrors();
    globals = listener.getGlobals();
    statement = listener.getStatement();
  }
  
  public List<String> getErrors() {
    return errors;
  }
  
  /**
   * This method returns a non-null result provided <code>getErrors()</code> is empty.
   */
  public Map<String, T> getGlobals() {
    return globals;
  }
  
  /**
   * This method returns a non-null result provided <code>getErrors()</code> is empty.
   */
  public S getStatement() {
    return statement;
  }
}
