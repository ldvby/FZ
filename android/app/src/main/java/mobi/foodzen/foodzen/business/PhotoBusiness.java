package mobi.foodzen.foodzen.business;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import mobi.foodzen.foodzen.entities.InstagramPhoto;
import mobi.foodzen.foodzen.entities.User;

/**
 * Created by yegia on 15.09.2016.
 */
public class PhotoBusiness {

    public static List<String> getInstagrammPhotosByUser(User user) {
        return null;
    }

    public static InstagramPhoto convertJSONtoPhoto(JSONObject JSONObject) throws JSONException {
        InstagramPhoto instagramPhoto = new InstagramPhoto();
        instagramPhoto.setId(JSONObject.getString("id"));
        JSONObject jsonLocation = JSONObject.getJSONObject("location");
        if (jsonLocation != null) {
            instagramPhoto.setLocationName(jsonLocation.getString("name"));
        }
        JSONObject jsonImages = JSONObject.getJSONObject("images");
        if (jsonImages != null) {
            instagramPhoto.setUrl(jsonImages.getJSONObject("standard_resolution").getString("url"));
        }

        return instagramPhoto;
    }

}
