package filestructure.constants;

public class CONSTANT_Utf8_info extends CONSTANT_info {
    public String name = "CONSTANT_Utf8";
    public short length;
    public byte bytes[];

    public CONSTANT_Utf8_info(byte ta, short a, byte[] by) {
        size = size + 2 + a;
        tag = ta;
        length = a;
        //bytes = new byte[length];
        bytes = by;
    }
}
