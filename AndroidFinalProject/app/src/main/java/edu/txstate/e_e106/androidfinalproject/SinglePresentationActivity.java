package edu.txstate.e_e106.androidfinalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class SinglePresentationActivity extends AppCompatActivity {

    List<Presentation> myPres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyPresentations();
        //setContentView(R.layout.activity_single_presentation);
    }

    protected void getMyPresentations(){
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        RestClient.get(SinglePresentationActivity.this, "presentations.json", headers.toArray(new Header[headers.size()]),null,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                myPres = new ArrayList<>();
                for(int j = 0; j < response.length(); j++){
                    try{
                        myPres.add(new Presentation(response.getJSONObject(j)));
                    }catch(Exception ex){ex.printStackTrace();}
                }
                System.out.println(myPres.size());
                setContentView(R.layout.activity_single_presentation);
                final TextView titleTxt = findViewById(R.id.titleText);
                final TextView rmTxt = findViewById(R.id.roomText);
                final TextView capTxt = findViewById(R.id.capacityText);
                final TextView numAttTxt = findViewById(R.id.attendeesText);
                titleTxt.setText("Presentation: " + myPres.get(0).getTitle());
                rmTxt.setText("Room Number: " + myPres.get(0).getRoomNum());
                capTxt.setText("Capacity: " + myPres.get(0).getCapacity());
                numAttTxt.setText("Attendees: " + myPres.get(0).getNumOfAttendees());
            }
        });
    }

}
