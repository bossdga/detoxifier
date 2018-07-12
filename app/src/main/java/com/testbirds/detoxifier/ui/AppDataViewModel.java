package com.testbirds.detoxifier.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.testbirds.detoxifier.model.AppData;
import com.testbirds.detoxifier.persistence.AppDataRepository;

import java.util.List;

import io.reactivex.Flowable;

/**
 * ViewModel to interact between the view and the model
 */
public class AppDataViewModel extends AndroidViewModel {

   private AppDataRepository mRepository;

   public AppDataViewModel (Application application) {
       super(application);
       mRepository = new AppDataRepository(application);
   }

   public Flowable<List<AppData>> getAllApps() {
       return mRepository.getAllApps();
   }

   public void insert(AppData appData) {
       mRepository.insert(appData);
   }

   public void delete(AppData appData) {
        mRepository.delete(appData);
   }
   public Flowable<AppData> findByPackage(String packageName) {
        return mRepository.findByPackage(packageName);
   }

}