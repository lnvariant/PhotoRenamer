package GUI.FileExplorer.PathNodes;

import java.io.File;

/**
 * A path node that is only associated with image files.
 */
public class ImagePathNode extends FilePathNode {


    /* ************************************************************************* *
     *                                                                           *
     * Static Variables                                                          *
     *                                                                           *
     * ************************************************************************  */

    /** The file path to the image icon of every ImagePathNode */
    private static String imageIconPath;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Instantiates a new Image path node with the given file.
     *
     * @param file the file the node represents
     */
    public ImagePathNode(File file) {
        super(file);

        /* Get image paths */
        if(imageIconPath == null) {
            imageIconPath = File.separator + "resources" + File.separator + "icon_image_file.png";
        }

        /* Set the node image's as the icon of the ImagePathNodes */
        setNodeImage(imageIconPath);
    }



}
