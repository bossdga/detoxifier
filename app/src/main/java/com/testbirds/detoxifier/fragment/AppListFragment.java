package com.testbirds.detoxifier.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testbirds.detoxifier.OnAppInfoClickListener;
import com.testbirds.detoxifier.R;
import com.testbirds.detoxifier.adapter.AppInfoAdapter;
import com.testbirds.detoxifier.model.AppData;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that will show a list of installed apps. If you click on any, it will be added to the blacklisted apps
 */
public class AppListFragment extends BaseFragment implements OnAppInfoClickListener {

    private AppInfoAdapter adapter;

    private PackageManager packageManager = null;
    private List<ApplicationInfo> appList = null;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    public AppListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        packageManager = getActivity().getPackageManager();
        new LoadApplications().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_list, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return rootView;
    }

    /**
     * Method that will add the clicked app into the database
     * @param appInfo
     */
    @Override
    public void onItemClick(ApplicationInfo appInfo) {
        AppData appData = new AppData();
        appData.setAppName(String.valueOf(appInfo.loadLabel(packageManager)));
        appData.setAppPackage(appInfo.packageName);

        appDataViewModel.insert(appData);
        getActivity().finish();
    }

    /**
     * Method that will return a list of apps that can be open
     * @param list
     */
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        List<ApplicationInfo> appList = new ArrayList<>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    appList.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return appList;
    }

    /**
     * AsyncTask that will return a list of installed apps
     */
    private class LoadApplications extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            appList = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter = new AppInfoAdapter(appList, getActivity(), AppListFragment.this);
            mRecyclerView.setAdapter(adapter);
            hideProgressDialog();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}
