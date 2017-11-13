package f1vote.news.ui.fragment;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import f1vote.news.R;
import f1vote.news.adapter.FiveNewsAdapter;
import f1vote.news.adapter.NewsAdapter;
import f1vote.news.ui.activity.NewsContentActivity;
import f1vote.news.view.RecycleViewDivider;
import f1vote.newssplider.bean.FiveNews;
import f1vote.newssplider.bean.News;
import f1vote.newssplider.bean.NewsItem;
import f1vote.newssplider.biz.FiveNewsBiz;
import f1vote.newssplider.biz.NewsItemBiz;
import f1vote.newssplider.util.URLUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NewsFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;

    private TextView fiveNewsOfTheDay;
    private TextView allF1Vote, lastNewsTitle;
    private ImageView dividerGreen1, dividerGreen2;

    private FiveNewsAdapter fnAdapter;
    private NewsAdapter adapter;
    private NewsAdapter lastAdapter;
    private boolean loading = false;
    private int currentPage = 1;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private int visibleThreshold = 2; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private List<NewsItem> list = new ArrayList<NewsItem>();
    private List<NewsItem> lastList = new ArrayList<NewsItem>();
    private Context context;
    private List<FiveNews> fnList = new ArrayList<FiveNews>();
    private List<NewsItem> templist = new ArrayList<NewsItem>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_news);

        fiveNewsOfTheDay = (TextView) view.findViewById(R.id.tv_5news_title);

        allF1Vote = (TextView) view.findViewById(R.id.tv_f1vote_title);


        lastNewsTitle = (TextView) view.findViewById(R.id.tv_last_title);

        dividerGreen1 = (ImageView) view.findViewById(R.id.divider_green1);

        dividerGreen2 = (ImageView) view.findViewById(R.id.divider_green2);


        fiveNewsOfTheDay.setVisibility(View.INVISIBLE);
        allF1Vote.setVisibility(View.INVISIBLE);
        dividerGreen1.setVisibility(View.INVISIBLE);
        lastNewsTitle.setVisibility(View.INVISIBLE);
        dividerGreen2.setVisibility(View.INVISIBLE);


        RecyclerView fnView = (RecyclerView) view.findViewById(R.id.five_news);

        RecyclerView lastNewsView = (RecyclerView) view.findViewById(R.id.last_news);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.lv_news);


        fnView.setNestedScrollingEnabled(false);

        listView.setNestedScrollingEnabled(false);

        lastNewsView.setNestedScrollingEnabled(false);


        listView.addItemDecoration(new RecycleViewDivider(getContext(),R.drawable.divider_mileage));

        fnView.addItemDecoration(new RecycleViewDivider(getContext(),R.drawable.divider_mileage));

        lastNewsView.addItemDecoration(new RecycleViewDivider(getContext(),R.drawable.divider_mileage));


        refreshLayout.setColorSchemeResources(R.color.swipe_color_1);

        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        refreshLayout.setProgressViewEndTarget(true, 200);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        final LinearLayoutManager fnLayoutManager = new LinearLayoutManager(getContext());

        final LinearLayoutManager lastLayoutManager = new LinearLayoutManager(getContext());


        fnView.setLayoutManager(fnLayoutManager);

        listView.setLayoutManager(layoutManager);

        lastNewsView.setLayoutManager(lastLayoutManager);


        fnAdapter = new FiveNewsAdapter(getContext(), fnList);

        adapter = new NewsAdapter(getContext(), list);

        lastAdapter = new NewsAdapter(getContext(), lastList);


        fnView.setAdapter(fnAdapter);

        listView.setAdapter(adapter);

        lastNewsView.setAdapter(lastAdapter);



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
                if (!refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(true);
            }

        });


        fnAdapter.setOnItemClickListener(new FiveNewsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("url", fnAdapter.getList().get(position).getLink());
                intent.putExtra("date", fnAdapter.getList().get(position).getDate());
                intent.putExtra("img", fnAdapter.getList().get(position).getImgLink());
                intent.putExtra("title", fnAdapter.getList().get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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


        lastAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("url", lastAdapter.getList().get(position).getLink());
                intent.putExtra("date", lastAdapter.getList().get(position).getDate());
                intent.putExtra("img", lastAdapter.getList().get(position).getImgLink());
                intent.putExtra("title", lastAdapter.getList().get(position).getTitle());
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
                    list = new NewsItemBiz().getNewsItems(URLUtil.NEWS_TYPE_NEWS);
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    fnList = new FiveNewsBiz().getFiveNews(URLUtil.NEWS_TYPE_CLOUD);
                    handler.sendEmptyMessage(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    lastList = new NewsItemBiz().getNewsItems(URLUtil.NEWS_TYPE_LAST);
                    for(int i=1; i<lastList.size(); i++){
                        if (Objects.equals(lastList.get(i).getCategory(), "f1news")) {
                            lastList.remove(i);
                        } else {
                            templist.add(lastList.get(i));
                        }
                    }
                    handler.sendEmptyMessage(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void onLoadMore(int currentPage) {
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
                        adapter.addAll(list.subList(0, 6));
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.addAll(list.subList(0, 6));
                        adapter.notifyDataSetChanged();
                    }
                    fiveNewsOfTheDay.setVisibility(View.VISIBLE);
                    allF1Vote.setVisibility(View.VISIBLE);
                    dividerGreen1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    if (!fnAdapter.getList().isEmpty()) {
                        fnAdapter.getList().clear();
                        fnAdapter.addAll(fnList);
                        fnAdapter.notifyDataSetChanged();
                    } else {
                        fnAdapter.addAll(fnList);
                        fnAdapter.notifyDataSetChanged();
                    }
                    break;
                case 3:
                    if (!lastAdapter.getList().isEmpty()) {
                        lastAdapter.getList().clear();
                        lastAdapter.addAll(templist);
                        lastAdapter.notifyDataSetChanged();
                    } else {
                        lastAdapter.addAll(templist);
                        lastAdapter.notifyDataSetChanged();
                    }
                    lastNewsTitle.setVisibility(View.VISIBLE);
                    dividerGreen2.setVisibility(View.VISIBLE);
                    loading = false;
                    refreshLayout.setRefreshing(false);
                    break;

            }
        }
    };


}