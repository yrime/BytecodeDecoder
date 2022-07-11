package filestructure.attributes;

import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Exceptions_attribute {
    public short attribute_name_index;
    public int attribute_length;
    public short number_of_exceptions;
    public short exception_index_table[];//[number_of_exceptions];
    public Exceptions_attribute(byte[] info){
        this.attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(info, 0, (short) 0,0, 2));;
        this.attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(info, 2, (short) 0,0, 4));;
        this.number_of_exceptions = Short.reverseBytes(Conversion.byteArrayToShort(info, 6, (short) 0,0, 2));;
        this.exception_index_table = new short[this.number_of_exceptions];
        ByteBuffer.wrap(Arrays.copyOfRange(info, 8, this.number_of_exceptions + 8)).
                order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(this.exception_index_table);
    }
    public String getAttrStr(ClassFile cf){
        return String.format("\n\t\tException attribute \"Exception\": \n\t\t\tattribute_name_index: %d\n\t\t\t" +
                        "attribute_length: %d\n\t\t\tnumber_of_exceptions: %d\n\t\t\texception_tables: %s",
                    this.attribute_name_index, this.attribute_length, this.number_of_exceptions, getExceptIndexTable()
                );
    }
    private String getExceptIndexTable(){
        String out = "";
        for(int i = 0; i < this.exception_index_table.length; ++i){
            out += String.format(" %x ", this.exception_index_table[i]);
        }
        return out;
    }
}
