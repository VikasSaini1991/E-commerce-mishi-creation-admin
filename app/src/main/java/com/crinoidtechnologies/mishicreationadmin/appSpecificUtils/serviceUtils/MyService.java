package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serviceUtils;

import java.util.TimerTask;

/**
 * Created by shubham on 2/16/2018.
 */

public class MyService extends NonStopService {

    MonitoringAppModified syncTask =new MonitoringAppModified();

    @Override
    protected void initializeService() {
        service = MyService.class;
        syncTask.cancel();
        syncTask =new MonitoringAppModified();
   //     timer.schedule(syncTask, Constants.DATA_UPDATE_ON_SERVER_FIRST_TIME, Constants.DATA_UPDATE_ON_SERVER);

    }

    @Override
    protected void isServiceRunning() {
//        Toast.makeText(getBaseContext(), "MyService is running in background "+syncTask.scheduledExecutionTime(), Toast.LENGTH_LONG).show();
//        if(syncTask.)
    }

    private class MonitoringAppModified extends TimerTask {
        @Override
        public void run() {
          //  Log.d("My Service runnig", "run() called = "+"mnmbvsakjfbknfldsnvcdnfkvmfks///////////////");

        }
    }


}
