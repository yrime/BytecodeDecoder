package filestructure.constants;

public class CONSTANT_NameAndType_info extends CONSTANT_info {
    public String name = "CONSTANT_NameAndType";
    public short name_index;
    public short descriptor_index;

    public CONSTANT_NameAndType_info(byte ta, short a, short b) {
        size += 4;
        tag = ta;
        name_index = a;
        descriptor_index = b;
    }
}
