package photo_renamer.Logging;

import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * Keeps tracks of all the renaming ever done to files through image logs..
 */
public class ImageLogger {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    public ConcurrentLinkedDeque<ImageLog> imageLogs;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Instantiates a new Image logger.
     */
    private ImageLogger() {
        if(imageLogs == null) {
            imageLogs = new  ConcurrentLinkedDeque<>();
        }
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Adds an image log to the list of logs given the old name and the new name of the image.
     *
     * @param oldName the old name
     * @param newName the new name
     */
    public void addLog(String oldName, String newName){
        imageLogs.add(new ImageLog(oldName, newName));
    }


    /**
     * Returns the most recent log of the given image's name
     *
     * @param name the name of the image log to get
     * @return the image log
     */
    public ImageLog getImageLog(String name){
        for(ImageLog i : imageLogs){
            if(i.getCurrentName().equals(name)){
                return i;
            }
        }
        return null;
    }

    /**
     * Returns a string version of all the image logs.
     *
     * @return the string
     */
    public String viewLogs(){
        return imageLogs.toString();
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    public ConcurrentLinkedDeque<ImageLog> getImageLogs() {
        return imageLogs;
    }

    public void setImageLogs(ConcurrentLinkedDeque<ImageLog> imageLogs) {
        this.imageLogs = imageLogs;
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static ImageLogger getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final ImageLogger INSTANCE = new ImageLogger();
    }
}
