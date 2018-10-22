package com.grp17.dalassist.MyAccount.Student.Mydigest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.grp17.dalassist.ModelClass.StaffFaculty;
import com.grp17.dalassist.MyAccount.MyAccountActivity;
import com.grp17.dalassist.R;

import java.util.List;

/* Referred following cites for Recycler view and Holder concepts
 * https://www.android-examples.com/show-firebase-database-data-into-recyclerdoc/
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/*/

public class MydigestAppointAdapter extends RecyclerView.Adapter<MydigestAppointAdapter.ViewHolder> {
    private static final String TAG = "CartAdapter";
    private Context context;
    private List<StaffFaculty> loginList;

    public MydigestAppointAdapter(Context context, List<StaffFaculty> loginList) {
        this.context = context;
        this.loginList = loginList;

    }

    @Override
    public MydigestAppointAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_appointmnet, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StaffFaculty login = loginList.get(position);
         if (MyAccountActivity.nameOfStudent.equals(login.getTime())){
            holder.tvtime.setText(login.getContactNo());
            holder.tvName.setText(login.getEmailId());
        }else {
             holder.tvtime.setVisibility(View.VISIBLE);
             holder.tvName.setVisibility(View.VISIBLE);

         }
          }


    @Override
    public int getItemCount() {
        return loginList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // public CardView user_post_list;
        Button btnEdit;
        TextView tvName,tvtime;

        public ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            tvName = itemView.findViewById(R.id.tvName);
            tvtime = itemView.findViewById(R.id.tvtime);
        }

    }

}