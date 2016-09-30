package mobi.foodzen.foodzen.business;

import java.util.HashMap;

import mobi.foodzen.foodzen.entities.Place;

/**
 * Created by artur.egiazarov on 29.09.2016.
 */

public class PlaceBusiness {

    public static Place convertFromDBHashMap(HashMap<String, String> hashMap) {
        Place place = new Place();
        place.setId(hashMap.get("_id"));
        place.setNameNative(hashMap.get("nameNative"));
        place.setCityNative(hashMap.get("cityNative"));
        return place;
    }
}
