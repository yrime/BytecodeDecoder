package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class SourceDebugExtension_attribute {
    short attribute_name_index;
    int attribute_length;
    byte debug_extension[];//[attribute_length]
    public SourceDebugExtension_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
        debug_extension = Arrays.copyOfRange(info, 6, attribute_length + 5);//new byte[attribute_length];
    }
    public String getAttr(ClassFile cf){
        return String.format("\n\t\t\tSynthetic attribute:\n\t\t\t\tattribute_name_index %d: %s" +
                        "\n\t\t\t\tattribute_length %d\n\t\t\t\tdebug_extension %s",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index), attribute_length, debug_extension.toString());
    }
}
