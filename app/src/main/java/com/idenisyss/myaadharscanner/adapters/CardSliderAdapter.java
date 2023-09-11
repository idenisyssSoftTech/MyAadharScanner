package com.idenisyss.myaadharscanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.ui.home.homemodels.CardImagesModel;

import java.util.List;

public class CardSliderAdapter  extends PagerAdapter {
    FragmentActivity context;
    List<CardImagesModel> cardImagesModelList;

    private String[] cardTitles = {"Card 1", "Card 2", "Card 3", "Card 4", "Card 5"};

    public CardSliderAdapter(FragmentActivity activity, List<CardImagesModel> cardImagesModelList) {
        this.context = activity;
        this.cardImagesModelList = cardImagesModelList;
    }

    @Override
    public int getCount() {
        return cardImagesModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View cardView = LayoutInflater.from(container.getContext()).inflate(R.layout.card_image_list, container, false);
        ImageView imageView = cardView.findViewById(R.id.card_im);
        imageView.setImageResource(cardImagesModelList.get(position).getDrawbleim());
        container.addView(cardView);
        return cardView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
