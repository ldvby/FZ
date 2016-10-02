package mobi.foodzen.foodzen.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.io.IOException;

import mobi.foodzen.foodzen.FoodzenApplication;
import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.entities.User;
import mobi.foodzen.foodzen.files.FileManager;
import mobi.foodzen.foodzen.transport.RestRequester;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserDetailsFragment.OnUserDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";

    private String mUserId;

    private FloatingActionButton mDoneButton;
    private TextView mUpdatePhotoLink;
    private TextView mDeletePhotoLink;
    private TextInputEditText mLoginEditText;
    private TextInputEditText mAboutEditText;
    private ImageView mUserImageView;

    private File mUserPic;
    private boolean mDeleteUserPic;

    //    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private StorageReference mStorageReference;
    private User mUser;
    private DatabaseReference mCurUserDBRef;
    private StorageReference mUserPicRef;

    private OnUserDetailsFragmentInteractionListener mListener;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance(String userId) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
//        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mDoneButton = (FloatingActionButton) view.findViewById(R.id.user_done_fab);
        mUpdatePhotoLink = (TextView) view.findViewById(R.id.user_add_update_pic);
        mDeletePhotoLink = (TextView) view.findViewById(R.id.user_delete_pic);
        mLoginEditText = (TextInputEditText) view.findViewById(R.id.user_login);
        mAboutEditText = (TextInputEditText) view.findViewById(R.id.user_about);
        mUserImageView = (ImageView) view.findViewById(R.id.user_pic);


        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        mUpdatePhotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.startCameraApplication();
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
            mCurUserDBRef = usersRef.child(mUserId);
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
        if (mUserId != null) {
            builder.append(mUserId).append(".jpg");
            if (mStorageReference != null) {
                mUserPicRef = mStorageReference.child(builder.toString());
                mUserPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        RestRequester.getInstance(getContext()).getImageLoader().
                                get(uri.toString(), ImageLoader.getImageListener(mUserImageView, 0, 0));
                    }
                });
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserDetailsFragmentInteractionListener) {
            mListener = (OnUserDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setUserPicture(File userPic) {
        mUserPic = userPic;
        mUserImageView.setImageBitmap(BitmapFactory.decodeFile(mUserPic.getAbsolutePath()));
        mDeleteUserPic = false;
    }

    private void saveUser() {
        boolean goToMain = false;
        if (mUser == null) {
            mUser = new User();
            goToMain = true;
        }
//        mUser.setEmail(mFirebaseUser.getEmail());
        mUser.setId(mUserId);
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
                                mListener.onDoneButtonClick(finalGoToMain);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Unable to upload userpic to server", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Can't get user photo", Toast.LENGTH_SHORT).show();
                    }
                } else if (mDeleteUserPic) {
                    mUserPicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mListener.onDoneButtonClick(finalGoToMain);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof StorageException) {
                                mListener.onDoneButtonClick(finalGoToMain);
                            }
                        }
                    });
                } else {
                    mListener.onDoneButtonClick(finalGoToMain);
                }
            }
        });

    }

    public interface OnUserDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDoneButtonClick(boolean goToMain);

        void startCameraApplication();
    }
}
