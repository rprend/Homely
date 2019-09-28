package org.homely;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class CardAdaptor extends PagerAdapter {

    private List<Critiques> critiques;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardAdaptor(List<Critiques> critiques, Context context) {
        this.critiques = critiques;
        this.context = context;
    }

    @Override
    public int getCount() {
        return critiques.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        TextView title, desc;

        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        title.setText(critiques.get(position).getTitle());
        desc.setText(critiques.get(position).getDesc());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
