package com.grp17.dalassist.MyAccount.Student.StaffFaculty;

import android.content.Context;
import android.content.Intent;
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
import com.grp17.dalassist.ModelClass.StaffFaculty;
import com.grp17.dalassist.MyAccount.MyAccountActivity;
import com.grp17.dalassist.MyAccount.Staff.MainViewPagerStaffActivity;
import com.grp17.dalassist.R;

import java.util.List;

/* Referred following cites for Recycler view and Holder concepts
 * https://www.android-examples.com/show-firebase-database-data-into-recyclerdoc/
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/*/

public class StaffFacultyAdapter extends RecyclerView.Adapter<StaffFacultyAdapter.ViewHolder> {
    private static final String TAG = "StaffFacultyAdapter";
    private List<StaffFaculty> loginList;

     private Context context;
    private List<Login> logins;
    DatabaseReference databaseReference;

    public StaffFacultyAdapter(Context context, List<StaffFaculty> loginList) {

        this.context = context;
        this.loginList = loginList;

    }

    @Override
    public StaffFacultyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_staff_faculty_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        databaseReference = FirebaseDatabase.getInstance().getReference("SatffAndFacultyAppointmnet");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StaffFaculty login = loginList.get(position);


        holder.btnEdit.setVisibility(View.VISIBLE);
       holder.tvPost.setText(login.getTime());
        holder.tvContact.setText(login.getContactNo()+"");
        holder.tvEmail.setText(login.getEmailId());
        holder.tvName.setText("Staff");
        holder.btnEdit.setText("Appointment");
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_student = MyAccountActivity.nameOfStudent;
                String time = holder.tvPost.getText().toString();
                String name_faculty = holder.tvName.getText().toString();
                String id = databaseReference.push().getKey();
                StaffFaculty login = new StaffFaculty(id, name_student, time,name_faculty);
                databaseReference.child(id).setValue(login);
                holder.btnEdit.setText("Appointment Taken");
            //    Toast.makeText(context,"Appointment Taken",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean updateArtist(String id, String name) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SatffAndFaculty").child(id);
        StaffFaculty login = new StaffFaculty(id, name);
        databaseReference.setValue(login);
        Intent intent=new Intent(context, MainViewPagerStaffActivity.class);
        context.startActivity(intent);
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
        Button btnEdit, btnUpdate,btnApp;
        TextView tvPost,tvContact,tvEmail,tvName;
        EditText etEdit;
        public ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnApp = itemView.findViewById(R.id.btnApp);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            tvPost = itemView.findViewById(R.id.tvPost);
            etEdit = itemView.findViewById(R.id.etEdit);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvContact = itemView.findViewById(R.id.tvContact);


        }

    }

}