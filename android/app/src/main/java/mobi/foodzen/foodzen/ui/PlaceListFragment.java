package mobi.foodzen.foodzen.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.adapters.PlaceRecyclerViewAdapter;
import mobi.foodzen.foodzen.entities.Place;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPlaceItemInteractionListener}
 * interface.
 */
public class PlaceListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_PLACES_LIST = "places_list";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ArrayList<Place> mPlaces;
    private OnPlaceItemInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlaceListFragment newInstance(ArrayList<Place> places) {
        PlaceListFragment fragment = new PlaceListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PLACES_LIST, places);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPlaces = getArguments().getParcelableArrayList(ARG_PLACES_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (mPlaces != null) {
                recyclerView.setAdapter(new PlaceRecyclerViewAdapter(mPlaces, mListener));
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlaceItemInteractionListener) {
            mListener = (OnPlaceItemInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPlaceItemInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPlaceItemInteractionListener {
        void onPlaceItemClick(Place item);
    }
}
