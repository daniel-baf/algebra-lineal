package Controller;

import Model.Compiler.CompilerModel;
import Model.Compiler.MatrixLexer;
import Model.Compiler.MatrixParser;
import Model.Utils.CustomLogger;
import Model.Utils.CustomReader;
import View.CompilerJForm;

import java.io.File;
import java.io.StringReader;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
        this.view.infoJMenuItem.addActionListener(e -> this.displayInfo());
        this.view.saveJMenuItem.addActionListener(e -> this.saveFile());
        this.view.openJMenuItem.addActionListener(e -> this.openFile());
    }

    /**
     * Execute the parser and get a final result, add a step by step if true
     *
     * @param verbose true or false to print step by step
     */
    public void compile(boolean verbose) {
        String input = this.view.textEditorJTextArea.getText(); // recover data
        // check input
        if (Objects.isNull(input) || input.isEmpty() || input.isBlank()) {
            return;
        }
        try {
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

    /**
     * Display data of how to use application
     */
    public void displayInfo() {
        String text = CustomReader.getInstance().readFile(this.model.getInfoPath());
        this.view.displayMessage(text);
    }

    /**
     * Save data in the IDE into a text to re open it in the future
     */
    public void saveFile() {
        // get path from UI
        File newFileUser = this.view.chooseFilePathToSave();
        if (Objects.isNull(newFileUser)) {
            return;
        }
        // Ensure the file has a .txt extension
        if (!newFileUser.getName().toLowerCase().endsWith(".txt")) {
            newFileUser = new File(newFileUser.getAbsolutePath() + ".txt");
        }
        CustomReader.getInstance().saveFile(newFileUser, this.view.textEditorJTextArea.getText());
    }

    /**
     * Open a file and display in data
     */
    public void openFile() {
        // get path from UI
        File newFileUser = this.view.chooseFilePathToOpen();
        if (Objects.isNull(newFileUser)) {
            return;
        }
        String data = CustomReader.getInstance().readFile(newFileUser.getAbsolutePath());
        if (Objects.isNull(data)) {
            this.view.resultJTextArea.setText("UNABLE TO OPEN FILE");
        }
        this.view.textEditorJTextArea.setText(data);
    }


}
