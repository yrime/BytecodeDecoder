package filestructure.access;

public class AccessflagsClass {
    final static short[] afc = {0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0200, 0x0400, 0x1000, 0x2000, 0x4000};
    final static String[] afs = {"ACC_PUBLIC", "ACC_PRIVATE", "ACC_PROTECTED", "ACC_STATIC", "ACC_FINAL",
            "ACC_INTERFACE", "ACC_ABSTRACT", "ACC_SYNTHETIC", "ACC_ANNOTATION", "ACC_ENUM"};

    public static String getStringAccFlag(short a) {
        String out = "";
        for (int i = 0; i < afc.length; ++i) {
            if ((afc[i] & a) != 0) {
                out += (afs[i] + " ");
            }
        }
        return out;
    }
}
