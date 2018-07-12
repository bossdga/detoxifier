# detoxifier

This app has two fragments:  
AppListFragment: This fragment is showing the list of installed apps.  
MainFragment: This fragment is showing the list of blacklisted apps.  

The app uses Room to handle persistent data.  
The app uses a background service to check which app is running on the foreground (I used a library for this: implementation 'com.rvalerio:fgchecker:1.1.0')  


From the main screen the user can trigger the list of selected apps and add them to the blacklist. The user also will be able to start and stop the no-distraction-mode.
