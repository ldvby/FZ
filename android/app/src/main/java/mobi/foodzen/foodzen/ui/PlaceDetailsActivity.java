package mobi.foodzen.foodzen.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.business.PlaceBusiness;
import mobi.foodzen.foodzen.entities.InstagramPhoto;
import mobi.foodzen.foodzen.entities.Place;

public class PlaceDetailsActivity extends AppCompatActivity implements PlacePhotoListFragment.OnPlacePhotoInteractionListener {

    public static final String EXTRAS_PLACE = "extras_place";

    Place mPlace;

    TextView mAddressNative;
    TextView mAddressTranslit;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAddressNative = (TextView) findViewById(R.id.place_details_address_native);
        mAddressTranslit = (TextView) findViewById(R.id.place_details_address_translit);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlace != null) {
                    if (PlaceBusiness.isPlaceInFavorites(mPlace)) {
                        ((FloatingActionButton) view).setImageResource(R.drawable.heart_outline);
                        PlaceBusiness.removePlaceFromFavorites(mPlace);
                    } else {
                        ((FloatingActionButton) view).setImageResource(R.drawable.heart);
                        PlaceBusiness.addPlaceToFavorites(mPlace);
                    }
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(EXTRAS_PLACE)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            mPlace = bundle.getParcelable(EXTRAS_PLACE);
            if (mPlace != null) {
                getSupportActionBar().setTitle(mPlace.getNameNative());
                mAddressNative.setText(mPlace.getFullNativeAddress());
                mAddressTranslit.setText(mPlace.getFullTranslitAddress());
                PlacePhotoListFragment photoListFragment = PlacePhotoListFragment.newInstance(
                        mPlace.getInstagramGeo());
                fragmentTransaction.replace(R.id.place_details_photos_frame, photoListFragment).commit();
                mFab.setImageResource(PlaceBusiness.isPlaceInFavorites(mPlace) ?
                        R.drawable.heart : R.drawable.heart_outline);
            }
        }


    }

    @Override
    public void onPlacePhotoClick(InstagramPhoto item) {

    }
}
