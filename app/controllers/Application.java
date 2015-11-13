package controllers;
import play.*;
import play.mvc.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import views.html.*;
import java.sql.SQLException;
import java.util.*;



public class Application extends Controller {

    AppController myAppController = new AppController();

    public Result index() {

        //Story s = new Story();
        //Ebean.save(s);

        //Get all stories
        ArrayList<Story> storyList = new ArrayList<Story>();
        //For each story, add to storyList 
        //public Segment(Segment parentSeg, String title, String author, String content, int id, String[] tags)
        Segment test1 = new Segment(null, "Title 1", "Author 1", "Content 1", 100, new String[] {"a", "b"});
        Segment test2 = new Segment(null, "Title 2", "Author 2", "Content 2", 100, new String[] {"x", "y"});
        Story s1 = new Story(test1, 1);
        storyList.add(s1);
        Story s2 = new Story(test2, 2);
        storyList.add(s2);   

        return ok(index.render("Homepage", storyList));
    }

    public Result search(){
        //Get all stories
        ArrayList<Story> storyList = new ArrayList<Story>();
        //For each story, add to storyList 
        //public Segment(Segment parentSeg, String title, String author, String content, int id, String[] tags)
        Segment test1 = new Segment(null, "Title 1", "Author 1", "Content 1", 100, new String[] {"a", "b"});
        Segment test2 = new Segment(null, "Title 2", "Author 2", "Content 2", 100, new String[] {"x", "y"});
        Story s1 = new Story(test1, 1);
        storyList.add(s1);
        Story s2 = new Story(test2, 2);
        storyList.add(s2);
        
        return ok(index.render("Results", storyList));
    }

    /* Make controller object and set form.get("name") */
    public Result facebookName() {
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
            System.out.println(form.get("name"));
            session("name", form.get("name"));
    		return ok("Sucess");
    	}
    	return badRequest();
    }

    public Result newStory(){
    	return ok(newStory.render("New Story"));
    }

    /* create a new story from form data */
    public Result newStorySubmit() throws SQLException{
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            String title = form.get("title");
            System.out.println(title);
            String content = form.get("content");
            System.out.println(content);
            String tagsRaw = form.get("tags");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            System.out.println(tags);
            System.out.println(session("name"));
            Segment seg = new Segment(null, title, session("name"),
                content, 0, tags);
            Story myStory = myAppController.createStory(seg);
            return ok("Submitted");
        }
    }

    public Result newFork(){
        return  ok(newStory.render("New Fork"));
    }

    /* create a new fork from form data */
    public Result newForkSubmit(){
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            String title = form.get("title");
            String content = form.get("content");
            String tagsRaw = form.get("tags");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            Segment seg = new Segment(null, title, session("name"), content, 0, tags);
            //add segment to story

            return ok("Submitted");
        }
    }
}
