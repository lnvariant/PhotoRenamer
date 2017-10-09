package GUI.MenuBar;


import GUI.FileExplorer.FileExplorerController;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Controls the all the components of the Menu Bar GUI located at the top of the main window.
 */
public class MenuBarController {

    private MenuBarController() {}

    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Constructs this controller with the given GUI components allowing it to control functionality for them.
     *
     */
    public void construct(Menu menuFile, Menu menuEdit, Menu menuHelp){

        /* Set up the File drop down menu */
        MenuItem menuItemOpen = new MenuItem("Open...");
        menuItemOpen.setOnAction(this::onItemOpenClick);
        menuFile.getItems().add(menuItemOpen);

        /* Set up the Edit drop down menu */
        MenuItem menuItemRevert = new MenuItem("Revert...");
        menuItemRevert.setOnAction(this::onItemRevertClick);
        menuEdit.getItems().add(menuItemRevert);

        /* Set up the Help drop down menu */
        MenuItem menuItemAbout = new MenuItem("About");
        menuHelp.getItems().add(menuItemAbout);

    }

    /**
     * <b>Action:</b> Revert menu item is clicked in the Menu Bar GUI.
     *
     * <br><br>
     *
     * <b>Job:</b> Displays a revert window allowing user to revert the name of the selected image.
     *
     * @param e the event that occured
     */
    private void onItemRevertClick(ActionEvent e){
        RevertWindow.display("Revert Image Name...");

    }

    /**
     * <b>Action:</b> Open menu item is clicked in the Menu Bar GUI.
     *
     * <br><br>
     *
     * <b>Job:</b> Opens a file chooser allowing user to set a new directory.
     *
     * @param e the event that occured
     */
    private void onItemOpenClick(ActionEvent e){
        /* Exact same functionality as clicking the browse files button */
        FileExplorerController.getInstance().onBtnBrowseFilesClick(e);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static MenuBarController getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MenuBarController INSTANCE = new MenuBarController();
    }
}



