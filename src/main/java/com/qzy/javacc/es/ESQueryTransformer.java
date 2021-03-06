/* ESQueryTransformer.java */
/* Generated By:JavaCC: Do not edit this line. ESQueryTransformer.java */
package com.qzy.javacc.es;

import java.io.Reader;
import java.io.StringReader;

public class ESQueryTransformer implements ESQueryTransformerConstants {

    public static String logicalToQueryString(String input) {

        Reader reader = new StringReader(input);
        ESQueryTransformer transformer = new ESQueryTransformer(reader);
        StringBuffer buffer = new StringBuffer();

        try {
            transformer.doTransform(buffer);
        } catch (TokenMgrError e) {
            e.printStackTrace();
            throw new IllegalStateException();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
        return buffer.toString();
    }

    public String dealQuote(String inString) {
        StringBuffer buffer = new StringBuffer();
        String[] items = inString.split(",");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (item.startsWith("\'")) {
                item = "\\\'" + item.substring(1, item.length() - 1) + "\\\'";
            } else if (item.startsWith("\"")) {
                item = "\\\"" + item.substring(1, item.length() - 1) + "\\\"";
            }
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append(item);
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String text = "trx_amt between 123 and 890 and (city not in (\"\u6d93\u5a43\u6363\",\"\u947b\u5fd3\u7a9e\") or trx_cnt <= 345 and trx_cnt != 334)";
        String queryString = logicalToQueryString(text);
        System.out.println(queryString);
    }

    final public void doTransform(StringBuffer buffer) throws ParseException {
        String value;
        label_1:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case LPAREN:
                case IDENTIFIER: {
                    ;
                    break;
                }
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            value = expression();
            buffer.append(value);
        }
        jj_consume_token(0);
    }

