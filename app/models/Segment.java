package models;

import java.util.ArrayList;
import javax.persistence.*;
import java.io.*;
import java.util.*;

/* A segment of a story containing that segment's description and content
 * @author Java the Hutt
 * @version 0.1
 */
public class Segment implements Serializable{

    //Segment and story ids
    private int id = 0; //default = 0 for root of story
    private int storyId = 0;

    //Segment information
    private String title;
    private String author;
    private String content;  
    private String[] tags;
    private Segment parentSeg = null;
    private ArrayList<Segment> childSegs;
    private static final long serialVersionUID = 1L;
    
    /* Constructor for Segment 
     * @param parentSeg - parent segment of this segment
     * @param title - title of this segment
     * @param author - author of this segment
     * @param content - content of this segment
     * @param tags - tags of this segment
     */
    public Segment(String title, String author, String content, String[] tags) {
        this.title = title;
        this.author = author;
        this.content = content;
        if (tags == null){
          tags = new String[]{};  
        } else{
            this.tags = tags;
        }
        this.childSegs = new ArrayList<Segment>();  
    }

    //Accessors and Mutators
    public int getStoryId() {
        return this.storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public boolean addChild(Segment child) {
        return childSegs.add(child);
    }

    public Segment getParentSeg() {
        return this.parentSeg;
    }
    
    public void setParentSeg(Segment seg) {
        this.parentSeg = seg;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public int getSegmentId() {
        return this.id;
    }

    public void setSegmentId(int segId) {
        this.id = segId;
    }

    public String[] getTags() {
        return this.tags;
    }

    public ArrayList<Segment> getChildSegs() {
        return this.childSegs;
    }

    /* Gets the ids of this segment up to the root and returns in an 
     * array list
     * @return an arraylist of segment ids from this segment to the root
     *  (ordered by parent id -> child id)
     */
    public ArrayList<Integer> getParentSegIds() {
         ArrayList<Integer> parentSegIds = new ArrayList<Integer>();
         getParentSegIds(this, parentSegIds);
         Collections.reverse(parentSegIds);
         parentSegIds.add(this.id);
         return parentSegIds;
    }

    /* Recursive call to get the ids of the parent segments up to the root
     * @param parent - segment to get the parent of 
     * @param parentSegIds - ids of the parent segment 
     * @return the ids of the parent segment up to the root
     */
    private ArrayList<Integer> getParentSegIds(Segment parent, ArrayList<Integer> parentSegIds) {
        if (parent.getSegmentId() == 0) {
             return parentSegIds;
        }
        parentSegIds.add(parent.getParentSeg().getSegmentId());
        return getParentSegIds(parent.getParentSeg(), parentSegIds);
    }

    /* Cleans up the content of the segment for display on the 
     * front page of the website. Truncates message/cleans
     * up html.
     * @return cleaned and shortened version of the content
     */
    public String displayContent() {
        if (this.content == null){
            this.content = "";
        }
        String htmlString = new String(this.content);
        String noHTMLString = htmlString.replaceAll("\\<.*?>", "");
        if(noHTMLString.length() > 251) {
            return noHTMLString.substring(0, 250) + "...";
        }
        return noHTMLString;
    }

    /* Checks if this segment is an ending segment of this story
     * @return true if the segment is an ending segment, otherwise false
     */
    public boolean isLeafNode(){
        return this.childSegs.isEmpty();
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Segment))return false;
        Segment otherSegment = (Segment) other;
        if (this.id != otherSegment.id) return false;
        if (this.storyId != otherSegment.storyId) return false;
        if (!this.title.equals(otherSegment.title)) return false;
        if (!this.author.equals(otherSegment.author)) return false;
        if (!this.content.equals(otherSegment.content)) return false;
        if (!Arrays.equals(this.tags, otherSegment.tags)) return false;
        if (!(this.parentSeg == null && otherSegment.parentSeg == null) && !this.parentSeg.equals(otherSegment.parentSeg)) {
            return false;
        }
        if (!this.childSegs.equals(otherSegment.childSegs)) return false;
        return true;
    }
}