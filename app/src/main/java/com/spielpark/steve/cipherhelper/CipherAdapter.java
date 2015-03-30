package com.spielpark.steve.cipherhelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.spielpark.steve.cipherhelper.customViews.StrokedTextView;

import java.util.List;

/**
 * Created by Steve on 3/25/2015.
 */
public class CipherAdapter extends ArrayAdapter {
    private List<String> mArray;
    private LayoutInflater mInflator;
    private int mLayout;
    private Context ctx;

    public CipherAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mArray = objects;
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = resource;
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = mInflator.inflate(mLayout, null);
        }
        final StrokedTextView cipher = ((StrokedTextView) v.findViewById(R.id.txtCipher));
        cipher.setStrokeColor(Color.BLACK);
        cipher.setStrokeWidth(8);
        cipher.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "data.ttf"));
        cipher.setText(mArray.get(position));
        return v;
    }
}
