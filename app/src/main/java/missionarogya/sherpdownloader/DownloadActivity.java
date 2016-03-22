package missionarogya.sherpdownloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {
    SherpData sherpData = SherpData.getInstance();
    JSONArray interviewJSON;
    String logmessage = "";
    MediaPlayer mp = new MediaPlayer();
    String currentlyPlaying = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final ImageButton downloadDataFromServer = (ImageButton) findViewById(R.id.buttonDownload);
        downloadDataFromServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDataFromServer.setClickable(false);
                try {
                    if (downloadFromServer()) {
                        final ImageButton go = (ImageButton) findViewById(R.id.go);
                        go.setVisibility(View.VISIBLE);
                        go.setClickable(true);
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DownloadActivity.this, DisplayInterviewData.class);
                                DownloadActivity.this.startActivity(intent);
                                DownloadActivity.this.finish();
                            }
                        });
                        Toast.makeText(getApplicationContext(), "Interview Data successfully downloaded from server.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "There was an error downloading Interview Data from the server.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //roll back changes
                DownloadActivity.this.finish();
            }
        });
    }

    private boolean downloadFromServer() throws Exception{
        boolean success;
        final JSONDownloader mJSONDownloader = new JSONDownloader(DownloadActivity.this, sherpData);
        try {
            String status = mJSONDownloader.execute("").getStatus().name();
            Toast.makeText(DownloadActivity.this, "Status of download process: " + status + "\n", Toast.LENGTH_LONG).show();
            success = true;
        }catch(Exception ex){
            Toast.makeText(DownloadActivity.this, "Error occured while downloading to server:"+ex.getMessage(), Toast.LENGTH_LONG).show();
            success = false;
        }
        return success;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


class JSONDownloader extends AsyncTask<String, Void, String> {
    SherpData sherpData;
    String output="";
    String logmessage;
    DownloadActivity activity;
    ProgressDialog progressDialog;

    public JSONDownloader(DownloadActivity activity, SherpData sherpData) {
        this.activity = activity;
        this.sherpData = sherpData;
    }

    @Override
    public String doInBackground(String... params) {
        try {
            logmessage = logmessage + "\nDownloading from server....\n";
            URL url = new URL("http://springdemo11-sampledemosite.rhcloud.com/profile/GetInterviewDataToServer");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            logmessage = logmessage + "Response from server: "+"\n";
            while ((output = br.readLine()) != null) {
                logmessage = logmessage + output +"\n";
                if(output.length() > 0){
                    output = output.substring(1,output.length()-1);
                    sherpData.setInterviewData(output.substring(75,output.length()));
                    SherpData.setInstance(sherpData);
                    String[] arr = output.split(",");
                    for (String s:arr) {
                        String[] arr1 = s.split(":");
                        if(arr1[0].substring(1,arr1[0].length()-1).equals("httpStatus")){
                            sherpData.setHttpStatusDownload(arr1[1].substring(1, arr1[1].length() - 1));
                            SherpData.setInstance(sherpData);
                        }
                        if(arr1[0].substring(1,arr1[0].length()-1).equals("message")){
                            sherpData.setMessageDownload(arr1[1].substring(1, arr1[1].length() - 1));
                            SherpData.setInstance(sherpData);
                        }
                    }
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            logmessage = logmessage + "\nFATAL ERROR :: "+ e;
        }

        return output;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        logmessage = logmessage + "Will download data from server.\n";
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Downloading data from server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressPercentFormat(null);
        progressDialog.setProgressNumberFormat(null);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        progressDialog = null;
    }

}