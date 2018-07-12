package com.testbirds.detoxifier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testbirds.detoxifier.OnAppDataClickListener;
import com.testbirds.detoxifier.R;
import com.testbirds.detoxifier.model.AppData;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from apps.
 */
public class AppDataAdapter extends RecyclerView.Adapter<AppDataAdapter.ViewHolder> {

    private List<AppData> appDataList = new ArrayList<>();
    private final OnAppDataClickListener listener;

    /**
     * Provide a reference to the type of views used (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView appIcon;
        private final TextView appName;
        private final TextView appPackage;

        public ViewHolder(View v, final List<AppData> appDataList, final OnAppDataClickListener listener) {
            super(v);

            // Listener to set a click action for every row
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(appDataList.get(getAdapterPosition()));
                }
            });

            appIcon = v.findViewById(R.id.AppIcon);
            appName = v.findViewById(R.id.AppName);
            appPackage = v.findViewById(R.id.AppPackage);
        }

        ImageView getAppIcon() {
            return appIcon;
        }

        TextView getAppName() {
            return appName;
        }

        TextView getAppPackage() {
            return appPackage;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param appDataList List<AppData> containing the data to populate views to be used by RecyclerView.
     */
    public AppDataAdapter(List<AppData> appDataList, Context context, OnAppDataClickListener listener) {
        this.appDataList = appDataList;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_app_data, viewGroup, false);

        return new ViewHolder(v, appDataList, listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final AppData appData = appDataList.get(position);

        viewHolder.getAppName().setText(appData.getAppName());
        viewHolder.getAppPackage().setText(appData.getAppPackage());
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return appDataList.size();
    }

}