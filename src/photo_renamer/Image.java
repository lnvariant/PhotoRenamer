package photo_renamer;

import photo_renamer.Logging.ImageLogger;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents an image file with its attached tags.
 */
public class Image implements Serializable {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** The image file associated with this */
	private File imageFile;

    /** A list of the tags attached to this image */
	private List<ImageTag> attachedTags;

	/** A list of the previous files names of the image */
	private List<String> previousFileNames;

	/** The original name of the image */
	private String ogName = "";

	/** A list of all image objects every renamed */
	private static Map<String, Image> allImages;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

	/**
	 * Instantiates a new Image.
	 *
	 * @param file the file
	 */
	public Image(File file){
		this.imageFile = file;
		this.attachedTags = new ArrayList<>();
		this.previousFileNames = new ArrayList<>();
		this.ogName = this.imageFile.getName();

		if(allImages == null){
			allImages = new HashMap<>();
		}

		/* If there is a saved version of this Image on disc, then load its tags and previous file names onto this image */
		loadSavedImage();

		/* Load any tags that have been attached to the name of the image outside the program */
		parseTags();
	}


	/* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

	/**
	 * Add the given tag to the list of attached tags if it isn't already there.
	 *
	 * @param name the name
	 */
	public void addTag(String name){
		ImageTag tag = new ImageTag(name);

		if(!attachedTags.contains(tag)) {
			attachedTags.add(tag);
		}
	}

	/**
	 * Delete given tag from the list of all the tags and add it to the list of previously used tags.
	 *
	 * @param name the name
	 */
	public void deleteTag(String name){
		for (ImageTag t: attachedTags){
			if (("@" + name).equals(t.getName())){
				attachedTags.remove(t);
                return;
			}	
		}
	}

	/**
	 * Rename the image file with the attachedTags appended to it.
	 *
	 */
	public boolean renameFile(){

		/* Add current file name to list of previousFileNames */
		if(!previousFileNames.contains(getName())){
			previousFileNames.add(getName());
		}

		/* Create a str with all the attached tags in it */
		String addedTags = "";
		for (ImageTag tag: attachedTags){
			addedTags += tag.getName() + " ";
		}

		/* Get the new name of the file with the addedTags appended to the end of it */
		String newName = ogName.contains("@") ? (ogName.substring(0, ogName.indexOf('@')).trim()
										  + " "
										  + addedTags.trim()).trim()
				                          + ogName.substring(ogName.indexOf('.'))

										  : (ogName.substring(0, ogName.indexOf('.')).trim()
										  + " "
										  + addedTags.trim()).trim()
										  + ogName.substring(ogName.indexOf('.'));

		/* Create a new file with the new name and path to the image file being renamed */
		String newPath = imageFile.getAbsolutePath().substring(0, imageFile.getAbsolutePath()
				                  .lastIndexOf(File.separator)) + File.separator;
		File fileWithName = new File(newPath + newName);

		/* Rename the image. If the rename is successful, then update the current reference to the image file to the
		* newly named file */
        if(imageFile.renameTo(fileWithName)){

            /* Since the file variable still refers to old file name+path, must change it to the new file name+path */
			imageFile = fileWithName;

			/* Create a log of the rename */
            ImageLogger.getInstance().addLog(previousFileNames.get(previousFileNames.size() - 1), newName);

			/* Since the rename was successful, we want to keep track of this image and its tags, so we add it to a
			* list of Images that will persist even after the program closes. */
			addImage(this);
            return true;
        }
        /* If the rename fails, return false. */
        else {
            return false;
        }
	}

	/**
	 * Reverts the name of the Image to the given name as long as it is one of the previous names of the image ifle.
	 *
	 * @param name the previous name to revert to
	 */
	public boolean revertName(String name){

		if(previousFileNames.contains(name)){
			String newPath = imageFile.getAbsolutePath().substring(0, imageFile.getAbsolutePath().lastIndexOf(File.separator)) + File.separator;
			File fileWithName = new File(newPath + name);

             /* Since the file variable still refers to old file name+path, must change it to the new file name+path */
			if(imageFile.renameTo(fileWithName)){
				imageFile = fileWithName;
				return true;
			}
			else {
				return false;
			}
		}

		return false;
	}

	/**
	 * If this image was saved in a previous run of the program, then load those saved image's attached tags and previous
	 * file names lists onto this image.
	 */
	private void loadSavedImage(){

		String key = getName();

		if(allImages.containsKey(key)){
			previousFileNames = allImages.get(key).getPreviousFileNames();
			attachedTags = allImages.get(key).getAttachedTags();
		}
	}

	/**
	 * If this image file had tags added to it outside of the program, then those tags will be parsed, converted into
	 * ImageTags, and then attached to this Image's list of tags.
	 */
	private void parseTags(){
		Pattern p = Pattern.compile("@[^@.\\s]*");
		Matcher m = p.matcher(imageFile.getName());

		while(m.find()){
			addTag(m.group());
		}
	}

	/**
	 * Adds the given image to the list of all images ever renamed.
	 *
	 * @param image the image to add
	 */
	private void addImage(Image image){
		if(!allImages.containsKey(image.getName())){
			allImages.put(image.getName(), image);
		}
	}


	/* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Get name
     *
     * @return the string
     */
    public String getName(){
        return imageFile.getName();
    }

	/**
	 * Gets attached tags.
	 *
	 * @return the attached tags
	 */
	public List<ImageTag> getAttachedTags() {
		return attachedTags;
	}

	/**
	 * Sets attached tags.
	 *
	 * @param attachedTags the attached tags
	 */
	public void setAttachedTags(List<ImageTag> attachedTags) {
		this.attachedTags = attachedTags;
	}

	/**
	 * Gets file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return imageFile;
	}

	/**
	 * Sets file.
	 *
	 * @param file the file
	 */
	public void setFile(File file) {
		this.imageFile = file;
	}

	/**
	 * Gets previous file names.
	 *
	 * @return the previous file names
	 */
	public List<String> getPreviousFileNames() {
		return previousFileNames;
	}

	/**
	 * Sets previous file names.
	 *
	 * @param previousFileNames the previous file names
	 */
	public void setPreviousFileNames(List<String> previousFileNames) {
		this.previousFileNames = previousFileNames;
	}

	public static Map<String, Image> getAllImages() {
		return allImages;
	}

	public static void setAllImages(Map<String, Image> allImages) {
		Image.allImages = allImages;
	}
}
