package lxd.RecordingRoom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lxd.ComposeAudio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayRoomFragment extends Fragment {


    public PlayRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_room, container, false);
    }

}
