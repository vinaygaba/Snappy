package vinay.gaba.snappy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vgaba on 1/25/2015.
 */
public class CustomRecyclerAdapter
        extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<UsernamesHolder> mData = Collections.emptyList();
    private static Context sContext;


    public CustomRecyclerAdapter() {
        // Pass context or other static stuff that will be needed.
    }

    public CustomRecyclerAdapter(Context context, ArrayList<UsernamesHolder> myDataset) {
        // Pass context or other static stuff that will be needed.
        this.mData = myDataset;
        this.sContext = context;

    }

    public void updateList(List<UsernamesHolder> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_row, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.username.setText(mData.get(position).getUsername());
       // viewHolder.add.setText(mData.get(position).getAdd());
       // viewHolder.inapp.setText(mData.get(position).getIn_app());
        //viewHolder.icon.setBackgroundColor(Color.parseColor(mData.get(position).color));
    }
}