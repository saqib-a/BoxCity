package com.example.saqib.myapplication.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saqib.myapplication.Interface.ItemClickListener;
import com.example.saqib.myapplication.Model.RSSObject;
import com.example.saqib.myapplication.R;

//credit to: https://www.youtube.com/watch?v=APInjVO0WkQ
class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public TextView textTitle;
    public TextView textDate;
    public TextView textContent, textLink;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);

        textTitle = (TextView) itemView.findViewById(R.id.textTitle);
        textDate = (TextView) itemView.findViewById(R.id.textDate);
        textContent = (TextView) itemView.findViewById(R.id.textContent);
        textLink = (TextView) itemView.findViewById(R.id.textLink);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.row, viewGroup, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i) {

        feedViewHolder.textTitle.setText(rssObject.getItems().get(i).getTitle().replaceAll("(<(!--|script)(.|\n[^<])(--|script)>)|(<|&lt;)(/?[\\w!?]+)\\s?[^<](>|&gt;)|(&[\\w]+;)", " "));
        feedViewHolder.textDate.setText(rssObject.getItems().get(i).getPubDate());
        feedViewHolder.textContent.setText(rssObject.getItems().get(i).getDescription().replaceAll("(<(!--|script)(.|\n[^<])(--|script)>)|(<|&lt;)(/?[\\w!?]+)\\s?[^<](>|&gt;)|(&[\\w]+;)", " "));
        feedViewHolder.textLink.setText(rssObject.getItems().get(i).getLink());

        feedViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                if (!isLongClick)
                {
                    Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    mContext.startActivity(openBrowser);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
