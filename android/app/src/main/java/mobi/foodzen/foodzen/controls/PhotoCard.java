package mobi.foodzen.foodzen.controls;

import android.content.Context;
import android.support.v7.widget.CardView;

/**
 * Created by yegia on 16.09.2016.
 */
public class PhotoCard extends CardView {
    public PhotoCard(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.setMeasuredDimension(width, width);
    }
}
