package filestructure.attributes.frames;

import org.apache.commons.lang3.Conversion;

public class verification_type_info {
    byte tag;
    short cpool_index;
    short offset;
    int size;
    String name;
    String names[] = {"ITEM_Top", "ITEM_Integer", "ITEM_Float", "ITEM_Long", "ITEM_Double",
            "ITEM_Null", "ITEM_UninitializedThis", "ITEM_Object", "ITEM_Uninitialized"};
    public verification_type_info(byte[] b){
        tag = b[0];
        if(tag == 7){
            size = 3;
            cpool_index = Short.reverseBytes(Conversion.byteArrayToShort(b, 1, (short) 0, 0, 2));
        }else if(tag == 8){
            size = 3;
            offset = Short.reverseBytes(Conversion.byteArrayToShort(b, 1, (short) 0, 0, 2));
        }else{
            size = 1;
        }
        name = names[tag];
    }
    public String printVerTyInfo(){
        String out = String.format("\t\tVerification Type Info:\n\t\t\ttag %d: %s", tag, name);
        if(tag == 7){
            out += ("\n\t\t\t" + cpool_index);
        }else if (tag == 8){
            out += ("\n\t\t\t" + offset);
        }
        return out;
    }
}
