package GUI.TagBar;

import GUI.FileExplorer.FileExplorerController;
import GUI.LogManager.LogManagerController;
import GUI.Shared.AlertBox;
import GUI.Shared.TagButton;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import photo_renamer.Image;
import photo_renamer.ImageTag;

import java.util.Observable;
import java.util.Observer;

/**
 * Controls the all the components of the Tag Bar GUI located at the centre of the main window.
 */
public class TagBarController implements Observer{


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** A reference to the currently selected image */
    private Image imageRef;

    /* GUI Components */

    /** The box containing all the currently added tags */
    private HBox tagBox;

    /** A button that commits all the tags added to the image to the name of the image */
    private Button btnCommitTags;

    /** Keeps track of whether or not btnCommitTags is being hovered over */
    private boolean btnCommitTags_isHovered = false;

    private TagBarController() {}


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Constructs this controller with the given GUI components allowing it to control functionality for them.
     *
     * @param tagBox the box containing all of the tag buttons
     * @param btnCommitTags the button that commits (renames) the tags to the image file.
     */
    public void construct(HBox tagBox, Button btnCommitTags){
        this.tagBox = tagBox;
        this.btnCommitTags = btnCommitTags;

        btnCommitTags.setOnAction(this::onBtnCommitTagsClicked);
        btnCommitTags.setOnMouseEntered(this::onBtnCommitTagsHover);
        btnCommitTags.setOnMouseExited(this::onBtnCommitTagsHover);
    }

    /**
     * <b>Action:</b> Commit tags button is clicked.
     *
     * <br><br>
     *
     * <b>Job:</b> Appends all the added tags inside the tag box to the file name.
     *
     * @param e the event called
     */
    private void onBtnCommitTagsClicked(ActionEvent e){

        /* If there is no image selected, then display an error */
        if (imageRef == null){
            AlertBox.display("No image selected.");
            return;
        }

        /* Rename the image, log the rename, and refresh the directorying containing the image */
        if(imageRef.renameFile()) {
            LogManagerController.getInstance().addLog();
            FileExplorerController.getInstance().refreshDirectory(FileExplorerController.getInstance().getFieldCurrentDir().getText());
        }
    }

    /**
     * <b>Action:</b> Commit tags button is hovered or un-hovered.
     *
     * <br><br>
     *
     * <b>Job:</b> Animates the button.
     *
     * @param e event that occurred
     */
    private void onBtnCommitTagsHover(MouseEvent e){
        /* If the mouse isn't hovered over the btn, then set it to be hovered over and animate it up */
        if(!btnCommitTags_isHovered){
            btnCommitTags_isHovered = true;
            btnCommitTags.getScene().setCursor(Cursor.HAND);
            btnCommitTags.setTranslateY(btnCommitTags.getTranslateY() - 3);
        }
        /* Otherwise, set the button to be not hovered over and animate it back to original position */
        else{
            btnCommitTags_isHovered = false;
            btnCommitTags.getScene().setCursor(Cursor.DEFAULT);
            btnCommitTags.setTranslateY(btnCommitTags.getTranslateY() + 3);
        }
    }

    /**
     * Adds the given tag button to the tag box. Will not add the tag if the tag is already attached.
     *
     * @param tagButton the button to add to the tag box
     */
    public void addTagButton(TagButton tagButton){

        /* If there is no image selected, don't bother adding the tag */
        if (imageRef == null){
            AlertBox.display("No image selected.");
            return;
        }

        /* Make sure tag isn't already added */
        for(Node t : tagBox.getChildren()){
            if(((TagButton)t).getText().equals(tagButton.getText())){
                AlertBox.display("Tag is already attached.");
                return;
            }
        }

        /* Add the tag button to the tag box, and the Tag to the image */
        tagBox.getChildren().add(tagButton);
        imageRef.addTag(tagButton.getText());
        tagButton.setOnAction(this::onTagBtnClick);
    }

    /**
     * <b>Action:</b> Tag button is clicked in the Tag Bar GUI.
     *
     * <br><br>
     *
     * <b>Job:</b> Deletes the tag button (and tag) from the image's tag bar.
     *
     * @param e the event that occured
     */
    private void onTagBtnClick(ActionEvent e){

        /* Delete the tag from the image, and the tag button from the vertical box GUI */
        imageRef.deleteTag(((TagButton) e.getSource()).getText());
        tagBox.getChildren().remove(e.getSource());
    }

    /**
     * Update the reference to the image that is currently selected.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        imageRef = (Image) arg;
        tagBox.getChildren().clear();
        loadTags();
    }

    /**
     * Loads the list of the tags the user as used into the tag box.
     */
    private void loadTags(){
        if(imageRef.getAttachedTags() != null) {

            /* For every tag in allTags, create a corresponding TagButton to add to the tag box */
            for (ImageTag t : imageRef.getAttachedTags()) {
                TagButton tagButton = new TagButton(t.getName(), TagButton.TagType.REMOVE);
                tagButton.setOnAction(this::onTagBtnClick);
                tagBox.getChildren().add(tagButton);
            }
        }
    }


    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static TagBarController getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final TagBarController INSTANCE = new TagBarController();
    }
}
