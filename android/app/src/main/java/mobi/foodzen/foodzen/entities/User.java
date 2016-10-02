package mobi.foodzen.foodzen.entities;

import java.util.ArrayList;

/**
 * Created by yegia on 16.09.2016.
 */
public class User {

    private String mId;
    private String mEmail;
    private String mLogin;
    private String mAvatar;
    private String mAbout;
    private ArrayList<UserPhoto> mUserPhotoList = new ArrayList<>();
    private ArrayList<Place> mPlaceList = new ArrayList<>();

    public User() {
    }

    public User(String about, String avatar, String email, String id, String login, ArrayList<Place> placeList, ArrayList<UserPhoto> userPhotoList) {
        mAbout = about;
        mAvatar = avatar;
        mEmail = email;
        mId = id;
        mLogin = login;
        mPlaceList = placeList;
        mUserPhotoList = userPhotoList;
    }

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public ArrayList<Place> getPlaceList() {
        return mPlaceList;
    }

    public void setPlaceList(ArrayList<Place> placeList) {
        mPlaceList = placeList;
    }

    public ArrayList<UserPhoto> getUserPhotoList() {
        return mUserPhotoList;
    }

    public void setUserPhotoList(ArrayList<UserPhoto> userPhotoList) {
        mUserPhotoList = userPhotoList;
    }
}
