package vinay.gaba.snappy;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class AddFriend extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<UsernamesHolder> usernameList = new ArrayList<UsernamesHolder>();
    ParseUser currentUser;
    ParseRelation<ParseUser> parseRelation;
    List<ParseUser> parseUsersClone;
    public static String TAG_FRIEND = "Friend";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        getSupportActionBar().setCustomView(R.layout.search_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        // do whatever
                        final int clicked_position = position;
                        TextView add = (TextView)view.findViewById(R.id.addTextView);
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Crouton.makeText(AddFriend.this, "Add Clicked!", Style.CONFIRM).show();
                                ParseUser friendUser = parseUsersClone.get(clicked_position);
                                parseRelation.add(friendUser);
                                currentUser.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Crouton.makeText(AddFriend.this, "Friend Successfully Added!", Style.CONFIRM).show();
                                    }
                                });

                            }
                        });
                    }
                })
        );
        // Setting the LayoutManager.
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setting the adapter.
        mAdapter = new CustomRecyclerAdapter(this, usernameList);
        mRecyclerView.setAdapter(mAdapter);
        currentUser = ParseUser.getCurrentUser();
        parseRelation = currentUser.getRelation(TAG_FRIEND);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereStartsWith("username", "abc");
        query.whereNotEqualTo("username", currentUser.getUsername());


        query.findInBackground(new FindCallback<ParseUser>() {

            public void done(List<ParseUser> parseUsers, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + parseUsers.size() + " scores");
                    if(parseUsers.size() == 0){
                        Crouton.makeText(AddFriend.this, "No users found!", Style.INFO).show();
                    }

                    //if more than one user found
                    else{
                        parseUsersClone = parseUsers;
                        for(ParseUser parseUser:parseUsers){
                            String username = parseUser.getUsername();
                            UsernamesHolder usernameHolder= new  UsernamesHolder(username,"");
                            usernameList.add(usernameHolder);
                        }
                        mAdapter.notifyDataSetChanged();

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    Crouton.makeText(AddFriend.this, e.getMessage(), Style.ALERT).show();
                }

            }
        });
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        return true;
    }*/

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
