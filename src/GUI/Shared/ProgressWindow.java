package GUI.Shared;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Window displayed to user upon calling its display method. Displays a progress bar to the user.
 */
public class ProgressWindow {

    private static Stage window;

    /**
     * Display the Progress Window with its contents to the user.
     *
     * @param message the message to display in the window
     */
    public static void display(String message){
        window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Loading");
        window.setWidth(250);

        /* Set up label with given message */
        Label label = new Label();
        label.setStyle("-fx-text-fill: #ffffff;");
        label.setText(message);

        /* Set up progress bar */
        ProgressBar progress = new ProgressBar();
        progress.setProgress(.5);

        /* Set up contents of window */
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #494949;");
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, progress);
        layout.setAlignment(Pos.CENTER);

        Scene progressBox = new Scene(layout);
        window.setScene(progressBox);
        window.show();

    }

    /**
     * Close this window.
     */
    public static void closeWindow(){
        window.close();
    }
}
