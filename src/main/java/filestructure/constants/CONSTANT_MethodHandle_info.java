package filestructure.constants;

public class CONSTANT_MethodHandle_info extends CONSTANT_info {
    public String name = "CONSTANT_MethodHandle";
    public byte reference_kind;
    public short reference_index;

    public CONSTANT_MethodHandle_info(byte ta, byte a, short b) {
        size += 3;
        tag = ta;
        reference_kind = a;
        reference_index = b;
    }
}
