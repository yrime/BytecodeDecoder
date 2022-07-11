package filestructure.attributes.frames;

import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class APPEND_FRAME extends STACK_MAP_FRAME{
    //u1 frame_type = APPEND; /* 252-254 */
    short offset_delta;
    verification_type_info locals[];//[frame_type - 251];
    public APPEND_FRAME(byte b1,byte[] bytes){
        name = "APPEND";
        frame_type = b1;
        offset_delta = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 0, (short) 0, 0, 2));
        locals = new verification_type_info[frame_type - 251];
        size = 3;
        for(int i = 0, j = 0; i < locals.length; ++i){
            locals[i] = new verification_type_info(Arrays.copyOfRange(bytes, j, bytes.length));
            j = locals[i].size;
            size += j;
        }
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s\n\t\t\toffset_delta: %d\n\t\t\t" +
                        "verification_type_info %s", frame_type, name, offset_delta, getVerification());
    }
    private String getVerification(){
        String out = "";
        for(int i = 0; i < locals.length; ++i){
            out += locals[i].printVerTyInfo();
        }
        return out;
    }
}
