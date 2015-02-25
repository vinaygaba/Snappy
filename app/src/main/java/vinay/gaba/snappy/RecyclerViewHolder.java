package vinay.gaba.snappy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vgaba on 1/25/2015.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {


    public TextView username;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
       username = (TextView) itemView.findViewById(R.id.usernameTextView);
        //add = (TextView) itemView.findViewById(R.id.addTextView);



    }
}