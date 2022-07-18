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
                Code_attribute attr = new Code_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("Exceptions")){
                Exceptions_attribute attr = new Exceptions_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("ConstantValue")){
                ConstantValue_attribute attr = new ConstantValue_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("StackMapTable")){
                StackMapTable_attribute attr = new StackMapTable_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("InnerClasses")){
                InnerClasses_attribute attr = new InnerClasses_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("EnclosingMethod")){
                EnclosingMethod_attribute attr = new EnclosingMethod_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("Synthetic")){
                Synthetic_attribute attr = new Synthetic_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("Signature")){
                Signature_attribute attr = new Signature_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("SourceFile")){
                SourceFile_attribute attr = new SourceFile_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("SourceDebugExtension")){
                SourceDebugExtension_attribute attr = new SourceDebugExtension_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("LineNumberTable")){
                LineNumberTable_attribute attr = new LineNumberTable_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("LocalVariableTable")){
                LocalVariableTable_attribute attr = new LocalVariableTable_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("LocalVariableTypeTable")){
                LocalVariableTypeTable_attribute attr = new LocalVariableTypeTable_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("Deprecated")){
                Deprecated_attribute attr = new Deprecated_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("RuntimeVisibleAnnotations")){
                RuntimeVisibleAnnotations_attribute attr = new RuntimeVisibleAnnotations_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("RuntimeInvisibleAnnotations")){
                RuntimeInvisibleAnnotations_attribute attr = new RuntimeInvisibleAnnotations_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("RuntimeVisibleParameterAnnotations")){
                RuntimeVisibleParameterAnnotations_attribute attr = new RuntimeVisibleParameterAnnotations_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("RuntimeInvisibleParameterAnnotations")){
                RuntimeInvisibleParameterAnnotations_attribute attr = new RuntimeInvisibleParameterAnnotations_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("AnnotationDefault")){
                AnnotationDefault_attribute attr = new AnnotationDefault_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }else if(name.equals("BootstrapMethods")){
                BootstrapMethods_attribute attr = new BootstrapMethods_attribute(attributes[i].info);
                out += attr.getAttrStr(cf);
            }
        }
        return out;
    }
}