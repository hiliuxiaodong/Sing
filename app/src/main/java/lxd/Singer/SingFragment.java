package lxd.Singer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lxd.Common.MyApplication;
import lxd.MainActivity;
import lxd.ComposeAudio.R;
import lxd.Share.ShareFragmentAdapter;


public class SingFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener, MyViewPager.OnViewPagerTouchListener {

    private static final String TAG="SingFragment";
    private static Context mContext = MyApplication.getContext();
    private Handler mHandler = MainActivity.getHandler();
    private boolean mIsTouch=false;
    private LinearLayout mPointContatiner;
    private MyViewPager mLoopPager;
    private LoopAdapter mLoopAdapter;

    private static List<Integer> sPics = new ArrayList<>();
    static {
        sPics.add(R.mipmap.top_3);
        sPics.add(R.mipmap.top_2);
        sPics.add(R.mipmap.top_1);
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentAdapter mAdapter;
    private String[] title = {
            "热门歌曲",
            "新歌速度"
    };


    public static SingFragment newInstance(String param1, String param2) {
        SingFragment fragment = new SingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    private Runnable mLooperTask=new Runnable() {
        @Override
        public void run() {
            if (!mIsTouch){
                int currentItem = mLoopPager.getCurrentItem();
                mLoopPager.setCurrentItem(++currentItem,true);
            }
            mHandler.postDelayed(this,3000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sing, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(mLooperTask);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mLooperTask);
    }

    private void initView(View view){

        mViewPager = view.findViewById(R.id.vp);
        mTabLayout = view.findViewById(R.id.tablayout);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new RecommedFragment());
        fragments.add(new RecommedFragment());
        FragmentManager fragmentManager = SingFragment.this.getChildFragmentManager();
        mAdapter = new MyFragmentAdapter(fragmentManager, fragments);

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);


        mLoopPager = (MyViewPager)view.findViewById(R.id.viewPager);
        mLoopAdapter=new LoopAdapter();
        mLoopAdapter.setData(sPics);
        mLoopPager.setAdapter(mLoopAdapter);
        mLoopPager.setOnViewPagerTouchListener(this);
        mLoopPager.addOnPageChangeListener(this);
        mPointContatiner=(LinearLayout) view.findViewById(R.id.lineLayout_dot);
        insertPoint();
        mLoopPager.setCurrentItem(mLoopAdapter.getDataRealSize()*100,false);
    }
    private void initListener(){
//        play.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.play_button:
//                Message message = new Message();
//                message.what = 2;
//                handler.sendMessage(message);
//                break;
        }
    }

    private void insertPoint(){
        for (int i=0;i<sPics.size();i++){
            View point = new View(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(35,35);
            point.setBackground(getResources().getDrawable(R.drawable.ic_dot_normal,null));
            layoutParams.leftMargin=5;
            point.setLayoutParams(layoutParams);
            mPointContatiner.addView(point);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int realPosition;
        if (mLoopAdapter.getDataRealSize()!=0){
            realPosition = position%mLoopAdapter.getDataRealSize();
        }else {
            realPosition=0;
        }
        setSelectPoint(realPosition);
    }

    private void setSelectPoint(int realPosition){
        for(int i=0;i<mPointContatiner.getChildCount();i++){
            View point = mPointContatiner.getChildAt(i);
            if (i!=realPosition){
                point.setBackgroundResource(R.drawable.ic_dot_normal);
            }else {
                point.setBackgroundResource(R.drawable.ic_dot_focused);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPagerTouch(boolean isTouch) {
        mIsTouch = isTouch;
    }



    class MyFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
