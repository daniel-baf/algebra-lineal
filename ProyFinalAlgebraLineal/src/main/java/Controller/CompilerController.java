package Controller;

import Model.Compiler.CompilerModel;
import Model.Compiler.MatrixLexer;
import Model.Compiler.MatrixParser;
import Model.Utils.CustomLogger;
import Model.Utils.CustomReader;
import View.CompilerJForm;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debf
 */
public class CompilerController {

    // model
    private CompilerModel model;
    // view
    private CompilerJForm view;

    public CompilerController(CompilerModel model, CompilerJForm view) {
        this.model = model;
        this.view = view;
        this.configureListeners();
    }

    /**
     * Display data
     */
    public void display() {
        this.view.setup();
        this.view.setVisible(true);
    }

    /**
     * Load the default example to test code
     */
    public void getExampleInput() {
        CustomReader customReader = new CustomReader();
        String data = customReader.readFile(this.model.getExamplePath());
        this.view.textEditorJTextArea.setText(data); // clear text and update
    }

    /**
     * Configure all action listeners
     */
    public void configureListeners() {
        this.view.loadExampleJMenuItem.addActionListener(e -> this.getExampleInput());
        this.view.runJMenuItem.addActionListener(e -> this.compile(true));
    }

    /**
     * Execute the parser and get a final result, add a step by step if true
     *
     * @param verbose true or false to print step by step
     */
    public void compile(boolean verbose) {
        try {
            String input = this.view.textEditorJTextArea.getText(); // recover data
            MatrixLexer lexer = new MatrixLexer(new StringReader(input));  // reset lexer
            MatrixParser parser = new MatrixParser(lexer);
            parser.parse();
            parser.solve(verbose);
            String output = CustomLogger.getInstance().getAllLogsAsString();
            this.view.resultJTextArea.setText(output);
        } catch (Exception ex) {
            Logger.getLogger(CompilerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
