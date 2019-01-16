package io.oasis.slide;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import io.oasis.slide.database.News;


public class VerticlePagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> mNews;

    public VerticlePagerAdapter(Context context , ArrayList<News> news) {
        mContext = context;
        mNews = news;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);

        TextView label = (TextView) itemView.findViewById(R.id.textView);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        TextView heading = itemView.findViewById(R.id.heading);

        Button link = itemView.findViewById(R.id.newsLink);
        final String linker  = mNews.get(position).getUrl();

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,WebActivity.class);
                intent.putExtra("link",linker);
                mContext.startActivity(intent);
            }
        });

        heading.setText(mNews.get(position).getTitle());
        label.setText(mNews.get(position).getDesc());
        Picasso.get().load(mNews.get(position).getLink()).placeholder(R.drawable.bg).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}
