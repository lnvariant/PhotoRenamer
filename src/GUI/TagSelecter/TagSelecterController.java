package GUI.TagSelecter;

import GUI.Shared.AlertBox;
import GUI.Shared.TagButton;
import GUI.TagBar.TagBarController;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import photo_renamer.ImageTag;

import java.util.HashSet;
import java.util.Set;

/**
 * Controls the all the components of the Tag Selecter GUI located at the right in the main window.
 */
public class TagSelecterController {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** Names of all the button tags added so far */
    private Set<String> buttonNames;

    /** A vertical box containing all the tags */
    private VBox tagVBox;

    /** A text field where the user can type in the tag they want to add */
    private TextField tagField;

    /** A button that adds a tag to the tagVBox with the name given in the tagField */
    private Button btnAdd;

    /** Keeps track of whether or not btnAdd is being hovered over */
    private boolean btnAdd_isHovered = false;

    /** A Hbox where tags can be dragged onto to delete them */
    private HBox tagDeleteBox;


    private TagSelecterController() {}


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Constructs this controller with the given GUI components allowing it to control functionality for them.
     *
     * @param tagVBox the box containing all the tags ever added
     * @param tagField the field to type in the tag to add
     * @param btnAdd the button that adds the tag typed into the text field
     */
    public void construct(VBox tagVBox, TextField tagField, Button btnAdd, HBox tagDeleteBox){
        this.tagVBox = tagVBox;
        this.tagField = tagField;
        this.btnAdd = btnAdd;
        this.tagDeleteBox = tagDeleteBox;

        buttonNames = new HashSet<>();
        this.btnAdd.setOnAction(this::onBtnAddClick);
        this.btnAdd.setOnMouseEntered(this::onBtnAddHover);
        this.btnAdd.setOnMouseExited(this::onBtnAddHover);

        tagField.setOnKeyPressed(this::pressEnter);

        loadTags();
    }

    /**
     * <b>Action:</b> Add button is clicked.
     *
     * <br><br>
     *
     * <b>Job:</b> Creates a tag button with the name given inside the text field and puts it inside the tag box.
     */
    private void onBtnAddClick(ActionEvent e){
        TagButton tagButton = new TagButton(tagField.getText(), TagButton.TagType.ADD);

        /* Only add the tag if the name in the field isn't empty and we haven't already added the tag */
        if (!tagField.getText().equals("") && !buttonNames.contains(tagButton.getText())){


            tagButton.setOnAction(this::onTagBtnClick);

            /* Add the tag's name to a list of previously added names to avoid adding duplicates to the box of tags */
            buttonNames.add(tagButton.getText());

            /* Add the tag button to the vertical box of all tags */
            tagVBox.getChildren().addAll(tagButton);

            /* Clear the tag text field's text and give focus back to it */
            tagField.clear();
            tagField.requestFocus();
        }
        else{
            /* Display an error if tag already exists */
            AlertBox.display("Tag already exists.");
        }
    }

    /**
     * <b>Action:</b> Add button is hovered or un-hovered.
     *
     * <br><br>
     *
     * <b>Job:</b> Animates the button.
     *
     * @param e event that occurred
     */
    private void onBtnAddHover(MouseEvent e){
        /* If the mouse isn't hovered over the btn, then set it to be hovered over and animate it up */
        if(!btnAdd_isHovered){
            btnAdd_isHovered = true;
            btnAdd.getScene().setCursor(Cursor.HAND);
            btnAdd.setTranslateY(btnAdd.getTranslateY() - 3);
        }
        /* Otherwise, set the button to be not hovered over and animate it back to original position */
        else{
            btnAdd_isHovered = false;
            btnAdd.getScene().setCursor(Cursor.DEFAULT);
            btnAdd.setTranslateY(btnAdd.getTranslateY() + 3);
        }
    }

    /**
     * <b>Action:</b> Tag button is clicked in the Tag Selecter GUI.
     *
     * <br><br>
     *
     * <b>Job:</b> Adds the tag button (and tag) to the currently selected image and its
     * tag box.
     *
     * @param e the event that occured
     */
    private void onTagBtnClick(ActionEvent e){

        /* Get the tag button that was clicked on */
        TagButton currentButton = (TagButton) e.getSource();

        /* Create a new tag button to send to the selected image's tag box */
        TagButton buttonToAdd = new TagButton(currentButton.getText(), TagButton.TagType.REMOVE);
        buttonToAdd.setImageForTagBox(currentButton.getBtnImage());

        /* Add the tag button to the selected image's tag box */
        TagBarController.getInstance().addTagButton(buttonToAdd);
    }

    /**
     * Calls the createTag method (creates a tag) when the enter key is pressed inside the tag textfield.
     *
     * @param k the key event that occurred
     */
    private void pressEnter(KeyEvent k){
        if(k.getCode().equals(KeyCode.ENTER)){
            onBtnAddClick(new ActionEvent());
        }
    }

    /**
     * Delete the given tag button from the tag box and the list of all tags.
     *
     * @param tagButton the button to delete.
     */
    public void deleteTagButton(TagButton tagButton){
        buttonNames.remove(tagButton.getText());
        tagVBox.getChildren().remove(tagButton);
        ImageTag.deleteTag(tagButton.getText());
    }

    /**
     * Loads the list of the tags the user as used into the tag box.
     */
    private void loadTags(){
        if(ImageTag.getAllTags() != null) {

            /* For every tag in allTags, create a corresponding TagButton to add to the tag box */
            for (ImageTag t : ImageTag.getAllTags()) {
                TagButton tagButton = new TagButton(t.getName(), TagButton.TagType.ADD);
                tagButton.setOnAction(this::onTagBtnClick);
                buttonNames.add(tagButton.getText());
                tagVBox.getChildren().add(tagButton);
            }
        }
    }

    public HBox getTagDeleteBox() {
        return tagDeleteBox;
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static TagSelecterController getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final TagSelecterController INSTANCE = new TagSelecterController();
    }
}
