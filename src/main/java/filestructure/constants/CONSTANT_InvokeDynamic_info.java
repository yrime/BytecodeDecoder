package filestructure.constants;

public class CONSTANT_InvokeDynamic_info extends CONSTANT_info {
    public String name = "CONSTANT_InvokeDynamic";
    public short bootstrap_method_attr_index;
    public short name_and_type_index;

    public CONSTANT_InvokeDynamic_info(byte ta, short a, short b) {
        size += 4;
        tag = ta;
        bootstrap_method_attr_index = a;
        name_and_type_index = b;
    }
}
