package filestructure.attributes.frames;

public abstract class STACK_MAP_FRAME {
    byte frame_type;
    int size;
    String name;
    public abstract String getFrame();
}
