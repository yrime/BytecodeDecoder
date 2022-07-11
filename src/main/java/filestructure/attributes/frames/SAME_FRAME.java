package filestructure.attributes.frames;

public class SAME_FRAME extends STACK_MAP_FRAME{
    public SAME_FRAME(byte b){
        name = "SAME_FRAME";
        frame_type = b; // u1 frame_type = SAME; /* 0-63 */
        size = 1;
    }
    @Override
    public String getFrame() {
        return String.format("\t\t\tframe_type: %d %s", frame_type, name);
    }

}
