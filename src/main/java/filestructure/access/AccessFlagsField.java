package filestructure.access;

public class AccessFlagsField {
    final static short[] acf = {0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0040, 0x0080, 0x1000, 0x4000};
    final static String[] acs = {"ACC_PUBLIC", "ACC_PRIVATE", "ACC_PROTECTED", "ACC_STATIC", "ACC_FINAL",
            "ACC_VOLATILE", "ACC_TRANSIENT", "ACC_SYNTHETIC", "ACC_ENUM"};

    public static String getStringAccFlag(short a) {
        String out = "";
        for (int i = 0; i < acf.length; ++i) {
            if ((acf[i] & a) != 0) {
                out += (acs[i] + " ");
            }
        }
        return out;
    }
}
