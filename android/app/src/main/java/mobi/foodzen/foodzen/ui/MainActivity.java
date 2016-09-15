package mobi.foodzen.foodzen.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements RestPhotoFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton buttonSearch = new ImageButton(this);
        buttonSearch.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        buttonSearch.setContentDescription("Search");
        buttonSearch.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
        toolbar.addView(buttonSearch);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userName = extras.getString("user");
            if (userName != null) {

            }
        }

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
