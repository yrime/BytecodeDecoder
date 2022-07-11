package filestructure.constants;

public class CONSTANT_Float_info extends CONSTANT_info {
    public String name = "CONSTANT_Float";
    public int bytes;

    public CONSTANT_Float_info(byte ta, int a) {
        size += 4;
        tag = ta;
        bytes = a;
    }
}
