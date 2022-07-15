package filestructure.attributes.annotations;

import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class annotation {
    int size = 4;
    short type_index;
    short num_element_value_pairs;
    elemet_value_pairs elemet_value_pairs[];//[num_element_value_pairs]
    public annotation(byte[] b){
        type_index = Short.reverseBytes(Conversion.byteArrayToShort(b, 0, (short) 0, 0, 2));
        num_element_value_pairs = Short.reverseBytes(Conversion.byteArrayToShort(b, 2, (short) 0, 0, 2));
        elemet_value_pairs = new elemet_value_pairs[num_element_value_pairs];
        for (int i = 0; i < num_element_value_pairs; ++i){
            elemet_value_pairs[i] = new elemet_value_pairs(Arrays.copyOfRange(b, 4, b.length));
            size += elemet_value_pairs[i].size;
        }
    }
    private class elemet_value_pairs{
        int size = 2;
        short element_name_index;
        element_value value;
        elemet_value_pairs(byte[] b){
            element_name_index = Short.reverseBytes(Conversion.byteArrayToShort(b, 0, (short) 0, 0, 2));
            value = new element_value(b);
            size += value.size;
        }
        String getSt(ClassFile cf){
            return String.format("element_name_index %d, element_value %s", element_name_index, value.getStVal(cf));
        }
    }
    public String getAnno(ClassFile cf){
        return String.format("Annotation size: %d, type_index: %d, num_element_value_pairs: %d, elemet_value_pairs: %s",
                size, type_index, num_element_value_pairs, getEVP(cf));
    }
    private String getEVP(ClassFile cf){
        String out = "";
        for(int i = 0; i < elemet_value_pairs.length; ++i){
            out += String.format("\n%d, %s", i, elemet_value_pairs[i].getSt(cf));
        }
        return out;
    }
}
