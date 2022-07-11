package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.attributes.frames.SAME_FRAME;
import filestructure.attributes.frames.SAME_LOCALS_1_STACK_ITEM_FRAME;
import filestructure.attributes.frames.STACK_MAP_FRAME;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class StackMapTable_attribute {
    short attribute_name_index;
    int attribute_length;
    short number_of_entries;
    STACK_MAP_FRAME entries[];//[number_of_entries];
    public StackMapTable_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, 0,0, 4));
        number_of_entries = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 6, (short)0, 0, 2));
        entries = new STACK_MAP_FRAME[number_of_entries];
        setEntries(entries, Arrays.copyOfRange(info, 8, info.length));
    }
    public String getAttr(ClassFile cf) {
        return String.format("\n\t\t\tStackMapTable attribute:\n\t\t\t\tattribute_name_index %d: %s" +
                "\n\t\t\t\tattribute_length %d\n\t\t\t\tnumber_of_entries %d\n\t\t\t\tentries: %s",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index), attribute_length,
                number_of_entries, getFramesStr());
    }
    private String getFramesStr(){
        String out = "";
        for(int i = 0; i < entries.length; ++i){
            out += entries[i].getFrame();
        }
        return out;
    }
    private void setEntries(STACK_MAP_FRAME[] entries, byte[] bytes){
        byte b = bytes[0];
        for(int i = 0, j = 0; i < entries.length; ++i){
            if(b < 64){
                entries[i] = new SAME_FRAME(bytes[j]);
            }else if(b < 128){
                entries[i] = new SAME_LOCALS_1_STACK_ITEM_FRAME(bytes[j],
                        Arrays.copyOfRange(bytes, j + 1, bytes.length));
            }
        }
    }
}
