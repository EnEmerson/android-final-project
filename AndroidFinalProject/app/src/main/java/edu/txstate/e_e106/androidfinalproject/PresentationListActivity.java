package edu.txstate.e_e106.androidfinalproject;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class PresentationListActivity extends ListActivity {

    List<Presentation> presentations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentations();
    }

    protected void getPresentations(){
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        RestClient.get(PresentationListActivity.this, "presentations.json", headers.toArray(new Header[headers.size()]),null, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                presentations = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    try{
                        presentations.add(new Presentation(response.getJSONObject(i)));
                    }catch(Exception ex){ex.printStackTrace();}
                }
                setListAdapter(new ArrayAdapter<Presentation>(PresentationListActivity.this,R.layout.activity_presentation_list, R.id.listItemText, presentations));
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Presentation curPresentation = presentations.get(position);
        // used this if statement to reset the number of attendees to 0 to test the rest of my code
        /*if(curPresentation.getNumOfAttendees() >= curPresentation.getCapacity()){
            addAttendee(position, curPresentation.getNumOfAttendees());
        }*/
        if(curPresentation.getNumOfAttendees() < curPresentation.getCapacity()){
            addAttendee(position, curPresentation.getNumOfAttendees());
        }
        else{
            Toast.makeText(this, "No seats available!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void addAttendee(int position, int numAttendees){
        String url = "presentations/" + position + "/NumOfAttendees.json";
        StringEntity entity = null;
        try{
            int newAttendees = numAttendees + 1;
            entity = new StringEntity("" + newAttendees);
        }catch(Exception ex){
            Toast.makeText(this, "Error: Unable to increment attendees.", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
        RestClient.put(PresentationListActivity.this, url, entity, "application/text", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(PresentationListActivity.this, "Error updating attendees.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(PresentationListActivity.this, "Welcome to the presentation!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
