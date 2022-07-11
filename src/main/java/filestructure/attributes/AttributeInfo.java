package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.concurrent.atomic.AtomicInteger;

public class AttributeInfo {
    public short attribute_name_index;
    public int attribute_length;
    public byte info[];//size = attribute_length

    public AttributeInfo(byte[] bytes, AtomicInteger ii) {
        this.attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get(), (short) 0, 0, 2));
        this.attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 2, 0, 0, 4));
        ii.set(ii.get() + 6);
        info = new byte[this.attribute_length];
        for (int j = 0; j < this.attribute_length; ++j) {
            info[j] = bytes[ii.get() + j];
        }
        ii.set(ii.get() + this.attribute_length);
    }
    public static String getAttrString(ClassFile cf, AttributeInfo[] attributes){
        String out = "";
        String name;
        for(int i = 0; i < attributes.length; ++i){
            name = FileAnalyze.printUtf8(cf, attributes[i].attribute_name_index);
            if (name.equals("Code")){
                Code_attribute ca = new Code_attribute(attributes[i].info);
                out += ca.getAttrStr(cf);
            }
            if(name.equals("Exceptions")){
                Exceptions_attribute ea = new Exceptions_attribute(attributes[i].info);
                out += ea.getAttrStr(cf);
            }
        }
        return out;
    }
}