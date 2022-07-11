package filestructure.constants;

public class CONSTANT_MethodType_info extends CONSTANT_info {
    public String name = "CONSTANT_MethodType";
    public short descriptor_index;

    public CONSTANT_MethodType_info(byte ta, short a) {
        size += 2;
        tag = ta;
        descriptor_index = a;
    }
}
