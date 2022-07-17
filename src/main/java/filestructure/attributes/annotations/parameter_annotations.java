package filestructure.attributes.annotations;

import filestructure.classes.ClassFile;
import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class parameter_annotations {
    public int size = 2;
    short num_annotations;
    annotation annotations[];//[num_annotations];
    public parameter_annotations(byte[] b){
        num_annotations = Short.reverseBytes(Conversion.byteArrayToShort(
                b, 0, (short) 0, 0, 2));
        annotations = new annotation[num_annotations];
        for(int i = 0; i < num_annotations; ++i){
            annotations[i] = new annotation(Arrays.copyOfRange(b, size, b.length - 1));
            this.size += annotations[i].size;
        }
    }
    public String getParAnno(ClassFile cf){
        return String.format("parameter_annotations: num_annotations = %d, annotations = %s",
                num_annotations, getAnno(cf));
    }
    private String getAnno(ClassFile cf){
        String out = "Annotations:\n\t";
        for (int i = 0; i < num_annotations; ++i){
            out += annotations[i].getAnno(cf);
        }
        return out;
    }
}
