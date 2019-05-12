package lxd.RecordingRoom;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Tool.Function.AudioFunction;
import com.Tool.Function.FileFunction;
import com.Tool.Function.LogFunction;
import com.Tool.Function.VoiceFunction;
import com.Tool.Global.Constant;
import com.Tool.Global.Variable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lxd.ComposeAudio.R;
import lxd.ComposeAudio.Tool.Interface.ComposeAudioInterface;
import lxd.ComposeAudio.Tool.Interface.DecodeOperateInterface;
import lxd.ComposeAudio.Tool.Interface.VoicePlayerInterface;
import lxd.ComposeAudio.Tool.Interface.VoiceRecorderOperateInterface;
import lxd.ComposeAudio.Tool.Player.VoicePlayerEngine;
import lxd.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordingRoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordingRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordingRoomFragment extends Fragment
        implements View.OnClickListener, View.OnTouchListener, VoicePlayerInterface, DecodeOperateInterface, ComposeAudioInterface, VoiceRecorderOperateInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String tempVoicePcmUrl;
    private MainActivity mActivity = MainActivity.getmActivity();
    private ImageView switch_mode;
    private ImageView resing;
    private ImageView commit;
    private ImageView back;
    private TextView switch_mode_text;
    private TextView resing_text;
    private TextView commit_text;
    private Button text_play;

    private boolean isSwitchMode = false;
    private int actualRecordTime;
    private String musicFileUrl;
    private String decodeFileUrl;
    private String composeVoiceUrl;
    private String lrcUrl;
    private String testMusic;
    private String testMusicb;


    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayerb;
    private boolean recordVoiceBegin;
    private int recordTime;
    private String className;


    private boolean isPrepareA = false;
    private boolean isPrepareB = false;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecordingRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordingRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordingRoomFragment newInstance(String param1, String param2) {
        RecordingRoomFragment fragment = new RecordingRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recordTime = 0;

        tempVoicePcmUrl = Variable.StorageDirectoryPath + "tempVoice.pcm";
        musicFileUrl = Variable.StorageDirectoryPath + "十年伴奏.mp3";
        decodeFileUrl = Variable.StorageDirectoryPath + "decodeFile.pcm";
        composeVoiceUrl = Variable.StorageDirectoryPath + "composeVoice.mp3";
        lrcUrl = Variable.StorageDirectoryPath + "十年.lrc";
        testMusic = Variable.StorageDirectoryPath + "十年.mp3";
        testMusicb = Variable.StorageDirectoryPath + "十年伴奏.mp3";

//        initMusicFile();

        View view = inflater.inflate(R.layout.fragment_recording_room, container, false);
        switch_mode = view.findViewById(R.id.switch_mode);
        resing = view.findViewById(R.id.resing);
        commit = view.findViewById(R.id.commit);
        back = view.findViewById(R.id.back);
        switch_mode_text = view.findViewById(R.id.switch_mode_text);
        resing_text = view.findViewById(R.id.resing_text);
        commit_text = view.findViewById(R.id.commit_text);
        text_play = view.findViewById(R.id.test_play);

        switch_mode.setOnClickListener(this);
        resing.setOnTouchListener(this);
        commit.setOnTouchListener(this);
        back.setOnTouchListener(this);
        text_play.setOnClickListener(this);

        VoiceFunction.StartRecordVoice(tempVoicePcmUrl, this);
        text_play.setText("現在正在錄音中");
        initLrc(view);
        return view;
    }

    private void initLrc(View view){
        mediaPlayer = new MediaPlayer();
        mediaPlayerb = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(testMusic);
            mediaPlayerb.setDataSource(testMusicb);
        } catch (IOException e) {
            Log.d("Recording..","mediaplayer2 createfail");
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
            mediaPlayerb.prepare();
        } catch (IOException e) {
            Log.d("Recording..","mediaplayer3 createfail");
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepareA = true;
                if(isPrepareB){
                    mediaPlayer.start();
                    mediaPlayerb.start();
                }
            }
        });
        mediaPlayerb.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepareB = true;
                if(isPrepareA){
                    mediaPlayer.start();
                    mediaPlayerb.start();
                }
            }
        });

        String lrcStr = readSD();
        Log.d("lrc is",lrcStr);
        LrcView lrcView = view.findViewById(R.id.lrc_view);
        lrcView.setLrc(lrcStr);

