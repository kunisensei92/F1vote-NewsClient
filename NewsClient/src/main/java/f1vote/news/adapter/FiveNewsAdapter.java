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
import android.widget.TextView;

import f1vote.news.R;
import f1vote.newssplider.bean.FiveNews;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

import java.util.List;

public class FiveNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<FiveNews> list;

    public void addAll(List<FiveNews> list) {
        this.list.addAll(list);
    }

    public List<FiveNews> getList(){
        return list;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener fnOnItemCLickListener;

    public FiveNewsAdapter(Context context, List<FiveNews> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener fnOnItemCLickListener) {
        this.fnOnItemCLickListener = fnOnItemCLickListener;
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
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
            View view = LayoutInflater.from(context).inflate(R.layout.fn_item, parent,
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
            FiveNews fiveNews = list.get(position);

            String title = fiveNews.getTitle();
            String date = fiveNews.getDate();

            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
            SpannableStringBuilder ssBuilder_date = new SpannableStringBuilder(date);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "PTsans.ttf");

            CalligraphyTypefaceSpan typefaceDate = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), "fonts/Helvetica_CE_Regular.ttf"));

            ssBuilder.setSpan(
                    typefaceDate, // Span to add
                    title.indexOf(fiveNews.getTitle()),
                    title.indexOf(fiveNews.getTitle()) + String.valueOf(fiveNews.getTitle()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
            );

            ssBuilder_date.setSpan(
                    typefaceDate,
                    fiveNews.getDate().indexOf(fiveNews.getDate()),
                    fiveNews.getDate().indexOf(fiveNews.getDate()) + String.valueOf(fiveNews.getDate()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );


            ((ItemViewHolder) holder).title.setText(ssBuilder);
            ((ItemViewHolder) holder).date.setText(ssBuilder_date);


            if (fnOnItemCLickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        fnOnItemCLickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        fnOnItemCLickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }

        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;

        public ItemViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.fn_title);
            date = (TextView) view.findViewById(R.id.fn_date);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }

}
