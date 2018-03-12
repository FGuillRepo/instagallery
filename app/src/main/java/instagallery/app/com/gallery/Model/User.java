package instagallery.app.com.gallery.Model;

public class User {

    private String access_token;
    private String id;

    public User(String id,String access_token){
        this.access_token=access_token;
        this.id=id;
    }


    public String getAccess_token() {
        return access_token;
    }

    public String getId() {
        return id;
    }


}