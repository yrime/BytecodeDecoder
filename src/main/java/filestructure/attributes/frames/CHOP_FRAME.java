package filestructure.attributes.frames;

public class CHOP_FRAME extends STACK_MAP_FRAME{
    //    u1 frame_type = CHOP; /* 248-250 */
    short offset_delta;
    public CHOP_FRAME(byte b1, short b2){
        name = "CHOP";
        frame_type = b1;
        offset_delta = b2;
        size = 3;
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s\n\t\t\toffset_delta: %d\n\t\t\t",
                frame_type, name, offset_delta);
    }
}
