package mobi.foodzen.foodzen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.entities.InstagramPhoto;
import mobi.foodzen.foodzen.transport.RestRequester;
import mobi.foodzen.foodzen.ui.PlacePhotoListFragment.OnListFragmentInteractionListener;

public class PhotoRestRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRestRecyclerViewAdapter.ViewHolder> {

    private final List<InstagramPhoto> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mCtx;

    public PhotoRestRecyclerViewAdapter(Context ctx, List<InstagramPhoto> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place_photo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCaptionTextView.setText(holder.mItem.getLocationName());

        holder.mPhotoImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader imageLoader = RestRequester.getInstance(mCtx).getImageLoader();
        holder.mPhotoImageView.setImageUrl(holder.mItem.getUrl(), imageLoader);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final NetworkImageView mPhotoImageView;
        public final TextView mCaptionTextView;
        public InstagramPhoto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPhotoImageView = (NetworkImageView) view.findViewById(R.id.rest_photo_list_item_photo);
            mCaptionTextView = (TextView) view.findViewById(R.id.rest_photo_list_item_text);
        }
    }
}
