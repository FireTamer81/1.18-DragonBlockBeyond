package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import javax.annotation.Nullable;

public class WindowNodeData {
    private final String identifier; //name
    private final V2_ContentBox contentBoxObject; //This holds the position, color, and other data used in drawing the object
    //private final V2_WindowButton buttonObject;

    public WindowNodeData(String identifierIn, V2_ContentBox contentBoxObjectIn) {
        this.identifier = identifierIn;
        this.contentBoxObject = contentBoxObjectIn;
        //this.buttonObject = null;
    }

    public WindowNodeData(String identifierIn, V2_WindowButton buttonObjectIn) {
        this.identifier = identifierIn;
        this.contentBoxObject = buttonObjectIn;
        //this.buttonObject = buttonObjectIn;
    }

    /*
    public boolean isObjectButton() {
        if (buttonObject != null && contentBoxObject == null) {
            return true;
        } else {
            return false;
        }
    }
    */

    public boolean isObjectButton() {
        if (contentBoxObject instanceof V2_WindowButton) {
            return true;
        } else return false;
    }

    public String getIdentifier() { return identifier; }
    public V2_ContentBox getContentBoxObject() { return contentBoxObject; }
    //public V2_WindowButton getBasicButtonObject() { return buttonObject; }
}
