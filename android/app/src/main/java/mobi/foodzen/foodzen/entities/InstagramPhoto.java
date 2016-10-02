package mobi.foodzen.foodzen.entities;

/**
 * Created by artur.egiazarov on 20.09.2016.
 */
public class InstagramPhoto {
    private String mId;
    private String mLocationName;
    private String mUrl;
    private int mCreatedTime;

    public InstagramPhoto(int createdTime, String id, String locationName, String url) {
        mCreatedTime = createdTime;
        mId = id;
        mLocationName = locationName;
        mUrl = url;
    }

    public InstagramPhoto() {
    }

    public int getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(int createdTime) {
        mCreatedTime = createdTime;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String locationName) {
        mLocationName = locationName;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
