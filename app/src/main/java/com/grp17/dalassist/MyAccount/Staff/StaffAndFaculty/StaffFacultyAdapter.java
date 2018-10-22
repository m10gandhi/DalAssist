package com.grp17.dalassist.MyAccount.Staff.StaffAndFaculty;

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
import com.grp17.dalassist.ModelClass.StaffFaculty;
import com.grp17.dalassist.MyAccount.Staff.MyAccounStafftActivity;
import com.grp17.dalassist.R;

import java.util.List;

/* Referred following cites for Recycler view and Holder concepts
 * https://www.android-examples.com/show-firebase-database-data-into-recyclerdoc/
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/*/

public class StaffFacultyAdapter extends RecyclerView.Adapter<StaffFacultyAdapter.ViewHolder> {
    private static final String TAG = "StaffFacultyAdapter";

    DatabaseReference databaseReference;
    private Context context;
    private List<StaffFaculty> loginList;


    public StaffFacultyAdapter(Context context, List<StaffFaculty> logins) {
        this.context = context;
        this.loginList = logins;

    }

    @Override
    public StaffFacultyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_staff_faculty_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        databaseReference = FirebaseDatabase.getInstance().getReference("SatffAndFacultyAppointmnet");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.btnEdit.setVisibility(View.VISIBLE);
        final StaffFaculty login = loginList.get(position);


        holder.tvPost.setText(login.getTime());
        holder.tvContact.setText(login.getContactNo());
        holder.tvEmail.setText(login.getEmailId());
        holder.tvName.setText(MyAccounStafftActivity.Name);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + login.getId());
                Log.d(TAG, "onClick: " + login.getTime());
                holder.etEdit.setVisibility(View.VISIBLE);
                holder.btnUpdate.setVisibility(View.VISIBLE);

            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edit = holder.etEdit.getText().toString().trim();

                String editCon = holder.tvContact.getText().toString().trim();
                String editEM = holder.tvEmail.getText().toString().trim();
                Log.d(TAG, "loginList: "+loginList);
                Log.d(TAG, "id: "+login.getId());
                updateArtist(login.getId(), edit,editCon,editEM);
                holder.btnUpdate.setText("Time Updated ");
                holder.etEdit.setVisibility(View.GONE);

            }
        });


    }

    private boolean updateArtist(String id, String name, String editCon, String editEM) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SatffAndFacultyAppointmnet").child(id);
        StaffFaculty login = new StaffFaculty(id, name,editCon,editEM);
        databaseReference.setValue(login);

        Log.d(TAG, "updateArtist: " + "UPDATED");

        return true;
    }


    @Override
    public int getItemCount() {
        return loginList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // public CardView user_post_list;
        Button btnEdit, btnUpdate;
        TextView tvPost,tvContact,tvEmail,tvName;
        EditText etEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            tvPost = itemView.findViewById(R.id.tvPost);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvContact = itemView.findViewById(R.id.tvContact);
            etEdit = itemView.findViewById(R.id.etEdit);
        }

    }

}