package filestructure.attributes;

import filestructure.FileAnalyze;
import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class BootstrapMethods_attribute {
 //   short attribute_name_index;
 //   int attribute_length;
    short num_bootstrap_methods;
    bootstrap_methods bootstrap_methods[];//[num_bootstrap_methods];

    BootstrapMethods_attribute(byte[] info){
 /*       attribute_name_index = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        attribute_length = Integer.reverseBytes(Conversion.byteArrayToInt(
                info, 2, (short)0, 0, 4));
   */     num_bootstrap_methods = Short.reverseBytes(Conversion.byteArrayToShort(
                info, 0, (short)0, 0, 2));
        bootstrap_methods = new bootstrap_methods[num_bootstrap_methods];
        for(int i = 0, j = 2; i < bootstrap_methods.length; ++i){
            bootstrap_methods[i] = new bootstrap_methods(Arrays.copyOfRange(info, j, info.length));
            j += bootstrap_methods[i].size;
        }
    }

    public String getAttrStr(ClassFile cf) {
        return String.format("\n\t\tAttribute \"BootstrapMethods\":\n\t\t\tnum_bootstrap_methods %d\n\t\t\tbootstrap_methods:" +
                        "%s",
                num_bootstrap_methods, printBootstrapmeth(cf));
    }
    private String printBootstrapmeth(ClassFile cf){
        String out = "";
        for(int i = 0; i < bootstrap_methods.length; ++i){
            out += String.format("\n\t\t\t\t%s", bootstrap_methods[i].getBootStr(cf));
        }
        return out;
    }
    private class bootstrap_methods{
        int size = 0;
        short bootstrap_method_ref;
        short num_bootstrap_arguments;
        short bootstrap_arguments[];//[num_bootstrap_arguments];
        bootstrap_methods(byte[] b){
            bootstrap_method_ref = Short.reverseBytes(Conversion.byteArrayToShort(
                    b, 0, (short) 0, 0, 2));
            num_bootstrap_arguments = Short.reverseBytes(Conversion.byteArrayToShort(
                    b, 2, (short) 0, 0, 2));
            bootstrap_arguments = new short[num_bootstrap_arguments];
            size = 4 + num_bootstrap_arguments * 2;
            for(int i = 0; i < bootstrap_arguments.length; ++i){
                bootstrap_arguments[i] = Short.reverseBytes(Conversion.byteArrayToShort(
                        b, (4 + i*2), (short) 0, 0, 2));
            }
        }
        String getBootStr(ClassFile cf){
            return String.format("Bootstrap method: bootstrap_method_ref %d, num_bootstrap_arguments: %d" +
                    ", bootstrap_arguments: %s", bootstrap_method_ref, num_bootstrap_arguments,
                    getbootstapArgs(cf));
        }
        private String getbootstapArgs(ClassFile cf){
            String out = "";
            for(int i = 0; i < bootstrap_arguments.length; ++i){
                out += String.format("----bootstrap arg %d: %x = %s ----", i, bootstrap_arguments[i],
                        cf.constant_pool[bootstrap_arguments[i] - 1].name);
            }
            return out;
        }
    }
}
