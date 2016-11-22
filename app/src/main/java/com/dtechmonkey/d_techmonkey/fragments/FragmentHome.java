package com.dtechmonkey.d_techmonkey.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtechmonkey.d_techmonkey.DetailActivity;
import com.dtechmonkey.d_techmonkey.EndlessRecyclerViewScrollListener;
import com.dtechmonkey.d_techmonkey.MainActivity;
import com.dtechmonkey.d_techmonkey.PostRetrieve;
import com.dtechmonkey.d_techmonkey.R;
import com.dtechmonkey.d_techmonkey.TopYapsServiceGen;
import com.dtechmonkey.d_techmonkey.adapters.JSONDataAdapter;
import com.dtechmonkey.d_techmonkey.helper.Constants;
import com.dtechmonkey.d_techmonkey.helper.Utils;
import com.dtechmonkey.d_techmonkey.models.PostJSONData;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome  extends Fragment implements SwipeRefreshLayout.OnRefreshListener /*implements JSONDataAdapter.HomeItemClickListener*/ {
    private SwipeRefreshLayout refreshLayout;
    private TextView internet;
    private RecyclerView recyclerView;
    private JSONDataAdapter adapter;
    private ProgressDialog progressDialog;

    private EndlessRecyclerViewScrollListener scrollListener;

    private String category;
    private int position;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static FragmentHome getInstance(String category, int position) {
        Bundle arguments = new Bundle();
        arguments.putString("category", category);
        arguments.putInt("pos", position);
        FragmentHome fragmentHome = new FragmentHome();
        fragmentHome.setArguments(arguments);
        return fragmentHome;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
            position = getArguments().getInt("pos");
        }
    }

    public FragmentHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_home);
        refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        internet=(TextView)view.findViewById(R.id.internet);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case JSONDataAdapter.VIEW_FIRST:
                        return 2;
                    case JSONDataAdapter.VIEW_REST:
                        return 1;
                    default:
                        return 0;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new JSONDataAdapter(new ArrayList<PostJSONData>(), new JSONDataAdapter.ItemClickListener() {
            @Override
            public void onItemClick(PostJSONData data) {
                Intent intent=new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Constants.REFERENCE.TOPYAPS_DATA, data);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        scrollListener=new EndlessRecyclerViewScrollListener(gridLayoutManager)
        {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                makeRequest(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                JSONDataAdapter jsonDataAdapter=new JSONDataAdapter();
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition()==jsonDataAdapter.getItemCount()-1){
                    makeRequest();
                }
            }
        });*/
        refreshLayout.setOnRefreshListener(this);
        /*refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                makeRequest();
            }
        });*/
        return view;
    }
    //OnClick to go detailActivity with data.
    /*@Override
    public void onClickHome(int position) {
        PostJSONData selectedData=adapter.getSelectedData(position);
        Intent intent=new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.TOPYAPS_DATA, selectedData);
        startActivity(intent);
    }*/

    @Override
    public void onRefresh() {
        adapter.flash();
        makeRequest(PostRetrieve.offset);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Utils.isNetworkAvailable(getContext())) {
            if (adapter.getItemCount()==0)
                makeRequest(PostRetrieve.offset);
        }
        else{
            internet.setText("No Internet Connection");
            //makeRequestFromDB();
        }
    }

    //Request from Database
    private void makeRequestFromDB() {

    }

    // Request from JSON
    public void makeRequest(int pageNumber) {
        showPD();
        try {
            PostRetrieve client = TopYapsServiceGen.createService(PostRetrieve.class);
            Call<List<PostJSONData>> call;
            if (position == 0) {
                call = client.getPostList(pageNumber);
            } else {
                call = client.getPostListCategory(category);
            }

            call.enqueue(new Callback<List<PostJSONData>>() {
                @Override
                public void onResponse(Call<List<PostJSONData>> call, Response<List<PostJSONData>> response) {
                    hidePD();
                    List<PostJSONData> postJSONData = response.body();
                    adapter.addData(postJSONData);
                }

                @Override
                public void onFailure(Call<List<PostJSONData>> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    hidePD();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
    // for defining the size of the image
    /*private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;
        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount=spanCount;
            this.spacing=spacing;
            this.includeEdge=includeEdge;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            int position=parent.getChildAdapterPosition(view);
            int column=position%spanCount;
            if(includeEdge)
            {
                outRect.left=spacing-column*spacing/spanCount;
                outRect.right=(column+0)*spacing/spanCount;
                if(position<spanCount){
                    outRect.top=spacing;
                }
                outRect.bottom=spacing;
            }
            else
            {
                outRect.left=column*spacing/spanCount;
                outRect.right=spacing-(column+0)*spacing/spanCount;
                if(position>=spanCount){
                    outRect.top=spacing;
                }
            }


        }
    }*/

    // conversion dp to pixel
    private int dpToPx(int dp) {
        Resources resources=getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
    }
    public void showPD(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Data Loading....");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
        }
    }
    public void hidePD(){
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
}