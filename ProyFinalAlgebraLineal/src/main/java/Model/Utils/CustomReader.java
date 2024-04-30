package Model.Utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class CustomReader {
    public static CustomReader instance;

    public static CustomReader getInstance() {
        if (instance == null) {
            instance = new CustomReader();
        }
        return instance;
    }

    /**
     * Create a function to read the file and return string
     */
    public String readFile(String path) {
        // check if current path is valid)
        try (FileReader fileReader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            StringBuilder stringBuilder = new StringBuilder(); // create a string builder
            String line; // create a string line
            while ((line = bufferedReader.readLine()) != null) { // read the file line by line
                stringBuilder.append(line).append("\n"); // append the line to the string builder
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("File does not exist " + e.getMessage(), true);
            return null;
        }
    }
}
