package filestructure.attributes.frames;

public class SAME_LOCALS_1_STACK_ITEM_FRAME extends STACK_MAP_FRAME{
    verification_type_info[] stack;//[1];
    public SAME_LOCALS_1_STACK_ITEM_FRAME(byte b1, byte[] bytes){
        name = "SAME_LOCALS_1_STACK_ITEM_FRAME";
        frame_type = b1; /* 64-127 */
        stack = new verification_type_info[1];
        stack[0] = new verification_type_info(bytes);
        size = stack[0].size + 1;
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s\n\t\t\tverification_type_info %s",
                frame_type, name, getVerification());
    }
    private String getVerification(){
        String out = "";
        for(int i = 0; i < stack.length; ++i){
            out += stack[i].printVerTyInfo();
        }
        return out;
    }
}
