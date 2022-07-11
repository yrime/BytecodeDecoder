package filestructure.constants;

public class CONSTANT_Integer_info extends CONSTANT_info {
    public String name = "CONSTANT_Integer";
    public int bytes;

    public CONSTANT_Integer_info(byte ta, int a) {
        size += 4;
        tag = ta;
        bytes = a;
    }
}
