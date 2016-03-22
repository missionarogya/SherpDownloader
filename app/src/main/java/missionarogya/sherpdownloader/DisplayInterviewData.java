package missionarogya.sherpdownloader;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DisplayInterviewData extends AppCompatActivity {
    SherpData sherpData = SherpData.getInstance();
    JSONArray interviewJSON;
    MediaPlayer mp = new MediaPlayer();
    String currentlyPlaying = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_interview_data);

        final Button exit1 = (Button) findViewById(R.id.exit1);
        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //roll back changes
                DisplayInterviewData.this.finish();
            }
        });

        String interviewee = "";
        String qasetId = "";
        String venue = "";
        String interviewer = "";
        String interviewStartTime = "";
        String interviewEndTime = "";
        try{
            TableLayout table = (TableLayout)findViewById(R.id.interviewDataTable);
            if(validateJSON(sherpData.getInterviewData())){
                try {
                    JSONArray interviewArray = new JSONArray(sherpData.getInterviewData());
                    for(int i=0; i<interviewArray.length(); i++){

                        final TableRow row = new TableRow(this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(lp);
                        row.setPadding(2, 2, 2, 2);
                        row.setBackgroundColor(Color.WHITE);
                        row.setGravity(Gravity.CENTER);

                        JSONObject interviewDetails = interviewArray.getJSONObject(i);

                        qasetId = interviewDetails.getString("qaset_id");

                        TextView txtQasetId = new TextView(this);
                        txtQasetId.setText(" " + qasetId + " ");
                        txtQasetId.setGravity(Gravity.CENTER);
                        txtQasetId.setTextColor(Color.WHITE);
                        txtQasetId.setBackgroundColor(Color.BLACK);
                        txtQasetId.setTypeface(Typeface.DEFAULT);
                        txtQasetId.setLayoutParams(new TableRow.LayoutParams(0));
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)txtQasetId.getLayoutParams();
                        params.setMargins(0, 0, 2, 0);
                        txtQasetId.setLayoutParams(params);
                        row.addView(txtQasetId);

                        interviewer = interviewDetails.getString("interviewer_id");

                        TextView txtInterviewer = new TextView(this);
                        txtInterviewer.setText(" " + interviewer + " ");
                        txtInterviewer.setGravity(Gravity.CENTER);
                        txtInterviewer.setTextColor(Color.WHITE);
                        txtInterviewer.setBackgroundColor(Color.BLACK);
                        txtInterviewer.setTypeface(Typeface.DEFAULT);
                        txtInterviewer.setLayoutParams(new TableRow.LayoutParams(1));
                        LinearLayout.LayoutParams interviewerParams = (LinearLayout.LayoutParams)txtInterviewer.getLayoutParams();
                        interviewerParams.setMargins(0, 0, 2, 0);
                        txtInterviewer.setLayoutParams(interviewerParams);
                        row.addView(txtInterviewer);

                        interviewee = interviewDetails.getString("interviewee_id");

                        TextView txtInterviewee = new TextView(this);
                        txtInterviewee.setText(" " + interviewee + " ");
                        txtInterviewee.setGravity(Gravity.CENTER);
                        txtInterviewee.setTextColor(Color.WHITE);
                        txtInterviewee.setBackgroundColor(Color.BLACK);
                        txtInterviewee.setTypeface(Typeface.DEFAULT);
                        txtInterviewee.setLayoutParams(new TableRow.LayoutParams(2));
                        LinearLayout.LayoutParams intervieweeParams = (LinearLayout.LayoutParams)txtInterviewee.getLayoutParams();
                        intervieweeParams.setMargins(0, 0, 2, 0);
                        txtInterviewee.setLayoutParams(intervieweeParams);
                        row.addView(txtInterviewee);

                        JSONObject interviewTime = interviewDetails.getJSONObject("interview_dttm");
                        interviewStartTime = interviewTime.getString("startdt_tm");
                        interviewEndTime = interviewTime.getString("enddt_tm");

                        TextView txtInterviewStartTime = new TextView(this);
                        txtInterviewStartTime.setText(" " + interviewStartTime + " ");
                        txtInterviewStartTime.setGravity(Gravity.CENTER);
                        txtInterviewStartTime.setTextColor(Color.WHITE);
                        txtInterviewStartTime.setBackgroundColor(Color.BLACK);
                        txtInterviewStartTime.setTypeface(Typeface.DEFAULT);
                        txtInterviewStartTime.setLayoutParams(new TableRow.LayoutParams(3));
                        LinearLayout.LayoutParams interviewStartTimeParams = (LinearLayout.LayoutParams)txtInterviewStartTime.getLayoutParams();
                        interviewStartTimeParams.setMargins(0, 0, 2, 0);
                        txtInterviewStartTime.setLayoutParams(interviewStartTimeParams);
                        row.addView(txtInterviewStartTime);

                        TextView txtInterviewEndTime = new TextView(this);
                        txtInterviewEndTime.setText(" " + interviewEndTime + " ");
                        txtInterviewEndTime.setGravity(Gravity.CENTER);
                        txtInterviewEndTime.setTextColor(Color.WHITE);
                        txtInterviewEndTime.setBackgroundColor(Color.BLACK);
                        txtInterviewEndTime.setTypeface(Typeface.DEFAULT);
                        txtInterviewEndTime.setLayoutParams(new TableRow.LayoutParams(4));
                        LinearLayout.LayoutParams interviewEndTimeParams = (LinearLayout.LayoutParams)txtInterviewEndTime.getLayoutParams();
                        interviewEndTimeParams.setMargins(0, 0, 2, 0);
                        txtInterviewEndTime.setLayoutParams(interviewEndTimeParams);
                        row.addView(txtInterviewEndTime);

                        venue = interviewDetails.getString("venue");

                        TextView txtVenue = new TextView(this);
                        txtVenue.setText(" "+venue+" ");
                        txtVenue.setGravity(Gravity.CENTER);
                        txtVenue.setTextColor(Color.WHITE);
                        txtVenue.setBackgroundColor(Color.BLACK);
                        txtVenue.setTypeface(Typeface.DEFAULT);
                        txtVenue.setLayoutParams(new TableRow.LayoutParams(5));
                        LinearLayout.LayoutParams qasetParams = (LinearLayout.LayoutParams)txtVenue.getLayoutParams();
                        qasetParams.setMargins(0, 0, 2, 0);
                        txtVenue.setLayoutParams(qasetParams);
                        row.addView(txtVenue);

                        JSONArray interviewAnswers = interviewDetails.getJSONArray("answer");
                        for(int k=0; k<interviewAnswers.length(); k++){

                            JSONObject interviewAnswersObj = interviewAnswers.getJSONObject(k);
                            String question = interviewAnswersObj.getString("question");
                            String answer = interviewAnswersObj.getString("answer");

                            if(question.equals("q_district_eng")){
                                if(answer.equals("1")){
                                    answer = " Patna ";
                                }else if(answer.equals("2")){
                                    answer = " Purnia ";
                                }else if(answer.equals("3")){
                                    answer = " Bhagalpur ";
                                }else if(answer.equals("4")){
                                    answer = " Gopalganj ";
                                }else if(answer.equals("5")){
                                    answer = " Champaran ";
                                }
                                TextView txtAnswers = new TextView(this);
                                txtAnswers.setText(answer);
                                txtAnswers.setLayoutParams(new TableRow.LayoutParams(6));
                                LinearLayout.LayoutParams answerParams = (LinearLayout.LayoutParams)txtAnswers.getLayoutParams();
                                answerParams.setMargins(0, 0, 2, 0);
                                txtAnswers.setLayoutParams(answerParams);
                                txtAnswers.setGravity(Gravity.CENTER);
                                txtAnswers.setTextColor(Color.WHITE);
                                txtAnswers.setBackgroundColor(Color.BLACK);
                                txtAnswers.setTypeface(Typeface.DEFAULT);
                                row.addView(txtAnswers);
                            }else if(question.equals("q_block_eng")){
                                TextView txtAnswers = new TextView(this);
                                txtAnswers.setText(" "+answer+" ");
                                txtAnswers.setLayoutParams(new TableRow.LayoutParams(7));
                                LinearLayout.LayoutParams answerParams = (LinearLayout.LayoutParams)txtAnswers.getLayoutParams();
                                answerParams.setMargins(0, 0, 2, 0);
                                txtAnswers.setLayoutParams(answerParams);
                                txtAnswers.setGravity(Gravity.CENTER);
                                txtAnswers.setTextColor(Color.WHITE);
                                txtAnswers.setBackgroundColor(Color.BLACK);
                                txtAnswers.setTypeface(Typeface.DEFAULT);
                                row.addView(txtAnswers);
                            }else if(question.equals("q_village_eng")){
                                TextView txtAnswers = new TextView(this);
                                txtAnswers.setText(" "+answer+" ");
                                txtAnswers.setLayoutParams(new TableRow.LayoutParams(8));
                                LinearLayout.LayoutParams answerParams = (LinearLayout.LayoutParams)txtAnswers.getLayoutParams();
                                answerParams.setMargins(0, 0, 2, 0);
                                txtAnswers.setLayoutParams(answerParams);
                                txtAnswers.setGravity(Gravity.CENTER);
                                txtAnswers.setTextColor(Color.WHITE);
                                txtAnswers.setBackgroundColor(Color.BLACK);
                                txtAnswers.setTypeface(Typeface.DEFAULT);
                                row.addView(txtAnswers);
                            }else if(question.equals("q_awccode_eng")){
                                TextView txtAnswers = new TextView(this);
                                txtAnswers.setText(" "+answer+" ");
                                txtAnswers.setLayoutParams(new TableRow.LayoutParams(9));
                                LinearLayout.LayoutParams answerParams = (LinearLayout.LayoutParams)txtAnswers.getLayoutParams();
                                answerParams.setMargins(0, 0, 2, 0);
                                txtAnswers.setLayoutParams(answerParams);
                                txtAnswers.setGravity(Gravity.CENTER);
                                txtAnswers.setTextColor(Color.WHITE);
                                txtAnswers.setBackgroundColor(Color.BLACK);
                                txtAnswers.setTypeface(Typeface.DEFAULT);
                                row.addView(txtAnswers);
                            }
                        }
                        final ImageView audio = new ImageView(this);
                        audio.setPadding(0, 0, 0, 0);
                        audio.setImageResource(R.drawable.play);
                        audio.setVisibility(View.VISIBLE);
                        audio.setClickable(true);
                        audio.setBackgroundColor(Color.BLACK);
                        audio.setLayoutParams(new TableRow.LayoutParams(10));
                        row.addView(audio);

                        final String audioFile = interviewee + ".mp3";
                        audio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    playMusic(audioFile, audio);
                                } catch (Exception e) {
                                    Toast.makeText(DisplayInterviewData.this, "Error: " + e.getStackTrace()[0].getLineNumber() + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        table.addView(row);
                    }
                }catch(JSONException e){
                    Toast.makeText(DisplayInterviewData.this, "JSON Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(DisplayInterviewData.this, "Error in display: "+ e.getStackTrace()[0].getLineNumber() + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }catch(Exception e){
            Toast.makeText(DisplayInterviewData.this, "Error: "+e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateJSON(String jsonContent) throws Exception{
        boolean success;
        try{
            interviewJSON = new JSONArray(jsonContent);
            //Toast.makeText(DisplayInterviewData.this, "Valid JSON", Toast.LENGTH_LONG).show();
            success = true;
        }catch(JSONException e){
            Toast.makeText(DisplayInterviewData.this, "Invalid JSON: "+e.getMessage(), Toast.LENGTH_LONG).show();
            success = false;
        }catch(Exception e){
            Toast.makeText(DisplayInterviewData.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            success = false;
        }
        return success;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_interview_data, menu);
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


    private void playMusic(String audioName, ImageView audio){
        int resume;
        if(currentlyPlaying.equals(audioName)){
            resume = 1;
        }else{
            currentlyPlaying = audioName;
            resume = 0;
        }
        FileInputStream fis = null;
        File qasetDir ;
        File soundFile ;
        File soundDir;
        FileDescriptor fd = null;
        try {
            File parentDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Sherp");
            if (parentDir.exists() && parentDir.isDirectory()) {
                qasetDir = new File(parentDir, "ANC-STI");
                if (qasetDir.exists() && qasetDir.isDirectory()) {
                    soundDir = new File(qasetDir, "audio");
                    if (soundDir.exists() && soundDir.isDirectory()) {
                        soundFile = new File(soundDir, audioName);
                        if(soundFile.exists() && soundFile.isFile()){
                            soundFile.setReadable(true);
                            fis = new FileInputStream(soundFile.getAbsolutePath());
                            fd = fis.getFD();
                        }
                    }
                }
            }
            if (fd != null ) {
                if (mp.isPlaying() && resume==1) {
                    mp.pause();
                    audio.setBackgroundColor(Color.DKGRAY);
                }else {
                    mp.reset();
                    mp.setDataSource(fd);
                    mp.prepare();
                    mp.start();
                    fis.close();
                    audio.setBackgroundColor(Color.GRAY);
                }
            }
        }catch (FileNotFoundException e) {
            Toast.makeText(DisplayInterviewData.this, "Error(File not found) in playing audio: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            Toast.makeText(DisplayInterviewData.this, "Error(Illegal state exception) in playing audio: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(DisplayInterviewData.this, "Error(I/O) in playing audio: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(DisplayInterviewData.this, "Error in playing audio: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
