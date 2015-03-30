package com.spielpark.steve.cipherhelper;


import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Transposition#getInstance} factory method to
 * create an instance of this fragment.
 */
public class Transposition extends Fragment {
    private static Transposition mInstance;
    private static LinearLayout mLinear;
    private static View mView;
    private static EditText txtCipher;
    private static EditText txtColumns;
    private static String mCipher;
    private static int mColumns = -1;
    private static int numTextViews = 0;
    private static final LayoutParams txtLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment transposition.
     */
    public static Transposition getInstance() {
        if (mInstance == null) {
            mInstance = new Transposition();
        }
        return mInstance;
    }

    public Transposition() {

    }

    private void scrollTextViews(int amt) {
        for (int i = 1; i <= numTextViews; i++) {
            mLinear.findViewWithTag("txtViewNum" + i).setScrollY(amt);
        }
    }

    private void handleColumns() {
        for (int i = 1; i <= numTextViews; i++) {
            mLinear.removeView(mLinear.findViewWithTag("txtViewNum" + i));
        }
        numTextViews = 0;
        if (mColumns == -1 || mCipher == null) return;
        int numRows = mCipher.length() / mColumns;
        int rem = mCipher.length() % mColumns;
        numRows = rem > 0 ? numRows + 1 : numRows;
        char[] cipher = mCipher.toCharArray();
        char[][] grid = new char[mColumns][numRows];
        for (int i = 0; i < mColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                int pos = j + (i*numRows);
                grid[i][j] = pos > cipher.length - 1 ? 'X' : cipher[pos];
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mColumns; i++) {
            sb.setLength(0);
            for (int j = 0; j < numRows; j++) {
                sb.append(grid[i][j]);
            }
            Log.d("handleColumns", "Appended: " + sb.toString());
            mLinear.addView(makeText(sb.toString().replaceAll(".(?=.)", "$0  ")));
        }
    }

    private TextView makeText(String text) {
        numTextViews++;
        ScrollTextView tv = new ScrollTextView(getActivity());
        tv.setLayoutParams(txtLayout);
        tv.setMaxWidth(75);
        if (numTextViews %2 == 0) {
            tv.setBackgroundColor(Color.LTGRAY);
        }
        tv.setTextSize(12);
        tv.setText(text);
        tv.setLineSpacing(8, 1);
        tv.setPadding(25, 15, 15, 15);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setTag("txtViewNum" + numTextViews);
        return tv;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtLayout.setMargins(0, 0, 0, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_transposition, container, false);
        mLinear = (LinearLayout) mView.findViewById(R.id.linearColumns);
        txtCipher = ((EditText) mView.findViewById(R.id.edtCipherText));
        txtColumns = ((EditText) mView.findViewById(R.id.edtNumRows));
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Transposition Cipher");
        txtCipher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCipher = s.toString().toUpperCase();
                Transposition.this.handleColumns();
            }
        });
        txtColumns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mColumns = -1;
                    return;
                }
                mColumns = Integer.parseInt(s.toString(), 10);
                if (mColumns <= 0) {
                    s.clear();
                    s.append("1");
                    mColumns = 1;
                }
                Transposition.this.handleColumns();
            }
        });
    }

    private class ScrollTextView extends TextView {
        public ScrollTextView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                Transposition.this.scrollTextViews(this.getScrollY());
            }
            return super.onTouchEvent(event);
        }
    }
}
