package photo_renamer;

/*
***************************************************************************
*                                                                         *
*                             READ ME                                     *
*                                                                         *
***************************************************************************

*This README is a summmery of the README.md file, please refer to that README for an indepth explanation.*

In this code we do not use Swing. We use a newer version of Swing called JavaFX. It's essentially the same
thing except instead of 'JButtons' and 'JPanels', we have 'Buttons' and 'Panes'. JavaFX is intuitive for
the most part and follows the same logic as Swing. The main difference is that how the GUI looks is defined
in a separate file, called FXML. The file does not use Java, rather it uses tags to define how the GUI looks.
How each component works inside the GUI is controlled by Controller. The Controller connects the backend
with the View (FXML). In other words, the when something happens in the view, the controller picks it up, and
then calls the appropriate method inside the backend.

In this program, we have a 'GUI/Main' package that has a photorenamer.fxml and PhotoRenamerController. The
FXML file defines how the entire GUI will look, and the controller then connects each component inside
the GUI with its individual controllers. We have 5 main components: FileExplorer, TagSelecter, TagBar,
LogManager, and MenuBar. Each component has a controller, and any GUI that the controller needs is given
to it through its construct method. Each Controller is also a singleton, since we will only need just one
instance of each controller. The PhotoRenamerController defines the FXML variables, then creates an instance
of the other controllers, passes the FXML variables to the controllers, and then the individual controllers
control what to do with the FXML variables.

The PhotoRenamer class in the backend extends Application, which is how the program knows it's the main class
and that we're working with JavaFX. Inside the class' start method, we create a scene with the PhotoRenamerController
as the root of the scene. The PhotoRenamerController then loads up the main FXML file that will display all
the GUI. Then we create a window and scene for which the FXML file will be displayed inside of. We also create a separate
window for a start screen that shows a small tutorial and has the user select the starting directory for the
program. After this, anything the user does in the main program is controlled by the individual controllers
in the 'GUI' package.
*/

import GUI.Main.PhotoRenamerController;
import GUI.Main.StartSceenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main GUI application class that is run when the program starts. The main FXML file is loaded up and the start
 * screen is displayed when the application starts.
 */
public class PhotoRenamer extends Application {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /**
     * A framed window that contains all scenes
     */
    public Stage window;

    /**
     * A panel inside the window that contains all the GUI components
     */
    public Scene scene;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    public PhotoRenamer() {
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        /* Create a scene with the Main PhotoRenamerController as the root, and set the window to display the scene */
        scene = new Scene(new PhotoRenamerController(), 700, 500);

        window.setTitle("PhotoRenamer");
        window.setScene(scene);
        window.setMaximized(true);

        /* Add the application icon to the window */
        window.getIcons().add(new javafx.scene.image.Image("/resources/photo_renamer_icon.png"));

        /* We do not display the main window. We first display the start screen window (which is separate) from where
        * the user can view the tutorial and then select the starting directory. The start screen will show this main
        * window once a directory has been chosen. */

        /* Display the start screen */
        StartSceenController startSceen = new StartSceenController();
        startSceen.display();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        ImageManager.getInstance().saveData();
        System.out.println("SAVED");
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static PhotoRenamer getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PhotoRenamer INSTANCE = new PhotoRenamer();
    }
}