package instagallery.app.com.gallery.Model;

import java.io.Serializable;

public class AccessToken {

    private String access_token;

    public String getAccessToken() {
        return access_token;
    }
    public User user;

    public User getUser() {
        return user;
    }

    public class User implements Serializable {

        private String id;

        public String getId() {
            return id;
        }

    }

}