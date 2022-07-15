package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class LocalVariableTypeTable_attribute {
    short attribute_name_index;
    int attribute_length;
    short local_variable_type_table_length;
    local_variable_type_table local_variable_type_table[];//[local_variable_table_length]
    public LocalVariableTypeTable_attribute(byte[] info){
        attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
        local_variable_type_table_length = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 6, (short)0, 0, 2));
        local_variable_type_table = new local_variable_type_table[local_variable_type_table_length];
        for(int i = 0, j = 8; i < local_variable_type_table_length; ++i){
            local_variable_type_table[i] = new local_variable_type_table(Arrays.copyOfRange(info, j, j += 10));
        }
    }
    public String getAttr(ClassFile cf){
        return String.format("\n\t\t\tLocalVariableTypeTable attribute:\n\t\t\t\tattribute_name_index %d: %s" +
                        "\n\t\t\t\tattribute_length %d\n\t\t\t\tlocal_variable_type_table_length %d\n\t\t\t\tlocal_variable_type_table: %s",
                attribute_name_index, FileAnalyze.printUtf8(cf, attribute_name_index), attribute_length, local_variable_type_table_length, printlocalVar(cf));
    }
    private String printlocalVar(ClassFile cf){
        String out = "";
        for(int i = 0; i < local_variable_type_table.length; ++i){
            out += String.format("\n\t\t\t\t\t i=%d %s", i, local_variable_type_table[i].getStr(cf));
        }
        return out;
    }
    private class local_variable_type_table{
        short start_pc;
        short length;
        short name_index;
        short signature_index;
        short index;
        local_variable_type_table(byte[] lvar){
            start_pc = Short.reverseBytes(Conversion.byteArrayToShort(
                    lvar, 0, (short)0, 0, 2));
            length = Short.reverseBytes(Conversion.byteArrayToShort(
                    lvar, 2, (short)0, 0, 2));
            name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                    lvar, 4, (short)0, 0, 2));
            signature_index = Short.reverseBytes(Conversion.byteArrayToShort(
                    lvar, 6, (short)0, 0, 2));
            index = Short.reverseBytes(Conversion.byteArrayToShort(
                    lvar, 8, (short)0, 0, 2));
        }
        String getStr(ClassFile cf){
            return String.format("local_variable_type_table: start_pc %d, length %d, name_index %d --- %s, descriptor_index %d, index %d",
                    start_pc, length, name_index, FileAnalyze.printUtf8(cf, name_index), signature_index, index);
        }
    }
}
