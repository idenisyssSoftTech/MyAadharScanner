package com.abhiram.qrbarscanner.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.abhiram.qrbarscanner.activities.EnterDetailsactivity;
import com.abhiram.qrbarscanner.utilities.AppConstants;

public class HomeViewsControler {

    private CardView cardView;
    private String home_selected_title;
    private   int drawableResourceId;
    Context context;
    public HomeViewsControler(Context context, CardView listCardView, String home_selected_title, int drawableResourceId) {
        this.context = context;
        this.cardView = listCardView;
        this.home_selected_title = home_selected_title;
        this.drawableResourceId = drawableResourceId;
        setupButtonClickListener();
    }


    // Method to set up click listener
    private void setupButtonClickListener() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // You can perform actions or call methods
                Intent j = new Intent(context, EnterDetailsactivity.class);
                j.putExtra(AppConstants.INTENT_KEY_HOME_TITLE,home_selected_title);
                j.putExtra(AppConstants.INTENT_KEY_HOME_IMAGE,drawableResourceId );
                context.startActivity(j);
            }
        });
    }
}
