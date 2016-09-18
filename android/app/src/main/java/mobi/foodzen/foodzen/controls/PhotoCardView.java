package mobi.foodzen.foodzen.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.CardView;

/**
 * Created by yegia on 16.09.2016.
 */
public class PhotoCardView extends CardView {
    public PhotoCardView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY));
    }
}
