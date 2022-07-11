package filestructure.classes;

import filestructure.attributes.AttributeInfo;
import filestructure.constants.CONSTANT_info;

public class ClassFile {
    public int struct_size = 16;
    public int magic;
    public short minor_version;
    public short major_version;
    public short constant_pool_count;
    public CONSTANT_info constant_pool[];//[constant_pool_count-1];
    public short access_flags;
    public short this_class;
    public short super_class;
    public short interfaces_count;
    public short interfaces[];
    public short fields_count;
    public FIELD_info fields[];
    public short methods_count;
    public METHOD_info methods[];//[methods_count];
    public short attributes_count;
    public AttributeInfo attributes[];//[attributes_count];
}
