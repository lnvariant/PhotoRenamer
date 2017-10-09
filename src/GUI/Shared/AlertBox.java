package GUI.Shared;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A window that is displayed upon calling its display method. Window displays an error message to the user.
 */
public class AlertBox {

    /**
     * Display the Alert Window with its contents to the user.
     *
     * @param message the error message to display in the window
     */
    public static void display(String message){
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
        window.setWidth(250);

        /* Create a label with the passed in error message */
        Label label = new Label();
        label.setStyle("-fx-text-fill: #ffffff;");
        label.setText(message);

        /* Button that closes the error window */
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        /* Set up the window and its contents */
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #494949;");
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene alertBox = new Scene(layout);
        window.setScene(alertBox);
        window.showAndWait();
    }
}
