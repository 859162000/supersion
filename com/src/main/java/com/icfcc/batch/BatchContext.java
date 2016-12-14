package com.icfcc.batch;

import com.icfcc.batch.util.LogConfiger;
import java.util.HashMap;

public class BatchContext
{
    public static String baseHome = "";
    private HashMap properites = new HashMap();
    private static BatchContext _instance = null;

    public static synchronized BatchContext getInstance()
    {
        if (_instance == null) {
            _instance = new BatchContext();
        }

        return _instance;
    }

    public synchronized String getBaseHome()
    {
        /**
        String home = System.getProperty("Home");
        if (home != null) {
            String homeTemp = LogConfiger.formatFilePath(home);
            if (!(homeTemp.endsWith("/")))
                homeTemp = homeTemp + "/";

            return homeTemp;
        }
        if ((this.baseHome == null) || (this.baseHome.length() == 0)) {
            String userHome = System.getProperty("user.dir");
            String temp = LogConfiger.formatFilePath(userHome);
            temp = temp.substring(0, temp.lastIndexOf("/"));
            this.baseHome = temp + "/";
        }
        else {
            String temp = LogConfiger.formatFilePath(this.baseHome);
            if (!(temp.endsWith("/")))
                this.baseHome = temp + "/";
        }
        */
        return this.baseHome;
    }

    public synchronized void setBaseHome(String baseHome)
    {
        if (this.baseHome.length() == 0)
            this.baseHome = baseHome;
    }

    public synchronized HashMap getProperites()
    {
        return this.properites;
    }

    public synchronized Object getProperites(String properityName)
    {
        return this.properites.get(properityName);
    }

    public synchronized void addPropetiry(String properityName, Object properityObject)
    {
        this.properites.put(properityName, properityObject);
    }
}