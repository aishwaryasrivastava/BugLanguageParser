import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.program.Program;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class ProgramTest {

    /**
     * The names of a files containing a (possibly invalid) BL programs.
     */
    private static final String FILE_NAME_1 = "test/program1.bl",
            FILE_NAME_2 = "test/program2.bl",
            PROGRAM_NO_INSTR = "test/programNoInstructions.bl",
            PROGRAM_NO_BODY = "test/programNoBody.bl",
            PROGRAM_EMPTY = "test/programEmpty.bl",
            NO_INSTR = "test/programNoInstr.bl",
            NO_IS_INSTR = "test/programNoIsInstr.bl",
            NO_END_INSTR = "test/programNoEndInstr.bl",
            PRIMITIVE_NAME_MOVE = "test/programInstrMove.bl",
            PRIMITIVE_NAME_SKIP = "test/programInstrSkip.bl",
            PRIMITIVE_NAME_TURNLEFT = "test/programInstrTurnleft.bl",
            PRIMITIVE_NAME_TURNRIGHT = "test/programInstrTurnright.bl",
            PRIMITIVE_NAME_INFECT = "test/programInstrInfect.bl",
            INSTR_DIFF_NAME = "test/programInstrDiffName.bl",
            INSTR_SAME_NAMES = "test/programInstrSameNames.bl",
            INSTR_NAME_NOT_IDENTIFIER = "test/programInstrNameNotIdentifier.bl",
            NO_PROGRAM = "test/programNoProgram.bl",
            NO_IS_PROGRAM = "test/programNoIsProgram.bl",
            NO_BEGIN = "test/programNoBegin.bl",
            NO_END_PROGRAM = "test/programNoEndProgram.bl",
            PROGRAM_DIFF_NAME = "test/programDiffName.bl",
            PROGRAM_NAME_NOT_IDENTIFIER = "test/programNameNotIdentifier.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructorTest = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructorRef = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    // TODO - add more test cases for valid inputs

    /**
     * Test of parse on syntactically valid input with no instructions.
     */
    @Test
    public final void testParseValidNoInstruction() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PROGRAM_NO_INSTR);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(PROGRAM_NO_INSTR);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test of parse on syntactically valid input with no body.
     */
    @Test
    public final void testParseValidNoBody() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PROGRAM_NO_BODY);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(PROGRAM_NO_BODY);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test of parse on syntactically valid input that has no body and no
     * instructions.
     */
    @Test
    public final void testParseValidEmpty() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PROGRAM_EMPTY);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(PROGRAM_EMPTY);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    // TODO - add more test cases for as many distinct syntax errors as possible

    /**
     * Test of parse on program with an instruction missing the "INSTRUCTION"
     * keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoInstructionKeyword() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_INSTR);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction missing the "IS" keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoIsKeyword() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_IS_INSTR);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction missing the "END" keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoEndKeywordInstr() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_END_INSTR);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction that has a different name
     * after "END".
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoInstructionDiffName() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_DIFF_NAME);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction named "move".
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionMove() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_NAME_MOVE);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction named "infect".
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionInfect() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_NAME_INFECT);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction named "skip".
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionSkip() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_NAME_SKIP);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction named "turnleft".
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionTurnleft() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_NAME_TURNLEFT);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction named "turnright".
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionTurnright() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PRIMITIVE_NAME_TURNRIGHT);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with an instruction whose name is not an
     * identifier.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionNameNotIdentifier() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_NAME_NOT_IDENTIFIER);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program with two instructions with same name.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInstructionsSameName() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_SAME_NAMES);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program missing the "PROGRAM" keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoProgramKeyword() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_PROGRAM);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program missing the "IS" keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoProgramIsKeyword() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_IS_PROGRAM);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program missing the "BEGIN" keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoBeginKeyword() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_BEGIN);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program missing the "END" keyword.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorNoProgramEndKeyword() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(NO_END_PROGRAM);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program whose name at end is different.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorProgramDiffNames() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROGRAM_DIFF_NAME);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    /**
     * Test of parse on program whose name is not an identifier.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorProgramNameNotIdentifier() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROGRAM_NAME_NOT_IDENTIFIER);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }
}
