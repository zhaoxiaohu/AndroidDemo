package com.example.guohl.androiddemo.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.guohl.androiddemo.R;

public class ProvidersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        query();
    }

    //--------------------------------------------------------------------------
    // A "projection" defines the columns that will be returned for each row
    String[] mProjection =
            {
                    UserDictionary.Words._ID,    // Contract class constant for the _ID column name
                    UserDictionary.Words.WORD,   // Contract class constant for the word column name
                    UserDictionary.Words.LOCALE  // Contract class constant for the locale column name
            };
    // Defines a string to contain the selection clause
    String mSelectionClause = null;
    /*
     * This defines a one-element String array to contain the selection argument.
     */
    String[] mSelectionArgs = {""};
    // Gets a word from the UI
    String mSearchString = "";//mSearchWord.getText().toString();
    Cursor mCursor;

    private void query() {
        // If the word is the empty string, gets everything
        if (TextUtils.isEmpty(mSearchString)) {
            // Setting the selection clause to null will return all words
            mSelectionClause = null;
            mSelectionArgs[0] = "";

        } else {
            // Constructs a selection clause that matches the word that the user entered.
            mSelectionClause = UserDictionary.Words.WORD + " = ?";

            // Moves the user's input string to the selection arguments.
            mSelectionArgs[0] = mSearchString;

        }

        // Does a query against the table and returns a Cursor object
//        mCursor = getContentResolver().query(
//                UserDictionary.Words.CONTENT_URI,  // The content URI of the words table
//                mProjection,                       // The columns to return for each row
//                mSelectionClause,                   // Either null, or the word the user entered
//                mSelectionArgs,                    // Either empty, or the string the user entered
//                null/*mSortOrder*/);                       // The sort order for the returned rows
        mCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                ContactsContract.Contacts.CONTENT_URI,  // The content URI of the  table
                null,                       // The columns to return for each row
                null,                   // Either null, or the word the user entered
                null,                    // Either empty, or the string the user entered
                null);                       // The sort order for the returned rows
        // Some providers return null if an error occurs, others throw an exception
        if (null == mCursor) {
        /*
         * Insert code here to handle the error. Be sure not to use the cursor! You may want to
         * call android.util.Log.e() to log this error.
         *
         */
            // If the Cursor is empty, the provider found no matches
        } else if (mCursor.getCount() < 1) {

        /*
         * Insert code here to notify the user that the search was unsuccessful. This isn't necessarily
         * an error. You may want to offer the user the option to insert a new row, or re-type the
         * search term.
         */

        } else {
            // Insert code here to do something with the results

            setAdapter(mCursor);
        }
    }

    void setAdapter(Cursor cursor) {
        // Defines a list of columns to retrieve from the Cursor and load into an output row
        String[] mWordListColumns =
                {
                        // Contract class constant containing the locale column name
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                };

        // Defines a list of View IDs that will receive the Cursor columns for each row
        int[] mWordListItems = {R.id.tvName, R.id.tvPhone};

        // Creates a new SimpleCursorAdapter
        SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),               // The application's Context object
                R.layout.list_row_contacts,                  // A layout in XML for one row in the ListView
                mCursor,                               // The result from the query
                mWordListColumns,                      // A string array of column names in the cursor
                mWordListItems,                        // An integer array of view IDs in the row layout
                0);                                    // Flags (usually none are needed)


        // Sets the adapter for the ListView
        ListView mWordList = (ListView)findViewById(R.id.listview);
        mWordList.setAdapter(mCursorAdapter);
    }

    private void insert() {
    }

    private void modify() {

    }

    private void delete() {

    }
}
