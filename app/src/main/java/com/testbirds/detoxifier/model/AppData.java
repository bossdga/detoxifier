package com.testbirds.detoxifier.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Class that represents an App
 */
@Entity(tableName = "app_data")
public class AppData {

    private int id;
    @ColumnInfo(name = "app_package")
    private String appPackage;
    @ColumnInfo(name = "app_icon")
    private String appIcon;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "app_name")
    private String appName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}