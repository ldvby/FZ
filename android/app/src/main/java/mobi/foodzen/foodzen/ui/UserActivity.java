package mobi.foodzen.foodzen.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import mobi.foodzen.foodzen.FoodzenApplication;
import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.business.PhotoBusiness;
import mobi.foodzen.foodzen.entities.User;
import mobi.foodzen.foodzen.files.FileManager;
import mobi.foodzen.foodzen.transport.RestRequester;

public class UserActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PHOTO = 302;


    private FloatingActionButton mDoneButton;
    private TextView mUpdatePhotoLink;
    private TextView mDeletePhotoLink;
    private TextInputEditText mLoginEditText;
    private TextInputEditText mAboutEditText;
    private ImageView mUserImageView;

    private File mUserPic;
    private boolean mDeleteUserPic;

    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private StorageReference mStorageReference;
    private User mUser;
    private DatabaseReference mCurUserDBRef;
    private StorageReference mUserPicRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mDoneButton = (FloatingActionButton) findViewById(R.id.user_done_fab);
        mUpdatePhotoLink = (TextView) findViewById(R.id.user_add_update_pic);
        mDeletePhotoLink = (TextView) findViewById(R.id.user_delete_pic);
        mLoginEditText = (TextInputEditText) findViewById(R.id.user_login);
        mAboutEditText = (TextInputEditText) findViewById(R.id.user_about);
        mUserImageView = (ImageView) findViewById(R.id.user_pic);


        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        mUpdatePhotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(PhotoBusiness.getCameraPhotoIntent(), REQUEST_CAMERA_PHOTO);
            }
        });

        mDeletePhotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteUserPic = true;
                mUserPic = null;
                mUserImageView.setImageBitmap(null);
            }
        });

        DatabaseReference usersRef = mFirebaseDatabase.getReference("users");
        if (usersRef != null) {
            mCurUserDBRef = usersRef.child(mFirebaseUser.getUid());
            if (mCurUserDBRef != null) {
                mCurUserDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUser = dataSnapshot.getValue(User.class);
                        if (mUser != null) {
                            mLoginEditText.setText(mUser.getLogin());
                            mAboutEditText.setText(mUser.getAbout());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FoodzenApplication.showErrorInternetToast();
                    }
                });
            }
        }

        StringBuilder builder = new StringBuilder("userpics/");
        if (mFirebaseUser != null) {
            builder.append(mFirebaseUser.getUid()).append(".jpg");
            if (mStorageReference != null) {
                mUserPicRef = mStorageReference.child(builder.toString());
                mUserPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        RestRequester.getInstance(UserActivity.this).getImageLoader().
                                get(uri.toString(), ImageLoader.getImageListener(mUserImageView, 0, 0));
                    }
                });

            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        mUserPic = PhotoBusiness.getCameraPhotoAsFile();
                        mUserImageView.setImageBitmap(BitmapFactory.decodeFile(mUserPic.getAbsolutePath()));
                        mDeleteUserPic = false;
                    } catch (FileNotFoundException e) {
                        Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void finishActivity(boolean goToMain){
        if (goToMain){
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void saveUser() {
        boolean goToMain = false;
        if (mUser == null) {
            mUser = new User();
            goToMain = true;
        }
        mUser.setEmail(mFirebaseUser.getEmail());
        mUser.setId(mFirebaseUser.getUid());
        mUser.setAbout(mAboutEditText.getText().toString());
        mUser.setLogin(mLoginEditText.getText().toString());
        final boolean finalGoToMain = goToMain;
        mCurUserDBRef.setValue(mUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (mUserPic != null) {
                    try {
                        File extFile = FileManager.getInstance().copyToInternalStorageWithGeneratingNewName(mUserPic);
                        Uri fileUri = Uri.fromFile(extFile);
                        mUserPicRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                finishActivity(finalGoToMain);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserActivity.this, "Unable to upload userpic to server", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (IOException e) {
                        Toast.makeText(UserActivity.this, "Can't get user photo", Toast.LENGTH_SHORT).show();
                    }
                } else if (mDeleteUserPic) {
                    mUserPicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finishActivity(finalGoToMain);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof StorageException){
                                finishActivity(finalGoToMain);
                            }
                        }
                    });
                } else {
                    finishActivity(finalGoToMain);
                }
            }
        });

    }
}
