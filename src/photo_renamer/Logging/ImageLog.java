package photo_renamer.Logging;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A log that keeps track of an image's previous and current names at a moment in time..
 */
public class ImageLog implements Serializable{


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    private String currentName;
    private String oldName;
    private String timeStamp;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Instantiates a new ImageLog with the given old name of the image and the new name.
     *
     * @param currentName the current name
     * @param oldName     the old name
     */
    public ImageLog(String oldName, String currentName) {
        this.currentName = currentName;
        this.oldName = oldName;
        this.timeStamp = (new SimpleDateFormat("dd/MM/yy HH:mm:ss")).format(new Date());
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Gets current name.
     *
     * @return the current name
     */
    public String getCurrentName() {
        return currentName;
    }

    /**
     * Sets current name.
     *
     * @param currentName the current name
     */
    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    /**
     * Gets old name.
     *
     * @return the old name
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * Sets old name.
     *
     * @param oldName the old name
     */
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * Gets timestamp.
     *
     * @return the timetstamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets timeStamp.
     *
     * @param timeStamp the timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
