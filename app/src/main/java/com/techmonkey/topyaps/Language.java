package com.techmonkey.topyaps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.techmonkey.topyaps.adapters.LanguageRecyclerAdapter;
import com.techmonkey.topyaps.helper.MakeProperTag;
import com.techmonkey.topyaps.models.category.Collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class
Language extends AppCompatActivity implements View.OnClickListener, LanguageRecyclerAdapter.ItemSelectedListener {

    private static final String TAG = Language.class.getSimpleName();

    /*public static final String LANGUAGES_SELECTED = "lang_selected";
    private String clearLanguage;
    private String languageKey="";*/

    private final ArrayList<String> languageSelected = new ArrayList<>();

    private boolean startedFromActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageKeys = preferences.getString("LANGUAGES", "");
        String[] xmlLanguageKeys = getResources().getStringArray(R.array.languages_key);
        ArrayList<Integer> positions = new ArrayList<>();
        if (!languageKeys.isEmpty()) {
            ArrayList<String> userLanguageKeys = new ArrayList<>(Arrays.asList(languageKeys.split(",")));
            for (int i = 0; i < xmlLanguageKeys.length; i++) {
                if (userLanguageKeys.contains(xmlLanguageKeys[i])) {
                    positions.add(i);
                }
            }
        }
        Log.d(TAG, "onCreate: " + Arrays.toString(positions.toArray()));

        startedFromActivity = getIntent().getBooleanExtra(MainActivity.LANGUAGE_SELECTOR, false);

        Toolbar toolbar = (Toolbar)findViewById(R.id.add_comment_toolbar);
        setSupportActionBar(toolbar);

        Button buttonContinue=(Button)findViewById(R.id.language_continue);
        Button buttonSkip=(Button)findViewById(R.id.language_skip);

        String[] languages=getResources().getStringArray(R.array.languages);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.language_recyclerView);
        LanguageRecyclerAdapter adapter=new LanguageRecyclerAdapter(languages, positions);
        adapter.setListener(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //Skip languages button having click listener
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSetting();
                Intent intent = new Intent(Language.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //Setting up languages button having click listener
        buttonContinue.setOnClickListener(this);
    }
    // for language selection event final
    /*public void prepareSelection(View view, int position){
        if(((CheckBox)view).isChecked()){
            Toast.makeText(this, languages[position]+" "+"language is selected",Toast.LENGTH_SHORT).show();
            languageSelected=position;
        }
    }*/
    //button click listener for setting up language
    @Override
    public void onClick(View v) {
        if(!languageSelected.isEmpty()){
            String languages = "";
            for (String languageKey : languageSelected) {
                languages += languageKey + ",";
            }
            languages = MakeProperTag.makeTag(languages);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("LANGUAGES", languages);
            editor.apply();

            if (!startedFromActivity) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            setResult(RESULT_OK);
            finish();

        } else
            Toast.makeText(this,"Please select any one language",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onItemClicked(int position) {
        String languageKey = getResources().getStringArray(R.array.languages_key)[position];
        if (languageSelected.contains(languageKey)) {
            languageSelected.remove(languageKey);
        } else {
            languageSelected.add(languageKey);
        }
        Log.d(TAG, "onItemClicked: " + Arrays.toString(languageSelected.toArray()));
    }

    public void clearSetting(){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}