package com.example.javamail;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, String>> mDataset;
    private Context mcontext;
    private Activity mactivity;
    private OnItemClickListener listener;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public interface OnItemClickListener {
        void onItemClick(HashMap<String, String> item);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void add(int position, HashMap<String, String> item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(HashMap<String, String> item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String, String>> myDataset, RecyclerView recyclerView) {
        mcontext = context;
        mactivity = (Activity) context;
        mDataset = myDataset;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && (lastVisibleItem + visibleThreshold) >= totalItemCount) {
                    if(onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mactivity).inflate(R.layout.item, parent,false);
            return new ViewHolderRow(view);
        } else if(viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mactivity).inflate(R.layout.item_progressbar, parent, false);
            return new ViewHolderLoading(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderRow) {
            HashMap<String, String> map = mDataset.get(position);

            ViewHolderRow userviewHolder = (ViewHolderRow) holder;

            userviewHolder.txtEmail.setText(map.get("KEY_EMAIL"));
            userviewHolder.txtPhone.setText(map.get("KEY_PHONE"));

            userviewHolder.bind(mDataset.get(position), listener);
        } else if(holder instanceof ViewHolderLoading) {
            ViewHolderLoading lodingViewHolder = (ViewHolderLoading) holder;
            lodingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null? 0 : mDataset.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
        }
    }

    public class ViewHolderRow extends RecyclerView.ViewHolder {
        public TextView txtEmail, txtPhone;

        public ViewHolderRow(View view) {
            super(view);
            txtEmail = (TextView) view.findViewById(R.id.textViewFrom);
            txtPhone = (TextView) view.findViewById(R.id.textViewSubject);
        }

        public void bind(final HashMap<String, String> item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
