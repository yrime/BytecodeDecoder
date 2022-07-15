package filestructure.attributes.annotations;

import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class union_annotation_value {
    int size;
    byte tag;
    short const_value_index;
    enum_const_value enum_const_value;
    short class_info_index;
    annotation annotation_value;
    array_value array_value;

    private class array_value{
        int size = 2;
        short num_values;
        element_value values[];//[num_values]
        private array_value(byte[] b){
            num_values = Short.reverseBytes(Conversion.byteArrayToShort(b, 0, (short) 0, 0, 2));
            values = new element_value[num_values];
            for(int i = 0; i < num_values; ++i){
                values[i] = new element_value(Arrays.copyOfRange(b, 2, b.length - 1));
                size += values[i].size;
            }
        }
        String getStrVal(ClassFile cf){
            String out = "num_values " + num_values;
            for(int i = 0; i < values.length; ++i){
                out += String.format("\n values %d = %s", i, values[i].getStVal(cf));
            }
            return out;
        }
    }
    private class enum_const_value{
        short type_name_index;
        short const_name_index;
        enum_const_value(short a, short b){
            type_name_index = a;
            const_name_index = b;
        }
    }
    public union_annotation_value(byte[] b){
        tag = b[0];
        switch (tag){
            case 's':
                const_value_index = Short.reverseBytes(Conversion.byteArrayToShort(b, 1, (short) 0, 0, 2));
                size = 2;
                break;
            case 'e':
                enum_const_value = new enum_const_value(Short.reverseBytes(Conversion.byteArrayToShort(b, 1, (short) 0, 0, 2)),
                        Short.reverseBytes(Conversion.byteArrayToShort(b, 3, (short) 0, 0, 2)));
                size = 4;
                break;
            case 'c':
                class_info_index = Short.reverseBytes(Conversion.byteArrayToShort(b, 1, (short) 0, 0, 2));
                size = 2;
                break;
            case '@':
                annotation_value = new annotation(Arrays.copyOfRange(b, 1,b.length - 1));
                size = annotation_value.size;
                break;
            case '[':
                array_value = new array_value(Arrays.copyOfRange(b, 1,b.length - 1));
                size = array_value.size;
                break;
        }
    }
    public String getUnionStrAnno(ClassFile cf){
        switch (tag){
            case 's':
                return String.format("const_value_index: %d", const_value_index);
            case 'e':
                return String.format("enum_const_value: type_name_index = %d, const_name_index = %d", enum_const_value.type_name_index, enum_const_value.const_name_index);
            case 'c':
                return String.format("class_info_index: %d", class_info_index);
            case '@':
                return String.format("annotation_value: %s", annotation_value.getAnno(cf));
            case '[':
                return String.format("array_value: %d", array_value.getStrVal(cf));
        }
        return null;
    }
}
