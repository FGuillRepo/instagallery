package instagallery.app.com.gallery.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Data implements Parcelable {
private Images images;
    private User user;
    private Likes likes;
    private Comments comments;
    private int position;

    public Images getImages() {
        return images;
    }
 
    public User getUser() {
        return user;
    }

    public Likes getLikes() {
        return likes;
    }

    public Comments getComments() {
        return comments;
    }




    public class User implements Serializable{
 
        private String profile_picture;
 
        private String full_name;
 
        public String getProfile_picture() {
            return profile_picture;
        }
 
        public String getFull_name() {
            return full_name;
        }
    }

    public class Likes implements Serializable{

        private String count;

        public String getCount() {
            return count;
        }
    }

    public class Comments implements Serializable {

        private String count;

        public String getCount() {
            return count;
        }
    }





    public class Images implements Serializable{



        private Thumbnail thumbnail;
        private Standard_resolution standard_resolution;


        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public Standard_resolution getStandard_resolution() {
            return standard_resolution;
        }



        public class Thumbnail implements Serializable{

            private String url;

            public String getUrl() {
                return url;
            }
        }

        public class Standard_resolution implements Serializable{
 
            private String url;
 
            public String getUrl() {
                return url;
            }
        }
    }



    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }




    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Data (Parcel parcel) {
        this.images = (Images) parcel.readSerializable();
        this.user = (User)parcel.readSerializable();
        this.likes = (Likes) parcel.readSerializable();
        this.comments = (Comments)parcel.readSerializable();
        this.position = parcel.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(images);
        dest.writeSerializable(user);
        dest.writeSerializable(likes);
        dest.writeSerializable(comments);
        dest.writeInt(position);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Data> CREATOR = new Creator<Data>() {

        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }

    };
}