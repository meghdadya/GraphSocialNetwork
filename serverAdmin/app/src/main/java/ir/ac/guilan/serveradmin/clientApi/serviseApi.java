package ir.ac.guilan.serveradmin.clientApi;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

public class serviseApi extends Service {//get API for connect admin to server


    static public Client mClient;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new connectTask().execute("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class connectTask extends AsyncTask<String, String, Client> {

        @Override
        protected Client doInBackground(String... message) {

            //we create a Client object and
            //here the messageReceived method is implemented
            //this method calls the onProgressUpdate

            mClient = new Client(this::publishProgress);
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //in the arrayList we add the messaged received from server
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            int i = 0;
            System.out.println(values[i]);
            EventBus.getDefault().post(values[i]);
            i++;

        }
    }


}
