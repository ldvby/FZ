package mobi.foodzen.foodzen.prefs;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mobi.foodzen.foodzen.entities.User;

/**
 * Created by yegia on 01.10.2016.
 */

public class UserPrefs {

    private static UserPrefs ourInstance;
    private User mUser;

    private UserPrefs() {
    }

    public static UserPrefs getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserPrefs();
        }
        return ourInstance;
    }

    public void initializeUser(final InitializationCompleteListener listener) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference("users");
        if (usersRef != null) {
            DatabaseReference curUserDBRef = usersRef.child(firebaseUser.getUid());
            if (curUserDBRef != null) {
                curUserDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            mUser = user;
                        } else {
                            mUser = new User();
                            mUser.setId(firebaseUser.getUid());
                        }
                        listener.initializationCompleted();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.initializationError();
                    }
                });
            }
        }
    }

    public User getUser() {
        return mUser;
    }

    public interface InitializationCompleteListener {
        void initializationCompleted();

        void initializationError();
    }
}
