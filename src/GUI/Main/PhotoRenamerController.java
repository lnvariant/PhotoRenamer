package GUI.Main;

import GUI.FileExplorer.FileExplorerController;
import GUI.LogManager.LogManagerController;
import GUI.MenuBar.MenuBarController;
import GUI.TagBar.TagBarController;
import GUI.TagSelecter.TagSelecterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import photo_renamer.ImageManager;
import photo_renamer.Logging.ImageLog;
import photo_renamer.Logging.ImageLogger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the main GUI of the application. Makes sure that all components in the FXML (GUI) are associated with their
 * individual Controllers.
 */
public class PhotoRenamerController extends AnchorPane {

    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /* File Explorer GUI Variables */
    @FXML
    private TreeView<String> fileTreeView;
    @FXML
    private ImageView selectedImageView;
    @FXML
    private TextField filePathField;
    @FXML
    private Button browseFilesButton;
    @FXML
    private Label lblImagePath;
    FileExplorerController fileExplorerController;

    /* Tag Selecter GUI Variables */
    @FXML
    private VBox tagVBox;
    @FXML
    private TextField tagField;
    @FXML
    private Button addButton;
    @FXML
    private HBox tagDeleteBox;
    TagSelecterController tagSelecterController;

    /* Log Manager GUI Variables */
    @FXML
    private TableColumn<ImageLog, String> curNameCol;
    @FXML
    private TableColumn<ImageLog, String> oldNameCol;
    @FXML
    private TableColumn<ImageLog, String> timeStampCol;
    @FXML
    private TableView<ImageLog> logTable;
    LogManagerController logManagerController;

    /* Tag Bar GUI Variables */
    @FXML
    private HBox tagBox;
    @FXML
    private Button commitTagButton;
    TagBarController tagBarController;

    /* Menu Bar GUI Variables */
    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuEdit;
    @FXML
    private Menu menuHelp;
    MenuBarController menuBarController;

    /* Backend Singleton Variables */
    ImageLogger imageLogger;
    ImageManager imageManager;


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Load up the FXML main photorenamer.fxml file, set this as its controller, and then construct and initialize
     * all the other controllers.
     */
    public PhotoRenamerController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("photorenamer.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException ex) {
        }

        ImageManager.getInstance().configure();

        /* Set up all the GUI component controllers with their individual components */
        FileExplorerController.getInstance().construct(fileTreeView, selectedImageView, filePathField, browseFilesButton, lblImagePath);
        TagSelecterController.getInstance().construct(tagVBox, tagField, addButton, tagDeleteBox);
        LogManagerController.getInstance().construct(curNameCol, oldNameCol, timeStampCol, logTable);
        TagBarController.getInstance().construct(tagBox, commitTagButton);
        FileExplorerController.getInstance().addObserver(TagBarController.getInstance());
        MenuBarController.getInstance().construct(menuFile, menuEdit, menuHelp);
    }
}
