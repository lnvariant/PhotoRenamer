package GUI.FileExplorer.PathNodes;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A general path node item in a tree view GUI. Keeps track of the file, its path, and the image of this node displayed in
 * the tree view.
 */
public class FilePathNode extends TreeItem<String>{


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** The file associated with this node */
    protected File file;

    /** The path of the file associated with this node */
    protected String filePath;

    /** The image of this node displayed in the tree view GUI */
    protected Image nodeImage;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Instantiates a new File path node with the given file.
     *
     * @param file the file the node represents
     */
    public FilePathNode(File file){
        super(file.getName());

        this.file = file;
        this.filePath = file.getPath();
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Sets the image of the node to the image at the given path.
     *
     * @param path the path to the image file
     * @return true if the image was found and set successfully
     */
    protected boolean setNodeImage(String path){
        nodeImage = new Image(path);
        System.out.println("Path set to: " + path);
        this.setGraphic(new ImageView(nodeImage));
        return true;
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
