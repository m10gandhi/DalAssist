package com.grp17.dalassist.MyAccount.Student.CourseRegister;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.R;

import java.util.List;
/* Referred following cites for Recycler view and Holder concepts
 * https://www.android-examples.com/show-firebase-database-data-into-recyclerdoc/
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/*/


public class CourseRegAdapter extends RecyclerView.Adapter<CourseRegAdapter.ViewHolder> {
    private static final String TAG = "CourseRegAdapter";
    private Context context;
    private List<Login> logins;
    DatabaseReference database_appoint;


    public CourseRegAdapter(Context context,List<Login> logins) {
        this.context = context;
        this.logins = logins;

    }

    @Override
    public CourseRegAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_course_reg, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        database_appoint = FirebaseDatabase.getInstance().getReference("CoursSelected");

        Login login=logins.get(position);
        Log.d(TAG, "onBindViewHolder: "+login.getName());
        holder.textViewCourse.setText(login.getName());
        holder.textViewCourseInfo.setText(login.getPassword());

        holder.btnReg.setVisibility(View.GONE);

        holder.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login login=logins.get(position);

                String getName =  login.getName();
                String getDesc =  login.getPassword();

                AddCou(database_appoint.push().getKey(), getName,getDesc);
            }
        });

    }


    void AddCou(String id ,String name, String desc) {

        Login model = new Login(id, name, desc);
        String ids = database_appoint.push().getKey();
        database_appoint.child(ids).setValue(model);
        Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return logins.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
       // public CardView user_post_list;
        TextView textViewCourse,textViewCourseInfo;
        Button btnReg;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewCourse=itemView.findViewById(R.id.textViewCourse);
            textViewCourseInfo=itemView.findViewById(R.id.textViewCourseInfo);
            btnReg=itemView.findViewById(R.id.btnReg);

        }

    }

}