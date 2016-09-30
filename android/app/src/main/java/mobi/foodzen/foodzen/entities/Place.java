package mobi.foodzen.foodzen.entities;

import android.location.Location;

import java.util.List;

/**
 * Created by yegia on 22.09.2016.
 */

public class Place {
    private String m_Id;
    private String mNameNative;
    private String mNameTranslit;
    private String mCountryNative;
    private String mCountryTranslit;
    private String mCityNative;
    private String mCityTranslit;
    private String mAddressNative;
    private String mAddressTranslit;
    private List<Integer> mGeo_instagram;
    private Location mLocation;
    private int mDateCreate;
    private int mDateUpdate;

    public Place() {
    }

    public Place(String addressNative, String addressTranslit, String cityNative, String cityTranslit, String countryNative, String countryTranslit, int dateCreated, int dateUpdated, String id, List<Integer> instagramGeo, Location location, String nameNative, String nameTranslit) {

        mAddressNative = addressNative;
        mAddressTranslit = addressTranslit;
        mCityNative = cityNative;
        mCityTranslit = cityTranslit;
        mCountryNative = countryNative;
        mCountryTranslit = countryTranslit;
        mDateCreate = dateCreated;
        mDateUpdate = dateUpdated;
        m_Id = id;
        mGeo_instagram = instagramGeo;
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

    public int getDateCreate() {
        return mDateCreate;
    }

    public void setDateCreate(int dateCreate) {
        mDateCreate = dateCreate;
    }

    public int getDateUpdate() {
        return mDateUpdate;
    }

    public void setDateUpdate(int dateUpdate) {
        mDateUpdate = dateUpdate;
    }

    public String getId() {
        return m_Id;
    }

    public void setId(String id) {
        m_Id = id;
    }

    public List<Integer> getInstagramGeo() {
        return mGeo_instagram;
    }

    public void setInstagramGeo(List<Integer> instagramGeo) {
        mGeo_instagram = instagramGeo;
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

    public String getAddressTranslit() {
        return mAddressTranslit;
    }

    public void setAddressTranslit(String addressTranslit) {
        mAddressTranslit = addressTranslit;
    }
}
