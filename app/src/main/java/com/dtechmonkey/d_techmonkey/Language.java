package com.dtechmonkey.d_techmonkey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dtechmonkey.d_techmonkey.adapters.LanguageRecyclerAdapter;

public class Language extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String[] languages;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Toolbar toolbar = (Toolbar)findViewById(R.id.add_comment_toolbar);
        setSupportActionBar(toolbar);

        languages=getResources().getStringArray(R.array.languages);
        recyclerView=(RecyclerView)findViewById(R.id.language_recyclerView);
        adapter=new LanguageRecyclerAdapter(languages);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        onContinue();
    }
    public void onContinue(){
        button=(Button)findViewById(R.id.language_continue);
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        startActivity(new Intent("com.dtechmonkey.d_techmonkey.MainActivity"));
                    }
                }
        );
    }
}
