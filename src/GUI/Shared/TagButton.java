package GUI.Shared;

import GUI.TagSelecter.TagSelecterController;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.Random;

/**
 * A button that displays the tag it is associated with.
 */
public class TagButton extends Button{


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** The image of this button */
    private ImageView btnImage;

    /** Whather or not this button is being hovered over */
    private boolean isHovered;

    private HBox tagDeleteBox;

    /** The type of tag image the tag button will have:
     * <li>
     * (1) Add image where the icon at the end is '+' <br></br>
     * (2) Remove image where the icon at the end is 'x'
     * </li>
     * */
    public enum TagType{
        ADD, REMOVE
    }


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Creates a tag button with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public TagButton(String text, TagType tagType) {
        super(text.replace("@", ""));
        isHovered = false;

        this.setOnMouseEntered(this::onBtnTagHovered);
        this.setOnMouseExited(this::onBtnTagHovered);
        this.setOnMouseDragged(this::onDrag);
        this.setOnMousePressed(this::onBeginDrag);
        this.setOnMouseReleased(this::onEndDrag);

        setImage(tagType);

        tagDeleteBox = TagSelecterController.getInstance().getTagDeleteBox();

        this.setStyle("-fx-background-color: transparent;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: 50%;" +
                "-fx-text-fill: #ffffff;");
    }

    /**
     * Sets the image of the tag button to a random color with a '+' icon at the end.
     */
    private void setImage(TagType tagType){

        Image image;

        /* Create image and its imageview to display on the button*/
        if(tagType == TagType.ADD) {
            image = new Image(File.separator + "resources" + File.separator + "tag_add_icon.png");
        }
        else{
            image = new Image(File.separator + "resources" + File.separator + "tag_remove_icon.png");
        }

        btnImage = new ImageView(image);
        btnImage.setClip(new ImageView(image));

        /* Change button image's color to a random color */
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue((new Random().nextInt(201) - 100)/100.0);
        btnImage.setEffect(colorAdjust);

        /* Set the button's image to the newly created image view */
        this.setGraphic(btnImage);
        this.setContentDisplay(ContentDisplay.CENTER);
    }

    /**
     * <b>Only to be used when adding a tag from the TagSelector GUI to the TagBox GUI.</b>
     *
     * <br><br>
     *
     * Changes the tag button's image to the same color as the given imageView, but instead with an 'X' icon at the end.
     *
     * @param newImageView the imageView to set to
     */
    public void setImageForTagBox(ImageView newImageView){

        Image image =  new Image(File.separator + "resources" + File.separator + "tag_remove_icon.png");
        ImageView tempImageView = new ImageView(image);
        tempImageView.setClip(new ImageView(image));
        tempImageView.setEffect(newImageView.getEffect());

        this.setGraphic(tempImageView);
        this.setContentDisplay(ContentDisplay.CENTER);
    }


    /**
     * <b>Action:</b> Tag button is hovered or un-hovered.
     *
     * <br><br>
     *
     * <b>Job:</b> Animates the button.
     *
     * @param e event that occurred
     */
    private void onBtnTagHovered(MouseEvent e){

        /* If the mouse isn't hovered over the btn, then set it to be hovered over and animate it up */
        if(!isHovered){
            isHovered = true;
            getScene().setCursor(Cursor.HAND);
            setTranslateY(getTranslateY() - 3);
        }
        /* Otherwise, set the button to be not hovered over and animate it back to original position */
        else{
            isHovered = false;
            getScene().setCursor(Cursor.DEFAULT);
            setTranslateY(getTranslateY() + 3);
        }
    }

    /* HANDLES DRAGING */
    private double mPosX, mPosY, bPosX, bPosY;

    /**
     * <b>Action:</b> Begins dragging button.
     *
     * <br><br>
     *
     * <b>Job:</b> Record the initial position of the mouse and the button.
     *
     * @param m mouse event that occurred
     */
    private void onBeginDrag(MouseEvent m){
        mPosX = m.getSceneX();
        mPosY = m.getSceneY();
        bPosX = getTranslateX();
        bPosY = getTranslateY();
        getScene().setCursor(Cursor.CLOSED_HAND);
    }

    /**
     * <b>Action:</b> While button is being dragged.
     *
     * <br><br>
     *
     * <b>Job:</b> Update the position of the button to that of the mouse so that the button follows it.
     *
     * @param m mouse event that occurred
     */
    private void onDrag(MouseEvent m){
        setTranslateX(m.getSceneX() - mPosX + bPosX);
        setTranslateY(m.getSceneY() - mPosY + bPosY);
        onBeginDrag(m);
    }

    /**
     * <b>Action:</b> Dragging ends, button is dropped.
     *
     * <br><br>
     *
     * <b>Job:</b> Record  the final position of the button. If the button was dropped onto the tagDeleteBox then delete
     * the tag.
     *
     * @param m mouse event that occurred
     */
    private void onEndDrag(MouseEvent m) {

        try {
            getScene().setCursor(Cursor.DEFAULT);
        }catch (NullPointerException ignored){}

        double finalBPosX = getLayoutX() + getTranslateX();
        double finalBPosY = getLayoutY() + getTranslateY();
        double boxPosX = tagDeleteBox.getLayoutX();
        double boxPosY = tagDeleteBox.getLayoutY();

        /* Check if this button is 100 pixels of the tagDeleteBox, if it is, delete it */
        if(boxPosX - 100 <= finalBPosX && finalBPosX <= boxPosX + 100
                && boxPosY - 50 <= finalBPosY && finalBPosY <= boxPosY + 50){
            TagSelecterController.getInstance().deleteTagButton(this);
        }
        /* Otherwise, place button back to its original position. */
        else {
            setTranslateX(0);
            setTranslateY(0);
        }
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    public ImageView getBtnImage() {
        return btnImage;
    }

    public void setBtnImage(ImageView btnImage) {
        this.btnImage = btnImage;
    }
}
