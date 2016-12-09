package com.dtechmonkey.d_techmonkey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.dtechmonkey.d_techmonkey.adapters.LanguageRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.dtechmonkey.d_techmonkey.R.array.languages;

public class Language extends AppCompatActivity implements View.OnClickListener, LanguageRecyclerAdapter.ItemSelectedListener {

    public static final String LANGUAGES_SELECTED = "lang_selected";
    private String clearLanguage;

    private final ArrayList<String> languageSelected = new ArrayList<>();

    private boolean startedFromActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        startedFromActivity = getIntent().getBooleanExtra(MainActivity.LANGUAGE_SELECTOR, false);

        Toolbar toolbar = (Toolbar)findViewById(R.id.add_comment_toolbar);
        setSupportActionBar(toolbar);

        Button buttonContinue=(Button)findViewById(R.id.language_continue);
        Button buttonSkip=(Button)findViewById(R.id.language_skip);

        String[] languages=getResources().getStringArray(R.array.languages);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.language_recyclerView);
        LanguageRecyclerAdapter adapter=new LanguageRecyclerAdapter(languages);
        adapter.setListener(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSetting();
                Intent intent = new Intent(Language.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        buttonContinue.setOnClickListener(this);
    }
    // for language selection event final
    /*public void prepareSelection(View view, int position){
        if(((CheckBox)view).isChecked()){
            Toast.makeText(this, languages[position]+" "+"language is selected",Toast.LENGTH_SHORT).show();
            languageSelected=position;
        }
    }*/

    //button click listener
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences=Language.this.getSharedPreferences(getString(R.string.LANG_FILE),MODE_PRIVATE);
        clearLanguage=sharedPreferences.getString(getString(R.string.LANGUAGE),"");
        if(!clearLanguage.equals("")){
            if (!startedFromActivity) {
                Intent intent = new Intent(Language.this, MainActivity.class);
                intent.putStringArrayListExtra(LANGUAGES_SELECTED, languageSelected);
                startActivity(intent);
                finish();
                return;
            }
            Intent returnIntent = new Intent();
            returnIntent.putStringArrayListExtra(LANGUAGES_SELECTED, languageSelected);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        else
            Toast.makeText(this,"Please select any one language",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onItemClicked(int position) {
        String languageKey = getResources().getStringArray(R.array.languages_key)[position];
        //Log.d("languageKey",languageKey);
        /*if (languageSelected.contains(languageKey)) {
            languageSelected.remove(languageKey);
            return;
        }
        languageSelected.add(languageKey);*/
        SharedPreferences sharedPreferences=Language.this.getSharedPreferences(getString(R.string.LANG_FILE),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(getString(R.string.LANGUAGE),languageKey);
        editor.commit();
    }
    public void clearSetting(){
        SharedPreferences sharedPreferences=Language.this.getSharedPreferences(getString(R.string.LANG_FILE),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}