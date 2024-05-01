package Model.Compiler;

import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;

import java.util.ArrayList;
import java.util.Objects;

public class ParserController<T> {

    private ParserModel<T> model;

    public ParserController(ParserModel<T> model) {
        this.model = model;
    }

    public ParserModel<T> getModel() {
        return model;
    }

    public void setModel(ParserModel<T> model) {
        this.model = model;
    }

    /**
     * Saves the value of Matrix into model
     *
     * @param newMatrix matrix to save
     */
    public void saveMatrix(Matrix newMatrix) {
        // check if current value exist in hashmap
        boolean valueExist = Objects.isNull(this.model.getMatrices().get(newMatrix.getName()));
        if (valueExist) {
            this.model.getMatrices().put(newMatrix.getName(), newMatrix);
        }
        CustomLogger.getInstance().addLog("MATRIX EXIST, AVOIDING IT", !valueExist);
    }

    /**
     * Saves in the hashmap common parameter a entry set depending on CommonHashKeys
     *
     * @param key   the key of hashmap
     * @param value the value to add to hashmap
     */
    public void saveInStringHashmap(CommonParserHashKey key, ArrayList<T> value) {
        ArrayList<T> existingValues = this.model.getKeysArrayListHashMap().get(key);
        boolean isEmpty = Objects.isNull(this.model.getKeysArrayListHashMap().get(key));

        if (isEmpty) {
            // Key doesn't exist, create a new one and add the values
            this.model.getKeysArrayListHashMap().put(key, new ArrayList<>(value));
            return;
        }
        // Key exists, check if the types are compatible
        if (!existingValues.isEmpty() && !existingValues.get(0).getClass().equals(value.get(0).getClass())) {
            // Types are not compatible, return
            return;
        }
        // All conditions are met, add values to the existing ArrayList
        existingValues.addAll(value);
    }

    public void saveDecrypts(ArrayList<Double> numbers) {
        if (Objects.isNull(numbers)) return;
        if (numbers.isEmpty()) return; // no operable
        // cast arraylist to double[] values
        ArrayList<ArrayList<Double>> data = new ArrayList<>() {{
            add(numbers);
        }};
        this.saveInStringHashmap(CommonParserHashKey.DECRYPT, (ArrayList<T>) data); // call submethod
    }
}
