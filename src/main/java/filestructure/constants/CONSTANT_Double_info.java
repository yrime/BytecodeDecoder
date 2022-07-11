package filestructure.constants;

public class CONSTANT_Double_info extends CONSTANT_info {
    public String name = "CONSTANT_Double";
    public int high_bytes;
    public int low_bytes;

    public CONSTANT_Double_info(byte ta, int a, int b) {
        size += 8;
        tag = ta;
        high_bytes = a;
        low_bytes = b;
    }
}
