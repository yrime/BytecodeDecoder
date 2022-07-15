package filestructure.attributes.annotations;

import filestructure.classes.ClassFile;

public class element_value {
    int size = 1;
    byte tag;
    union_annotation_value value;
    public element_value(byte[] b){
        tag = b[0];
        value = new union_annotation_value(b);
        size += value.size;
    }
    public String getStVal(ClassFile cf){
        return String.format("\n\tElement value: tag = %d, union_annotation_value = %s", tag, value.getUnionStrAnno(cf));
    }
}
