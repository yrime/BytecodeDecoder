package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.attributes.annotations.parameter_annotations;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class RuntimeInvisibleParameterAnnotations_attribute {
    short attribute_name_index;
    int attribute_length;
    byte num_parameters;
    parameter_annotations parameter_annotations[];//[num_parameters]
    public RuntimeInvisibleParameterAnnotations_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
        num_parameters = info[6];
        parameter_annotations = new parameter_annotations[num_parameters];
        for(int i = 0, j = 7; i < num_parameters; ++i){
            parameter_annotations[i] = new parameter_annotations(Arrays.copyOfRange(info, j, info.length - 1));
            j += parameter_annotations[i].size;
        }
    }
    public String getAttr(ClassFile cf){
        return String.format("\n\t\t\tRuntimeInvisibleParameterAnnotations attribute:" +
                        "\n\t\t\t\tattribute_name_index %d: %s" +
                        "\n\t\t\t\tattribute_length %d\n\t\t\t\tnum_parameters %x\n\t\t\t\tparameter_annotations %s",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index),
                attribute_length, num_parameters, printParam(cf));
    }
    private String printParam(ClassFile cf){
        String out = "";
        for(int i = 0; i < parameter_annotations.length; ++i){
            out += parameter_annotations[i].getParAnno(cf);
        }
        return out;
    }
}
