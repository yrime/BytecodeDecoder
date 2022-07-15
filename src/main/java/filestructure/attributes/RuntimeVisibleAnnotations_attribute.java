package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.attributes.annotations.annotation;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class RuntimeVisibleAnnotations_attribute {
    short attribute_name_index;
    int attribute_length;
    short num_annotations;
    annotation annotations[];//[num_annotations]
    public RuntimeVisibleAnnotations_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
        num_annotations = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 6, (short)0, 0, 2));
        annotations = new annotation[num_annotations];
        for(int i = 0; i < num_annotations; ++i){
            annotations[i] = new annotation(Arrays.copyOfRange(info, 8, info.length - 1));
        }
    }

    public String getAttr(ClassFile cf){
        return String.format("\n\t\t\tRuntimeVisibleAnnotations attribute:\n\t\t\t\tattribute_name_index %d: %s" +
                        "\n\t\t\t\tattribute_length %d\n\t\t\t\tnum_annotations %d %s",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index), attribute_length, num_annotations, printAnno(cf));
    }
    private String printAnno(ClassFile cf){
        String out = "";
        for (int i = 0; i < num_annotations; ++i){
            out += String.format("\n\t\t\t\t\tannotation %d: %s", i, annotations[i].getAnno(cf));
        }
        return out;
    }
}
