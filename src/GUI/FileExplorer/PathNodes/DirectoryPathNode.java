package GUI.FileExplorer.PathNodes;

import javafx.event.Event;
import javafx.scene.control.TreeItem;

import java.io.File;

/**
 * A path node that is only associated with directories files.
 */
public class DirectoryPathNode extends FilePathNode {


    /* ************************************************************************* *
     *                                                                           *
     * Static Variables                                                          *
     *                                                                           *
     * ************************************************************************  */

    /** The file path to the directory icons of every DirectoryPathNode */
    private static String fileOpenedIcon, fileClosedIcon;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Instantiates a new Directory path node with the given file.
     *
     * @param file the file the node represents
     */
    public DirectoryPathNode(File file) {
        super(file);

        /* Get image paths */
        if(fileOpenedIcon == null || fileClosedIcon == null) {
            fileOpenedIcon = File.separator + "resources" + File.separator + "icon_folder_opened.png";
            fileClosedIcon = File.separator + "resources" + File.separator + "icon_folder_closed.png";
        }

        /* Set the node image's as the icon of the open folder for DirectoryPathNodes */
        setNodeImage(fileClosedIcon);


        /* Add the item expanded and collapsed events */
        this.addEventHandler(TreeItem.branchCollapsedEvent(), this::collapsed); // Second param is the lambda expression: event -> collapsed(event)
        this.addEventHandler(TreeItem.branchExpandedEvent(), this::expanded);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * <b>Action:</b> When a directory item in the tree view is expanded (opened).
     *
     * <br><br>
     *
     * <b>Job:</b> Changes the icon of this directory to the open directory icon.
     *
     * @param e event that occured
     */
    private void expanded(Event e) {
        this.setNodeImage(fileOpenedIcon);
    }

    /**
     * <b>Action:</b> When a directory item in the tree view is collapsed (closed).
     *
     * <br><br>
     *
     * <b>Job:</b> Changes the icon of this directory to the closed directory icon.
     *
     * @param e event that occured
     */
    private void collapsed(Event e) {
        this.setNodeImage(fileClosedIcon);
    }


    /**
     * Populate this DirectoryPathNode's children with the contents of the directory associated with it.
     */
    public void populate() {
        final File[] files = file.listFiles();

        /* If there are no files in the directory associated with this, then don't bother with it */
        if(files == null){
            return;
        }

        /* Add all the files under the directory to the node's children */
        if (getChildren().isEmpty()) {
            for (File f : files) {

                /* If the file is a directory, then populate that directory as well */
                if(f.isDirectory()) {
                    DirectoryPathNode child = new DirectoryPathNode(f);
                    child.populate();
                    getChildren().add(child);
                }
                /* Otherwise, it's a file. So add it to the directory's children and associate it with an
                 * ImagePathNode if it's an image */
                else{
                    String fileName = f.getName();
                    String fileType = fileName.substring(fileName.indexOf('.') + 1).toLowerCase();

                    /* If the file is of type image then create a ImagePathNode */
                    if(fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg")){
                        getChildren().add(new ImagePathNode(f));
                    }
                }

            }
        }
    }
}
