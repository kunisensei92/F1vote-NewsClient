package f1vote.news.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import f1vote.news.R;
import f1vote.news.adapter.NewsAdapter;
import f1vote.news.ui.activity.NewsContentActivity;
import f1vote.news.view.RecycleViewDivider;
import f1vote.newssplider.bean.NewsItem;
import f1vote.newssplider.biz.NewsItemBiz;
import f1vote.newssplider.util.URLUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MobileFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private boolean loading = false;

    private int currentPage = 1;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private List<NewsItem> list = new ArrayList<NewsItem>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_mobile);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_mobile);

        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(),R.drawable.divider_mileage));


        refreshLayout.setColorSchemeResources(R.color.swipe_color_1);

        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        refreshLayout.setProgressViewEndTarget(true, 200);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getData();
            }
        });



        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("url", adapter.getList().get(position).getLink());
                intent.putExtra("date", adapter.getList().get(position).getDate());
                intent.putExtra("img", adapter.getList().get(position).getImgLink());
                intent.putExtra("title", adapter.getList().get(position).getTitle());
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        return view;
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = new NewsItemBiz().getNewsItems(URLUtil.NEWS_TYPE_MOBILE);
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void loadMoreData() {
        list.clear();
        getData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!adapter.getList().isEmpty()) {
                        adapter.getList().clear();
                        adapter.addAll(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                    loading = false;
                    refreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

}
