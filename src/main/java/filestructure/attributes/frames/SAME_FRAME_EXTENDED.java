package filestructure.attributes.frames;

public class SAME_FRAME_EXTENDED extends STACK_MAP_FRAME{
    //u1 frame_type = SAME_FRAME_EXTENDED; /* 251 */
    short offset_delta;
    public SAME_FRAME_EXTENDED(byte b1, short s){
        name = "SAME_FRAME_EXTENDED";
        frame_type = b1;
        offset_delta = s;
        size = 3;
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s\n\t\t\toffset_delta: %d\n\t\t\t" +
                "verification_type_info %s", frame_type, name, offset_delta);
    }

}
