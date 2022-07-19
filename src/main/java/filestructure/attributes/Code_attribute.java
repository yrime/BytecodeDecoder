package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class Code_attribute {
    public short max_stack;
    public short max_locals;
    public int code_length;
    public byte code[];//code_length];
    public short exception_table_length;
    EXEPTION_table exception_tables[];
    public short attributes_count;
    public AttributeInfo attributes[];//attributes_count];
    public Code_attribute(byte[] info){
        int i = 0;
        this.max_stack = Short.reverseBytes(Conversion.byteArrayToShort(info, i, (short) 0,0, 2));
        this.max_locals = Short.reverseBytes(Conversion.byteArrayToShort(info, i += 2, (short) 0,0, 2));
        this.code_length = Integer.reverseBytes(Conversion.byteArrayToInt(info, i += 2, (short) 0,0, 4));
        code = Arrays.copyOfRange(info, i += 4, this.code_length + i);
        this.exception_table_length = Short.reverseBytes(Conversion.byteArrayToShort(info, i += this.code_length, (short) 0,0, 2));
        exception_tables = new EXEPTION_table[this.exception_table_length];
        i = setExceptionTable(exception_tables, i, info);
        this.attributes_count = Short.reverseBytes(Conversion.byteArrayToShort(info, i, (short) 0,0, 2));
        attributes = new AttributeInfo[this.attributes_count];
        i = FileAnalyze.readAttributeInfo(info, i += 2, this.attributes_count, attributes);
    }
    int setExceptionTable(EXEPTION_table[] et, int i, byte[] b){
        for(int j = 0; j < et.length; ++j){
            et[j] = new EXEPTION_table(Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2)),
                    Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2)),
                    Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2)),
                    Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2)));
        }
        return i+=2;
    }
    private class EXEPTION_table{
        short start_pc;
        short end_pc;
        short handler_pc;
        short catch_type;
        EXEPTION_table(short s1, short s2, short s3, short s4){
            start_pc = s1;
            end_pc = s2;
            handler_pc = s3;
            catch_type = s4;
        }
        String print(){
            return String.format("\n\t\t\t\tstart_pc: %d\n\t\t\t\tend_pc: %d\n\t\t\t\thandler_pc: %d" +
                    "\n\t\t\t\tcatch_type: %d", start_pc, end_pc, handler_pc, catch_type);
        }
    }
    String printExceptions(){
        String out = "";
        for(int i = 0; i < this.exception_tables.length; ++i){
            out += ("\n\t\t\t" + i + this.exception_tables[i].print());
        }
        return out;
    }
    String printCodeInHex(){
        String out = "";
        for (int i = 0; i < this.code.length; ++i){
            out += String.format("-%x-", this.code[i]);
        }
        return out;
    }

    public String getAttrStr(ClassFile cf){
        return String.format("\n\t\tMethod attribute \"Code\": \n\t\t\tmax_stack: %d\n\t\t\t" +
                        "max_local: %d\n\t\t\texception_table_length: %d\n\t\t\texception_tables: %s" +
                        "\n\t\t\tcode_length: %d\n\t\t\tcode: %s\n\t\t\tattribute_count: %d\n\t\t\t" +
                        "attributes: %s",
                this.max_stack, this.max_locals, this.exception_table_length, printExceptions(),
                this.code_length, printCodeInHex(), this.attributes_count, AttributeInfo.getAttrString(cf, this.attributes));
    }
    public void print(ClassFile cf){
        System.out.println(getAttrStr(cf));
    }
}
