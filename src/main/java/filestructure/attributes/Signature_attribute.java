package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

public class Signature_attribute {
//    short attribute_name_index;
  //  int attribute_length;
    short signature_index;
    public Signature_attribute(byte[] info){
    /*    attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
      */  signature_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tAttribute \"Signature\":\n\t\t\tsignature_index %d",
                signature_index);
    }
}
