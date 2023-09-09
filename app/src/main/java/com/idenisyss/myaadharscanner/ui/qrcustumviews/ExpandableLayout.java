package com.idenisyss.myaadharscanner.ui.qrcustumviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idenisyss.myaadharscanner.R;

public class ExpandableLayout extends LinearLayout {

    private boolean isExpanded = false;
    private TextView contentTextView; // The TextView to display dynamic content

    public ExpandableLayout(Context context) {
        super(context);
        initialize();
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setOrientation(VERTICAL);
        setVisibility(GONE);

        // Inflate the content layout and add it to the ExpandableLayout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View contentLayout = inflater.inflate(R.layout.expandable_content, this, false);
        contentTextView = contentLayout.findViewById(R.id.contentTextView);
        addView(contentLayout);
    }

    public void toggle() {
        isExpanded = !isExpanded;
        setVisibility(isExpanded ? VISIBLE : GONE);
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
        setVisibility(isExpanded ? VISIBLE : GONE);
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    // Method to set dynamic content
    public void setContentText(String content) {
        contentTextView.setText(content);
    }
}
