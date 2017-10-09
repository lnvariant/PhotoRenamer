package photo_renamer;

import photo_renamer.Logging.ImageLog;
import photo_renamer.Logging.ImageLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Manages and administrates the program to make sure it is running properly.
 */
public class ImageManager {


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Instantiates a new Image manager.
     *
     */
    private ImageManager(){}


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Configure the program to:
     *
     * - Load image objects, image logs, and image tags data
     */
    public void configure(){
        File imageObjs = new File("." + File.separator + "imageobjs.ser");
        File imageLogs = new File("." + File.separator + "imagelogs.ser");
        File imageTags = new File("." + File.separator + "imagetags.ser");

        if(imageObjs.exists() && imageLogs.exists() && imageTags.exists()) {
            loadData();
            System.out.println("FILES LOADED");
        }
        else{
            System.out.println("NO FILES LOADED");
        }
    }

    /**
     * Saves the following data:
     *
     * - All the images objects ever created due to renaming a file
     * - All the image logs ever created
     * - The current list of tags the user has used
     */
    public void saveData(){

        OutputStream file;
        ObjectOutputStream out;

        try {
            /* Save the list containing all the image objects*/
            file = new FileOutputStream("." + File.separator + "imageobjs.ser");
            out = new ObjectOutputStream(new BufferedOutputStream(file));
            out.writeObject(Image.getAllImages());
            out.close();
        }catch (Exception e){}

        try {
            /* Save queue containing all the image logs */
            file = new FileOutputStream("." + File.separator + "imagelogs.ser");
            out = new ObjectOutputStream(new BufferedOutputStream(file));
            out.writeObject(ImageLogger.getInstance().getImageLogs());
            out.close();
        }catch (Exception e){}

        try {
            /* Save the list containing all the image tags */
            file = new FileOutputStream("." + File.separator + "imagetags.ser");
            out = new ObjectOutputStream(new BufferedOutputStream(file));
            out.writeObject(ImageTag.getAllTags());
            out.close();
        }catch (Exception e){}
    }

    /**
     * Loads the following data:
     *
     * - All the images objects ever created due to renaming a file
     * - All the image logs ever created
     * - The current list of tags the user has used
     */
    public void loadData(){

        InputStream file;
        ObjectInputStream in;

        try {
            /* Load file containing the list of all the image objects */
            file = new FileInputStream("." + File.separator + "imageobjs.ser");
            in = new ObjectInputStream(new BufferedInputStream(file));
            Image.setAllImages((HashMap<String, Image>) in.readObject());
            in.close();
        }catch (Exception e) {}

        try {
            /* Load the file containing all the image logs */
            file = new FileInputStream("." + File.separator + "imagelogs.ser");
            in = new ObjectInputStream(new BufferedInputStream(file));
            ImageLogger.getInstance().setImageLogs((ConcurrentLinkedDeque<ImageLog>) in.readObject());
            in.close();
        }catch (Exception e){}

        try {
            /* Load the file containing all the image tags */
            file = new FileInputStream("." + File.separator + "imagetags.ser");
            in = new ObjectInputStream(new BufferedInputStream(file));
            ImageTag.setAllTagsList((ArrayList<ImageTag>) in.readObject());
            in.close();
        }catch (Exception e){}
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static ImageManager getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static final ImageManager INSTANCE = new ImageManager();
    }
}
