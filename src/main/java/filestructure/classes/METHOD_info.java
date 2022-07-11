package filestructure.classes;

import filestructure.FileAnalyze;
import filestructure.attributes.AttributeInfo;
import filestructure.attributes.Code_attribute;
import filestructure.attributes.Exceptions_attribute;
import org.apache.commons.lang3.Conversion;

import java.util.concurrent.atomic.AtomicInteger;

public class METHOD_info {
    public short access_flag;
    public short name_index;
    public short descriptor_index;
    public short attributes_count;
    public AttributeInfo attributes[];

    public METHOD_info(byte[] bytes, AtomicInteger ii) {
        this.access_flag = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get(), (short) 0, 0, 2));
        this.name_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 2, (short) 0, 0, 2));
        this.descriptor_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 4, (short) 0, 0, 2));
        this.attributes_count = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 6, (short) 0, 0, 2));
        attributes = new AttributeInfo[this.attributes_count];
        ii.set(ii.get() + 8);
        for (int j = 0; j < this.attributes_count; ++j) {
            attributes[j] = new AttributeInfo(bytes, ii);
        }
    }
    public String getAttrString(ClassFile cf){
        return AttributeInfo.getAttrString(cf, this.attributes);
    }
    public void printAttributes(ClassFile cf){
        String name;
        for(int i = 0; i < attributes.length; ++i){
            name = FileAnalyze.printUtf8(cf, attributes[i].attribute_name_index);
            if (name.equals("Code")){
                Code_attribute ca = new Code_attribute(this.attributes[i].info);
                ca.print(cf);
            }
        }
    }
}
