package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

public class EnclosingMethod_attribute {
    short attribute_name_index;
    int attribute_length;
    short class_index;
    short method_index;
    public EnclosingMethod_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short) 0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short) 0, 0, 4));
        class_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 6, (short) 0, 0, 2));
        method_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 8, (short) 0, 0, 2));
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tEnclosingMethod attribute:\n\t\t\tattribute_name_index %d: %s" +
                        "\n\t\t\tattribute_length %d\n\t\t\tclass_index %d\n\t\t\tmethod_index %d",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index), attribute_length,
                class_index, method_index
        );
    }
}
