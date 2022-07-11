package filestructure.constants;

public class CONSTANT_Methodref_info extends CONSTANT_info {
    public String name = "CONSTANT_Methodref";
    public short class_index;
    public short name_and_type_index;

    public CONSTANT_Methodref_info(byte ta, short a, short b) {
        size += 4;
        tag = ta;
        class_index = a;
        name_and_type_index = b;
    }
}
