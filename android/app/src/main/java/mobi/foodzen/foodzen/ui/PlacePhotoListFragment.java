package mobi.foodzen.foodzen.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.adapters.PhotoRestRecyclerViewAdapter;
import mobi.foodzen.foodzen.business.PhotoBusiness;
import mobi.foodzen.foodzen.entities.InstagramPhoto;
import mobi.foodzen.foodzen.prefs.RemotePreferences;
import mobi.foodzen.foodzen.transport.RestRequester;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlacePhotoListFragment extends Fragment {

    private static final String EXTRAS_PLACE_IDS = "instagram_place_id";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Integer> mPlaceInstagramId;
    private RecyclerView mRecyclerView;
    private ViewSwitcher mSwitcher;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public PlacePhotoListFragment() {
        Bundle args = getArguments();
        if (args.containsKey(EXTRAS_PLACE_IDS)) {
            mPlaceInstagramId = args.getIntegerArrayList(EXTRAS_PLACE_IDS);
        }
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlacePhotoListFragment newInstance(int columnCount) {
        PlacePhotoListFragment fragment = new PlacePhotoListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_photo_list, container, false);

        Context context = view.getContext();
        mSwitcher = (ViewSwitcher) view.findViewById(R.id.switcher);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        String accessToken = RemotePreferences.getInstance().getInstagramAccessToken();
        if (mPlaceInstagramId != null && !mPlaceInstagramId.isEmpty()) {
            String url = String.format(RestRequester.INSTAGRAM_PHOTOS_BY_PLACE_URL, mPlaceInstagramId.get(0), accessToken);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<InstagramPhoto> instagramPhotos = new ArrayList<>();
                            try {
                                JSONArray jsonArrayPhotos = response.getJSONArray("data");
                                for (int i = 0; i < jsonArrayPhotos.length(); i++) {
                                    JSONObject jsonPhoto = jsonArrayPhotos.getJSONObject(i);
                                    if (jsonPhoto != null) {
                                        instagramPhotos.add(PhotoBusiness.convertJSONtoPhoto(jsonPhoto));
                                    }
                                }
                            } catch (JSONException e) {
                                Snackbar.make(PlacePhotoListFragment.this.getView(), "Something wrong", Snackbar.LENGTH_LONG).show();
                            }
                            mRecyclerView.setAdapter(new PhotoRestRecyclerViewAdapter(getContext(), instagramPhotos, mListener));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(PlacePhotoListFragment.this.getView(), "Empty list", Snackbar.LENGTH_LONG).show();
                        }
                    });
            RestRequester.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        }
        showItems();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    void showItems() {
        if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter().getItemCount() > 0) {
            if (R.id.list == mSwitcher.getNextView().getId()) {
                mSwitcher.showNext();
            }
        } else if (R.id.empty_textview == mSwitcher.getNextView().getId()) {
            mSwitcher.showNext();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(InstagramPhoto item);
    }
}
