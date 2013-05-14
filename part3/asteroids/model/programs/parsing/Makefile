
ifndef JAVA
  $(error JAVA not defined)
endif

ifndef JAVAC
  $(error JAVAC not defined)
endif

ifndef CLASSPATH
  $(error CLASSPATH not defined)
endif


ANTLRPWD = $(shell pwd)
ANTLRJAR = antlr-4.0-complete.jar

SETENV   = export \
             CLASSPATH="./:${CLASSPATH}:${ANTLRPWD}/${ANTLRJAR}:$$CLASSPATH"

ANTLR4   = ${SETENV}; ${JAVA} -jar ${ANTLRPWD}/${ANTLRJAR}
GRUN     = ${SETENV}; ${JAVA} org.antlr.v4.runtime.misc.TestRig


all: antlr test_lexer test_api antlrclasspath

antlr:
	curl http://antlr4.org/download/antlr-4.0-complete.jar \
          -o antlr-4.0-complete.jar
	${ANTLR4}
	touch antlr

antlrclasspath: antlr
	echo "${ANTLRPWD}/${ANTLRJAR}" >antlrclasspath

AsteroidsParser.java: AsteroidsParser.g4 antlr
	${ANTLR4} AsteroidsParser.g4

AsteroidsParser.class: AsteroidsParser.java
	${SETENV}; ${JAVAC} \
          AsteroidsParserParser*.java AsteroidsParserLexer*.java \
          AsteroidsParserListener.java AsteroidsParserBaseListener.java

test_lexer: AsteroidsParser.class
	cd ../../../../; \
          ${GRUN} asteroids.model.programs.parsing.AsteroidsParser \
          eval -tree <asteroids/resources/syntax_test.txt
	cd ../../../../; \
          ${GRUN} asteroids.model.programs.parsing.AsteroidsParser \
          eval -tree <asteroids/resources/program.txt

test_lexer_gui: test_lexer
	cd ../../../../; \
          ${GRUN} asteroids.model.programs.parsing.AsteroidsParser \
          eval -gui <asteroids/resources/syntax_test.txt

Processor.class: Processor.java AsteroidsParser.class
	${SETENV}; ${JAVAC} Processor.java \
          AsteroidsParserParser*.java AsteroidsParserLexer*.java \
          AsteroidsParserListener.java AsteroidsParserBaseListener.java

test_api: Processor.class
	cd ../../../../; ${SETENV}; \
          ${JAVA} asteroids.model.programs.parsing.Processor \
          asteroids/resources/syntax_test.txt
	cd ../../../../; ${SETENV}; \
          ${JAVA} asteroids.model.programs.parsing.Processor \
          asteroids/resources/program.txt

clean:
	rm -f AsteroidsParserParser*.java
	rm -f AsteroidsParserLexer*.java
	rm -f AsteroidsParserListener.java
	rm -f AsteroidsParserBaseListener.java
	rm -f AsteroidsParser*.tokens
	rm -f AsteroidsParser*.class
	rm -f Processor.class
	rm -f antlrclasspath

distclean: clean

