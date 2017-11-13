package f1vote.news.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import f1vote.news.R;
import f1vote.newssplider.bean.NewsItem;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

import java.util.List;
import java.util.Objects;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<NewsItem> list;

    public void addAll(List<NewsItem> list) {
        this.list.addAll(list);
    }

    public List<NewsItem> getList(){
        return list;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context, List<NewsItem> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.empty_item, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            NewsItem newsItem = list.get(position);

            String title = newsItem.getTitle() + " " + newsItem.getContent();

            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);

            SpannableStringBuilder ssBuilder_date = new SpannableStringBuilder(newsItem.getDate());

            CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), "fonts/Helvetica_CE_Bold.ttf"));

            CalligraphyTypefaceSpan typefaceDate = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), "fonts/Helvetica_CE_Regular.ttf"));

            ssBuilder.setSpan(
                    typefaceSpan, // Span to add
                    title.indexOf(newsItem.getTitle()),
                    title.indexOf(newsItem.getTitle()) + String.valueOf(newsItem.getTitle()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
            );

            ssBuilder.setSpan(
                    typefaceDate,
                    title.indexOf(newsItem.getContent()),
                    title.indexOf(newsItem.getContent()) + String.valueOf(newsItem.getContent()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            ssBuilder_date.setSpan(
                    typefaceDate,
                    newsItem.getDate().indexOf(newsItem.getDate()),
                    newsItem.getDate().indexOf(newsItem.getDate()) + String.valueOf(newsItem.getDate()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );


            ((ItemViewHolder) holder).title.setText(ssBuilder);
            ((ItemViewHolder) holder).date.setText(ssBuilder_date);

           if (newsItem.getImgLink() != null) {
                ((ItemViewHolder) holder).img.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(newsItem.getImgLink())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ItemViewHolder) holder).img);
            } else {
                ((ItemViewHolder) holder).img.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).caption.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ((ItemViewHolder) holder).caption_partners.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            if (Objects.equals(newsItem.getCategory(), "memes")) {
                ((ItemViewHolder) holder).caption.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).caption.setText("Мемы");
            } else if(Objects.equals(newsItem.getCategory(), "razbor")) {
                ((ItemViewHolder) holder).caption.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).caption.setText("Разбор");
            } else if(Objects.equals(newsItem.getCategory(), "podborka")) {
                ((ItemViewHolder) holder).caption.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).caption.setText("Подборка");
            } else {
                ((ItemViewHolder) holder).caption.setVisibility(View.GONE);
            }


            if (Objects.equals(newsItem.getCategory(), "partners")) {
                ((ItemViewHolder) holder).caption_partners.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).caption_partners.setText("Партнёрский материал");
            } else {
                ((ItemViewHolder) holder).caption_partners.setVisibility(View.GONE);
            }

            if (newsItem.getPoll_button() != null) {
                ((ItemViewHolder) holder).poll_button.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).poll_button.setVisibility(View.GONE);

            }

            ((ItemViewHolder) holder).poll_button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, caption, poll_button, caption_partners;
        ImageView img;

        public ItemViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.tv_item_title);
            date = (TextView) view.findViewById(R.id.tv_item_date);
            img = (ImageView) view.findViewById(R.id.iv_item_img);
            caption = (TextView) view.findViewById(R.id.poll_caption);
            poll_button = (TextView) view.findViewById(R.id.poll_button);
            caption_partners = (TextView) view.findViewById(R.id.poll_caption_green);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }

}
