import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Grace Rhodes
 * @author Aishwarya Srivastava
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires [<"INSTRUCTION"> is a proper prefix of tokens]
     * @ensures
     *
     *          <pre>
     * if [an instruction string is a proper prefix of #tokens] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     *          </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";
        /*
         * Removing keyword INSTRUCTION from tokens
         */
        tokens.dequeue();
        /*
         * Removing the name of the instruction
         */
        String nameOfInstr = tokens.dequeue();
        /*
         * Checking if the name of the instruction is an identifier and is not
         * the same as a primitive instruction
         */
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(nameOfInstr),
                "Name of instruction is not a valid identifier: "
                        + nameOfInstr);
        Reporter.assertElseFatalError(
                !(nameOfInstr.equals("move") || nameOfInstr.equals("turnright")
                        || nameOfInstr.equals("turnleft")
                        || nameOfInstr.equals("skip")
                        || nameOfInstr.equals("infect")),
                "Cannot redefine primitive instruction: " + nameOfInstr);
        /*
         * Checking if name is followed by keyword IS
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Keyword \"IS\" should follow name of the instruction: "
                        + nameOfInstr);
        /*
         * Parsing the instruction body
         */
        body.parseBlock(tokens);
        /*
         * Checking if the instruction body is followed by keyword END
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Keyword \"END\" missing at the end of instruction: "
                        + nameOfInstr);
        /*
         * Checking if keyword END is followed by the name of the instruction
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals(nameOfInstr),
                "Name of instruction at the end does not match name of instruction at the beginning: "
                        + nameOfInstr);

        return nameOfInstr;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        /*
         * Checking if the program starts with keyword PROGRAM
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Keyword PROGRAM missing");
        /*
         * Removing program name and checking it is an identifier
         */
        String programName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(programName),
                "Program name is not an identifier");
        /*
         * Checking if program name is followed by keyword IS
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Keyword \"IS\" missing after program name");
        /*
         * Adding instructions to context
         */
        Map<String, Statement> ctxt = this.newContext();
        while (tokens.front().equals("INSTRUCTION")) {
            Statement instr = this.newBody();
            String nameOfInstr = parseInstruction(tokens, instr);
            Reporter.assertElseFatalError(!ctxt.hasKey(nameOfInstr),
                    "More than one user-defined instruction has the same name: "
                            + nameOfInstr);
            ctxt.add(nameOfInstr, instr);
        }
        /*
         * Adding the new context to this
         */
        this.replaceContext(ctxt);
        /*
         * Removing keyword BEGIN
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Missing keyword \"INSTRUCTION\" or \"BEGIN\"");
        /*
         * Parsing the body of this
         */
        Statement body = this.newBody();
        body.parseBlock(tokens);
        /*
         * Checking if the body of this is followed by keyword END and the
         * program name (and nothing else)
         */
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Keyword \"END\" missing at end of program");
        Reporter.assertElseFatalError(tokens.dequeue().equals(programName),
                "Program name at the end does not match program name at the beginning");
        Reporter.assertElseFatalError(
                tokens.dequeue().equals(Tokenizer.END_OF_INPUT),
                "Extra code appears after end of program.");
        /*
         * Adding the new body to this and changing the program name of this
         */
        this.replaceBody(body);
        this.replaceName(programName);
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
//        String fileName = "data/ProgramTest.bl";
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
