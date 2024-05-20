package Model.Utils;

import java.io.*;

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
        // check if current path is valid
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

    /**
     * Save data into a file, into the specific file path
     *
     * @param fileToSave file path
     * @param data       data to save
     * @return true or false on create
     */
    public boolean saveFile(File fileToSave, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
            writer.write(data);
            return true;
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("UNABLE TO SAVE FILE: " + e.getMessage(), true);
            return false;
        }
    }
}
