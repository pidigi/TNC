// Generated from AsteroidsParser.g4 by ANTLR 4.0
 package asteroids.model.programs.parsing; 
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AsteroidsParserParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SELF=1, TRUE=2, FALSE=3, NULL=4, BOOL=5, DOUBLE=6, ENTITY=7, SHIP=8, ASTEROID=9, 
		BULLET=10, ANY=11, GETRADIUS=12, GETX=13, GETY=14, GETVX=15, GETVY=16, 
		GETDIR=17, SQRT=18, SIN=19, COS=20, NOT=21, THRUSTON=22, THRUSTOFF=23, 
		TURN=24, FIRE=25, SKIP=26, PRINT=27, IF=28, THEN=29, ELSE=30, WHILE=31, 
		DO=32, FOREACH=33, ASSIGN=34, MUL=35, DIV=36, ADD=37, SUB=38, EQ=39, NEQ=40, 
		LT=41, GT=42, LEQ=43, GEQ=44, AND=45, OR=46, NUMBER=47, FLOAT=48, INTEGER=49, 
		SIGN=50, IDENTIFIER=51, LEFT_PAREN=52, RIGHT_PAREN=53, LEFT_BRACE=54, 
		RIGHT_BRACE=55, SEMICOLON=56, COMMA=57, WHITESPACE=58, SINGLE_COMMENT=59, 
		MOD=60;
	public static final String[] tokenNames = {
		"<INVALID>", "'self'", "'true'", "'false'", "'null'", "'bool'", "'double'", 
		"'entity'", "'ship'", "'asteroid'", "'bullet'", "'any'", "'getradius'", 
		"'getx'", "'gety'", "'getvx'", "'getvy'", "'getdir'", "'sqrt'", "'sin'", 
		"'cos'", "'!'", "'thrust'", "'thrust_off'", "'turn'", "'fire'", "'skip'", 
		"'print'", "'if'", "'then'", "'else'", "'while'", "'do'", "'foreach'", 
		"':='", "'*'", "'/'", "'+'", "'-'", "'=='", "'!='", "'<'", "'>'", "'<='", 
		"'>='", "'&&'", "'||'", "NUMBER", "FLOAT", "INTEGER", "SIGN", "IDENTIFIER", 
		"'('", "')'", "'{'", "'}'", "';'", "','", "WHITESPACE", "SINGLE_COMMENT", 
		"MOD"
	};
	public static final int
		RULE_eval = 0, RULE_decl = 1, RULE_action = 2, RULE_unop = 3, RULE_ctrl = 4, 
		RULE_ifthenelse = 5, RULE_whiledo = 6, RULE_foreach = 7, RULE_assign = 8, 
		RULE_expr = 9, RULE_namedconst = 10, RULE_type = 11, RULE_entityspec = 12, 
		RULE_binop = 13;
	public static final String[] ruleNames = {
		"eval", "decl", "action", "unop", "ctrl", "ifthenelse", "whiledo", "foreach", 
		"assign", "expr", "namedconst", "type", "entityspec", "binop"
	};

	@Override
	public String getGrammarFileName() { return "AsteroidsParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }




	public AsteroidsParserParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EvalContext extends ParserRuleContext {
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(AsteroidsParserParser.SEMICOLON, 0); }
		public EvalContext eval() {
			return getRuleContext(EvalContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PRINT() { return getToken(AsteroidsParserParser.PRINT, 0); }
		public CtrlContext ctrl() {
			return getRuleContext(CtrlContext.class,0);
		}
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public EvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterEval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitEval(this);
		}
	}

	public final EvalContext eval() throws RecognitionException {
		EvalContext _localctx = new EvalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eval);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY) | (1L << THRUSTON) | (1L << THRUSTOFF) | (1L << TURN) | (1L << FIRE) | (1L << SKIP) | (1L << PRINT) | (1L << IF) | (1L << WHILE) | (1L << FOREACH) | (1L << IDENTIFIER) | (1L << SEMICOLON))) != 0)) {
				{
				setState(43);
				switch (_input.LA(1)) {
				case BOOL:
				case DOUBLE:
				case ENTITY:
					{
					setState(28); decl();
					setState(29); match(SEMICOLON);
					}
					break;
				case THRUSTON:
				case THRUSTOFF:
				case TURN:
				case FIRE:
				case SKIP:
					{
					setState(31); action();
					setState(32); match(SEMICOLON);
					}
					break;
				case IDENTIFIER:
					{
					setState(34); assign();
					setState(35); match(SEMICOLON);
					}
					break;
				case PRINT:
					{
					setState(37); match(PRINT);
					setState(38); expr(0);
					setState(39); match(SEMICOLON);
					}
					break;
				case IF:
				case WHILE:
				case FOREACH:
					{
					setState(41); ctrl();
					}
					break;
				case SEMICOLON:
					{
					setState(42); match(SEMICOLON);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(46);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(45); eval();
					}
					break;
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(AsteroidsParserParser.IDENTIFIER, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(AsteroidsParserParser.ASSIGN, 0); }
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50); type();
			setState(51); match(IDENTIFIER);
			setState(54);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(52); match(ASSIGN);
				setState(53); expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionContext extends ParserRuleContext {
		public TerminalNode FIRE() { return getToken(AsteroidsParserParser.FIRE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode TURN() { return getToken(AsteroidsParserParser.TURN, 0); }
		public TerminalNode THRUSTOFF() { return getToken(AsteroidsParserParser.THRUSTOFF, 0); }
		public TerminalNode THRUSTON() { return getToken(AsteroidsParserParser.THRUSTON, 0); }
		public TerminalNode SKIP() { return getToken(AsteroidsParserParser.SKIP, 0); }
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitAction(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_action);
		try {
			setState(62);
			switch (_input.LA(1)) {
			case THRUSTON:
				enterOuterAlt(_localctx, 1);
				{
				setState(56); match(THRUSTON);
				}
				break;
			case THRUSTOFF:
				enterOuterAlt(_localctx, 2);
				{
				setState(57); match(THRUSTOFF);
				}
				break;
			case TURN:
				enterOuterAlt(_localctx, 3);
				{
				setState(58); match(TURN);
				setState(59); expr(0);
				}
				break;
			case FIRE:
				enterOuterAlt(_localctx, 4);
				{
				setState(60); match(FIRE);
				}
				break;
			case SKIP:
				enterOuterAlt(_localctx, 5);
				{
				setState(61); match(SKIP);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnopContext extends ParserRuleContext {
		public TerminalNode GETVY() { return getToken(AsteroidsParserParser.GETVY, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(AsteroidsParserParser.LEFT_PAREN, 0); }
		public TerminalNode GETVX() { return getToken(AsteroidsParserParser.GETVX, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(AsteroidsParserParser.RIGHT_PAREN, 0); }
		public TerminalNode NOT() { return getToken(AsteroidsParserParser.NOT, 0); }
		public TerminalNode SIN() { return getToken(AsteroidsParserParser.SIN, 0); }
		public TerminalNode SQRT() { return getToken(AsteroidsParserParser.SQRT, 0); }
		public TerminalNode GETRADIUS() { return getToken(AsteroidsParserParser.GETRADIUS, 0); }
		public TerminalNode GETY() { return getToken(AsteroidsParserParser.GETY, 0); }
		public TerminalNode GETX() { return getToken(AsteroidsParserParser.GETX, 0); }
		public TerminalNode COS() { return getToken(AsteroidsParserParser.COS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterUnop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitUnop(this);
		}
	}

	public final UnopContext unop() throws RecognitionException {
		UnopContext _localctx = new UnopContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_unop);
		try {
			setState(91);
			switch (_input.LA(1)) {
			case GETRADIUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(64); match(GETRADIUS);
				setState(65); expr(0);
				}
				break;
			case GETX:
				enterOuterAlt(_localctx, 2);
				{
				setState(66); match(GETX);
				setState(67); expr(0);
				}
				break;
			case GETY:
				enterOuterAlt(_localctx, 3);
				{
				setState(68); match(GETY);
				setState(69); expr(0);
				}
				break;
			case GETVX:
				enterOuterAlt(_localctx, 4);
				{
				setState(70); match(GETVX);
				setState(71); expr(0);
				}
				break;
			case GETVY:
				enterOuterAlt(_localctx, 5);
				{
				setState(72); match(GETVY);
				setState(73); expr(0);
				}
				break;
			case SQRT:
				enterOuterAlt(_localctx, 6);
				{
				setState(74); match(SQRT);
				setState(75); match(LEFT_PAREN);
				setState(76); expr(0);
				setState(77); match(RIGHT_PAREN);
				}
				break;
			case SIN:
				enterOuterAlt(_localctx, 7);
				{
				setState(79); match(SIN);
				setState(80); match(LEFT_PAREN);
				setState(81); expr(0);
				setState(82); match(RIGHT_PAREN);
				}
				break;
			case COS:
				enterOuterAlt(_localctx, 8);
				{
				setState(84); match(COS);
				setState(85); match(LEFT_PAREN);
				setState(86); expr(0);
				setState(87); match(RIGHT_PAREN);
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 9);
				{
				setState(89); match(NOT);
				setState(90); expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CtrlContext extends ParserRuleContext {
		public ForeachContext foreach() {
			return getRuleContext(ForeachContext.class,0);
		}
		public WhiledoContext whiledo() {
			return getRuleContext(WhiledoContext.class,0);
		}
		public IfthenelseContext ifthenelse() {
			return getRuleContext(IfthenelseContext.class,0);
		}
		public CtrlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ctrl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterCtrl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitCtrl(this);
		}
	}

	public final CtrlContext ctrl() throws RecognitionException {
		CtrlContext _localctx = new CtrlContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ctrl);
		try {
			setState(96);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(93); ifthenelse();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 2);
				{
				setState(94); whiledo();
				}
				break;
			case FOREACH:
				enterOuterAlt(_localctx, 3);
				{
				setState(95); foreach();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfthenelseContext extends ParserRuleContext {
		public List<TerminalNode> RIGHT_BRACE() { return getTokens(AsteroidsParserParser.RIGHT_BRACE); }
		public EvalContext eval(int i) {
			return getRuleContext(EvalContext.class,i);
		}
		public TerminalNode THEN() { return getToken(AsteroidsParserParser.THEN, 0); }
		public List<EvalContext> eval() {
			return getRuleContexts(EvalContext.class);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> LEFT_BRACE() { return getTokens(AsteroidsParserParser.LEFT_BRACE); }
		public TerminalNode RIGHT_BRACE(int i) {
			return getToken(AsteroidsParserParser.RIGHT_BRACE, i);
		}
		public TerminalNode ELSE() { return getToken(AsteroidsParserParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(AsteroidsParserParser.IF, 0); }
		public TerminalNode LEFT_BRACE(int i) {
			return getToken(AsteroidsParserParser.LEFT_BRACE, i);
		}
		public IfthenelseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifthenelse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterIfthenelse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitIfthenelse(this);
		}
	}

	public final IfthenelseContext ifthenelse() throws RecognitionException {
		IfthenelseContext _localctx = new IfthenelseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_ifthenelse);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); match(IF);
			setState(99); expr(0);
			setState(101);
			_la = _input.LA(1);
			if (_la==THEN) {
				{
				setState(100); match(THEN);
				}
			}

			setState(103); match(LEFT_BRACE);
			setState(104); eval();
			setState(105); match(RIGHT_BRACE);
			setState(111);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(106); match(ELSE);
				setState(107); match(LEFT_BRACE);
				setState(108); eval();
				setState(109); match(RIGHT_BRACE);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiledoContext extends ParserRuleContext {
		public TerminalNode RIGHT_BRACE() { return getToken(AsteroidsParserParser.RIGHT_BRACE, 0); }
		public TerminalNode DO() { return getToken(AsteroidsParserParser.DO, 0); }
		public EvalContext eval() {
			return getRuleContext(EvalContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(AsteroidsParserParser.WHILE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LEFT_BRACE() { return getToken(AsteroidsParserParser.LEFT_BRACE, 0); }
		public WhiledoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiledo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterWhiledo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitWhiledo(this);
		}
	}

	public final WhiledoContext whiledo() throws RecognitionException {
		WhiledoContext _localctx = new WhiledoContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_whiledo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113); match(WHILE);
			setState(114); expr(0);
			setState(116);
			_la = _input.LA(1);
			if (_la==DO) {
				{
				setState(115); match(DO);
				}
			}

			setState(118); match(LEFT_BRACE);
			setState(119); eval();
			setState(120); match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForeachContext extends ParserRuleContext {
		public TerminalNode RIGHT_BRACE() { return getToken(AsteroidsParserParser.RIGHT_BRACE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(AsteroidsParserParser.LEFT_PAREN, 0); }
		public TerminalNode DO() { return getToken(AsteroidsParserParser.DO, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(AsteroidsParserParser.RIGHT_PAREN, 0); }
		public TerminalNode FOREACH() { return getToken(AsteroidsParserParser.FOREACH, 0); }
		public EvalContext eval() {
			return getRuleContext(EvalContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(AsteroidsParserParser.COMMA, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(AsteroidsParserParser.LEFT_BRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(AsteroidsParserParser.IDENTIFIER, 0); }
		public EntityspecContext entityspec() {
			return getRuleContext(EntityspecContext.class,0);
		}
		public ForeachContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreach; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterForeach(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitForeach(this);
		}
	}

	public final ForeachContext foreach() throws RecognitionException {
		ForeachContext _localctx = new ForeachContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_foreach);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122); match(FOREACH);
			setState(123); match(LEFT_PAREN);
			setState(124); entityspec();
			setState(125); match(COMMA);
			setState(126); match(IDENTIFIER);
			setState(127); match(RIGHT_PAREN);
			setState(129);
			_la = _input.LA(1);
			if (_la==DO) {
				{
				setState(128); match(DO);
				}
			}

			setState(131); match(LEFT_BRACE);
			setState(132); eval();
			setState(133); match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(AsteroidsParserParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(AsteroidsParserParser.ASSIGN, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitAssign(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135); match(IDENTIFIER);
			setState(136); match(ASSIGN);
			setState(137); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public int _p;
		public TerminalNode LEFT_PAREN() { return getToken(AsteroidsParserParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(AsteroidsParserParser.RIGHT_PAREN, 0); }
		public UnopContext unop() {
			return getRuleContext(UnopContext.class,0);
		}
		public TerminalNode GETDIR() { return getToken(AsteroidsParserParser.GETDIR, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public NamedconstContext namedconst() {
			return getRuleContext(NamedconstContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode IDENTIFIER() { return getToken(AsteroidsParserParser.IDENTIFIER, 0); }
		public BinopContext binop() {
			return getRuleContext(BinopContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(AsteroidsParserParser.NUMBER, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState, _p);
		ExprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			switch (_input.LA(1)) {
			case NUMBER:
				{
				setState(140); match(NUMBER);
				}
				break;
			case IDENTIFIER:
				{
				setState(141); match(IDENTIFIER);
				}
				break;
			case GETDIR:
				{
				setState(142); match(GETDIR);
				}
				break;
			case LEFT_PAREN:
				{
				setState(143); match(LEFT_PAREN);
				setState(144); expr(0);
				setState(145); match(RIGHT_PAREN);
				}
				break;
			case SELF:
			case TRUE:
			case FALSE:
			case NULL:
				{
				setState(147); namedconst();
				}
				break;
			case GETRADIUS:
			case GETX:
			case GETY:
			case GETVX:
			case GETVY:
			case SQRT:
			case SIN:
			case COS:
			case NOT:
				{
				setState(148); unop();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(157);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExprContext(_parentctx, _parentState, _p);
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(151);
					if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
					setState(152); binop();
					setState(153); expr(0);
					}
					} 
				}
				setState(159);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NamedconstContext extends ParserRuleContext {
		public TerminalNode FALSE() { return getToken(AsteroidsParserParser.FALSE, 0); }
		public TerminalNode TRUE() { return getToken(AsteroidsParserParser.TRUE, 0); }
		public TerminalNode SELF() { return getToken(AsteroidsParserParser.SELF, 0); }
		public TerminalNode NULL() { return getToken(AsteroidsParserParser.NULL, 0); }
		public NamedconstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedconst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterNamedconst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitNamedconst(this);
		}
	}

	public final NamedconstContext namedconst() throws RecognitionException {
		NamedconstContext _localctx = new NamedconstContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_namedconst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SELF) | (1L << TRUE) | (1L << FALSE) | (1L << NULL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode DOUBLE() { return getToken(AsteroidsParserParser.DOUBLE, 0); }
		public TerminalNode ENTITY() { return getToken(AsteroidsParserParser.ENTITY, 0); }
		public TerminalNode BOOL() { return getToken(AsteroidsParserParser.BOOL, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntityspecContext extends ParserRuleContext {
		public TerminalNode ANY() { return getToken(AsteroidsParserParser.ANY, 0); }
		public TerminalNode BULLET() { return getToken(AsteroidsParserParser.BULLET, 0); }
		public TerminalNode SHIP() { return getToken(AsteroidsParserParser.SHIP, 0); }
		public TerminalNode ASTEROID() { return getToken(AsteroidsParserParser.ASTEROID, 0); }
		public EntityspecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entityspec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterEntityspec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitEntityspec(this);
		}
	}

	public final EntityspecContext entityspec() throws RecognitionException {
		EntityspecContext _localctx = new EntityspecContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_entityspec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SHIP) | (1L << ASTEROID) | (1L << BULLET) | (1L << ANY))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinopContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(AsteroidsParserParser.LT, 0); }
		public TerminalNode NEQ() { return getToken(AsteroidsParserParser.NEQ, 0); }
		public TerminalNode MOD() { return getToken(AsteroidsParserParser.MOD, 0); }
		public TerminalNode OR() { return getToken(AsteroidsParserParser.OR, 0); }
		public TerminalNode GT() { return getToken(AsteroidsParserParser.GT, 0); }
		public TerminalNode SUB() { return getToken(AsteroidsParserParser.SUB, 0); }
		public TerminalNode GEQ() { return getToken(AsteroidsParserParser.GEQ, 0); }
		public TerminalNode DIV() { return getToken(AsteroidsParserParser.DIV, 0); }
		public TerminalNode EQ() { return getToken(AsteroidsParserParser.EQ, 0); }
		public TerminalNode AND() { return getToken(AsteroidsParserParser.AND, 0); }
		public TerminalNode MUL() { return getToken(AsteroidsParserParser.MUL, 0); }
		public TerminalNode ADD() { return getToken(AsteroidsParserParser.ADD, 0); }
		public TerminalNode LEQ() { return getToken(AsteroidsParserParser.LEQ, 0); }
		public BinopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).enterBinop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsteroidsParserListener ) ((AsteroidsParserListener)listener).exitBinop(this);
		}
	}

	public final BinopContext binop() throws RecognitionException {
		BinopContext _localctx = new BinopContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_binop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << ADD) | (1L << SUB) | (1L << EQ) | (1L << NEQ) | (1L << LT) | (1L << GT) | (1L << LEQ) | (1L << GEQ) | (1L << AND) | (1L << OR) | (1L << MOD))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return 1 >= _localctx._p;
		}
		return true;
	}

	public static final String _serializedATN =
		"\2\3>\u00ab\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2.\n\2\3\2\5\2\61"+
		"\n\2\5\2\63\n\2\3\3\3\3\3\3\3\3\5\39\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4A"+
		"\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5^\n\5\3\6\3\6\3\6\5\6c\n"+
		"\6\3\7\3\7\3\7\5\7h\n\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7r\n\7\3\b\3"+
		"\b\3\b\5\bw\n\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0084"+
		"\n\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\5\13\u0098\n\13\3\13\3\13\3\13\3\13\7\13\u009e\n\13\f"+
		"\13\16\13\u00a1\13\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\2\20\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\32\34\2\6\3\3\6\3\7\t\3\n\r\4%\60>>\u00bc"+
		"\2\62\3\2\2\2\4\64\3\2\2\2\6@\3\2\2\2\b]\3\2\2\2\nb\3\2\2\2\fd\3\2\2\2"+
		"\16s\3\2\2\2\20|\3\2\2\2\22\u0089\3\2\2\2\24\u0097\3\2\2\2\26\u00a2\3"+
		"\2\2\2\30\u00a4\3\2\2\2\32\u00a6\3\2\2\2\34\u00a8\3\2\2\2\36\37\5\4\3"+
		"\2\37 \7:\2\2 .\3\2\2\2!\"\5\6\4\2\"#\7:\2\2#.\3\2\2\2$%\5\22\n\2%&\7"+
		":\2\2&.\3\2\2\2\'(\7\35\2\2()\5\24\13\2)*\7:\2\2*.\3\2\2\2+.\5\n\6\2,"+
		".\7:\2\2-\36\3\2\2\2-!\3\2\2\2-$\3\2\2\2-\'\3\2\2\2-+\3\2\2\2-,\3\2\2"+
		"\2.\60\3\2\2\2/\61\5\2\2\2\60/\3\2\2\2\60\61\3\2\2\2\61\63\3\2\2\2\62"+
		"-\3\2\2\2\62\63\3\2\2\2\63\3\3\2\2\2\64\65\5\30\r\2\658\7\65\2\2\66\67"+
		"\7$\2\2\679\5\24\13\28\66\3\2\2\289\3\2\2\29\5\3\2\2\2:A\7\30\2\2;A\7"+
		"\31\2\2<=\7\32\2\2=A\5\24\13\2>A\7\33\2\2?A\7\34\2\2@:\3\2\2\2@;\3\2\2"+
		"\2@<\3\2\2\2@>\3\2\2\2@?\3\2\2\2A\7\3\2\2\2BC\7\16\2\2C^\5\24\13\2DE\7"+
		"\17\2\2E^\5\24\13\2FG\7\20\2\2G^\5\24\13\2HI\7\21\2\2I^\5\24\13\2JK\7"+
		"\22\2\2K^\5\24\13\2LM\7\24\2\2MN\7\66\2\2NO\5\24\13\2OP\7\67\2\2P^\3\2"+
		"\2\2QR\7\25\2\2RS\7\66\2\2ST\5\24\13\2TU\7\67\2\2U^\3\2\2\2VW\7\26\2\2"+
		"WX\7\66\2\2XY\5\24\13\2YZ\7\67\2\2Z^\3\2\2\2[\\\7\27\2\2\\^\5\24\13\2"+
		"]B\3\2\2\2]D\3\2\2\2]F\3\2\2\2]H\3\2\2\2]J\3\2\2\2]L\3\2\2\2]Q\3\2\2\2"+
		"]V\3\2\2\2][\3\2\2\2^\t\3\2\2\2_c\5\f\7\2`c\5\16\b\2ac\5\20\t\2b_\3\2"+
		"\2\2b`\3\2\2\2ba\3\2\2\2c\13\3\2\2\2de\7\36\2\2eg\5\24\13\2fh\7\37\2\2"+
		"gf\3\2\2\2gh\3\2\2\2hi\3\2\2\2ij\78\2\2jk\5\2\2\2kq\79\2\2lm\7 \2\2mn"+
		"\78\2\2no\5\2\2\2op\79\2\2pr\3\2\2\2ql\3\2\2\2qr\3\2\2\2r\r\3\2\2\2st"+
		"\7!\2\2tv\5\24\13\2uw\7\"\2\2vu\3\2\2\2vw\3\2\2\2wx\3\2\2\2xy\78\2\2y"+
		"z\5\2\2\2z{\79\2\2{\17\3\2\2\2|}\7#\2\2}~\7\66\2\2~\177\5\32\16\2\177"+
		"\u0080\7;\2\2\u0080\u0081\7\65\2\2\u0081\u0083\7\67\2\2\u0082\u0084\7"+
		"\"\2\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\u0086\78\2\2\u0086\u0087\5\2\2\2\u0087\u0088\79\2\2\u0088\21\3\2\2\2"+
		"\u0089\u008a\7\65\2\2\u008a\u008b\7$\2\2\u008b\u008c\5\24\13\2\u008c\23"+
		"\3\2\2\2\u008d\u008e\b\13\1\2\u008e\u0098\7\61\2\2\u008f\u0098\7\65\2"+
		"\2\u0090\u0098\7\23\2\2\u0091\u0092\7\66\2\2\u0092\u0093\5\24\13\2\u0093"+
		"\u0094\7\67\2\2\u0094\u0098\3\2\2\2\u0095\u0098\5\26\f\2\u0096\u0098\5"+
		"\b\5\2\u0097\u008d\3\2\2\2\u0097\u008f\3\2\2\2\u0097\u0090\3\2\2\2\u0097"+
		"\u0091\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0096\3\2\2\2\u0098\u009f\3\2"+
		"\2\2\u0099\u009a\6\13\2\3\u009a\u009b\5\34\17\2\u009b\u009c\5\24\13\2"+
		"\u009c\u009e\3\2\2\2\u009d\u0099\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d"+
		"\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\25\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2"+
		"\u00a3\t\2\2\2\u00a3\27\3\2\2\2\u00a4\u00a5\t\3\2\2\u00a5\31\3\2\2\2\u00a6"+
		"\u00a7\t\4\2\2\u00a7\33\3\2\2\2\u00a8\u00a9\t\5\2\2\u00a9\35\3\2\2\2\17"+
		"-\60\628@]bgqv\u0083\u0097\u009f";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}