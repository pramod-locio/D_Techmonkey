package com.techmonkey.topyaps.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techmonkey.topyaps.DetailActivity;
import com.techmonkey.topyaps.EndlessRecyclerViewScrollListener;
import com.techmonkey.topyaps.MainActivity;
import com.techmonkey.topyaps.PostRetrieve;
import com.techmonkey.topyaps.R;
import com.techmonkey.topyaps.TopYapsServiceGen;
import com.techmonkey.topyaps.adapters.JSONDataAdapter;
import com.techmonkey.topyaps.helper.Constants;
import com.techmonkey.topyaps.helper.MakeProperTag;
import com.techmonkey.topyaps.helper.Utils;
import com.techmonkey.topyaps.models.PostJSONData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragmentHome  extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener /*implements JSONDataAdapter.HomeItemClickListener*/ {
    private SwipeRefreshLayout refreshLayout;
    private static final String[] TYLanguage = {"35459", "2579", "31188","17313"};
    private RecyclerView recyclerView;
    private JSONDataAdapter adapter;
    private ProgressDialog progressDialog;
    private EndlessRecyclerViewScrollListener scrollListener;
    private String category,selectedLanguage;
    private int position;
    List<PostJSONData> arrayList = new ArrayList<>();

    HashMap<String, String> hm = new HashMap<String, String>();

    private String initialLanguageData="35459 2579 17313 31188";

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
        hm.put("news", "34114");
        hm.put("culture", "3");
        hm.put("entertainment", "7");
        hm.put("military", "34925");
        hm.put("women", "34129");
        hm.put("nature", "34921");
        hm.put("food", "34938");
        hm.put("history", "9");
        hm.put("videos", "9027");
        hm.put("opinion", "23058");
        if (getArguments() != null) {
            category = getArguments().getString("category");
            position = getArguments().getInt("pos");
            if (hm.containsKey(category)) {
                category = hm.get(category);
                Log.d("key", category);
            }
        }
    }

    public FragmentHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        selectedLanguage = sharedPreferences.getString("LANGUAGES", "");
        Log.d(TAG, "onItemClicked22: " + selectedLanguage);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        setHasOptionsMenu(true);///////////For Search////////////////
        /*internet=(TextView)view.findViewById(R.id.internet);*/

        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setItemPrefetchEnabled(true);
            /* {
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
        });*/
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10),true));
        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new JSONDataAdapter(new ArrayList<PostJSONData>(), new JSONDataAdapter.ItemClickListener() {
            @Override
            public void onItemClick(PostJSONData data) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Constants.REFERENCE.TOPYAPS_DATA, data);
                Log.d("DATA",data+"");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

            scrollListener = new EndlessRecyclerViewScrollListener() {
                @Override
                public void onLoadMore(int page) {
                    Log.d(TAG, "onLoadMore:  called");
                    adapter.addProgressDialog();
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemInserted(adapter.getItemCount());
                        }
                    });
                    makeRequest(page + 1, PostRetrieve.PER_PAGE_AFTER, false, false, true);
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
        scrollListener.reset();
        makeRequest(PostRetrieve.OFFSET, PostRetrieve.PER_PAGE_INITIAL, false, true, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (Utils.getInstance().checkIfHasNetwork())
        {*/
            if (adapter.getItemCount() == 0) {
                makeRequest(PostRetrieve.OFFSET, PostRetrieve.PER_PAGE_INITIAL, true, false, false);
        } /*else if(!Utils.getInstance().checkIfHasNetwork() && adapter.getItemCount()!=0)
        {
            makeRequest(PostRetrieve.OFFSET, PostRetrieve.PER_PAGE_INITIAL, false, false, false);
        }
        else
        {
            internet.setText("No Internet Connection");
            //makeRequestFromDB();
        }*/
    }

    //Request from Database
    private void makeRequestFromDB() {

    }

    private void setSwipeRefreshingFalse() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void showSwipeRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    // Request from JSON
    public void makeRequest(int pageNumber, int perPageCount, final boolean showProgressDialog, final boolean clearData, final boolean loadMore) {

        if (showProgressDialog) showSwipeRefresh();
        try {
            PostRetrieve client = TopYapsServiceGen.provideTopyapsService();
            Call<List<PostJSONData>> call;
            if (position == 0) {
                if (selectedLanguage.equals(TYLanguage[0]))
                    call = client.getPostListEng(PostRetrieve.ENGLISH, pageNumber, perPageCount);
                else if (selectedLanguage.equals(TYLanguage[1]) || selectedLanguage.equals(TYLanguage[2]) || selectedLanguage.equals(TYLanguage[3]))
                    call = client.getPostListMix(selectedLanguage, pageNumber, perPageCount);
                else if (selectedLanguage.contains("35459") && selectedLanguage.length()>5)
                    call=client.getPostListEng(MakeProperTag.makeTagEnglish(selectedLanguage), pageNumber, perPageCount);
                else if (!selectedLanguage.contains("35459") && selectedLanguage.length()>4)
                    call = client.getPostListMix(selectedLanguage, pageNumber, perPageCount);
                else
                    call = client.getPostList(pageNumber, perPageCount);
            }
            else {
                if (selectedLanguage.equals(TYLanguage[0]))
                    call = client.getPostListCategoryWithLanguageEng(PostRetrieve.ENGLISH, category, pageNumber, perPageCount);
                else if (selectedLanguage.equals(TYLanguage[1]) || selectedLanguage.equals(TYLanguage[2])|| selectedLanguage.equals(TYLanguage[3]))
                    call = client.getPostListCategoryWithLanguageMix(selectedLanguage, category, pageNumber, perPageCount);
                else if (selectedLanguage.contains("35459") && selectedLanguage.length()>5)
                    call=client.getPostListCategoryWithLanguageEng(MakeProperTag.makeTagEnglish(selectedLanguage), category, pageNumber, perPageCount);
                else if(!selectedLanguage.contains("35459") && selectedLanguage.length()>4)
                    call=client.getPostListCategoryWithLanguageMix(selectedLanguage, category, pageNumber, perPageCount);
                else
                    call = client.getPostListCategory(category, pageNumber, perPageCount);
            }

            call.enqueue(new Callback<List<PostJSONData>>() {
                @Override
                public void onResponse(Call<List<PostJSONData>> call, Response<List<PostJSONData>> response) {
                    if (clearData) adapter.flash();
                    setSwipeRefreshingFalse();
                    if (loadMore) {
                        adapter.removeProgressDialog();
                    }
                    List<PostJSONData> postJSONData = response.body();
                    arrayList=response.body();
                    try {
                        adapter.addAllData(postJSONData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //adapter.printData(TAG);
                }

                @Override
                public void onFailure(Call<List<PostJSONData>> call, Throwable t) {
                    if (loadMore) {
                        adapter.removeProgressDialog();
                    }
                    setSwipeRefreshingFalse();
                    Log.e(TAG, t.toString());
                    hidePD();
                    Snackbar.make(getView(), t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
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

    ////////////////For Search////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    // conversion dp to pixel
    private static int dpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public void showPD() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.isIndeterminate();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
        }
    }

    public void hidePD() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /////////////////////////////////for searching/////////////////////
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        List<PostJSONData> newList = new ArrayList<>();
        for (PostJSONData total : arrayList) {
            String name = total.getTitle().getRendered().toLowerCase();
            if (name.contains(newText)) {
                newList.add(total);
            }
        }
        adapter.setFilter(newList);
        return  true;
    }
}