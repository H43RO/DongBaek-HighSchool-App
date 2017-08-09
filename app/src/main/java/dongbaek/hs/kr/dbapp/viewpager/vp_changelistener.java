package dongbaek.hs.kr.dbapp.viewpager;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import dongbaek.hs.kr.dbapp.R;

public class vp_changelistener implements OnPageChangeListener {

    private ImageView[] imageViews;

    public vp_changelistener(ImageView[] imageViews) {
        super();
        this.imageViews = imageViews;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < imageViews.length; i++) {
            imageViews[position].setBackgroundResource(R.mipmap.circle_selection);
            if (position != i) {
                imageViews[i].setBackgroundResource(R.mipmap.circle);
            }
        }
    }
}