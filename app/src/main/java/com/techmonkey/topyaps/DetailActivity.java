package com.techmonkey.topyaps;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techmonkey.topyaps.helper.Constants;
import com.techmonkey.topyaps.helper.DateTimeFormat;
import com.techmonkey.topyaps.helper.Utils;
import com.techmonkey.topyaps.models.AuthorInfo;
import com.techmonkey.topyaps.models.PostJSONData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG=MainActivity.class.getSimpleName();
    private FloatingActionButton fab;
    private WebView webView;
    private ImageView currentImage,authorImage;
    private TextView title,date,authorName;
    private String content,subject,link;
    private Button viewResponse;
    private int authorId;
    //private CollapsingToolbarLayout collapsing_container;

    private PostJSONData postJSONData, notificationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        postJSONData = (PostJSONData) intent.getSerializableExtra(Constants.REFERENCE.TOPYAPS_DATA);

        notificationData= (PostJSONData) intent.getSerializableExtra(Constants.REFERENCE.TOPYAPS_DATA);


        // title on collapsing toolbar
        /*collapsing_container = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);
        collapsing_container.setTitle(postJSONData.getTitle().getRendered());
*/      configView();

        for(String key: getIntent().getExtras().keySet()){
            if (key.equals("title")){
                title.setText(getIntent().getExtras().getString(key));
            }
            else
                date.setText(getIntent().getExtras().getString(key));
        }

        if (postJSONData != null) {
            String newDate= DateTimeFormat.DateTimeFormat(postJSONData.getDate());
            subject = postJSONData.getTitle().getRendered().replaceAll("(&#8230;)|(&#8216;)|(&#038;)|(<i>)|(</i>)|(&#8217;)|(&#8221;)|(&#8220;)|(&#8211;)", "\'");
            link = postJSONData.getLink();
            authorId = postJSONData.getAuthor();

            try {
                Glide.with(getApplicationContext()).load(postJSONData.getBetterFeaturedImage().getSourceUrl()).into(currentImage);
            } catch (Exception e) {
                e.printStackTrace();
            }


            title.setText(subject);
            date.setText("Originally Posted on "+newDate);

            //Request for Author name
            if (Utils.getInstance().checkIfHasNetwork()){
                PostRetrieve client = TopYapsServiceGen.provideTopyapsService();
                Call<AuthorInfo> call = client.getAuthorInfo(authorId);
                call.enqueue(new Callback<AuthorInfo>() {
                    @Override
                    public void onResponse(Call<AuthorInfo> call, Response<AuthorInfo> response) {
                        AuthorInfo authorInfo = response.body();
                        authorName.setVisibility(View.VISIBLE);
                        authorImage.setVisibility(View.VISIBLE);
                        authorName.setText(authorInfo.getName());
                    }

                    @Override
                    public void onFailure(Call<AuthorInfo> call, Throwable t) {
                        Log.e(TAG, t.toString());

                    }
                });
            }
            //Request for Author images
        /*try {
            Glide.with(this).load("https://github.com/pramod-locio/Images/blob/master/2015-08-07_14.52.214-200x200.jpg").into(authorImage);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
            content=postJSONData.getContent().getRendered();
            StringBuilder html = new StringBuilder();
            try{
                html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"hottopix-hi-IN\" prefix=\"og: http://ogp.me/ns#\">");
                html.append("<head>");
                html.append("<meta name=\"viewport\" content=\"initial-scale=1.0, maximum-scale=3.0, user-scalable=no, width=device-width\">");
                html.append("<style>\n" +

                        "iframe {\n" +
                            "width:100%; \n" +
                            "max-height:100%; \n" + "}"+

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
                html.append("<script type=\"text/javascript\">\n" +
                        "\t\t\twindow._wpemojiSettings = {\"baseUrl\":\"https:\\/\\/s.w.org\\/images\\/core\\/emoji\\/2\\/72x72\\/\",\"ext\":\".png\",\"svgUrl\":\"https:\\/\\/s.w.org\\/images\\/core\\/emoji\\/2\\/svg\\/\",\"svgExt\":\".svg\",\"source\":{\"concatemoji\":\"http:\\/\\/topyaps.com\\/wp-includes\\/js\\/wp-emoji-release.min.js\"}};\n" +
                        "\t\t\t!function(a,b,c){function d(a){var c,d,e,f,g,h=b.createElement(\"canvas\"),i=h.getContext&&h.getContext(\"2d\"),j=String.fromCharCode;if(!i||!i.fillText)return!1;switch(i.textBaseline=\"top\",i.font=\"600 32px Arial\",a){case\"flag\":return i.fillText(j(55356,56806,55356,56826),0,0),!(h.toDataURL().length<3e3)&&(i.clearRect(0,0,h.width,h.height),i.fillText(j(55356,57331,65039,8205,55356,57096),0,0),c=h.toDataURL(),i.clearRect(0,0,h.width,h.height),i.fillText(j(55356,57331,55356,57096),0,0),d=h.toDataURL(),c!==d);case\"diversity\":return i.fillText(j(55356,57221),0,0),e=i.getImageData(16,16,1,1).data,f=e[0]+\",\"+e[1]+\",\"+e[2]+\",\"+e[3],i.fillText(j(55356,57221,55356,57343),0,0),e=i.getImageData(16,16,1,1).data,g=e[0]+\",\"+e[1]+\",\"+e[2]+\",\"+e[3],f!==g;case\"simple\":return i.fillText(j(55357,56835),0,0),0!==i.getImageData(16,16,1,1).data[0];case\"unicode8\":return i.fillText(j(55356,57135),0,0),0!==i.getImageData(16,16,1,1).data[0];case\"unicode9\":return i.fillText(j(55358,56631),0,0),0!==i.getImageData(16,16,1,1).data[0]}return!1}function e(a){var c=b.createElement(\"script\");c.src=a,c.type=\"text/javascript\",b.getElementsByTagName(\"head\")[0].appendChild(c)}var f,g,h,i;for(i=Array(\"simple\",\"flag\",\"unicode8\",\"diversity\",\"unicode9\"),c.supports={everything:!0,everythingExceptFlag:!0},h=0;h<i.length;h++)c.supports[i[h]]=d(i[h]),c.supports.everything=c.supports.everything&&c.supports[i[h]],\"flag\"!==i[h]&&(c.supports.everythingExceptFlag=c.supports.everythingExceptFlag&&c.supports[i[h]]);c.supports.everythingExceptFlag=c.supports.everythingExceptFlag&&!c.supports.flag,c.DOMReady=!1,c.readyCallback=function(){c.DOMReady=!0},c.supports.everything||(g=function(){c.readyCallback()},b.addEventListener?(b.addEventListener(\"DOMContentLoaded\",g,!1),a.addEventListener(\"load\",g,!1)):(a.attachEvent(\"onload\",g),b.attachEvent(\"onreadystatechange\",function(){\"complete\"===b.readyState&&c.readyCallback()})),f=c.source||{},f.concatemoji?e(f.concatemoji):f.wpemoji&&f.twemoji&&(e(f.twemoji),e(f.wpemoji)))}(window,document,window._wpemojiSettings);\n" +
                        "\t\t</script>");

                html.append("<script type='text/javascript' src='http://topyaps.com/wp-includes/js/jquery/jquery.js?ver=1.12.4'></script>");
                html.append("<script type='text/javascript' src='http://topyaps.com/wp-includes/js/jquery/jquery-migrate.min.js?ver=1.4.1'></script>");
                html.append("<script type='text/javascript' src='http://topyaps.com/wp-includes/js/jquery/jquery.js'></script>");
                html.append("<script type='text/javascript' src='http://topyaps.com/wp-includes/js/jquery/jquery-migrate.min.js'></script>");
                html.append("<script type='text/javascript' src='http://topyaps.com/wp-includes/js/swfobject.js'></script>");
                html.append("<link rel=\"wlwmanifest\" type=\"application/wlwmanifest+xml\" href=\"http://topyaps.com/wp-includes/wlwmanifest.xml\"/>");
                html.append("<script type='text/javascript' src='http://topyaps.com/wp-includes/js/wp-embed.min.js'></script>");
                html.append("</head>");
                html.append("<body >");
                html.append("<div class=\"post \"><div class=\"post-page-content\">");
                html.append("<div class=\"videoWrapper\">");
                html.append(content);
                html.append("<script src=\"http://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>");
                html.append("<script async defer src=\"http://platform.instagram.com/en_US/embeds.js\"></script>");

                html.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js\"></script>");
                html.append("<script type='text/javascript' src='http://topyaps.com/wp-content/plugins/totalpoll/assets/js/fastclick.min.js'></script>\n" +
                        "<script type='text/javascript' src='http://topyaps.com/wp-content/plugins/totalpoll/assets/js/totalpoll.min.js'></script>");

                html.append("</div></div>");
                html.append("</body></html>");
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setClickable(true);
                //Log.d("HTML",html.toString());
                webView.loadData(html.toString(), "text/html; charset=utf-8","utf-8");

                webView.setWebChromeClient(new WebChromeClient(){
                    public boolean onConsoleMessage(ConsoleMessage cm){
                        Log.d("MyApplication",cm.message()+ "from line no."+ cm.lineNumber()+ "of" +cm.sourceId() );
                        return true;

                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {

        }

        viewResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        fab.setOnClickListener(this);
        //Log.d("pppppppppp", String.valueOf(html));

        onNewIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("NotificationMessage")) {
                setContentView(R.layout.activity_detail);
                // extract the extra-data in the Notification
                String msg = extras.getString("NotificationMessage");
                title.setText(msg);
            }
        }
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
        finish();
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

    //When back press video will be stopped in background of webView
    @Override
    public void onDestroy(){
        super.onDestroy();
        webView.destroy();
    }
}