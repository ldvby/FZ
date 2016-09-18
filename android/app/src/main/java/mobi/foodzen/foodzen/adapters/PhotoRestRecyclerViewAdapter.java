package mobi.foodzen.foodzen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.dummy.DummyContent.DummyItem;
import mobi.foodzen.foodzen.ui.PlacePhotoFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PhotoRestRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRestRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public PhotoRestRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mCaptionTextView.setText(mValues.get(position).content);

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
        public final ImageView mPhotoImageView;
        public final TextView mCaptionTextView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPhotoImageView = (ImageView) view.findViewById(R.id.rest_photo_list_item_photo);
            mCaptionTextView = (TextView) view.findViewById(R.id.rest_photo_list_item_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCaptionTextView.getText() + "'";
        }
    }
}
