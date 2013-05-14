package asteroids.model.programs.parsing;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;

public class Processor {

  public static void main(String[] args) throws IOException, RecognitionException {
    if (args.length == 0) {
      new Processor().processInteractive();
    } else if (args.length == 1) {
      new Processor().processFile(args[0]);
    } else {
      System.err.println("usage: java Processor [file-name]");
    }
  }

  private void processFile(String filePath) throws IOException, RecognitionException {
    CharStream cs = new ANTLRFileStream(filePath);
    AsteroidsParserLexer lexer = new AsteroidsParserLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AsteroidsParserParser parser = new AsteroidsParserParser(tokens);
    parser.setBuildParseTree(true);

    @SuppressWarnings("unused")
    ParserRuleContext tree = parser.eval();

//    tree.inspect(parser); // show parse tree
  }

  private void processInteractive() throws IOException, RecognitionException {

  }
}
