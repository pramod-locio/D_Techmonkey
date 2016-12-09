package com.dtechmonkey.d_techmonkey;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private WebView webView;
    private ImageView currentImage,authorImage;
    private TextView title,date,authorName;
    private String content,subject,link;
    private Button viewResponse;
    private int authorId;
    //private CollapsingToolbarLayout collapsing_container;

    private PostJSONData postJSONData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        postJSONData = (PostJSONData) intent.getSerializableExtra(Constants.REFERENCE.TOPYAPS_DATA);

        // title on collapsing toolbar
        /*collapsing_container = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);
        collapsing_container.setTitle(postJSONData.getTitle().getRendered());
*/
        configView();
        try {
            Glide.with(getApplicationContext()).load(postJSONData.getBetterFeaturedImage().getSourceUrl()).into(currentImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        subject=postJSONData.getTitle().getRendered().replaceAll("(&#8216;)|(&#038;)|(<i>)|(</i>)|(&#8217;)|(&#8221;)|(&#8220;)","\'");
        link=postJSONData.getLink();
        title.setText(subject);
        authorId=postJSONData.getAuthor();
        date.setText("Originally Posted on "+postJSONData.getDateGmt());
        try {
            //Glide.with(this).load("http://topyaps.com/author/anuradha/").into(authorImage);

            //authorName.setText(postJSONData.getLinks().getAuthor().get(1).getHref());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        authorName.setText(postJSONData.getAuthor());

        content=postJSONData.getContent().getRendered();
        StringBuilder html = new StringBuilder();
        try{
            html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"hottopix-hi-IN\" prefix=\"og: http://ogp.me/ns#\">");
            html.append("<head>");
            html.append("<meta name=\"viewport\" content=\"initial-scale=1.0, maximum-scale=3.0, user-scalable=no, width=device-width\">");
            html.append("<style>\n" +"   iframe {  \n" +
                    "   width:100%; \n" +
                    "   height:auto; \n" + "}"+

                    "a{color: #777;}"+

                    ".post img, img, .aligncenter  {\n" +
                    "max-width: 100%;\n" +
                    "height: auto;\n" +
                    "margin-top: 15px;\n" +
                    "margin-bottom: 15px;\n" + "}\n" +

                    ".aligncenter, .alignnone {\n" +
                    "text-align: center!important;\n" +
                    "margin-left: auto!important;\n" +
                    "margin-right: auto!important;\n" +
                    "float: none!important;\n" +
                    "display: block!important;\n" + "}");

            html.append("</style>");
            html.append("<script src=\"http://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>");
            html.append("</head>");
            html.append("<body >");
            html.append("<div class=\"post \"><div class=\"post-page-content\">");
            html.append("<div class=\"videoWrapper\">");
            html.append(content);
            html.append("</div></div>");
            html.append("</body></html>");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(html.toString(), "text/html; charset=utf-8","utf-8");

        }catch (Exception e){
            e.printStackTrace();
        }

        viewResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
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
        webView=(WebView)findViewById(R.id.card_view_content);
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
    //Favorite implementation here
}