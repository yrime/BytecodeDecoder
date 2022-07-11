package filestructure.attributes.frames;

import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class SAME_LOCALS_1_STACK_ITEM_frame_EXTENDED extends STACK_MAP_FRAME{
    //u1 frame_type = SAME_LOCALS_1_STACK_ITEM_EXTENDED; /* 247 */
    short offset_delta;
    verification_type_info stack[];//[1];
    public SAME_LOCALS_1_STACK_ITEM_frame_EXTENDED(byte b1, byte[] bytes){
        name = "SAME_LOCALS_1_STACK_ITEM_frame_EXTENDED";
        frame_type = b1;
        offset_delta = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 0, (short) 0, 0, 2));
        stack = new verification_type_info[1];
        stack[0] = new verification_type_info(Arrays.copyOfRange(bytes, 2, bytes.length));
        size = stack[0].size + 3;
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s\n\t\t\toffset_delta: %d\n\t\t\t" +
                "verification_type_info stack %s", frame_type, name, offset_delta, getVerification());
    }
    private String getVerification(){
        String out = "";
        for(int i = 0; i < stack.length; ++i){
            out += stack[i].printVerTyInfo();
        }
        return out;
    }
}
