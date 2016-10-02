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
import java.util.Collections;
import java.util.Comparator;
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
 * Activities containing this fragment MUST implement the {@link OnPlacePhotoInteractionListener}
 * interface.
 */
public class PlacePhotoListFragment extends Fragment {

    public static final String EXTRAS_PLACE_IDS = "instagram_place_id";

    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnPlacePhotoInteractionListener mListener;
    private ArrayList<Integer> mPlaceInstagramIds;
    private RecyclerView mRecyclerView;
    private ViewSwitcher mSwitcher;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public PlacePhotoListFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlacePhotoListFragment newInstance(ArrayList<Integer> instagram_ids) {
        PlacePhotoListFragment fragment = new PlacePhotoListFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(EXTRAS_PLACE_IDS, instagram_ids);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPlaceInstagramIds = getArguments().getIntegerArrayList(EXTRAS_PLACE_IDS);
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
        if (mPlaceInstagramIds != null && !mPlaceInstagramIds.isEmpty()) {
            final List<InstagramPhoto> instagramPhotos = new ArrayList<>();
            for (int i = 0; i < mPlaceInstagramIds.size(); i++) {
                final Integer placeInstagramId = mPlaceInstagramIds.get(i);
                String url = String.format(RestRequester.INSTAGRAM_PHOTOS_BY_PLACE_URL, placeInstagramId, accessToken);
                final int finalI = i;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
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
                                if (finalI == mPlaceInstagramIds.size() - 1) {
                                    Collections.sort(instagramPhotos, new Comparator<InstagramPhoto>() {
                                        @Override
                                        public int compare(InstagramPhoto instagramPhoto, InstagramPhoto t1) {
                                            return Integer.compare(instagramPhoto.getCreatedTime(), t1.getCreatedTime());
                                        }
                                    });
                                    Collections.reverse(instagramPhotos);
                                    mRecyclerView.setAdapter(new PhotoRestRecyclerViewAdapter(getContext(), instagramPhotos, mListener));
                                    showItems();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Snackbar.make(PlacePhotoListFragment.this.getView(), "Empty list", Snackbar.LENGTH_LONG).show();
                            }
                        });
                RestRequester.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
            }
        }
        showItems();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlacePhotoInteractionListener) {
            mListener = (OnPlacePhotoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPlaceItemInteractionListener");
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

    public interface OnPlacePhotoInteractionListener {
        void onPlacePhotoClick(InstagramPhoto item);
    }
}
