package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

public class WindowNodeData {
    private String identifier; //name
    private V2_ContentBox object; //This holds the position, color, and other data used in drawing the object

    public WindowNodeData(String identifierIn, V2_ContentBox contentBoxObject) {
        this.identifier = identifierIn;
        this.object = contentBoxObject;
    }

    public String getIdentifier() { return identifier; }

    //For some reason, I highly doubt that this will work
    public boolean isObjectButton() {
        if (object instanceof V2_ContentBox) {
            return false;
        } else {
            return true;
        }
    }

    //If isObjectbutton returns false, use this method to retrieve the node object.
    public V2_ContentBox getObject() {
        return object;
    }

    //If isObjectbutton returns true, use this method to retrieve the node object (Which would be a child extension of the contentBox class).
    //public V2_ContentBox getButtonObject() { return object; }
}
