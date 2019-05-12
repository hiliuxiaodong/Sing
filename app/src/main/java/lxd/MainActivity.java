package lxd;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.Tool.Common.CommonThreadPool;
import com.Tool.Function.AudioFunction;
import com.Tool.Function.CommonFunction;
import com.Tool.Function.FileFunction;
import com.Tool.Function.LogFunction;
import com.Tool.Function.VoiceFunction;
import com.Tool.Global.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lxd.Common.ManageFragments;
import lxd.Common.MyApplication;
import lxd.ComposeAudio.Tool.Interface.ComposeAudioInterface;
import lxd.ComposeAudio.Tool.Interface.DecodeOperateInterface;
import lxd.ComposeAudio.Tool.Interface.VoicePlayerInterface;
import lxd.ComposeAudio.Tool.Interface.VoiceRecorderOperateInterface;
import lxd.RecordingRoom.RecordingRoomFragment;
import lxd.Share.ShareFragment;
import lxd.Singer.SingFragment;
import lxd.User.UserFragment;
import lxd.ComposeAudio.R;

public class MainActivity extends AppCompatActivity implements RecordingRoomFragment.OnFragmentInteractionListener
, VoicePlayerInterface, DecodeOperateInterface, ComposeAudioInterface, VoiceRecorderOperateInterface {

    private static MainActivity mActivity;
    private static BottomNavigationView navigation;
    private List<Fragment> mFragments;
    private int lastPostion;
    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;

    private static MediaPlayer player = new MediaPlayer();

    private Handler handler;
    private MyApplication application;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ManageFragments.setFragmentPosition(0);
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
                    break;
                case R.id.navigation_sing:
                    ManageFragments.setFragmentPosition(1);
                    Log.d("MainActivity","current Thread is:"+ Thread.currentThread().getName());
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
                    break;
                case R.id.navigation_user:
                    ManageFragments.setFragmentPosition(2);
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        initView();
        initData();
        initListener();

//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE } ,1);
//        }else{
////            initMediaPlayer();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    initMediaPlayer();
                }else{
                    Toast.makeText(this,"拒绝权限，无法播放",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void initMediaPlayer(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"Honor.mp3");
            player.setDataSource(file.getPath());
            player.prepare();
        }catch (Exception e){

        }
    }

    public static void setNavigationVisible(int visible){
        navigation.setVisibility(visible);
    }

    private void initView(){
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        mToolbar = findViewById(R.id.toolbar);
    }

    private void initData(){
        mActivity = this;
        application = MyApplication.getInstance();

        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                application.initialiseInUIThread();
            }
        };

        ManageFragments.addFragmentList(new ShareFragment());
        ManageFragments.addFragmentList(new SingFragment());
        ManageFragments.addFragmentList(new UserFragment());
        setSupportActionBar(mToolbar);
        ManageFragments.setFragmentPosition(0);

        CommonThreadPool.getThreadPool().addFixedTask(initialiseThread);

    }

    private Runnable initialiseThread = new Runnable() {
        @Override
        public void run() {
            application.initialise();
            Message.obtain(handler).sendToTarget();
//            begin();
        }
    };

    private void initListener(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    player.start();
                    break;
            }
        }
    };


    public static Handler getHandler(){
        return mHandler;
    }

    public static MainActivity getmActivity() {
        return mActivity;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void updateComposeProgress(int composeProgress) {

    }

    @Override
    public void composeSuccess() {

    }

    @Override
    public void composeFail() {

    }

    @Override
    public void updateDecodeProgress(int decodeProgress) {

    }

    @Override
    public void decodeSuccess() {

    }

    @Override
    public void decodeFail() {

    }

    @Override
    public void playVoiceBegin() {

    }

    @Override
    public void playVoiceFail() {

    }

    @Override
    public void playVoiceFinish() {

    }

    @Override
    public void recordVoiceBegin() {

    }

    @Override
    public void recordVoiceStateChanged(int volume, long recordDuration) {

    }

    @Override
    public void prepareGiveUpRecordVoice() {

    }

    @Override
    public void recoverRecordVoice() {

    }

    @Override
    public void giveUpRecordVoice() {

    }

    @Override
    public void recordVoiceFail() {

    }

    @Override
    public void recordVoiceFinish() {

    }
}