package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

public class LineNumberTable_attribute {
 //   short attribute_name_index;
   // int attribute_length;
    short line_number_table_length;
    line_number_table line_number_table[];
    public LineNumberTable_attribute(byte[] info){
     /*   attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
       */ line_number_table_length = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        line_number_table = new line_number_table[line_number_table_length];
        for(int i = 0, j = 0; i < line_number_table_length; ++i){
            line_number_table[i] = new line_number_table(Short.reverseBytes(Conversion.byteArrayToShort(info, j += 2, (short)0, 0, 2)),
                    Short.reverseBytes(Conversion.byteArrayToShort(info, j += 2, (short)0, 0, 2)));
        }
    }
    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tAttribute \"LineNumberTable\":\n\t\t\tline_number_table (len %d) %s",
                line_number_table_length, getLineNumberTable());
    }
    private String getLineNumberTable(){
        String out = "";
        for(int i = 0; i < line_number_table_length; ++i){
            out += String.format("line_number_table: %d ---- %s", i, line_number_table[i].getDate());
        }
        return out;
    }
    private class line_number_table{
        short start_pc;
        short line_number;
        line_number_table(short s1, short s2){
            start_pc = s1;
            line_number = s2;
        }
        String getDate(){
            return String.format("start_pc: %d, line_number: %d", start_pc, line_number);
        }
    }
}
