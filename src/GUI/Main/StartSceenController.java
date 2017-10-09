package GUI.Main;

import GUI.FileExplorer.FileExplorerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A start screen window that displays a small tutorial. To exit the start screen and display the main program, the user
 * must choose a directory.
 */
public class StartSceenController implements Initializable {

    @FXML
    Button btnChooseDir;

    public void display(){

        Stage window = new Stage();

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/Main/startscreen.fxml"));
            window.setTitle("PhotoRenamer");
            Scene scene = new Scene(root, 982, 534);
            window.setScene(scene);
            window.setResizable(false);
            window.getIcons().add(new Image("/resources/photo_renamer_icon.png"));
            window.show();
        }catch (Exception e){
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnChooseDir.setOnAction(this::onBtnChooseDirClick);
    }

    /**
     * <b>Action:</b> Choose directory button is clicked.
     *
     * <br><br>
     *
     * <b>Job:</b> Opens a DirectoryChooser from where the user can choose a directory. Then sets the dir as the current
     * working directory for the program.
     *
     * @param e the event that was called
     */
    private void onBtnChooseDirClick(ActionEvent e){
        /* Calls the method onBtnBrowseFilesClick which has the exact same job as this button */
        FileExplorerController.getInstance().onBtnBrowseFilesClick(e);

        /* If a directory was selected, then... */
        if(!FileExplorerController.getInstance().getFieldCurrentDir().getText().equals("Choose File Directory...")) {
            /* Close the start screen window and display the main program's window */
            ((Stage) btnChooseDir.getScene().getWindow()).close();
            ((Stage) FileExplorerController.getInstance().getFieldCurrentDir().getScene().getWindow()).show();
        }
    }
}
