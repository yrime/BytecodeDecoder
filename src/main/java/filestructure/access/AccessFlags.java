package filestructure.access;

public class AccessFlags {
    final static short[] acc = {0x0001, 0x0010, 0x0020, 0x0200, 0x0400, 0x1000, 0x2000, 0x4000};
    final static String[] ass = {"ACC_PUBLIC", "ACC_FINAL", "ACC_SUPER", "ACC_INTERFACE",
            "ACC_ABSTRACT", "ACC_SYNTHETIC", "ACC_ANNOTATION", "ACC_ENUM"};

    public static String getStringAccFlag(short a) {
        String out = "";
        for (int i = 0; i < acc.length; ++i) {
            if ((acc[i] & a) != 0) {
                out += (ass[i] + " ");
            }
        }
        return out;
    }
}
