package com.dtechmonkey.d_techmonkey.adapters;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.dtechmonkey.d_techmonkey.R;
import com.dtechmonkey.d_techmonkey.models.PostJSONData;

public class JSONDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PostJSONData> postJSONData;

    /*private static HomeItemClickListener home;
    private static NewsItemClickListener news;
    private static CultureItemClickListener culture;
    private static EnterTainmentItemClickListener entertainment;
    private static MilitaryItemClickListener military;
    private static WomenItemClickListener women;
    private static NatureItemClickListener nature;
    private static FoodItemClickListener food;
    private static HistoryItemClickListener history;
    private static VideosItemClickListener videos;
    private static OpinionItemClickListener opinion;

    //Home
    public interface HomeItemClickListener{
        void onClickHome(int position);
    }
    public void setClickListener(HomeItemClickListener home){
        this.home=home;
    }
    //News
    public interface NewsItemClickListener{
        void onClickNews(int position);
    }
    public void setClickListener(NewsItemClickListener news){
        this.news=news;
    }
    //Culture
    public interface CultureItemClickListener{
        void onClickCulture(int position);
    }
    public void setClickListener(CultureItemClickListener culture){
        this.culture=culture;
    }
    //Entertainment
    public interface EnterTainmentItemClickListener{
        void onClickEntertainment(int position);
    }
    public void setClickListener(EnterTainmentItemClickListener entertainment){
        this.entertainment=entertainment;
    }
    //Military
    public interface MilitaryItemClickListener{
        void onClickMilitary(int position);
    }
    public void setClickListener(MilitaryItemClickListener military){
        this.military=military;
    }
    //Women
    public interface WomenItemClickListener{
        void onClickWomen(int position);
    }
    public void setClickListener(WomenItemClickListener women){
        this.women=women;
    }
    //Nature
    public interface NatureItemClickListener{
        void onClickNature(int position);
    }
    public void setClickListener(NatureItemClickListener nature){
        this.nature=nature;
    }
    //Food
    public interface FoodItemClickListener{
        void onClickFood(int position);
    }
    public void setClickListener(FoodItemClickListener food){
        this.food=food;
    }
    //History
    public interface HistoryItemClickListener{
        void onClickHistory(int position);
    }
    public void setClickListener(HistoryItemClickListener history){
        this.history=history;
    }
    //Videos
    public interface VideosItemClickListener{
        void onClickVideos(int position);
    }
    public void setClickListener(VideosItemClickListener videos){
        this.videos=videos;
    }
    //Opinion
    public interface OpinionItemClickListener{
        void onClickOpinion(int position);
    }
    public void setClickListener(OpinionItemClickListener opinion){
        this.opinion=opinion;
    }*/
    private ItemClickListener listener;

    public interface ItemClickListener {
        void onItemClick(PostJSONData data);
    }
/*
    //Return position.
    public PostJSONData getSelectedData(int position) {
        return postJSONData.get(position);
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title,subTitle;
        public MyViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.image_title);
            subTitle=(TextView) view.findViewById(R.id.image_subTitle);
            thumbnail=(ImageView)view.findViewById(R.id.thumbnail);
        }
    }

    public static class MyViewHolderForTitleImage extends RecyclerView.ViewHolder {
        private ImageView backdrop;
        private TextView title2,date;

        public MyViewHolderForTitleImage(View view) {
            super(view);
            backdrop = (ImageView) itemView.findViewById(R.id.backdrop);
            date=(TextView) view.findViewById(R.id.image_date);
            title2=(TextView) view.findViewById(R.id.image_title2);
        }
    }

    public static final int VIEW_FIRST = 0;
    public static final int VIEW_REST = 1;

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         if (viewType == VIEW_FIRST) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_first, parent, false);
            MyViewHolderForTitleImage holder2 = new MyViewHolderForTitleImage(itemView);
            setUpClickListener(holder2);
            return holder2;
        }
        else if (viewType == VIEW_REST) {
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row, parent, false);
             MyViewHolder holder = new MyViewHolder(itemView);
             setUpClickListener(holder);
             return holder;
         }
        throw new IllegalArgumentException("This viewType doesn't exist");
    }

    private void setUpClickListener(final RecyclerView.ViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PostJSONData data = postJSONData.get(position);
                listener.onItemClick(data);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_FIRST;
        }
        return VIEW_REST;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final  int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.title.setText(postJSONData.get(position).getTitle().getRendered().replaceAll("(&#8216;)|(&#038;)|(<i>)|(</i>)|(&#8217;)|(&#8221;)|(&#8220;)","\'"));
            myViewHolder.subTitle.setText(postJSONData.get(position).getDateGmt());

            //Log.d("ABCD", postJSONData.get(position).getId() + "");

            //Loading image using Glide library
            //Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
            //OR
            //Glide.with(myContext).load(data.getContent().getRendered()).into(holder.thumbnail);
            //OR
        /*Glide.with(context).load("http://192.168.1.7/test/images/" + current.fishImage)
        .placeholder(R.drawable.ic_img_error)
                .error(R.drawable.ic_img_error)
                .into(myHolder.ivFish);*/
            //OR
            try {
                Glide.with(myViewHolder.thumbnail.getContext()).load(postJSONData.get(position)
                        .getBetterFeaturedImage().getMediaDetails().getSizes().getMediumThumb().getSourceUrl())
                        .into(myViewHolder.thumbnail);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            MyViewHolderForTitleImage myViewHolderForTitleImage = (MyViewHolderForTitleImage) holder;
            myViewHolderForTitleImage.title2.setText(postJSONData.get(position).getTitle().getRendered().replaceAll("(&#8216;)|(&#8217;)|(&#8221;)|(&#8220;)","\'"));
            myViewHolderForTitleImage.date.setText(postJSONData.get(position).getDateGmt());
            try{
            Glide.with(myViewHolderForTitleImage.backdrop.getContext()).load(postJSONData.get(position)
                    .getBetterFeaturedImage().getMediaDetails().getSizes().getMediumThumb().getSourceUrl())
                    .into(myViewHolderForTitleImage.backdrop);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return postJSONData.size();
    }

    public JSONDataAdapter(List<PostJSONData> postJSONData, ItemClickListener listener) {
        this.postJSONData = postJSONData;
        this.listener = listener;
    }

    public void addData(List<PostJSONData> dataList) {
        postJSONData.addAll(dataList);
        notifyDataSetChanged();
    }
    public void flash() {
        postJSONData.clear();
        notifyDataSetChanged();
    }
    public void printData(String tag) {
        Log.d(tag, "printData: " + getItemCount());
    }
}