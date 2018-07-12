package com.testbirds.detoxifier.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.testbirds.detoxifier.OnAppDataClickListener;
import com.testbirds.detoxifier.R;
import com.testbirds.detoxifier.adapter.AppDataAdapter;
import com.testbirds.detoxifier.model.AppData;
import com.testbirds.detoxifier.service.BlacklistService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Fragment that will show a list of blacklisted apps, add new apps to the black list and start/stop
 * a background service to check which app is on the foreground
 */
public class MainFragment extends BaseFragment implements OnAppDataClickListener {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_list, container, false);

        setHasOptionsMenu(true);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return rootView;
    }

    /**
     * Method that will delete the clicked app from the database
     * @param appData
     */
    @Override
    public void onItemClick(final AppData appData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.dialog_remove));
        builder.setPositiveButton(getResources().getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                appDataViewModel.delete(appData);
            }
        })
        .setNegativeButton(getResources().getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method that sets an Observer object to listen on the list of apps
     * @param compositeDisposable
     */
    private void addObserver(CompositeDisposable compositeDisposable) {
        compositeDisposable.add(appDataViewModel.getAllApps()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AppData>>() {
                    @Override
                    public void accept(List<AppData> appDataList) throws Exception {
                        if(appDataList != null) {
                            AppDataAdapter adapter = new AppDataAdapter(appDataList, MainFragment.this.getActivity(), MainFragment.this);
                            mRecyclerView.setAdapter(adapter);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainFragment", "Exception while retrieving the list of apps");
                    }
                }));
    }

    @Override
    public void onResume() {
        super.onResume();

        addObserver(compositeDisposable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_detoxifier:
                System.out.println("::: Service started");
                Intent intent = new Intent(getActivity(), BlacklistService.class);
                PendingIntent pIntent = PendingIntent.getService(getActivity(), 0, intent, 0);
                AlarmManager alarm = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pIntent);
                break;
            case R.id.stop_detoxifier:
                getActivity().stopService(new Intent(getActivity(), BlacklistService.class));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
