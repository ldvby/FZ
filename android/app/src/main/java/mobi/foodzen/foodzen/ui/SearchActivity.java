package mobi.foodzen.foodzen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.business.PlaceBusiness;
import mobi.foodzen.foodzen.entities.Place;

public class SearchActivity extends AppCompatActivity implements PlaceListFragment.OnPlaceItemInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference placesRef = mDatabase.getReference("place");
        placesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Place> placeList = new ArrayList<>();
                HashMap<String, HashMap<String, Object>> placeListObj = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

                for (HashMap<String, Object> stringHashMap : placeListObj.values()) {
                    placeList.add(PlaceBusiness.convertFromDBHashMap(stringHashMap));
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                PlaceListFragment placeListFragment = PlaceListFragment.newInstance(placeList);
                transaction.replace(R.id.places_list_fragment_container, placeListFragment).commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onPlaceItemClick(Place item) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra(PlaceDetailsActivity.EXTRAS_PLACE, item);
        startActivity(intent);
    }
}
