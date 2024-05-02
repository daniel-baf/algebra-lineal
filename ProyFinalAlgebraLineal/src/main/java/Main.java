
import Controller.CompilerController;
import Model.Compiler.CompilerModel;
import Model.Compiler.MatrixLexer;
import Model.Compiler.MatrixParser;
import Model.Utils.CustomLogger;
import Model.Utils.CustomReader;
import View.CompilerJForm;

import java.io.StringReader;

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
            CompilerModel compilerModel = new CompilerModel();
            CompilerJForm compilerJFormView = new CompilerJForm();
            CompilerController compilerController = new CompilerController(compilerModel, compilerJFormView);
            compilerController.display();
        } catch (Exception ex) {
            CustomLogger.getInstance().addLog("UNABLE TO INIT APPLICATION " + ex.getMessage(), true);
        }
    }
}
