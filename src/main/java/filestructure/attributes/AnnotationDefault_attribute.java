package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.attributes.annotations.element_value;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class AnnotationDefault_attribute {
//    short attribute_name_index;
//    int attribute_length;
    element_value default_value;
    public AnnotationDefault_attribute(byte[] info){
/*        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, 0, 0, 4));*/
        default_value = new element_value(Arrays.copyOfRange(info, 0, info.length));
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tAttribute \"AnnotationDefault\":\n\t\t\tdefault_value %s",
                default_value.getStVal(cf));
    }
}
