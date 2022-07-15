package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

public class SourceFile_attribute {
    short attribute_name_index;
    int attribute_length;
    short sourcefile_index;
    public SourceFile_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
        sourcefile_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 6, (short)0, 0, 2));
    }
    public String getAttr(ClassFile cf){
        return String.format("\n\t\t\tSynthetic attribute:\n\t\t\t\tattribute_name_index %d: %s" +
                        "\n\t\t\t\tattribute_length %d\n\t\t\t\tsourcefile_index %d",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index), attribute_length, sourcefile_index);
    }
}
