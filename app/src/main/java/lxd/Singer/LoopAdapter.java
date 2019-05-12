package lxd.Singer;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class LoopAdapter extends PagerAdapter {

    private List<Integer> mPics=null;
    @Override
    public int getCount(){
        if (mPics!=null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }
    @Override
    public Object instantiateItem(ViewGroup container,int posion){
        int realPosition=posion%mPics.size();
        ImageView imageView=new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(mPics.get(realPosition));
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(View view,Object object){
        return view==object;
    }
    public void setData(List<Integer> colos){
        this.mPics=colos;

    }
    public int getDataRealSize(){
        if (mPics!=null){
            return mPics.size();
        }
        return 0;
    }
}
