package com.testbirds.detoxifier.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.testbirds.detoxifier.model.AppData;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Dao to interact with the database
 */
@Dao
public interface AppDataDao {

    @Query("SELECT * FROM app_data")
    Flowable<List<AppData>> getAll();

    @Query("SELECT * FROM app_data WHERE id IN (:appDataIds)")
    List<AppData> loadAllByIds(int[] appDataIds);

    @Query("SELECT * FROM app_data WHERE app_name LIKE :appName LIMIT 1")
    AppData findByName(String appName);

    @Insert
    void insertAll(AppData... appDataList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppData appData);

    @Delete
    void delete(AppData appData);

}
