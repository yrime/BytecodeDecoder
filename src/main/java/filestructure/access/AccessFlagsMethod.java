package filestructure.access;

public class AccessFlagsMethod {
    final static short[] afm = {0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0020, 0x0040, 0x0080, 0x0100, 0x0400, 0x0800, 0x1000};
    final static String[] afs = {"ACC_PUBLIC", "ACC_PRIVATE", "ACC_PROTECTED", "ACC_STATIC", "ACC_FINAL", "ACC_SYNCHRONIZED",
            "ACC_BRIDGE", "ACC_VARARGS", "ACC_NATIVE", "ACC_ABSTRACT", "ACC_STRICT", "ACC_SYNTHETIC"};

    public static String getStringAccFlag(short a) {
        String out = "";
        for (int i = 0; i < afm.length; ++i) {
            if ((afm[i] & a) != 0) {
                out += (afs[i] + " ");
            }
        }
        return out;
    }
}
