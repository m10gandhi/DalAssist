package com.grp17.dalassist.MyAccount.Staff.EventStaff;

/* Referred following cites for Recycler view and Holder concepts
 * https://www.android-examples.com/show-firebase-database-data-into-recyclerdoc/
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/*/

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.R;

import java.util.List;



public class EventStaffAdapter extends RecyclerView.Adapter<EventStaffAdapter.ViewHolder> {
    private static final String TAG = "EventStaffAdapter";
    private Context context;
    private List<Login> loginList;

    public EventStaffAdapter(Context context, List<Login> loginList) {
        this.context = context;
        this.loginList = loginList;

    }

    @Override
    public EventStaffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_order_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btnEdit.setVisibility(View.VISIBLE);
        final Login login = loginList.get(position);

        Log.d(TAG, "onBindViewHolder: " + login.getName());
        Log.d(TAG, "onBindViewHolder: ID" + login.getId());
        Log.d(TAG, "onBindViewHolder: Name" + login.getPassword());
        holder.tvPost.setText(login.getPassword());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + login.getId());
                Log.d(TAG, "onClick: " + login.getPassword());
                holder.etEdit.setVisibility(View.VISIBLE);
                holder.btnUpdate.setVisibility(View.VISIBLE);
                holder.etEdit.setText(login.getPassword());

            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String edit = holder.etEdit.getText().toString().trim();
                Log.d(TAG, "onClick: "+edit);
                updateArtist(login.getId(), edit);

            }
        });


    }

    private boolean updateArtist(String id, String name) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("EventT").child(id);
        Login login = new Login(id, name);
        databaseReference.setValue(login);
//        Intent intent=new Intent(context, MainViewPagerStaffActivity.class);
//        context.startActivity(intent);
        Log.d(TAG, "updateArtist: " + "UPDATED");
        // Toast.makeText(context,"Event Updated",Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public int getItemCount() {
        return loginList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // public CardView user_post_list;
        Button btnEdit, btnUpdate;
        TextView tvPost;
        EditText etEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            tvPost = itemView.findViewById(R.id.tvPost);
            etEdit = itemView.findViewById(R.id.etEdit);
        }

    }

}