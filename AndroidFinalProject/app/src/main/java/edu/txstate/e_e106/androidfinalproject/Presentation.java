package edu.txstate.e_e106.androidfinalproject;

import org.json.JSONObject;

public class Presentation {

    private int id, capacity, numOfAttendees;
    private String roomNum,title;

    public Presentation(){
        //empty constructor
    }

    public Presentation(JSONObject presObject){

        try{
            this.id = presObject.getInt("Id");
            this.capacity = presObject.getInt("Capacity");
            this.numOfAttendees = presObject.getInt("NumOfAttendees");
            this.roomNum = presObject.getString("Room");
            this.title = presObject.getString("Title");
        }
        catch(Exception ex){ex.printStackTrace();}

    }

    public Presentation(int id, int capacity, int numOfAttendees, String roomNum, String title) {
        this.id = id;
        this.capacity = capacity;
        this.numOfAttendees = numOfAttendees;
        this.roomNum = roomNum;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumOfAttendees() {
        return numOfAttendees;
    }

    public void setNumOfAttendees(int numOfAttendees) {
        this.numOfAttendees = numOfAttendees;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Presentation: " + title + "\nNumber of Attendees: " + numOfAttendees;
    }
}
