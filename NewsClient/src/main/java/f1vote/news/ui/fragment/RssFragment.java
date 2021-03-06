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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import f1vote.news.R;
        ;
import f1vote.news.adapter.NewsAdapter;
import f1vote.news.ui.activity.NewsContentActivity;
import f1vote.news.view.RecycleViewDivider;
import f1vote.newssplider.bean.NewsItem;
import f1vote.newssplider.biz.NewsItemBiz;
import f1vote.newssplider.util.URLUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by markopolo on 06/07/2017.
 */

public class RssFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView listView;
    private NewsAdapter adapter;
    private boolean loading = false;
    private int currentPage = 1;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private int visibleThreshold = 2; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private List<NewsItem> list = new ArrayList<NewsItem>();
    private List<NewsItem> templist = new ArrayList<NewsItem>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rss, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_rss);
        listView = (RecyclerView) view.findViewById(R.id.rv_rss);

        listView.addItemDecoration(new RecycleViewDivider(getContext(),R.drawable.divider_mileage));


        refreshLayout.setColorSchemeResources(R.color.swipe_color_1);

        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        refreshLayout.setProgressViewEndTarget(true, 200);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(getContext(), list);

        listView.setAdapter(adapter);



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

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
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

    public void setLoading(boolean loading) {
        this.loading = loading;
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = new NewsItemBiz().getNewsItems(URLUtil.NEWS_TYPE_LAST);
                    if (templist.isEmpty()) {
                        for (int i = 1; i < list.size(); i++) {
                            if (Objects.equals(list.get(i).getCategory(), "f1news")) {
                                list.remove(i);
                            } else {
                                templist.add(list.get(i));
                            }
                        }
                    } else {
                        templist.clear();
                        for (int i = 1; i < list.size(); i++) {
                            if (Objects.equals(list.get(i).getCategory(), "f1news")) {
                                list.remove(i);
                            } else {
                                templist.add(list.get(i));
                            }
                        }
                    }
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




    private void onLoadMore(int currentPage) {
        templist.clear();
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
                        adapter.addAll(templist);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.addAll(templist);
                        adapter.notifyDataSetChanged();
                    }
                    loading = false;

                    refreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
}
