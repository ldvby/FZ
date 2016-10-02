package mobi.foodzen.foodzen.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by yegia on 22.09.2016.
 */

public class Place implements Parcelable {
    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
    private String m_Id;
    private String mNameNative;
    private String mNameTranslit;
    private String mCountryNative;
    private String mCountryTranslit;
    private String mCityNative;
    private String mCityTranslit;
    private String mAddressNative;
    private String mAddressTranslit;
    private ArrayList<Integer> mGeo_instagram;
    private LatLng mLocation;
    private int mDateCreate;
    private int mDateUpdate;

    public Place() {
    }

    public Place(String addressNative, String addressTranslit, String cityNative, String cityTranslit, String countryNative, String countryTranslit, int dateCreated, int dateUpdated, String id, ArrayList<Integer> instagramGeo, LatLng location, String nameNative, String nameTranslit) {

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

    protected Place(Parcel in) {
        this.m_Id = in.readString();
        this.mNameNative = in.readString();
        this.mNameTranslit = in.readString();
        this.mCountryNative = in.readString();
        this.mCountryTranslit = in.readString();
        this.mCityNative = in.readString();
        this.mCityTranslit = in.readString();
        this.mAddressNative = in.readString();
        this.mAddressTranslit = in.readString();
        this.mGeo_instagram = new ArrayList<>();
        in.readList(this.mGeo_instagram, Integer.class.getClassLoader());
        this.mLocation = in.readParcelable(LatLng.class.getClassLoader());
        this.mDateCreate = in.readInt();
        this.mDateUpdate = in.readInt();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((Place) obj).getId());
    }

    public String getFullNativeAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCountryNative()).append(", ");
        builder.append(getCityNative()).append(", ");
        builder.append(getAddressNative());
        return builder.toString();
    }

    public String getFullTranslitAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCountryTranslit()).append(", ");
        builder.append(getCityTranslit()).append(", ");
        builder.append(getAddressTranslit());
        return builder.toString();
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

    public ArrayList<Integer> getInstagramGeo() {
        return mGeo_instagram;
    }

    public void setInstagramGeo(ArrayList<Integer> instagramGeo) {
        mGeo_instagram = instagramGeo;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public void setLocation(LatLng location) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.m_Id);
        dest.writeString(this.mNameNative);
        dest.writeString(this.mNameTranslit);
        dest.writeString(this.mCountryNative);
        dest.writeString(this.mCountryTranslit);
        dest.writeString(this.mCityNative);
        dest.writeString(this.mCityTranslit);
        dest.writeString(this.mAddressNative);
        dest.writeString(this.mAddressTranslit);
        dest.writeList(this.mGeo_instagram);
        dest.writeParcelable(this.mLocation, flags);
        dest.writeInt(this.mDateCreate);
        dest.writeInt(this.mDateUpdate);
    }
}
