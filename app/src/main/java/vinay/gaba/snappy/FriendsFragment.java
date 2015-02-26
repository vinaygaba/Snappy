package vinay.gaba.snappy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vgaba on 2/24/2015.
 */
public class FriendsFragment extends Fragment {

    private static final int HIGHLIGHT_COLOR = 0x999be6ff;
    public static String TAG_FRIEND = "Friend";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ParseUser currentUser;
    ParseRelation<ParseUser> parseRelation;
    List<ListData> friendsList =  new ArrayList<ListData>();
    ListAdapter mAdapter = new ListAdapter();

    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        mDrawableBuilder = TextDrawable.builder()
                .round();
        // init the list view and its adapter
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        listView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue_normal,R.color.snappy_yellow);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();

            }
                });

        currentUser = ParseUser.getCurrentUser();

        if( currentUser != null) {
            parseRelation = currentUser.getRelation(TAG_FRIEND);

        //    parseRelation.getQuery().setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
            parseRelation.getQuery().fromPin("FRIENDS_LIST");
            parseRelation.getQuery().addAscendingOrder("username");
            parseRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> parseUsers, ParseException e) {
                    if(e!=null){

                    }
                    else {
                        if (parseUsers.size() == 0) {
                            Log.e("No", "Friends");
                        } else {

                            for (ParseUser friend : parseUsers) {

                                ListData listData = new ListData(friend.getUsername());
                                friendsList.add(listData);
                            }
                        }
                        mAdapter.notifyDataSetChanged();

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground("FRIENDS_LIST", parseUsers);
                    }

                }
            });

        }

        return rootView;
    }


    public void refreshContent(){
       // parseRelation.getQuery().setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        parseRelation.getQuery().addAscendingOrder("username");
        parseRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(final List<ParseUser> parseUsers, ParseException e) {
                if (e == null) {
                    friendsList.clear();
                    for (ParseUser friend : parseUsers) {
                        ListData listData = new ListData(friend.getUsername());
                        friendsList.add(listData);
                    }
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);


                    // Release any objects previously pinned for this query.
                    ParseObject.unpinAllInBackground("FRIENDS_LIST", parseUsers, new DeleteCallback() {
                        public void done(ParseException e) {
                            if (e != null) {
                                // There was some error.
                                return;
                            }

                            // Add the latest results for this query to the cache.
                            ParseObject.pinAllInBackground("FRIENDS_LIST", parseUsers);
                        }
                    });


                }
            }

        });
    }



    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return friendsList.size();
        }

        @Override
        public ListData getItem(int position) {
            return friendsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.friends_list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData item = getItem(position);


            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when the image is clicked, update the selected state
                    ListData data = getItem(position);


                }
            });
            holder.friendName.setText(item.data);
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)).toUpperCase(), mColorGenerator.getColor(item.data.charAt(0)));
            holder.imageView.setImageDrawable(drawable);
            holder.view.setBackgroundColor(Color.TRANSPARENT);


            return convertView;
        }


    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;

        private TextView friendName;



        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            friendName = (TextView) view.findViewById(R.id.friendTextView);

        }
    }



    private static class ListData {

        private String data;

        private boolean isChecked;

        public ListData(String data) {
            this.data = data;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }
    }
}
