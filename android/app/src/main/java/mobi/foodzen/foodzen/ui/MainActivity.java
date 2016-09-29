package mobi.foodzen.foodzen.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import mobi.foodzen.foodzen.FoodzenApplication;
import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.entities.InstagramPhoto;

public class MainActivity extends AppCompatActivity implements PlacePhotoFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton buttonUser = new ImageButton(this);
        buttonUser.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        buttonUser.setContentDescription("User");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            buttonUser.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_info_details, getTheme()));
        } else {
            buttonUser.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
        }
        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(userIntent);
            }
        });
        toolbar.addView(buttonUser);

        ImageButton buttonFind = new ImageButton(this);
        buttonFind.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        buttonFind.setContentDescription("Find");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            buttonFind.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search, getTheme()));
        } else {
            buttonFind.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
        }
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });
        toolbar.addView(buttonFind);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(InstagramPhoto item) {

    }
}
