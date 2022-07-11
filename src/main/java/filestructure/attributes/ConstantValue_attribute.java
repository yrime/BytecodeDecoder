package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

class ConstantValue_attribute {
    short attribute_name_index;
    int attribute_length;
    short constantvalue_index;
    public ConstantValue_attribute(byte[] info){
        this.attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(info, 0, (short) 0,0, 2));
        this.attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(info, 2, 0, 0, 4));
        this.constantvalue_index = Short.reverseBytes(Conversion.byteArrayToShort(info, 6, (short) 0, 0, 2));
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tConstantValue attribute \"ConstantValue\": \n\t\t\tattribute_name_index: %d = %s\n\t\t\t" +
                        "attribute_length: %d\n\t\t\tconstantvalue_index: %d",
                this.attribute_name_index, FileAnalyze.printUtf8(cf, this.attribute_name_index),
                this.attribute_length, this.constantvalue_index
        );
    }
}
