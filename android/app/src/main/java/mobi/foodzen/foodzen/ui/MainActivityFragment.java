package mobi.foodzen.foodzen.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.adapters.PhotoRestRecyclerViewAdapter;
import mobi.foodzen.foodzen.dummy.DummyContent;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView photoRecyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //photoRecyclerView = (RecyclerView) PhotoRestRecyclerViewAdapter(DummyContent.ITEMS, mListener);
        //photoRecyclerView.setAdapter(new PhotoRestRecyclerViewAdapter());
    }
}
