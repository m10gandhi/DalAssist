package com.grp17.dalassist.MyAccount.Student.AcadamicCalander;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.R;

import java.util.List;

/* Referred following cites for Recycler view and Holder concepts
 * https://www.android-examples.com/show-firebase-database-data-into-recyclerdoc/
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/*/

public class AcadamicCalanderAdapter extends RecyclerView.Adapter<AcadamicCalanderAdapter.ViewHolder> {
    private static final String TAG = "CartAdapter";
    private Context context;
    DatabaseReference databaseReference;
    private List<String> loginList;


    public AcadamicCalanderAdapter(Context context, List<String> loginList) {
        this.context = context;
        this.loginList = loginList;


    }

    @Override
    public AcadamicCalanderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_course_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        databaseReference = FirebaseDatabase.getInstance().getReference("CourseInfo");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String s = loginList.get(position);
        holder.tvCourse.setText(s);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"ADDED",Toast.LENGTH_SHORT).show();
                String s = holder.tvCourse.getText().toString();
                String s1 = holder.tvDetail.getText().toString();
                Log.d(TAG, "onClick: " + s + " Value OF S1" + s1);
                String id = databaseReference.push().getKey();
                Login login = new Login(id, s, s1, true);
                databaseReference.child(id).setValue(login);
                holder.btnAdd.setText("ADDED");
                holder.btnAdd.setClickable(false);

            }
        });
    }

    private void addValue() {

    }


    @Override
    public int getItemCount() {
        return loginList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // public CardView user_post_list;
        Button btnAdd;
        TextView tvCourse, tvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvCourse = itemView.findViewById(R.id.tvCourse);

        }

    }

}