    final public String expression() throws ParseException {
        String value;
        StringBuffer buffer = new StringBuffer();
        Token t;
        value = term();
        buffer.append(value);
        label_2:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case AND:
                case OR: {
                    ;
                    break;
                }
                default:
                    jj_la1[1] = jj_gen;
                    break label_2;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case AND: {
                    t = jj_consume_token(AND);
                    break;
                }
                case OR: {
                    t = jj_consume_token(OR);
                    break;
                }
                default:
                    jj_la1[2] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            value = term();
            buffer.append(" " + t.image.toUpperCase() + " ");
            buffer.append(value);
        }
        {
            if ("" != null) return buffer.toString();
        }
        throw new Error("Missing return statement in function");
    }

    final public String term() throws ParseException {
        String p;
        String exp;
        StringBuffer buffer = new StringBuffer();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case IDENTIFIER: {
                p = primary();
                buffer.append(p);
                {
                    if ("" != null) return buffer.toString();
                }
                break;
            }
            case LPAREN: {
                jj_consume_token(LPAREN);
                exp = expression();
                jj_consume_token(RPAREN);
                buffer.append("(" + exp + ")");
                {
                    if ("" != null) return buffer.toString();
                }
                break;
            }
            default:
                jj_la1[3] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    final public String primary() throws ParseException {
        Token t1;
        Token t2 = null;
        Token t3;
        Token t4;
        String primary;
        if (jj_2_1(2)) {
            t1 = jj_consume_token(IDENTIFIER);
            t2 = jj_consume_token(OPERATOR);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case IDENTIFIER: {
                    t3 = jj_consume_token(IDENTIFIER);
                    break;
                }
                case OPERAND: {
                    t3 = jj_consume_token(OPERAND);
                    break;
                }
                default:
                    jj_la1[4] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            String tmp = dealQuote(t3.image);
            switch (t2.image) {
                case "=":
                    primary = t1.image + ":" + tmp;
                    break;
                case "!=":
                    primary = "NOT " + t1.image + ":" + tmp;
                    break;
                default:
                    primary = t1.image + ":" + t2.image + tmp;
                    break;
            }
            {
                if ("" != null) return "(" + primary + ")";
            }
        } else if (jj_2_2(2)) {
            t1 = jj_consume_token(IDENTIFIER);
            jj_consume_token(BETWEEN);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case IDENTIFIER: {
                    t3 = jj_consume_token(IDENTIFIER);
                    break;
                }
                case OPERAND: {
                    t3 = jj_consume_token(OPERAND);
                    break;
                }
                default:
                    jj_la1[5] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            jj_consume_token(AND);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case IDENTIFIER: {
                    t4 = jj_consume_token(IDENTIFIER);
                    break;
                }
                case OPERAND: {
                    t4 = jj_consume_token(OPERAND);
                    break;
                }
                default:
                    jj_la1[6] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            String operand1 = dealQuote(t3.image);
            String operand2 = dealQuote(t4.image);
            {
                if ("" != null) return "(" + t1.image + ":[" + operand1 + " TO " + operand2 + "])";
            }
        } else if (jj_2_3(3)) {
            t1 = jj_consume_token(IDENTIFIER);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case NOT: {
                    t2 = jj_consume_token(NOT);
                    break;
                }
                default:
                    jj_la1[7] = jj_gen;
                    ;
            }
            jj_consume_token(IN);
            StringBuffer buffer = new StringBuffer();
            if (null == t2 || t2.image.equals("")) {
                buffer.append(t1.image + " : ");
            } else {
                buffer.append("NOT " + t1.image + " : ");
            }
            jj_consume_token(LPAREN);
            buffer.append("(");
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case IDENTIFIER: {
                    t3 = jj_consume_token(IDENTIFIER);
                    break;
                }
                case OPERAND: {
                    t3 = jj_consume_token(OPERAND);
                    break;
                }
                default:
                    jj_la1[8] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            buffer.append(dealQuote(t3.image));
            label_3:
            while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case COMMA: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[9] = jj_gen;
                        break label_3;
                }
                jj_consume_token(COMMA);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case IDENTIFIER: {
                        t3 = jj_consume_token(IDENTIFIER);
                        break;
                    }
                    case OPERAND: {
                        t3 = jj_consume_token(OPERAND);
                        break;
                    }
                    default:
                        jj_la1[10] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                buffer.append("," + dealQuote(t3.image));
            }
            jj_consume_token(RPAREN);
            buffer.append(")");
            {
                if ("" != null) return buffer.toString();
            }
        } else if (jj_2_4(3)) {
            t1 = jj_consume_token(IDENTIFIER);
            jj_consume_token(IS);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case NOT: {
                    t2 = jj_consume_token(NOT);
                    break;
                }
                default:
                    jj_la1[11] = jj_gen;
                    ;
            }
            jj_consume_token(NULL);
            if (null == t2 || t2.image.equals("")) {
                {
                    if ("" != null) return "(NOT _exists_: " + t1.image + ")";
                }
            } else {
                {
                    if ("" != null) return "(_exists_: " + t1.image + ")";
                }
            }
        } else {
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    private boolean jj_2_1(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_1());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(0, xla);
        }
    }

    private boolean jj_2_2(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_2());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(1, xla);
        }
    }

    private boolean jj_2_3(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_3());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(2, xla);
        }
    }

    private boolean jj_2_4(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_4());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(3, xla);
        }
    }

    private boolean jj_3_3() {
        if (jj_scan_token(IDENTIFIER)) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(9)) jj_scanpos = xsp;
        if (jj_scan_token(IN)) return true;
        if (jj_scan_token(LPAREN)) return true;
        return false;
    }

    private boolean jj_3_1() {
        if (jj_scan_token(IDENTIFIER)) return true;
        if (jj_scan_token(OPERATOR)) return true;
        return false;
    }

    private boolean jj_3_2() {
        if (jj_scan_token(IDENTIFIER)) return true;
        if (jj_scan_token(BETWEEN)) return true;
        return false;
    }

    private boolean jj_3_4() {
        if (jj_scan_token(IDENTIFIER)) return true;
        if (jj_scan_token(IS)) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(9)) jj_scanpos = xsp;
        if (jj_scan_token(NULL)) return true;
        return false;
    }

    /**
     * Generated Token Manager.
     */
    public ESQueryTransformerTokenManager token_source;
    SimpleCharStream jj_input_stream;
    /**
     * Current token.
     */
    public Token token;
    /**
     * Next token.
     */
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos, jj_lastpos;
    private int jj_la;
    private int jj_gen;
    final private int[] jj_la1 = new int[12];
    static private int[] jj_la1_0;

    static {
        jj_la1_init_0();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{0x11000, 0xc000, 0xc000, 0x11000, 0x110000, 0x110000, 0x110000, 0x200, 0x110000, 0x800, 0x110000, 0x200,};
    }

    final private JJCalls[] jj_2_rtns = new JJCalls[4];
    private boolean jj_rescan = false;
    private int jj_gc = 0;

    /**
     * Constructor with InputStream.
     */
    public ESQueryTransformer(java.io.InputStream stream) {
        this(stream, null);
    }

    /**
     * Constructor with InputStream and supplied encoding
     */
    public ESQueryTransformer(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new ESQueryTransformerTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 12; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 12; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Constructor.
     */
    public ESQueryTransformer(java.io.Reader stream) {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new ESQueryTransformerTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 12; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.Reader stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new SimpleCharStream(stream, 1, 1);
        } else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new ESQueryTransformerTokenManager(jj_input_stream);
        }

        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 12; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Constructor with generated Token Manager.
     */
    public ESQueryTransformer(ESQueryTransformerTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 12; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Reinitialise.
     */
    public void ReInit(ESQueryTransformerTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 12; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++) {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < jj_gen) c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    @SuppressWarnings("serial")
    static private final class LookaheadSuccess extends java.lang.Error {
        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    static private final LookaheadSuccess jj_ls = new LookaheadSuccess();

    private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0;
            Token tok = token;
            while (tok != null && tok != jj_scanpos) {
                i++;
                tok = tok.next;
            }
            if (tok != null) jj_add_error_token(kind, i);
        }
        if (jj_scanpos.kind != kind) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
        return false;
    }


    /**
     * Get the next Token.
     */
    final public Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /**
     * Get the specific Token.
     */
    final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    private int jj_ntk_f() {
        if ((jj_nt = token.next) == null)
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }

    private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) {
            return;
        }

        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];

            for (int i = 0; i < jj_endpos; i++) {
                jj_expentry[i] = jj_lasttokens[i];
            }

            for (int[] oldentry : jj_expentries) {
                if (oldentry.length == jj_expentry.length) {
                    boolean isMatched = true;

                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            isMatched = false;
                            break;
                        }

                    }
                    if (isMatched) {
                        jj_expentries.add(jj_expentry);
                        break;
                    }
                }
            }

            if (pos != 0) {
                jj_lasttokens[(jj_endpos = pos) - 1] = kind;
            }
        }
    }

    /**
     * Generate ParseException.
     */
    public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[23];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 12; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 23; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    private boolean trace_enabled;

    /**
     * Trace enabled.
     */
    final public boolean trace_enabled() {
        return trace_enabled;
    }

    /**
     * Enable tracing.
     */
    final public void enable_tracing() {
    }

    /**
     * Disable tracing.
     */
    final public void disable_tracing() {
    }

    private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 4; i++) {
            try {
                JJCalls p = jj_2_rtns[i];

                do {
                    if (p.gen > jj_gen) {
                        jj_la = p.arg;
                        jj_lastpos = jj_scanpos = p.first;
                        switch (i) {
                            case 0:
                                jj_3_1();
                                break;
                            case 1:
                                jj_3_2();
                                break;
                            case 2:
                                jj_3_3();
                                break;
                            case 3:
                                jj_3_4();
                                break;
                        }
                    }
                    p = p.next;
                } while (p != null);

            } catch (LookaheadSuccess ls) {
            }
        }
        jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }

        p.gen = jj_gen + xla - jj_la;
        p.first = token;
        p.arg = xla;
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }

}
