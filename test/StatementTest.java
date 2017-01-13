import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Grace Rhodes
 * @author Aishwarya Srivastava
 *
 */
public abstract class StatementTest {

    /**
     * The name of a file containing a sequence of BL statements.
     */
    private static final String FILE_NAME_1 = "test/statement1.bl",
            FILE_NAME_2 = "test/statement2.bl",

            EMPTY = "test/statementEmpty.bl",
            PRIMITIVE_CALL = "test/statementPrimitiveCall.bl",
            USER_DEFINED_CALL = "test/statementUserDefinedCall.bl",
            INVALID_CALL_IDENTIFIER = "test/statementCallInvalidIdentifier.bl",

            CORRECT_IF = "test/statementCorrectIf.bl",
            IF_MISSING_IF = "test/statementIfMissingIF.bl",
            IF_MISSING_THEN = "test/statementIfMissingTHEN.bl",
            IF_MISSING_CONDITION = "test/statementIfMissingCondition.bl",
            IF_INVALID_CONDITION = "test/statementIfInvalidCondition.bl",
            IF_EMPTY_THEN = "test/statementIfEmptyTHEN.bl",
            IF_MISSING_END = "test/statementIfMissingEND.bl",
            IF_MISSING_END_IF = "test/statementIfMissingEndIf.bl",

            CORRECT_IF_ELSE = "test/statementIfElseTHENnonEmptyELSEnonEmpty.bl",
            IF_ELSE_BOTH_EMPTY = "test/statementIfElseTHENemptyELSEempty.bl",
            IF_ELSE_EMPTY_THEN = "test/statementIfElseEmptyTHEN.bl",
            IF_ELSE_EMPTY_ELSE = "test/statementIfElseEmptyELSE.bl",
            IF_ELSE_MISSING_IF = "test/statementIfElseMissingIF.bl",
            IF_ELSE_MISSING_CONDITION = "test/statementIfElseMissingCondition.bl",
            IF_ELSE_INVALID_CONDITION = "test/statementIfElseInvalidCondition.bl",
            IF_ELSE_MISSING_THEN = "test/statementIfElseMissingTHEN.bl",
            IF_ELSE_MISSING_END = "test/statementIfElseMissingEND.bl",
            IF_ELSE_MISSING_END_IF = "test/statementIfElseMissingEndIF.bl",

            CORRECT_WHILE = "test/statementCorrectWhile.bl",
            EMPTY_DO = "test/statementWhileEmptyDO.bl",
            WHILE_MISSING_WHILE = "test/statementWhileMissingWHILE.bl",
            WHILE_INVALID_CONDITION = "test/statementWhileInvalidCondition.bl",
            WHILE_MISSING_CONDITION = "test/statementWhileMissingCondition.bl",
            WHILE_MISSING_DO = "test/statementWhileMissingDO.bl",
            WHILE_MISSING_END = "test/statementWhileMissingEND.bl",
            WHILE_MISSING_END_WHILE = "test/statementWhileMissingEndWHILE.bl",

            BLOCK_ONLY_CALLS = "test/statementValidBlock_OnlyCalls.bl",
            BLOCK_ONE_OF_EACH = "test/statementBlock_OneOfEach.bl";

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructorTest = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructorRef = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(EMPTY);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /*
     * **********************Testing call parser***************************
     */

    @Test
    public final void testParsePrimitiveCall() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_CALL);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(PRIMITIVE_CALL);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseUserDefinedCall() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(USER_DEFINED_CALL);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(USER_DEFINED_CALL);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseInvalidCallIdentifier() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INVALID_CALL_IDENTIFIER);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /*
     * ******************Testing Block Parser*********************************
     */
    @Test
    public final void testParseEmptyBlock() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(EMPTY);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(EMPTY);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
    }

    @Test
    public final void testParseBlock_singleCall() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_CALL);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(PRIMITIVE_CALL);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
    }

    @Test
    public final void testParseBlockOnlyCalls() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(BLOCK_ONLY_CALLS);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(BLOCK_ONLY_CALLS);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseBlockHavingOneOfEach() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(BLOCK_ONE_OF_EACH);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(BLOCK_ONE_OF_EACH);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /*
     * ********************Testing IF parser*****************************
     */

    @Test
    public final void testParseIfEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(IF_EMPTY_THEN);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(IF_EMPTY_THEN);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseIfNonEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(CORRECT_IF);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(CORRECT_IF);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfMissingIF() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_MISSING_IF);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfMissingTHEN() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_MISSING_THEN);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfMissingCondition() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_MISSING_CONDITION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfInvalidCondition() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_INVALID_CONDITION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfMissingEND() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_MISSING_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfMissingEndIF() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_MISSING_END_IF);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /*
     * ********************Testing IF-ELSE Parser*****************************
     */
    @Test
    public final void testParseIfElseBothNonEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(CORRECT_IF_ELSE);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(CORRECT_IF_ELSE);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseIfElseBothEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(IF_ELSE_BOTH_EMPTY);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(IF_ELSE_BOTH_EMPTY);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseIfElse_NonEmptyThenEmptyElse() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(IF_ELSE_EMPTY_ELSE);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(IF_ELSE_EMPTY_ELSE);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseIfElse_EmptyThenNonEmptyElse() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(IF_ELSE_EMPTY_THEN);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(IF_ELSE_EMPTY_THEN);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfElseMissingIF() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_ELSE_MISSING_IF);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfElseMissingTHEN() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_ELSE_MISSING_THEN);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfElseMissingCondition() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_ELSE_MISSING_CONDITION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfElseInvalidCondition() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_ELSE_INVALID_CONDITION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfElseMissingEND() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_ELSE_MISSING_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseIfElseMissingEndIF() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(IF_ELSE_MISSING_END_IF);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /*
     * ****************************Testing WHILE Parser***********************
     */
    @Test
    public final void testParseWhileEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(EMPTY_DO);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(EMPTY_DO);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testParseWhileNonEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(CORRECT_WHILE);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(CORRECT_WHILE);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
    }

    @Test(expected = RuntimeException.class)
    public final void testParseWhileMissingWHILE() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(WHILE_MISSING_WHILE);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseWhileMissingDO() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(WHILE_MISSING_DO);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseWhileMissingCondition() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(WHILE_MISSING_CONDITION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseWhileInvalidCondition() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(WHILE_INVALID_CONDITION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseWhileMissingEND() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(WHILE_MISSING_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseWhileMissingEndWHILE() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(WHILE_MISSING_END_WHILE);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }
}
