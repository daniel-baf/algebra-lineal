package Model.Compiler;

import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;

import java.util.ArrayList;
import java.util.Objects;

public class ParserController {

    private ParserModel model;

    public ParserController(ParserModel model) {
        this.model = model;
    }

    public ParserModel getModel() {
        return model;
    }

    public void setModel(ParserModel model) {
        this.model = model;
    }

    /**
     * Saves the value of Matrix into model
     * @param newMatrix
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
     * @param key the key of hashmap
     * @param value the value to add to hashmap
     */
    public void saveInStringHashmap(CommonParserHashKey key, ArrayList<String> value) {
        boolean isEmpty = Objects.isNull(this.model.getKeysArrayListHashMap().get(key));
        if(isEmpty) {
            this.model.getKeysArrayListHashMap().put(key, new ArrayList<>());
        }
        this.model.getKeysArrayListHashMap().get(key).addAll(value);
    }
}
