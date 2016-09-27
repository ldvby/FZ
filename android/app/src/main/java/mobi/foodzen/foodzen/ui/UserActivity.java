package mobi.foodzen.foodzen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.business.PhotoBusiness;
import mobi.foodzen.foodzen.entities.User;

public class UserActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PHOTO = 301;


    FloatingActionButton mDoneButton;
    TextView mUpdatePhotoLink;
    TextInputEditText mLoginEditText;
    TextInputEditText mAboutEditText;
    NetworkImageView mUserImageView;

    File mUserPic;

    FirebaseUser mFirebaseUser;
    FirebaseDatabase mFirebaseDatabase;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mLoginEditText = (TextInputEditText) findViewById(R.id.user_login);
        mUserPic =

        mDoneButton = (FloatingActionButton) findViewById(R.id.user_done_fab);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUpdatePhotoLink = (TextView) findViewById(R.id.user_add_update_pic);
        mUpdatePhotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(PhotoBusiness.getCameraPhotoIntent(), REQUEST_CAMERA_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CAMERA_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        mUserPic = PhotoBusiness.getCameraPhotoAsFile();
                        m
                    } catch (FileNotFoundException e) {
                        Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    }
                }
                break;
        }
    }

    private void saveUser() {

    }
}
