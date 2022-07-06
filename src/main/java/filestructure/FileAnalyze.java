package filestructure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Conversion;

public class FileAnalyze {
    ClassFile classFile;
    public FileAnalyze(byte[] file){
        classFile = new ClassFile();
        readStruct(classFile, file);
        printClassFileStruct(classFile);
    }
    void printClassFileStruct(ClassFile cf){
        System.out.println("magic: " + Integer.toHexString(cf.magic));
        System.out.println(String.format("minor version: %d, major version: %d", cf.minor_version, cf.major_version));
        System.out.println("Constant pool size: " + cf.constant_pool_count);
        printConstantPool(cf);
    }
    void printConstantPool(ClassFile cf){
        for(int i = 0; i < cf.constant_pool.length; ++i){
            switch (cf.constant_pool[i].tag){
                case (byte) 7:
                    CONSTANT_Class_info cci = (CONSTANT_Class_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name index: %d, size: %d",
                            i + 1, cci.tag, cci.name, cci.name_index, cci.size));
                    break;
                case (byte) 9:
                    CONSTANT_Fieldref_info cfi = (CONSTANT_Fieldref_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, name and type index: %d, class index: %d," +
                                    " size: %d",
                            i + 1, cfi.tag, cfi.name, cfi.name_and_type_index, cfi.class_index, cfi.size));
                    break;
                case (byte) 10:
                    CONSTANT_Methodref_info cmi = (CONSTANT_Methodref_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, name and tag index: %d, class index: %d, size: %d",
                            i + 1, cmi.tag, cmi.name, cmi.name_and_type_index, cmi.class_index, cmi.size));
                    break;
                case (byte) 11:
                    CONSTANT_InterfaceMethodref_info cimi = (CONSTANT_InterfaceMethodref_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, name and tag index: %d, class index: %d, size: %d",
                            i + 1, cimi.tag, cimi.name, cimi.name_and_type_index, cimi.class_index, cimi.size));
                    break;
                case (byte) 8:
                    CONSTANT_String_info csi = (CONSTANT_String_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, string index: %d, size: %d",
                            i + 1, csi.tag, csi.name, csi.string_index, csi.size));
                    break;
                case (byte) 3:
                    CONSTANT_Integer_info cii = (CONSTANT_Integer_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, bytes: %x, size: %d",
                            i + 1, cii.tag, cii.name, cii.bytes, cii.size));
                    break;
                case (byte) 4:
                    CONSTANT_Float_info cfii = (CONSTANT_Float_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, bytes: %x, size: %d",
                            i + 1, cfii.tag, cfii.name, cfii.bytes, cfii.size));
                    break;
                case (byte) 5:
                    CONSTANT_Long_info cli = (CONSTANT_Long_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, high bytes: %x, low bytes: %x, size: %d",
                            i + 1, cli.tag, cli.name, cli.high_bytes, cli.low_bytes, cli.size));
                    break;
                case (byte) 6:
                    CONSTANT_Double_info cdi = (CONSTANT_Double_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, high bytes: %x, low bytes: %x, size: %d",
                            i + 1, cdi.tag, cdi.name, cdi.high_bytes, cdi.low_bytes, cdi.size));
                    break;
                case (byte) 12:
                    CONSTANT_NameAndType_info cnati = (CONSTANT_NameAndType_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, name index: %d, descriptor index: %d, size: %d",
                            i + 1, cnati.tag, cnati.name, cnati.name_index, cnati.descriptor_index, cnati.size));
                    break;
                case (byte) 1:
                    CONSTANT_Utf8_info cu8i = (CONSTANT_Utf8_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, len byte arr: %d, byte string: \"%s\", size: %d",
                            i + 1, cu8i.tag, cu8i.name, cu8i.length, new String(cu8i.bytes, StandardCharsets.UTF_8), cu8i.size));
                    break;
                case (byte) 15:
                    CONSTANT_MethodHandle_info cmhi = (CONSTANT_MethodHandle_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, reference index: %d, reference kind: %x, size: %d",
                            i + 1, cmhi.tag, cmhi.name, cmhi.reference_index, cmhi.reference_kind, cmhi.size));
                    break;
                case (byte) 16:
                    CONSTANT_MethodType_info cmti = (CONSTANT_MethodType_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, descriptor index: %d, size: %d",
                            i + 1, cmti.tag, cmti.name, cmti.descriptor_index, cmti.size));
                    break;
                case (byte) 18:
                    CONSTANT_InvokeDynamic_info cidi = (CONSTANT_InvokeDynamic_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tad %x: %s, name and type index: %d, bootstrap method attr index: %d, size: %d",
                            i + 1, cidi.tag, cidi.name, cidi.name_and_type_index, cidi.bootstrap_method_attr_index, cidi.size));
                    break;
            }
        }
    }
    void readStruct(ClassFile cf, byte[] b){
        int i = 0;
      //  cf.magic = ((int)b[0] << 24) | (((int)b[1] & 0x000000FF) << 16) | (((int)b[2] & 0x000000FF) << 8) | ((int)b[3] & 0x000000FF);
        cf.magic = Integer.reverseBytes(Conversion.byteArrayToInt(b, i, 0, 0, 4));
        //System.out.println(Integer.toHexString(cf.magic));
        cf.minor_version = Short.reverseBytes(Conversion.byteArrayToShort(b, i += 4, (short) 0,0, 2));
        cf.major_version = Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2));
        cf.constant_pool_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2));
        cf.constant_pool = new CONSTANT_info[cf.constant_pool_count - 1];
        i = readConstantPool(b, i += 2, cf.constant_pool);
        cf.access_flags = Short.reverseBytes(Conversion.byteArrayToShort(b, i, (short) 0,0, 2));
        cf.this_class = Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2));
        cf.super_class = Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2));
        cf.interfaces_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i += 2, (short) 0,0, 2));
        cf.interfaces = new short[cf.interfaces_count];
        i = readInterfaces(b, i, cf.interfaces);
        cf.fields_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i, (short) 0,0, 2));
        i = readFields(b, i += 2, cf.fields_count, cf.fields);
        cf.methods_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i, (short) 0,0, 2));
        i = readMethods(b, i += 2, cf.methods_count, cf.methods);
        cf.attributes_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i, (short) 0,0, 2));
        i = readAttributeInfo(b, i += 2, cf.attributes_count, cf.attributes);
    }
    int readMethods(byte[] bytes, int i, short len, METHOD_info[] cfm){
        cfm = new METHOD_info[len];
        AtomicInteger ai = new AtomicInteger(i);
        for(int j = 0; j < len; ++j){
            cfm[j] = new METHOD_info(bytes, ai);
        }
        return ai.get();
    }
    int readFields(byte[] bytes, int i, short field_count, FIELD_info[] cff){
        cff = new FIELD_info[field_count];
        AtomicInteger ai = new AtomicInteger(i);
        for(int j = 0; j < field_count; ++j){
            cff[j] = new FIELD_info(bytes, ai);
        }
        return ai.get();
    }
    int readInterfaces(byte[] bytes, int i, short[] cfinf){
        for(int j = 0; j < cfinf.length; ++j){
            cfinf[j] = Short.reverseBytes(Conversion.byteArrayToShort(bytes, i += 2, (short) 0,0, 2));
        }
        return i + 2;
    }
    int readConstantPool(byte[] bytes, int i, CONSTANT_info[] constant_pool){
        int size = constant_pool.length;
        AtomicInteger ii = new AtomicInteger(i);
        for (int j = 0; j < size; ++j){
            constant_pool[j] = getConstant(bytes, ii);
        }
        return ii.get();
    }
    int readAttributeInfo(byte[] bytes, int i, short len, ATTRIBUTE_info[] attri){
        attri = new ATTRIBUTE_info[len];
        AtomicInteger ii = new AtomicInteger(i);
        for(int j = 0; j < len; ++j){
            attri[j] = new ATTRIBUTE_info(bytes, ii);
        }
        return ii.get();
    }
    private class ClassFile{
        int struct_size = 16;
        int magic;
        short minor_version;
        short major_version;
        short constant_pool_count;
        CONSTANT_info constant_pool[];//[constant_pool_count-1];
        short access_flags;
        short this_class;
        short super_class;
        short interfaces_count;
        short interfaces[];
        short fields_count;
        FIELD_info fields[];
        short methods_count;
        METHOD_info methods[];//[methods_count];
        short attributes_count;
        ATTRIBUTE_info attributes[];//[attributes_count];
    }
    CONSTANT_info getConstant(byte[] bytes, AtomicInteger ii){
        //int i = ii;
        byte tag = bytes[ii.get()];
        CONSTANT_info constant_info = null;
        switch (tag){
            case (byte) 7:
                constant_info = new CONSTANT_Class_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)));
                ii.set(ii.get() + 3);
                break;
            case (byte) 9:
                constant_info = new CONSTANT_Fieldref_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)),
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 3, (short) 0,0, 2)));
                ii.set(ii.get() + 5);
                break;
            case (byte) 10:
                constant_info = new CONSTANT_Methodref_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)),
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 3, (short) 0,0, 2)));
                ii.set(ii.get() + 5);
                break;
            case (byte) 11:
                constant_info = new CONSTANT_InterfaceMethodref_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)),
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 3, (short) 0,0, 2)));
                ii.set(ii.get() + 5);
                break;
            case (byte) 8:
                constant_info = new CONSTANT_String_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)));
                ii.set(ii.get() + 3);
                break;
            case (byte) 3:
                constant_info = new CONSTANT_Integer_info(tag,
                        Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 1, 0, 0, 4)));
                ii.set(ii.get() + 5);
                break;
            case (byte) 4:
                constant_info = new CONSTANT_Float_info(tag,
                        Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 1, 0, 0, 4)));
                ii.set(ii.get() + 5);
                break;
            case (byte) 5:
                constant_info = new CONSTANT_Long_info(tag,
                        Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 1, 0, 0, 4)),
                        Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 5, 0, 0, 4)));
                ii.set(ii.get() + 9);
                break;
            case (byte) 6:
                constant_info = new CONSTANT_Double_info(tag,
                        Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 1, 0, 0, 4)),
                        Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 5, 0, 0, 4)));
                ii.set(ii.get() + 9);
                break;
            case (byte) 12:
                constant_info = new CONSTANT_NameAndType_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)),
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 3, (short) 0,0, 2)));
                ii.set(ii.get() + 5);
                break;
            case (byte) 1:
                short lenb = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2));
                constant_info = new CONSTANT_Utf8_info(tag,
                        lenb,
                                                                Arrays.copyOfRange(bytes, ii.get() + 3, lenb + ii.get() + 3));
                ii.set(ii.get() + 3 + lenb);
                break;
            case (byte) 15:
                constant_info = new CONSTANT_MethodHandle_info(tag, bytes[ii.get() + 1],
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 2, (short) 0,0, 2)));
                ii.set(ii.get() + 4);
                break;
            case (byte) 16:
                constant_info = new CONSTANT_MethodType_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)));
                ii.set(ii.get() + 3);
                break;
            case (byte) 18:
                constant_info = new CONSTANT_InvokeDynamic_info(tag,
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 1, (short) 0,0, 2)),
                        Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 3, (short) 0,0, 2)));
                ii.set(ii.get() + 5);
                break;
        }
        return constant_info;
    }
    private class METHOD_info{
        short access_flag;
        short name_index;
        short descriptor_index;
        short attributes_count;
        ATTRIBUTE_info attributes[];
        METHOD_info(byte[] bytes, AtomicInteger ii){
            this.access_flag = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get(), (short) 0,0, 2));
            this.name_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 2, (short) 0,0, 2));
            this.descriptor_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 4, (short) 0,0, 2));
            this.attributes_count = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 6, (short) 0,0, 2));
            attributes = new ATTRIBUTE_info[this.attributes_count];
            ii.set(ii.get() + 8);
            for(int j = 0; j < this.attributes_count; ++j){
                attributes[j] = new ATTRIBUTE_info(bytes, ii);
            }
        }
    }
    private class FIELD_info{
        short access_flag;
        short name_index;
        short descriptor_index;
        short attributes_count;
        ATTRIBUTE_info attributes[];
        FIELD_info(byte[] bytes, AtomicInteger ii){
            this.access_flag = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get(), (short) 0,0, 2));
            this.name_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 2, (short) 0,0, 2));
            this.descriptor_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 4, (short) 0,0, 2));
            this.attributes_count = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get() + 6, (short) 0,0, 2));
            attributes = new ATTRIBUTE_info[this.attributes_count];
            ii.set(ii.get() + 8);
            for(int j = 0; j < this.attributes_count; ++j){
                attributes[j] = new ATTRIBUTE_info(bytes, ii);
            }
        }
    }
    private class ATTRIBUTE_info{
        short attribute_name_index;
        int attribute_length;
        byte info[];//size = attribute_length
        ATTRIBUTE_info(byte[] bytes, AtomicInteger ii){
            this.attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(bytes, ii.get(), (short) 0,0, 2));
            this.attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(bytes, ii.get() + 2, 0, 0, 4));
            ii.set(ii.get() + 6);
            info = new byte[this.attribute_length];
            for(int j = 0; j < this.attribute_length; ++j){
                info[j] = bytes[ii.get() + j];
            }
            ii.set(ii.get() + this.attribute_length);
        }
    }
    private abstract class CONSTANT_info{
        byte tag;
        int size = 1;
    }
    private class CONSTANT_Class_info extends CONSTANT_info{
        String name = "CONSTANT_Class";
        short name_index = 0;
        CONSTANT_Class_info(byte ta, short b){
            size += 2;
            tag = ta;
            name_index = b;
        }
    }
    private class CONSTANT_Fieldref_info extends CONSTANT_info {
        String name = "CONSTANT_Fieldref";
        short class_index;
        short name_and_type_index;
        CONSTANT_Fieldref_info(byte ta, short a, short b){
            size += 4;
            tag = ta;
            class_index = a;
            name_and_type_index = b;
        }
    }
    private class CONSTANT_Methodref_info extends CONSTANT_info {
        String name = "CONSTANT_Methodref";
        short class_index;
        short name_and_type_index;
        CONSTANT_Methodref_info(byte ta, short a, short b){
            size += 4;
            tag = ta;
            class_index = a;
            name_and_type_index = b;
        }
    }
    private class CONSTANT_InterfaceMethodref_info extends CONSTANT_info {
        String name = "CONSTANT_IntefaceMethodref";
        short class_index;
        short name_and_type_index;
        CONSTANT_InterfaceMethodref_info(byte ta, short a, short b){
            size += 4;
            tag = ta;
            class_index = a;
            name_and_type_index = b;
        }
    }
    private class CONSTANT_String_info extends CONSTANT_info {
        String name = "CONSTANT_String";
        short string_index;
        CONSTANT_String_info(byte ta, short a){
            size += 2;
            tag = ta;
            string_index = a;
        }
    }
    private class CONSTANT_Integer_info extends CONSTANT_info {
        String name = "CONSTANT_Integer";
        int bytes;
        CONSTANT_Integer_info(byte ta, int a){
            size += 4;
            tag = ta;
            bytes = a;
        }
    }
    private class CONSTANT_Float_info extends CONSTANT_info {
        String name = "CONSTANT_Float";
        int bytes;
        CONSTANT_Float_info(byte ta, int a){
            size += 4;
            tag = ta;
            bytes = a;
        }
    }
    private class CONSTANT_Long_info extends CONSTANT_info {
        String name = "CONSTANT_Long";
        int high_bytes;
        int low_bytes;
        CONSTANT_Long_info(byte ta, int a, int b){
            size += 8;
            tag = ta;
            high_bytes = a;
            low_bytes = b;
        }
    }
    private class CONSTANT_Double_info extends CONSTANT_info {
        String name = "CONSTANT_Double";
        int high_bytes;
        int low_bytes;
        CONSTANT_Double_info(byte ta, int a, int b){
            size += 8;
            tag = ta;
            high_bytes = a;
            low_bytes = b;
        }
    }
    private class CONSTANT_NameAndType_info extends CONSTANT_info {
        String name = "CONSTANT_NameAndType";
        short name_index;
        short descriptor_index;
        CONSTANT_NameAndType_info(byte ta, short a, short b){
            size += 4;
            tag = ta;
            name_index = a;
            descriptor_index = b;
        }
    }
    private class CONSTANT_Utf8_info extends CONSTANT_info {
        String name = "CONSTANT_Utf8";
        short length;
        byte bytes[];
        CONSTANT_Utf8_info(byte ta, short a, byte[] by){
            size = size + 2 + a;
            tag = ta;
            length = a;
            //bytes = new byte[length];
            bytes = by;
        }
    }
    private class CONSTANT_MethodHandle_info extends CONSTANT_info {
        String name = "CONSTANT_MethodHandle";
        byte reference_kind;
        short reference_index;
        CONSTANT_MethodHandle_info(byte ta, byte a, short b){
            size += 3;
            tag = ta;
            reference_kind = a;
            reference_index = b;
        }
    }
    private class CONSTANT_MethodType_info extends CONSTANT_info {
        String name = "CONSTANT_MethodType";
        short descriptor_index;
        CONSTANT_MethodType_info(byte ta, short a){
            size += 2;
            tag = ta;
            descriptor_index = a;
        }
    }
    private class CONSTANT_InvokeDynamic_info extends CONSTANT_info {
        String name = "CONSTANT_InvokeDynamic";
        short bootstrap_method_attr_index;
        short name_and_type_index;
        CONSTANT_InvokeDynamic_info(byte ta, short a, short b){
            size += 4;
            tag = ta;
            bootstrap_method_attr_index = a;
            name_and_type_index = b;
        }
    }
}
