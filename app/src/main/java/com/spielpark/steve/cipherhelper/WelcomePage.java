package com.spielpark.steve.cipherhelper;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class WelcomePage extends ActionBarActivity {
    private final String[] types = new String[] {
            "Transposition",
            "Monoalphabetic",
            "Polyalphabetic",
            "Vigenere",
            "One-Time Pass",
            "Playfair"
    };
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Typeface akaDora = Typeface.createFromAsset(getAssets(), "akaDora.ttf");
        ((TextView) findViewById(R.id.txtGreeting)).setTypeface(akaDora);
        ((TextView) findViewById(R.id.txtSelection)).setTypeface(akaDora);
        list = ((ListView) findViewById(R.id.listCiphers));
        setUpList();
    }

    private void setUpList() {
        ArrayList<String> cipherTypes = new ArrayList<>();
        for (String s : types) {
            cipherTypes.add(s);
        }
        list.setAdapter(new CipherAdapter(this, R.layout.list_ciper_options, cipherTypes));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WelcomePage.this, CipherActivity.class);
                intent.putExtra("fragmentName", (String) list.getItemAtPosition(position));
                WelcomePage.this.startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
