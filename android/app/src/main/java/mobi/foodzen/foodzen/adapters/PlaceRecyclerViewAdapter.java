package mobi.foodzen.foodzen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobi.foodzen.foodzen.R;
import mobi.foodzen.foodzen.entities.Place;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Place} and makes a call to the
 * specified {@link mobi.foodzen.foodzen.ui.PlaceFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

    private final List<Place> mValues;
    private final mobi.foodzen.foodzen.ui.PlaceFragment.OnListFragmentInteractionListener mListener;

    public PlaceRecyclerViewAdapter(List<Place> items, mobi.foodzen.foodzen.ui.PlaceFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getNameNative());
        holder.mAddress.setText(mValues.get(position).getCityNative());

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
        public final TextView mName;
        public final TextView mAddress;
        public Place mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.place_name_list_item_text);
            mAddress = (TextView) view.findViewById(R.id.place_address_list_item_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAddress.getText() + "'";
        }
    }
}
