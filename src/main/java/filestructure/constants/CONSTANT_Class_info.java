package filestructure.constants;

import filestructure.constants.CONSTANT_info;

public class CONSTANT_Class_info extends CONSTANT_info {
    public String name = "CONSTANT_Class";
    public short name_index = 0;

    public CONSTANT_Class_info(byte ta, short b) {
        size += 2;
        tag = ta;
        name_index = b;
    }
}
