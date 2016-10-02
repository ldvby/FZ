package mobi.foodzen.foodzen.business;

import android.content.Intent;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import mobi.foodzen.foodzen.FoodzenApplication;
import mobi.foodzen.foodzen.entities.InstagramPhoto;
import mobi.foodzen.foodzen.entities.User;
import mobi.foodzen.foodzen.files.PhotoFileProvider;

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
        instagramPhoto.setCreatedTime(JSONObject.getInt("created_time"));
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

    public static Intent getCameraPhotoIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, PhotoFileProvider.CONTENT_URI);
        return cameraIntent;
    }

    public static File getCameraPhotoAsFile() throws FileNotFoundException {
        File out = new File(FoodzenApplication.getCurrentApplicationContext().getFilesDir(), PhotoFileProvider.FILE_NAME);
        if (!out.exists()) {
            throw new FileNotFoundException("Can't find file from camera");
        }
        return out;
    }


}
