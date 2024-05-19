
import Controller.CompilerController;
import Domain.Vector.GraphVector;
import Domain.Vector.GraphVectorSolver;
import Model.Compiler.CompilerModel;
import Model.Compiler.MatrixLexer;
import Model.Compiler.MatrixParser;
import Model.Utils.CustomLogger;
import View.CompilerJForm;

import java.io.FileReader;
import java.util.HashMap;

/**
 * @author jefe_mayoneso
 */
public class Main {

    // create testing matrix
    // tmp matrix
    public Main() {
    }

    public static void main(String[] args) {
        // operations object
        try {
            boolean verbose = true;
//            runView(verbose);
            testParser(verbose);
        } catch (Exception ex) {
            CustomLogger.getInstance().addLog("UNABLE TO INIT APPLICATION " + ex.getMessage(), true);
        }
    }

    public static void runView(boolean verbose) {
        CompilerModel compilerModel = new CompilerModel();
        CompilerJForm compilerJFormView = new CompilerJForm();
        CompilerController compilerController = new CompilerController(compilerModel, compilerJFormView);
        compilerController.display();
    }

    public static void testParser(boolean verbose) {
        try (FileReader reader = new FileReader("src/main/Resources/example.txt")) {
            MatrixLexer lexer = new MatrixLexer(reader);
            MatrixParser parser = new MatrixParser(lexer);
            parser.parse();
            parser.solve(verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("ERROR OPPENING FILE " + e.getMessage(), true);
        }
        System.out.printf(CustomLogger.getInstance().getAllLogsAsString());
    }
}
