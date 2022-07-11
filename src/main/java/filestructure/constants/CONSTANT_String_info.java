package filestructure.constants;

public class CONSTANT_String_info extends CONSTANT_info {
    public String name = "CONSTANT_String";
    public short string_index;

    public CONSTANT_String_info(byte ta, short a) {
        size += 2;
        tag = ta;
        string_index = a;
    }
}
