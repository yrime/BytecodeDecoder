package filestructure;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import filestructure.access.AccessFlags;
import filestructure.access.AccessFlagsField;
import filestructure.access.AccessFlagsMethod;
import filestructure.attributes.AttributeInfo;
import filestructure.classes.ClassFile;
import filestructure.classes.FIELD_info;
import filestructure.classes.METHOD_info;
import filestructure.constants.*;
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
        System.out.println("Access flags: " + AccessFlags.getStringAccFlag(cf.access_flags));
        System.out.println("This class: " + cf.this_class + ", " + printClassInfo(cf, cf.this_class));
        System.out.println(String.format("Super class: %s, %s", cf.super_class, printClassInfo(cf, cf.super_class)));
        System.out.println("Interface count: " + cf.interfaces_count);
        System.out.println("Interfaces: " + printInterfaces(cf));
        System.out.println("Fields count: " + cf.fields_count);
        System.out.println(printFelds(cf));
        System.out.println("Methods count: " + cf.methods_count);
        System.out.println("Methods:" + printMethods(cf, cf.methods));
        System.out.println("Attributes count: " + cf.attributes_count);
        System.out.println("Attributes: " + printAttributInfo(cf, cf.attributes));
    }
    String printMethods(ClassFile cf, METHOD_info[] mi){
        String out = "";
        for(int i = 0; i < mi.length; ++i){
            out += String.format("\n\tMethod %d, Access flags %x: %s, Name index %d: %s, Descriptor index %d: %s," +
                            "Attributes count %d, Attributes: \n\t%s" +
                            "\n\t Decode attribute: %s", i, mi[i].access_flag,
                    AccessFlagsMethod.getStringAccFlag(mi[i].access_flag), mi[i].name_index, printUtf8(cf, mi[i].name_index),
                    mi[i].descriptor_index, printUtf8(cf, mi[i].descriptor_index), mi[i].attributes_count,
                    printAttributInfo(cf, mi[i].attributes), mi[i].getAttrString(cf));
        }
        return out;
    }
    String printFelds(ClassFile cf){
        String out = "";
        for(int i = 0; i < cf.fields.length; ++i){
            out += String.format("%d -- Access flags field %x: %s, Name index %d: %s," +
                            " Descriptor index %d: %s, Attribute info: %s\n\n\t Decode attribute: %s",
                    i, cf.fields[i].access_flag, AccessFlagsField.getStringAccFlag(cf.fields[i].access_flag), cf.fields[i].name_index,
                    printUtf8(cf, cf.fields[i].name_index), cf.fields[i].descriptor_index, printUtf8(cf, cf.fields[i].descriptor_index),
                    printAttributInfo(cf, cf.fields[i].attributes), cf.fields[i].getAttrString(cf));
        }
        return out;
    }
    String printAttributInfo(ClassFile cf, AttributeInfo[] fi){
        String out = "";
        for(int i = 0; i < fi.length; ++i){
            out += String.format("Attribute info %d: \n\tAttribute name index: %s\n\t" +
                    "Attribute length %d\n\tinfo: %s\n", i, printUtf8(cf, fi[i].attribute_name_index),
                    fi[i].attribute_length, new String(fi[i].info, StandardCharsets.UTF_8));
        }
        return out + " " + AttributeInfo.getAttrString(cf, fi);
    }
    String printInterfaces(ClassFile cf){
        String out = "";
        for(int i = 0; i < cf.interfaces.length; ++i){
            out += String.format("%d: %s, ", cf.interfaces[i], printClassInfo(cf, cf.interfaces[i]));
        }
        return out;
    }
    void printConstantPool(ClassFile cf){
        for(int i = 0; i < cf.constant_pool.length; ++i){
            switch (cf.constant_pool[i].tag){
                case (byte) 7:
                    CONSTANT_Class_info cci = (CONSTANT_Class_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name index: %d, size: %d",
                            i + 1, cci.tag, cci.name, cci.name_index, cci.size));
                       System.out.println(String.format("\t %s",
                           printUtf8(cf, cci.name_index)));
                    break;
                case (byte) 9:
                    CONSTANT_Fieldref_info cfi = (CONSTANT_Fieldref_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name and type index: %d, class index: %d," +
                                    " size: %d",
                            i + 1, cfi.tag, cfi.name, cfi.name_and_type_index, cfi.class_index, cfi.size));
                    System.out.println(String.format("\t  %s %s", printClassInfo(cf, cfi.class_index),
                            printNameAndTag(cf, cfi.name_and_type_index) ));
                    break;
                case (byte) 10:
                    CONSTANT_Methodref_info cmi = (CONSTANT_Methodref_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name and tag index: %d, class index: %d, size: %d",
                            i + 1, cmi.tag, cmi.name, cmi.name_and_type_index, cmi.class_index, cmi.size));
                    System.out.println(String.format("\t %s %s",
                            printClassInfo(cf, cmi.class_index),
                            printNameAndTag(cf, cmi.name_and_type_index) ));
                    break;
                case (byte) 11:
                    CONSTANT_InterfaceMethodref_info cimi = (CONSTANT_InterfaceMethodref_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name and tag index: %d, class index: %d, size: %d",
                            i + 1, cimi.tag, cimi.name, cimi.name_and_type_index, cimi.class_index, cimi.size));
                    break;
                case (byte) 8:
                    CONSTANT_String_info csi = (CONSTANT_String_info)cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, string index: %d, size: %d",
                            i + 1, csi.tag, csi.name, csi.string_index, csi.size));
                    System.out.println(String.format("\t %s",
                            printString(cf, csi.string_index)));
                    break;
                case (byte) 3:
                    CONSTANT_Integer_info cii = (CONSTANT_Integer_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, bytes: %x, size: %d",
                            i + 1, cii.tag, cii.name, cii.bytes, cii.size));
                    break;
                case (byte) 4:
                    CONSTANT_Float_info cfii = (CONSTANT_Float_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, bytes: %x, size: %d",
                            i + 1, cfii.tag, cfii.name, cfii.bytes, cfii.size));
                    break;
                case (byte) 5:
                    CONSTANT_Long_info cli = (CONSTANT_Long_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, high bytes: %x, low bytes: %x, size: %d",
                            i + 1, cli.tag, cli.name, cli.high_bytes, cli.low_bytes, cli.size));
                    ++i;
                    break;
                case (byte) 6:
                    CONSTANT_Double_info cdi = (CONSTANT_Double_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, high bytes: %x, low bytes: %x, size: %d",
                            i + 1, cdi.tag, cdi.name, cdi.high_bytes, cdi.low_bytes, cdi.size));
                    ++i;
                    break;
                case (byte) 12:
                    CONSTANT_NameAndType_info cnati = (CONSTANT_NameAndType_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name index: %d, descriptor index: %d, size: %d",
                            i + 1, cnati.tag, cnati.name, cnati.name_index, cnati.descriptor_index, cnati.size));
                    System.out.println(String.format("\t %s %s",
                            printUtf8(cf, cnati.name_index),
                            printUtf8(cf, cnati.descriptor_index)));
                    break;
                case (byte) 1:
                    CONSTANT_Utf8_info cu8i = (CONSTANT_Utf8_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, len byte arr: %d, byte string: \"%s\", size: %d",
                            i + 1, cu8i.tag, cu8i.name, cu8i.length, new String(cu8i.bytes, StandardCharsets.UTF_8), cu8i.size));
                    break;
                case (byte) 15:
                    CONSTANT_MethodHandle_info cmhi = (CONSTANT_MethodHandle_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, reference index: %d, reference kind: %x, size: %d",
                            i + 1, cmhi.tag, cmhi.name, cmhi.reference_index, cmhi.reference_kind, cmhi.size));
                    break;
                case (byte) 16:
                    CONSTANT_MethodType_info cmti = (CONSTANT_MethodType_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, descriptor index: %d, size: %d",
                            i + 1, cmti.tag, cmti.name, cmti.descriptor_index, cmti.size));
                    break;
                case (byte) 18:
                    CONSTANT_InvokeDynamic_info cidi = (CONSTANT_InvokeDynamic_info) cf.constant_pool[i];
                    System.out.println(String.format("index %d, tag %x: %s, name and type index: %d, bootstrap method attr index: %d, size: %d",
                            i + 1, cidi.tag, cidi.name, cidi.name_and_type_index, cidi.bootstrap_method_attr_index, cidi.size));
                    break;
            }
        }
    }
    public static String printUtf8(ClassFile cf, int i){
        return new String(((CONSTANT_Utf8_info)cf.constant_pool[i - 1]).bytes, StandardCharsets.UTF_8);
    }
    String printString(ClassFile cf, int i){
        return printUtf8(cf, i);
    }
    String printClassInfo(ClassFile cf, int i){
        return printUtf8(cf, ((CONSTANT_Class_info)cf.constant_pool[i - 1]).name_index);
    }
    String printNameAndTag(ClassFile cf, int i){
        return String.format("%s %s",
                printUtf8(cf, ((CONSTANT_NameAndType_info)cf.constant_pool[i - 1]).name_index),
                printUtf8(cf, ((CONSTANT_NameAndType_info)cf.constant_pool[i - 1]).descriptor_index));
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
        cf.fields = new FIELD_info[cf.fields_count];
        i = readFields(b, i += 2, cf.fields_count, cf.fields);
        cf.methods_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i, (short) 0,0, 2));
        cf.methods = new METHOD_info[cf.methods_count];
        i = readMethods(b, i += 2, cf.methods_count, cf.methods);
        cf.attributes_count = Short.reverseBytes(Conversion.byteArrayToShort(b, i, (short) 0,0, 2));
        cf.attributes = new AttributeInfo[cf.attributes_count];
        i = readAttributeInfo(b, i += 2, cf.attributes_count, cf.attributes);
    }
    int readMethods(byte[] bytes, int i, short len, METHOD_info[] cfm){
  //      cfm = new METHOD_info[len];
        AtomicInteger ai = new AtomicInteger(i);
        for(int j = 0; j < len; ++j){
            cfm[j] = new METHOD_info(bytes, ai);
        }
        return ai.get();
    }
    int readFields(byte[] bytes, int i, short field_count, FIELD_info[] cff){
        //cff = new FIELD_info[field_count];
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
            if((constant_pool[j].tag == (byte) 6)||(constant_pool[j].tag == (byte) 5)){
                ++j;
            }
        }
        return ii.get();
    }
    public static int readAttributeInfo(byte[] bytes, int i, short len, AttributeInfo[] attri){
     //   attri = new ATTRIBUTE_info[len];
        AtomicInteger ii = new AtomicInteger(i);
        for(int j = 0; j < len; ++j){
            attri[j] = new AttributeInfo(bytes, ii);
        }
        return ii.get();
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
}
