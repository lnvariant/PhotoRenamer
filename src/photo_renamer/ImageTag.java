package photo_renamer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tag attached to an image with an @ prefix.
 */
public class ImageTag implements Serializable{


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /* All the tags ever used */
    private static List<ImageTag> allTags;
    private String name;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Creates a tag with the '@' symbol prefixed to the name. Adds the tag to the list of all the tags if it isn't
     * already there.
     *
     * @param name the name of the tag
     */
    public ImageTag(String name){

        /* Prefix the name with '@' symbol, and make sure it isn't already in the passed in name */
        this.name =  "@" + name.replace("@", "");

        if(allTags == null){
            allTags = new ArrayList<>();
        }

        /* If the tag isn't empty and it isn't already in the list, add it to the list of all tags */
        if(!name.equals("") && !allTags.contains(this)) {
            allTags.add(this);
        }
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Deletes all occurences of tagName inside the list of all tags
     *
     * @param tagName the tag name
     */
    public static void deleteTag(String tagName){
        for(int i = 0; i < allTags.size(); i++){
            if(allTags.get(i).getName().equals("@" + tagName)){
                allTags.remove(i);
            }
        }
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Gets image tag name. Note that the name is prefixed with '@'.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets image tag name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets all tags.
     *
     * @return the all tags
     */
    public static List<ImageTag> getAllTags() {
        return allTags;
    }

    /**
     * Sets all tags list to a new list.
     *
     * @param allTags the all tags
     */
    public static void setAllTagsList(List<ImageTag> allTags) {
        ImageTag.allTags = allTags;
    }


    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof ImageTag)) {
            return false;
        }

        ImageTag other = (ImageTag) obj;

        return name.equals(other.getName());
    }
}
