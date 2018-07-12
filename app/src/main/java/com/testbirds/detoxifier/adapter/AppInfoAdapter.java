package com.testbirds.detoxifier.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testbirds.detoxifier.OnAppInfoClickListener;
import com.testbirds.detoxifier.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from apps.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    private List<ApplicationInfo> appInfoList = new ArrayList<>();
    private PackageManager packageManager;
    private final OnAppInfoClickListener listener;

    /**
     * Provide a reference to the type of views used (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView appIcon;
        private final TextView appName;
        private final TextView appPackage;

        public ViewHolder(View v, final List<ApplicationInfo> appInfoList, final OnAppInfoClickListener listener) {
            super(v);

            // Listener to set a click action for every row
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(appInfoList.get(getAdapterPosition()));
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
     * @param appInfoList List<ApplicationInfo> containing the data to populate views to be used by RecyclerView.
     */
    public AppInfoAdapter(List<ApplicationInfo> appInfoList, Context context, OnAppInfoClickListener listener) {
        this.appInfoList = appInfoList;
        this.packageManager = context.getPackageManager();
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_app_data, viewGroup, false);

        return new ViewHolder(v, appInfoList, listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final ApplicationInfo appData = appInfoList.get(position);

        viewHolder.getAppIcon().setImageDrawable(appData.loadIcon(packageManager));
        viewHolder.getAppName().setText(appData.loadLabel(packageManager));
        viewHolder.getAppPackage().setText(appData.packageName);
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

}