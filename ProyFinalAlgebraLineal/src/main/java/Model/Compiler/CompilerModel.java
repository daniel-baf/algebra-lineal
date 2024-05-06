package Model.Compiler;

/**
 * @author debf
 */
public class CompilerModel {

    private final String examplePath;
    private final String infoPath;

    public CompilerModel() {
        this.examplePath = "src/main/Resources/example.txt";
        this.infoPath = "src/main/Resources/info.txt";
    }

    public String getExamplePath() {
        return examplePath;
    }

    public String getInfoPath() { return infoPath; }

}
