package lxd.Common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lxd.ComposeAudio.R;
import lxd.MainActivity;
import lxd.Share.ShareFragment;
import lxd.Singer.SingFragment;
import lxd.User.UserFragment;

public class ManageFragments {

    private static MainActivity mActivity = MainActivity.getmActivity();
    private static FragmentTransaction transaction;
    private static List<Fragment> mFragments = new ArrayList<>();
    private static int lastPostion;
    private static Fragment mainFragment;

    public static void addFragmentList(Fragment fragment){
        mFragments.add(fragment);
    }


    public static void replaceFragment(int containID, Fragment fragment, boolean isAddToBackStack){
        transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(containID, fragment);
//        if (!fragment.isAdded()) {
//            mActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//            transaction.add(R.id.container, fragment);
//        }
        transaction.hide(mainFragment);
        if(isAddToBackStack){
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commitAllowingStateLoss();

    }

    public static void setFragmentPosition(int position){
        transaction  = mActivity.getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        mainFragment = currentFragment;
        Fragment lastFragment = mFragments.get(lastPostion);
        lastPostion = position;
        transaction.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            mActivity.getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            transaction.add(R.id.container, currentFragment);
        }
        transaction.show(currentFragment);
        transaction.commitAllowingStateLoss();
    }

}
