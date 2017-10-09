package GUI.MenuBar;

import GUI.FileExplorer.FileExplorerController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photo_renamer.Image;

/**
 * A window that appears upon calling its display method. The window allows the user to revert the name of a currently
 * selected image to one of it's old names.
 */
public class RevertWindow {

    /** The drop down containing all the previous file names of the selected image */
    private static ChoiceBox<String> previousNamesDropDown;
    /** The window containing the revert components */
    private static Stage window;

    /**
     * Display the Revert Window with its contents to the user.
     *
     * @param message the message to display in the window
     */
    public static void display(String message){

        /* Set up window */
        window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Revert Image Name");
        window.setWidth(250);

        /* Set up revert button */
        Button revertButton = new Button("Revert");
        revertButton.setOnAction(RevertWindow::onBtnRevert);

        /* Set up the drop down containing all the prev file names */
        previousNamesDropDown = new ChoiceBox<>();
        Image imageRef = FileExplorerController.getInstance().getSelectedImage();

        /* Adding previous filenames to previousNamesDropDown */
        if(imageRef != null){

            for(String prevName: imageRef.getPreviousFileNames()){
                previousNamesDropDown.getItems().add(prevName);
            }

            if(!imageRef.getPreviousFileNames().isEmpty()) {
                previousNamesDropDown.setValue(imageRef.getPreviousFileNames().get(0));
            }
        }

        /* Set up rest of the window */
        Label label = new Label();
        label.setStyle("-fx-text-fill: #ffffff;");
        label.setText(message);
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #494949;");
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label,previousNamesDropDown , revertButton);
        layout.setAlignment(Pos.CENTER);

        Scene alertBox = new Scene(layout);
        window.setScene(alertBox);
        window.show();


    }

    /**
     * <b>Action:</b> Revert button is clicked.
     *
     * <br><br>
     *
     * <b>Job:</b> Reverts the name of the selected image to the name selected in the drop down menu.
     *
     * @param e the event that was called
     */
    private static void onBtnRevert(ActionEvent e){
        Image imageRef = FileExplorerController.getInstance().getSelectedImage();

        /* Make sure the drop down contains a value and it isn't the empty string */
        if (previousNamesDropDown.getValue() != null || !previousNamesDropDown.getValue().equals("")) {
            imageRef.revertName(previousNamesDropDown.getValue());

            /* Refresh the directory containing the image to reflect changes */
            FileExplorerController.getInstance().refreshDirectory(FileExplorerController.getInstance().getFieldCurrentDir().getText());
            window.close();
        }

    }


}
