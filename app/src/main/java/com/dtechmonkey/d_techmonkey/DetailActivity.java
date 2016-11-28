package com.dtechmonkey.d_techmonkey;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dtechmonkey.d_techmonkey.adapters.JSONDataAdapter;
import com.dtechmonkey.d_techmonkey.helper.Constants;
import com.dtechmonkey.d_techmonkey.helper.Utils;
import com.dtechmonkey.d_techmonkey.models.PostJSONData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.text.DateFormat.getDateTimeInstance;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private WebView webView;
    private ImageView currentImage,authorImage;
    private TextView title,date,authorName;
    private String content,subject,link;
    private Button viewResponse;
    private int userID;
    private String url=Constants.REFERENCE.TOPYAPS_DATA;
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

        userID=postJSONData.getAuthor();
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
        subject=postJSONData.getTitle().getRendered().replaceAll("(&#8216;)|(&#038;)|(<i>)|(</i>)|(&#8217;)|(&#8221;)|(&#8220;)","\'");
        link=postJSONData.getLink();
        title.setText(subject);
        date.setText("Originally Posted on "+postJSONData.getDateGmt());
        try {
            //Glide.with(this).load("http://topyaps.com/author/anuradha/").into(authorImage);

            //authorName.setText(postJSONData.getLinks().getAuthor().get(1).getHref());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        content=postJSONData.getContent().getRendered();
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new MyJavaScriptInterface(this),"Android");
        webView.loadData(content, "text/html; charset=utf-8","utf-8");
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        viewResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, AddComment.class));
            }
        });

        fab.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void configView()
    {

        authorName=(TextView)findViewById(R.id.author_name);
        authorImage=(ImageView)findViewById(R.id.author_img);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        currentImage=(ImageView)findViewById(R.id.detail_imgView);
        webView=(WebView)findViewById(R.id.webView);
        title=(TextView)findViewById(R.id.detail_title);
        date=(TextView)findViewById(R.id.detail_date);
        viewResponse=(Button)findViewById(R.id.view_response);

    }

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
            /*case R.id.detail_share:
                shareIt();
                break;
*/            case R.id.detail_add_comment:
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
        Intent intent=new Intent(DetailActivity.this, AddComment.class);
        intent.putExtra("url","http://topyaps.com/"+postJSONData.getSlug());
        startActivity(intent);
    }

    //FloatingActionButton Action here
    @Override
    public void onClick(View v) {
        shareIt();
    }
    //New code for webView from here
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    public class MyJavaScriptInterface {
        Activity activity;
        public MyJavaScriptInterface(DetailActivity activity) {
            this.activity=activity;
        }
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void closeActivity() {
            activity.finish();
        }
    }
    //Favorite implementation here
}