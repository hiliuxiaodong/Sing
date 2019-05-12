package lxd.Share;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lxd.Common.ManageFragments;
import lxd.Common.MyApplication;
import lxd.ComposeAudio.R;
import lxd.MainActivity;
import lxd.RecordingRoom.RecordingRoomFragment;
import lxd.Singer.ItemRecommedFragment;

public class ShareFragmentAdapter extends RecyclerView.Adapter<ShareFragmentAdapter.ViewHolder>{


    private List<ItemShareFragment> itemList;

    public ShareFragmentAdapter(List<ItemShareFragment> find_itemList){
        this.itemList = find_itemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView headimage;
        TextView userName;
        TextView shareTime;
        ImageView songImage;
        TextView song;
        TextView singer;
        Button commit;

        public ViewHolder(View itemView) {
            super(itemView);
            headimage = itemView.findViewById(R.id.head_image);
            userName = itemView.findViewById(R.id.user_name);
            shareTime = itemView.findViewById(R.id.share_time);
            songImage = itemView.findViewById(R.id.song_image);
            song = itemView.findViewById(R.id.song);
            singer = itemView.findViewById(R.id.singer);
            commit = itemView.findViewById(R.id.commit);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_share_fragment,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordingRoomFragment recordingRoomFragment = new RecordingRoomFragment();
                ManageFragments.replaceFragment(R.id.root_container , recordingRoomFragment,true);
                MainActivity.setNavigationVisible(View.INVISIBLE);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareFragmentAdapter.ViewHolder holder, int position) {
        ItemShareFragment item = itemList.get(position);
        holder.singer.setText("测试");
        holder.song.setText("cs");
//        Glide.with(MyApplication.getContext()).load(R.mipmap.record).into(holder.headimage);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
