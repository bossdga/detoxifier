package com.testbirds.detoxifier.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.rvalerio.fgchecker.AppChecker;
import com.testbirds.detoxifier.R;
import com.testbirds.detoxifier.activity.MainActivity;
import com.testbirds.detoxifier.adapter.AppDataAdapter;
import com.testbirds.detoxifier.fragment.MainFragment;
import com.testbirds.detoxifier.model.AppData;
import com.testbirds.detoxifier.ui.AppDataViewModel;
import com.testbirds.detoxifier.util.MessageUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BlacklistService extends Service {

    private final static int NOTIFICATION_ID = 1234;
    private final static String STOP_SERVICE = BlacklistService.class.getPackage()+".stop";

    private BroadcastReceiver stopServiceReceiver;
    private AppChecker appChecker;

    private AppDataViewModel appDataViewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void start(Context context) {
        context.startService(new Intent(context, BlacklistService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, BlacklistService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appDataViewModel = new AppDataViewModel(this.getApplication());
        startChecker();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopChecker();
        stopSelf();
    }

    private void startChecker() {
        appChecker = new AppChecker();
        appChecker.whenOther(new AppChecker.Listener() {
                    @Override
                    public void onForeground(final String packageName) {
                        compositeDisposable.add(appDataViewModel.findByPackage(packageName)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<AppData>() {
                                    @Override
                                    public void accept(AppData appData) throws Exception {
                                        if(appData != null) {
                                            MessageUtils.showMessage(BlacklistService.this, R.string.app_blacklisted, Toast.LENGTH_LONG);
                                            startActivity(new Intent(BlacklistService.this, MainActivity.class));
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.e("BlacklistService", "Exception while retrieving app");
                                    }
                                }));
                    }
                })
                .timeout(5000)
                .start(this);
    }

    private void stopChecker() {
        appChecker.stop();
    }

}