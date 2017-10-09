package GUI.LogManager;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import photo_renamer.Logging.ImageLog;
import photo_renamer.Logging.ImageLogger;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Controls the all the components of the Log Manager GUI located at the bottom in the main window.
 */
public class LogManagerController {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /* GUI Components */

    /** The table column containing all the current names of images renamed */
    private TableColumn<ImageLog, String> curNameCol;

    /** The table column containing all the old names of images renamed */
    private TableColumn<ImageLog, String> oldNameCol;

    /** The table column containing all the time stamps of the images renamed */
    private TableColumn<ImageLog, String> timeStampCol;

    /** The table view containing all the table columns */
    private TableView<ImageLog> logTable;

    private LogManagerController() {}


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Constructs this controller with the given GUI components allowing it to control functionality for them.
     *
     * @param curNameCol the column containing the current names of all the logs
     * @param oldNameCol the column containing the old names of all the logs
     * @param timeStampCol the column containing the time stamps of all the logs
     * @param logTable the log table containing all the columns
     */
    public void construct(TableColumn<ImageLog, String> curNameCol, TableColumn<ImageLog, String> oldNameCol,
                          TableColumn<ImageLog, String> timeStampCol, TableView<ImageLog> logTable){

        this.curNameCol = curNameCol;
        this.oldNameCol = oldNameCol;
        this.timeStampCol = timeStampCol;
        this.logTable = logTable;

        oldNameCol.setCellValueFactory(new PropertyValueFactory<>("oldName"));
        curNameCol.setCellValueFactory(new PropertyValueFactory<>("currentName"));
        timeStampCol.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));

        loadLogs();
    }

    /**
     * Adds the most recent log in ImageLogger's list of logs to the GUI table.
     */
    public void addLog(){
        logTable.getItems().add(ImageLogger.getInstance().getImageLogs().getLast());

    }

    private void loadLogs(){
        final ConcurrentLinkedDeque<ImageLog> imageLogs = ImageLogger.getInstance().getImageLogs();

        for(ImageLog l : imageLogs){
            logTable.getItems().add(l);
        }
    }


    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static LogManagerController getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final LogManagerController INSTANCE = new LogManagerController();
    }
}

