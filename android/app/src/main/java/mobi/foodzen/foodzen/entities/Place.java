package mobi.foodzen.foodzen.entities;

import android.location.Location;

import java.util.List;

/**
 * Created by yegia on 22.09.2016.
 */

public class Place {
    private String mId;
    private String mNameNative;
    private String mNameTranslit;
    private String mCountryNative;
    private String mCountryTranslit;
    private String mCityNative;
    private String mCityTranslit;
    private String mAddressNative;
    private List<Integer> mInstagramGeo;
    private Location mLocation;
    private int mDateCreated;
    private int mDateUpdated;

    public Place() {
    }

    public Place(String addressNative, String cityNative, String cityTranslit, String countryNative, String countryTranslit, int dateCreated, int dateUpdated, String id, List<Integer> instagramGeo, Location location, String nameNative, String nameTranslit) {

        mAddressNative = addressNative;
        mCityNative = cityNative;
        mCityTranslit = cityTranslit;
        mCountryNative = countryNative;
        mCountryTranslit = countryTranslit;
        mDateCreated = dateCreated;
        mDateUpdated = dateUpdated;
        mId = id;
        mInstagramGeo = instagramGeo;
        mLocation = location;
        mNameNative = nameNative;
        mNameTranslit = nameTranslit;
    }

    public String getAddressNative() {
        return mAddressNative;
    }

    public void setAddressNative(String addressNative) {
        mAddressNative = addressNative;
    }

    public String getCityNative() {
        return mCityNative;
    }

    public void setCityNative(String cityNative) {
        mCityNative = cityNative;
    }

    public String getCityTranslit() {
        return mCityTranslit;
    }

    public void setCityTranslit(String cityTranslit) {
        mCityTranslit = cityTranslit;
    }

    public String getCountryNative() {
        return mCountryNative;
    }

    public void setCountryNative(String countryNative) {
        mCountryNative = countryNative;
    }

    public String getCountryTranslit() {
        return mCountryTranslit;
    }

    public void setCountryTranslit(String countryTranslit) {
        mCountryTranslit = countryTranslit;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<Integer> getInstagramGeo() {
        return mInstagramGeo;
    }

    public void setInstagramGeo(List<Integer> instagramGeo) {
        mInstagramGeo = instagramGeo;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getNameNative() {
        return mNameNative;
    }

    public void setNameNative(String nameNative) {
        mNameNative = nameNative;
    }

    public String getNameTranslit() {
        return mNameTranslit;
    }

    public void setNameTranslit(String nameTranslit) {
        mNameTranslit = nameTranslit;
    }
}
