package GUI.FileExplorer;

import GUI.FileExplorer.PathNodes.DirectoryPathNode;
import GUI.FileExplorer.PathNodes.ImagePathNode;
import GUI.Shared.ProgressWindow;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import photo_renamer.Image;
import photo_renamer.PhotoRenamer;

import java.io.File;
import java.util.Observable;


/**
 * Controls the all the components of the File Explorer GUI located at the left in the main window.
 */
public class FileExplorerController extends Observable{


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** The currently selected image in the file explorer */
    private Image selectedImage;

    /* GUI Components */

    /** A tree view that shows all the directories and image files of the currently selected main directory */
    private TreeView<String> fileTreeView;

    /** An image view that displays the currently selected image (located in the middle of the main GUI) */
    private ImageView selectedImageView;

    /** A textfield containing the current file directory the entire program is working in */
    private TextField fieldCurrentDir;

    /** A button that opens a file chooser that changing the current working directory of the program */
    private Button btnBrowseFiles;

    /** Keeps track of whether or not btnBrowseFiles is being hovered over */
    private boolean btnBrowseFiles_isHovered = false;

    /** A label that displays the absolute path of the currently selected image */
    private Label lblImagePath;

    /**
     * Creates a Pane layout.
     */
    private FileExplorerController() {}


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Constructs this controller with the given GUI components allowing it to control functionality for them.
     *
     * @param fileTreeView the tree view displaying the directories and images
     * @param selectedImageView the component displaying the currently selected image
     * @param fieldCurrentDir the field containing the absolute path of the current working directory
     * @param btnBrowseFiles the button allowing user to choose the current working directory
     */
    public void construct(TreeView<String> fileTreeView, ImageView selectedImageView,
                          TextField fieldCurrentDir, Button btnBrowseFiles, Label lblImagePath){
        this.fileTreeView = fileTreeView;
        this.selectedImageView = selectedImageView;
        this.fieldCurrentDir = fieldCurrentDir;
        this.btnBrowseFiles = btnBrowseFiles;
        this.lblImagePath = lblImagePath;

        /* Set up action events for all the buttons */
        this.btnBrowseFiles.setOnAction(this::onBtnBrowseFilesClick);
        this.btnBrowseFiles.setOnMouseEntered(this::onBtnBrowseFilesHover);
        this.btnBrowseFiles.setOnMouseExited(this::onBtnBrowseFilesHover);
        this.fileTreeView.setOnMouseClicked(this::itemSelected);
    }

    /**
     * <b>Action:</b> Browse files button is clicked.
     *
     * <br><br>
     *
     * <b>Job:</b> Opens a DirectoryChooser from where the user can choose a directory. Then sets the dir as the current
     * working directory for the program.
     *
     * @param e the event that was called
     */
    public void onBtnBrowseFilesClick(ActionEvent e){

        /* Open the directory chooser and set the directory to the chosen directory */
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory Containing Images");
        File directory = directoryChooser.showDialog(PhotoRenamer.getInstance().window);

        if(directory != null) {
            /* Change the text field's text to the path of the directory */
            fieldCurrentDir.setText(directory.getAbsolutePath());

            /* Change the current working directory of the program to the selected directory */
            refreshDirectory(directory.getAbsolutePath());
        }
    }

    /**
     * <b>Action:</b> Browse files button is hovered or un-hovered.
     *
     * <br><br>
     *
     * <b>Job:</b> Animates the button.
     *
     * @param e event that occurred
     */
    private void onBtnBrowseFilesHover(MouseEvent e){
        /* If the mouse isn't hovered over the btn, then set it to be hovered over and animate it up */
        if(!btnBrowseFiles_isHovered){
            btnBrowseFiles_isHovered = true;
            btnBrowseFiles.getScene().setCursor(Cursor.HAND);
            btnBrowseFiles.setTranslateY(btnBrowseFiles.getTranslateY() - 3);
        }
        /* Otherwise, set the button to be not hovered over and animate it back to original position */
        else{
            btnBrowseFiles_isHovered = false;
            btnBrowseFiles.getScene().setCursor(Cursor.DEFAULT);
            btnBrowseFiles.setTranslateY(btnBrowseFiles.getTranslateY() + 3);
        }
    }

    /**
     * <b>Action:</b> A tree item is double clicked.
     *
     * <br><br>
     *
     * <b>Job:</b> If the clicked item is an image, then an image obj is instantiated and set to
     * the currently selected image.
     *
     * @param m the mouse event that occurred
     */
    private void itemSelected(MouseEvent m){
        try{
            if(m.getClickCount() == 2) {
                /* Get the selected item as an ImagePathNode. If the cast fails, then we will catch the error */
                ImagePathNode imagePathNode = (ImagePathNode) fileTreeView.getSelectionModel().getSelectedItem();

                /* Update the currently selected image to the one from the selected ImagePathNode */
                updateSelectedImage(new Image(imagePathNode.getFile()));

                /* Set the lbl's text to display the currently selected image's path */
                lblImagePath.setText("Selected Image: " + imagePathNode.getFilePath());
            }
        }
        catch (Exception ignored){}
    }

    /**
     * Updates the currently selected image to the given image.
     *
     * @param image the image to update to
     */
    private void updateSelectedImage(Image image){
        selectedImage = image;

        /* Update and setup the image in the GUI */
        javafx.scene.image.Image imageViewImage = new javafx.scene.image.Image(image.getFile().toURI().toString());
        selectedImageView.setImage(imageViewImage);
        selectedImageView.setFitWidth(imageViewImage.getWidth());
        selectedImageView.setFitHeight(imageViewImage.getHeight());

        /* Notify observers of change in the selected image*/
        setChanged();
        notifyObservers(image);
    }

    /**
     * Updates and sets the currently opened directory to the one given by the path.
     *
     * @param path the directory path to update to
     */
    public void refreshDirectory(String path){

        ProgressWindow.display("Setting up files...");

        /* Get the file at the given path, create a DirectoryPathNode with the file, and then populate it */
        File file = new File(path);
        DirectoryPathNode rootNode = new DirectoryPathNode(file);
        rootNode.populate();

        ProgressWindow.closeWindow();

        /* Expand the root node and then display it in the tree view */
        rootNode.setExpanded(true);
        this.fileTreeView.setRoot(rootNode);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    public TextField getFieldCurrentDir() {
        return fieldCurrentDir;
    }

    public Image getSelectedImage() {
        return selectedImage;
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static FileExplorerController getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final FileExplorerController INSTANCE = new FileExplorerController();
    }

}
