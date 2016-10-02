package mobi.foodzen.foodzen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.TreeSet;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.entities.InstagramPhoto;
import mobi.foodzen.foodzen.entities.Place;
import mobi.foodzen.foodzen.entities.User;
import mobi.foodzen.foodzen.prefs.UserPrefs;

public class MainActivity extends AppCompatActivity implements PlacePhotoListFragment.OnPlacePhotoInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        User user = UserPrefs.getInstance().getUser();
        if (user != null) {
            ArrayList<Place> favoritePlaces = user.getPlaceList();
            TreeSet<Integer> instIdsSet = new TreeSet<>();
            for (Place place : favoritePlaces) {
                instIdsSet.addAll(place.getInstagramGeo());
            }
            ArrayList<Integer> instIdsList = new ArrayList<>();
            instIdsList.addAll(instIdsSet);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            PlacePhotoListFragment placePhotoListFragment = PlacePhotoListFragment.newInstance(instIdsList);
            transaction.replace(R.id.places_photo_list_fragment_container, placePhotoListFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(userIntent);
                return true;
            case R.id.action_map:
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapIntent);
                return true;
            case R.id.action_search:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPlacePhotoClick(InstagramPhoto item) {

    }
}
