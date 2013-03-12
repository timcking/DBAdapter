package com.timothyking.DBAdapterTest;

import java.util.ArrayList;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class DBAdapterTest extends ListActivity {
    /** Called when the activity is first created. */
    ArrayList<String> results = new ArrayList<String>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        DBAdapter db = new DBAdapter(this);

        CreateTitles (db);
        // DeleteTitle (db, 2);
        GetTitles (db);
        // GetOneTitle (db, 2);
    }    
    
    public void GetTitles (DBAdapter db)   
    {
        //---get all titles---
        db.open();
        Cursor c = db.getAllTitles();
        if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {
    				String s = "id: " + c.getString(0) + "\n";
    		        s += "ISBN: " + c.getString(1) + "\n";
    		        s += "Title: " + c.getString(2) + "\n";
    		        s += "Publisher:  " + c.getString(3);
    				results.add(s);
    			} while (c.moveToNext());
    		} 
        }
        
        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,results));
    		
        db.close();
    }
    
    public void GetOneTitle (DBAdapter db, int id)
    {
    	//---get a title---
        db.open();
        Cursor c = db.getTitle(id);
        if (c.moveToFirst())        
            DisplayTitle(c);
        else
            Toast.makeText(this, "No title found", 
            		Toast.LENGTH_LONG).show();
        db.close();
    }
    
    public void CreateTitles (DBAdapter db)
    {
        //---add 3 titles---
        db.open();        
        long id;
        id = db.insertTitle(
        		"040285818",
        		"C# 2008 Programmer's Reference",
        		"Wrox");        
        id = db.insertTitle(
        		"047017661",
        		"Professional Windows Vista Gadgets Programming",
        		"Wrox");
        id = db.insertTitle(
        		"023853393",
        		"The Moviegoer",
        		"Penguin");
        db.close();      	
    }
    
    public void DeleteTitle (DBAdapter db, int id)
    {
        //---delete a title---
        db.open();
        if (db.deleteTitle(id))
            Toast.makeText(this, "Delete successful.", 
                Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", 
                Toast.LENGTH_LONG).show();            
        db.close();
    }
    
    public void UpdateTitle (DBAdapter db, int id)
    {
        //---update title---
        db.open();
        if (db.updateTitle(1, 
        		"0470285818", 
        		"C# 2008 Programmer's Reference",
        		"Wrox Press"))
            Toast.makeText(this, "Update successful.", 
                Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", 
                Toast.LENGTH_LONG).show();
        //-------------------
        
        //---retrieve the same title to verify---
        Cursor c = db.getTitle(1);
        if (c.moveToFirst())        
            DisplayTitle(c);
        else
            Toast.makeText(this, "No title found", 
            		Toast.LENGTH_LONG).show();        
        //-------------------        
        db.close();
    }
    	
    public void DisplayTitle(Cursor c)
    {
        Toast.makeText(this, 
                "id: " + c.getString(0) + "\n" +
                "ISBN: " + c.getString(1) + "\n" +
                "TITLE: " + c.getString(2) + "\n" +
                "PUBLISHER:  " + c.getString(3),
                Toast.LENGTH_LONG).show();        
    } 
}