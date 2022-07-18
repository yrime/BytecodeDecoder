package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.access.AccessflagsClass;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class InnerClasses_attribute {
  //  short attribute_name_index;
    //int attribute_length;
    short number_of_classes;
    Classes classes[];//[number_of_classes];

    public InnerClasses_attribute(byte[] info){
      //  this.attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(info, 0, (short)0, 0, 2 ));
        //this.attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(info, 2, 0,0,4));
        this.number_of_classes = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short) 0, 0, 2));
        classes = new Classes[this.number_of_classes];
        for (int i = 0, j = 2; i < classes.length; ++i){
            classes[i] = new Classes(Arrays.copyOfRange(info, j, j += 8));
        }
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tAttribute \"InnerClasses\":\n\t\t\tnumber_of_classes %d\n\t\t\tclasses: %s",
                this.number_of_classes, printClasses(cf));
    }
    private String printClasses(ClassFile cf){
        String out = "";
        for (int i = 0; i < this.classes.length; ++i){
            out += this.classes[i].getAttr(cf);
        }
        return out;
    }
    private class Classes{
        short inner_class_info_index;
        short outer_class_info_index;
        short inner_name_index;
        short inner_class_access_flags;
        Classes(byte[] bytes){
            this.inner_class_info_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 0, (short) 0, 0, 2));
            this.outer_class_info_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 2, (short) 0, 0, 2));
            this.inner_name_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 4, (short) 0, 0, 2));
            this.inner_class_access_flags = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 6, (short) 0, 0, 2));
        }
        String getAttr(ClassFile cf){
            return String.format("\n\t\t\t\t\tClass:\n\t\t\t\t\t\tinner_class_info_index %d: %s\n\t\t\t\t\t\t" +
                    "outer_class_info_index %d: %s\n\t\t\t\t\t\ttinner_name_index %d: %s\n\t\t\t\t\t\t" +
                    "inner_class_access_flags %x: %s",
                    this.inner_class_info_index, FileAnalyze.printUtf8(cf, this.inner_class_info_index),
                    this.outer_class_info_index, FileAnalyze.printUtf8(cf, this.outer_class_info_index),
                    this.inner_name_index, FileAnalyze.printUtf8(cf, this.inner_name_index),
                    this.inner_class_access_flags, AccessflagsClass.getStringAccFlag(this.inner_class_access_flags));
        }
    }
}