//        VoiceFunction.PlayToggleVoice(musicFileUrl, this);
//        lrcView.setPlayer(VoicePlayerEngine.getInstance().getMediaPlayer());
        lrcView.setPlayer(mediaPlayer);
        lrcView.init();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_mode:
                if(!isSwitchMode){
                    switch_mode.setImageResource(R.mipmap.switch1);
                    switch_mode_text.setTextColor(getResources().getColor(R.color.clicked));
                    isSwitchMode = true;
                    mediaPlayer.setVolume(0f,0f);
                }else{
                    switch_mode.setImageResource(R.mipmap.switch0);
                    switch_mode_text.setTextColor(getResources().getColor(R.color.unclicked));
                    isSwitchMode = false;
                    mediaPlayer.setVolume(1f,1f);
                }
                break;

            case R.id.test_play:
                if (FileFunction.IsFileExists(composeVoiceUrl)) {
                    VoiceFunction
                            .PlayToggleVoice(composeVoiceUrl, this);
                }
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(v.getId() == R.id.resing){
                    resing.setImageResource(R.mipmap.resing1);
                    resing_text.setTextColor(getResources().getColor(R.color.clicked));

                }else if(v.getId() == R.id.commit){
                    commit.setImageResource(R.mipmap.yes1);
                    commit_text.setTextColor(getResources().getColor(R.color.clicked));

                }else if(v.getId() == R.id.back){
                    back.setImageResource(R.mipmap.back1);

                }
                break;

            case MotionEvent.ACTION_UP:
                if(v.getId() == R.id.resing){
                    resing.setImageResource(R.mipmap.resing0);
                    resing_text.setTextColor(getResources().getColor(R.color.unclicked));
                    compose();

                }else if(v.getId() == R.id.commit){
                    commit.setImageResource(R.mipmap.yes0);
                    commit_text.setTextColor(getResources().getColor(R.color.unclicked));
                    if (recordVoiceBegin) {
                        VoiceFunction.StopRecordVoice();
                        mediaPlayer.stop();
                        text_play.setText("結束錄音了");
                    }


                }else if(v.getId() == R.id.back){
                    back.setImageResource(R.mipmap.back0);
                    Log.d("testMedia","current second is:"+mediaPlayer.getCurrentPosition());
                    Log.d("testMedia","current second is:"+mediaPlayerb.getCurrentPosition());
                }
                break;
        }
        return true;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void initMusicFile() {
        byte buffer[] = new byte[1024];

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = FileFunction.GetFileOutputStreamFromFile(musicFileUrl);

        try {
            inputStream = getResources().openRawResource(R.raw.test);

            if (fileOutputStream != null) {
                while (inputStream.read(buffer) > -1) {
                    fileOutputStream.write(buffer);
                }
            }
        } catch (Exception e) {
            LogFunction.error("write file异常", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogFunction.error("close file异常", e);
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    LogFunction.error("close file异常", e);
                }
            }

            inputStream = null;
            fileOutputStream = null;
        }
    }

    private void goRecordSuccessState() {
        recordVoiceBegin = false;
        //todo:完成录音
    }

    private void goRecordFailState() {
        recordVoiceBegin = false;
    }

    private void compose() {
        AudioFunction.DecodeMusicFile(testMusicb, decodeFileUrl, 0,
                actualRecordTime + Constant.MusicCutEndOffset, this);
    }

    @Override
    public void recordVoiceBegin() {
        VoiceFunction.StopVoice();

        if (!recordVoiceBegin) {
            recordVoiceBegin = true;

            recordTime = 0;
        }
    }

    @Override
    public void recordVoiceStateChanged(int volume, long recordDuration) {
        if (recordDuration > 0) {
            recordTime = (int) (recordDuration / Constant.OneSecond);
        }
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
        if (recordVoiceBegin) {
            if (actualRecordTime != 0) {
                goRecordSuccessState();
            } else {
                goRecordFailState();
            }
        }
    }

    @Override
    public void recordVoiceFinish() {
        if (recordVoiceBegin) {
            actualRecordTime = recordTime;

            goRecordSuccessState();
        }
    }

    @Override
    public void playVoiceBegin() {
//        playVoiceButton.setImageResource(R.drawable.selector_record_voice_pause);
    }

    @Override
    public void playVoiceFail() {
//        playVoiceButton.setImageResource(R.drawable.selector_record_voice_play);
    }

    @Override
    public void playVoiceFinish() {
//        playVoiceButton.setImageResource(R.drawable.selector_record_voice_play);
    }

    @Override
    public void updateDecodeProgress(int decodeProgress) {
//        composeProgressBar.setProgress(
//                decodeProgress * Constant.MaxDecodeProgress / Constant.NormalMaxProgress);
    }

    @Override
    public void decodeSuccess() {
//        composeProgressBar.setProgress(Constant.MaxDecodeProgress);

        AudioFunction.BeginComposeAudio(tempVoicePcmUrl, decodeFileUrl, composeVoiceUrl, false,
                Constant.VoiceWeight, Constant.VoiceBackgroundWeight,
                -1 * Constant.MusicCutEndOffset / 2 * Constant.RecordDataNumberInOneSecond, this);
    }

    @Override
    public void decodeFail() {
        //todo:解码失败
    }

    @Override
    public void updateComposeProgress(int composeProgress) {
        //todo:合成进度
//        composeProgressBar.setProgress(
//                composeProgress * (Constant.NormalMaxProgress - Constant.MaxDecodeProgress) /
//                        Constant.NormalMaxProgress + Constant.MaxDecodeProgress);
    }

    @Override
    public void composeSuccess() {
        //todo:合成成功
    }

    @Override
    public void composeFail() {
        //todo:合成失败
    }


    public String readSD(){
        //设置文件路径
        File file = new File(lrcUrl);
        //文件输入流
        FileInputStream is;
        StringBuilder stringBuilder = null;
        try {
            if (file.length() != 0) {
                is = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                reader.close();
                is.close();
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(stringBuilder);
    }
}
