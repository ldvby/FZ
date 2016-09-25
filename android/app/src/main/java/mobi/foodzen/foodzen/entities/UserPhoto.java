package mobi.foodzen.foodzen.entities;

import android.location.Location;

/**
 * Created by yegia on 22.09.2016.
 */

public class UserPhoto {

    private String mId;
    private String mUserId;
    private String mPlaceId;
    private String mReview;
    private float mRating;
    private Location mGeo;
    private int mDateCreated;
    private int mDateUpdated;

    public UserPhoto() {
    }

    public UserPhoto(int dateCreated, int dateUpdated, Location geo, String id, String placeId, float rating, String review, String userId) {

        mDateCreated = dateCreated;
        mDateUpdated = dateUpdated;
        mGeo = geo;
        mId = id;
        mPlaceId = placeId;
        mRating = rating;
        mReview = review;
        mUserId = userId;
    }

    public int getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(int dateCreated) {
        mDateCreated = dateCreated;
    }

    public int getDateUpdated() {
        return mDateUpdated;
    }

    public void setDateUpdated(int dateUpdated) {
        mDateUpdated = dateUpdated;
    }

    public Location getGeo() {
        return mGeo;
    }

    public void setGeo(Location geo) {
        mGeo = geo;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(String placeId) {
        mPlaceId = placeId;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public String getReview() {
        return mReview;
    }

    public void setReview(String review) {
        mReview = review;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
