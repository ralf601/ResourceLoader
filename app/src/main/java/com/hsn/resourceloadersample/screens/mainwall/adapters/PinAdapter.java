package com.hsn.resourceloadersample.screens.mainwall.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsn.resourceloader.ResourceRequest;
import com.hsn.resourceloadersample.R;
import com.hsn.resourceloadersample.common.Utils;
import com.hsn.resourceloadersample.model.UserContent;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hassanshakeel on 2/15/18.
 */

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(UserContent userContent);
    }

    private List<UserContent> userContents = new ArrayList<>();
    private OnItemClickListener listener;

    public void update(List<UserContent> userContents) {
        this.userContents = userContents;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(userContents.get(position));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClick(userContents.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userContents.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View root;
        public CircleImageView profilePic;
        public TextView name;
        public TextView userId;
        public TextView date;
        public RelativeLayout userDetails;
        public TextView likeCount;


        public ViewHolder(View root) {
            super(root);
            this.root = root;
            imageView = root.findViewById(R.id.mainImageView);
            this.profilePic = (CircleImageView) root.findViewById(R.id.profilePic);
            this.name = (TextView) root.findViewById(R.id.name);
            this.userId = (TextView) root.findViewById(R.id.userId);
            this.date = (TextView) root.findViewById(R.id.date);
            this.userDetails = (RelativeLayout) root.findViewById(R.id.userDetails);
            this.likeCount = (TextView) root.findViewById(R.id.likeCount);
        }

        public void bind(UserContent userContent) {
            //Glide.with(imageView).load(userContent.getUser().getProfileImage().getLarge()).into(imageView);
            likeCount.setText(userContent.getLikes() + " Likes");
            userId.setText("@" + userContent.getUser().getUsername());
            name.setText(userContent.getUser().getName());
            date.setText(Utils.fromServerDateTimetoUiDateTime(userContent.getCreatedAt()));

            new ResourceRequest().load(userContent.getUrls().getRegular()).as(Bitmap.class, new ResourceRequest.OnCompelTeListener<Bitmap>() {
                @Override
                public void onComplete(Bitmap result) {
                    imageView.setImageBitmap(result);
                }
                @Override
                public void onError(Exception exception) {
                    exception.printStackTrace();
                }
            });

            new ResourceRequest().load(userContent.getUser().getProfileImage().getMedium()).as(Bitmap.class, new ResourceRequest.OnCompelTeListener<Bitmap>() {
                @Override
                public void onComplete(Bitmap result) {
                    profilePic.setImageBitmap(result);
                }
                @Override
                public void onError(Exception exception) {
                    exception.printStackTrace();
                }
            });

        }
    }


}
