package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class SourceDebugExtension_attribute {
//    short attribute_name_index;
  //  int attribute_length;
    byte debug_extension[];//[attribute_length]
    public SourceDebugExtension_attribute(byte[] info){
    /*    attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
      */  debug_extension = Arrays.copyOfRange(info, 0, info.length);//new byte[attribute_length];
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tAttribute \"SourceDebugExtension\":\n\t\t\tdebug_extension %s",
                debug_extension.toString());
    }
}
