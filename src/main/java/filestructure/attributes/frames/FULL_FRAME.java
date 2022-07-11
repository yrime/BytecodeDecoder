package filestructure.attributes.frames;

import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class FULL_FRAME extends STACK_MAP_FRAME{
    //u1 frame_type = FULL_FRAME; /* 255 */
    short offset_delta;
    short number_of_locals;
    verification_type_info locals[];//[number_of_locals];
    short number_of_stack_items;
    verification_type_info stack[];//[number_of_stack_items];
    public FULL_FRAME(byte b1, byte[] bytes){
        name = "FULL_FRAME";
        size = 3;
        frame_type = b1;
        offset_delta = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 0, (short) 0, 0, 2));
        number_of_locals = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 2, (short) 0, 0, 2));
        locals = new verification_type_info[number_of_locals];
        number_of_stack_items = Short.reverseBytes(Conversion.byteArrayToShort(bytes, 4, (short) 0, 0, 2));
        stack = new verification_type_info[number_of_stack_items];
        for(int i = 0, j = 0; i < locals.length; ++i){
            locals[i] = new verification_type_info(Arrays.copyOfRange(bytes, j, bytes.length));
            j += locals[i].size;
            size += locals[i].size;
        }
        for(int i = 0, j = size - 3; i < stack.length; ++i){
            stack[i] = new verification_type_info(Arrays.copyOfRange(bytes, j, bytes.length));
            j += stack[i].size;
            size += stack[i].size;
        }
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s\n\t\t\toffset_delta: %d\n\t\t\t" +
                "numbers_of_locals %d\n\t\t\tverification_type_info locals: %s" +
                        "\n\t\t\tnumber_of_stack_items %d\n\t\t\tverification_type_info stack: %s",
                frame_type, name, offset_delta, number_of_locals, getVerification(locals),
                number_of_stack_items, getVerification(stack));
    }
    private String getVerification(verification_type_info[] a){
        String out = "";
        for(int i = 0; i < a.length; ++i){
            out += a[i].printVerTyInfo();
        }
        return out;
    }
}
