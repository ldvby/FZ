package mobi.foodzen.foodzen.business;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import mobi.foodzen.foodzen.entities.Place;
import mobi.foodzen.foodzen.entities.User;
import mobi.foodzen.foodzen.prefs.UserPrefs;

/**
 * Created by artur.egiazarov on 29.09.2016.
 */

public class PlaceBusiness {

    public static Place convertFromDBHashMap(HashMap<String, Object> hashMap) {
        Place place = new Place();
        place.setId((String) hashMap.get("_id"));
        place.setNameNative((String) hashMap.get("nameNative"));
        place.setNameTranslit((String) hashMap.get("nameTranslit"));
        place.setAddressNative((String) hashMap.get("addressNative"));
        place.setAddressTranslit((String) hashMap.get("addressTranslit"));
        place.setCityNative((String) hashMap.get("cityNative"));
        place.setCityTranslit((String) hashMap.get("cityTranslit"));
        place.setCountryNative((String) hashMap.get("countryNative"));
        place.setCountryTranslit((String) hashMap.get("countryTranslit"));

        HashMap<String, Object> locationMap = (HashMap<String, Object>) hashMap.get("geo_coordinates");
        if (locationMap != null) {
            Double lat = Double.parseDouble(locationMap.get("lat").toString());
            Double lng = Double.parseDouble(locationMap.get("lng").toString());
            LatLng location = new LatLng(lat, lng);
            place.setLocation(location);
        }

        ArrayList<Long> instagramGeoLong = (ArrayList<Long>) hashMap.get("geo_instagram");
        if (instagramGeoLong != null) {
            ArrayList<Integer> instagramGeo = new ArrayList<>();
            for (Long aLong : instagramGeoLong) {
                instagramGeo.add(aLong.intValue());
            }
            place.setInstagramGeo(instagramGeo);
        }
        return place;
    }

    public static boolean isPlaceInFavorites(Place place) {
        User user = UserPrefs.getInstance().getUser();
        if (user != null) {
            return user.getPlaceList().contains(place);
        }
        return false;
    }

    public static void addPlaceToFavorites(Place place) {
        User user = UserPrefs.getInstance().getUser();
        if (user != null) {
            user.getPlaceList().add(place);
            FirebaseDatabase.getInstance().getReference("users/" + user.getId()).setValue(user);
        }
    }

    public static void removePlaceFromFavorites(Place place) {
        User user = UserPrefs.getInstance().getUser();
        if (user != null) {
            user.getPlaceList().remove(place);
            FirebaseDatabase.getInstance().getReference("users/" + user.getId()).setValue(user);
        }
    }
}
