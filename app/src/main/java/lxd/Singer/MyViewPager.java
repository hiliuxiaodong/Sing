package lxd.Singer;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
    private OnViewPagerTouchListener mTouchLister=null;
    public MyViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public MyViewPager(Context context){
        super(context);
    }
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mTouchLister!=null){
                    mTouchLister.onPagerTouch(true);
                }
                break;
                case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mTouchLister!=null){
                    mTouchLister.onPagerTouch(false);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    public void setOnViewPagerTouchListener(OnViewPagerTouchListener listener){
        this.mTouchLister=listener;
    }
    public interface  OnViewPagerTouchListener{
       void onPagerTouch(boolean isTouch);
    }
}
