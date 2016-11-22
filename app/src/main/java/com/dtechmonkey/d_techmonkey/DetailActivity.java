package com.dtechmonkey.d_techmonkey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dtechmonkey.d_techmonkey.helper.Constants;
import com.dtechmonkey.d_techmonkey.models.PostJSONData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.text.DateFormat.getDateTimeInstance;

public class DetailActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private WebView webView;
    private ImageView currentImage;
    private TextView title,date;
    private String content,subject,link;
    private Button viewResponse;
    //private CollapsingToolbarLayout collapsing_container;

    private PostJSONData postJSONData;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        postJSONData = (PostJSONData) intent.getSerializableExtra(Constants.REFERENCE.TOPYAPS_DATA);
        Log.d(TAG, "onCreate: " + postJSONData.toString());

        // title on collapsing toolbar
        /*collapsing_container = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);
        collapsing_container.setTitle(postJSONData.getTitle().getRendered());
*/
        configView();
        try {
            Glide.with(getApplicationContext()).load(postJSONData.getBetterFeaturedImage()
                    .getMediaDetails().getSizes().getMediumThumb().getSourceUrl())
                    .into(currentImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        subject=postJSONData.getTitle().getRendered().replaceAll("(&#8216;)|(&#8217;)|(&#8220;)|(&#8221;)","\'");
        link=postJSONData.getLink();
        title.setText(subject);
        date.setText("Originally Posted on "+postJSONData.getDateGmt());

        content=postJSONData.getContent().getRendered();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(content, "text/html;", "utf-8");
        viewResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, AddComment.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //makeRequest();
    }

    private void configView()
    {
        currentImage=(ImageView)findViewById(R.id.detail_imgView);
        webView=(WebView)findViewById(R.id.card_view_content);
        title=(TextView)findViewById(R.id.detail_title);
        date=(TextView)findViewById(R.id.detail_date);
        viewResponse=(Button)findViewById(R.id.view_response);
    }

    /*public void makeRequest() {
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading Content....");

        try {
            PostRetrieve client = TopYapsServiceGen.createService(PostRetrieve.class);
            Call<List<PostJSONData>> call = client.getPostList(client.OFFSET);
            call.enqueue(new Callback<List<PostJSONData>>() {
                @Override
                public void onResponse(Call<List<PostJSONData>> call, Response<List<PostJSONData>> response) {
                    progressDialog.hide();
                    //List<PostJSONData> postJSONData = response.body();
                    webView.getSettings().setDefaultTextEncodingName("utf-8");
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setHorizontalScrollBarEnabled(false);
                    webView.loadData(content, "text/html; charset=utf-8","utf-8");
                }

                @Override
                public void onFailure(Call<List<PostJSONData>> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    progressDialog.hide();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/

    //Go back most recent parent
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id)
        {
            case R.id.detail_share:
                shareIt();
                break;
            case R.id.detail_add_comment:
                addComment();
                break;
            /*case R.id.detail_star:
                Toast.makeText(this, "favourite is Working.....", Toast.LENGTH_SHORT).show();
                break;*/
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //Sharing implementation here
    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Click here for "+subject+" "+link);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }
    //Add Comment implementation here
    private void addComment(){
        Intent intent=new Intent(DetailActivity.this,AddComment.class);
        startActivity(intent);
    }
    //Favorite implementation here
}
