package mobi.foodzen.foodzen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.business.PhotoBusiness;
import mobi.foodzen.foodzen.entities.Place;
import mobi.foodzen.foodzen.entities.User;
import mobi.foodzen.foodzen.prefs.UserPrefs;

public class UserActivity extends AppCompatActivity implements UserDetailsFragment.OnUserDetailsFragmentInteractionListener, PlaceListFragment.OnPlaceItemInteractionListener {

    private static final int REQUEST_CAMERA_PHOTO = 301;
    private static final int REQUEST_PLACE_DETAILS = 302;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private UserDetailsFragment mUserDetailsFragment;
    private PlaceListFragment mPlaceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //getSupportActionBar().setTitle(R.string.user_profile);

        mViewPager = (ViewPager) findViewById(R.id.user_viewpager);

        mTabLayout = (TabLayout) findViewById(R.id.user_tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("User"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Favorites"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Photos"));
        mTabLayout.setupWithViewPager(mViewPager);


        mUserDetailsFragment = UserDetailsFragment.newInstance(FirebaseAuth.getInstance().getCurrentUser().getUid());

        User user = UserPrefs.getInstance().getUser();
        if (user != null) {
            mPlaceListFragment = PlaceListFragment.newInstance(user.getPlaceList());
        }

        mViewPager.setAdapter(new UserPagerAdapter(getSupportFragmentManager()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        if (mUserDetailsFragment != null) {
                            File userPic = PhotoBusiness.getCameraPhotoAsFile();
                            mUserDetailsFragment.setUserPicture(userPic);
                        }
                    } catch (FileNotFoundException e) {
                        Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case REQUEST_PLACE_DETAILS:
//                mPlaceListFragment = PlaceListFragment.newInstance(UserPrefs.getInstance().getUser().getPlaceList());
                break;
        }
    }

    @Override
    public void onDoneButtonClick(boolean goToMain) {
        if (goToMain) {
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public void startCameraApplication() {
        startActivityForResult(PhotoBusiness.getCameraPhotoIntent(), REQUEST_CAMERA_PHOTO);
    }

    @Override
    public void onPlaceItemClick(Place item) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra(PlaceDetailsActivity.EXTRAS_PLACE, item);
        startActivityForResult(intent, REQUEST_PLACE_DETAILS);
    }


    public class UserPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabTitles = new String[]{"User", "Favorites"};

        public UserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mUserDetailsFragment;
                case 1:
                    return mPlaceListFragment;
                case 2:
                    return null;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
