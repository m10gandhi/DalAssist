package com.grp17.dalassist.Assist;

/* Referred

https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java

https://stackoverflow.com/questions/46396846/how-can-i-get-data-out-of-ondatachange-method */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MainAdapter extends RecyclerView.Adapter<Assists.blogdatahelper> {
    private static final String TAG = MainAdapter.class.getSimpleName();
    public static ArrayList<HashMap<String, String>> arrays1 = new ArrayList<>();

    Context context;
    Boolean picLike;

    public MainAdapter(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public Assists.blogdatahelper onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mainrecyclerview, parent, false);

        Assists.blogdatahelper vh = new Assists.blogdatahelper(row_itemView, context);
        return vh;

    }

    @Override
    public void onBindViewHolder(Assists.blogdatahelper holder, int position) {
        try {
            Log.e(TAG, "onBindViewHolder: " + arrays1);

            HashMap<String, String> searchModel = (HashMap<String, String>) Assists.userimages.get(holder.getAdapterPosition());


            holder.setinfo(searchModel, holder.getAdapterPosition());
            String picname = searchModel.get("picName");
            String pic = searchModel.get("pic");


            holder.setNumberLike(picname);
            holder.setLike(picname);

            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picLike = true;

                    DatabaseReference currentuser_db = holder.mdatabaseReference;

                    currentuser_db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (picLike) {

                                if (dataSnapshot.child(picname).child("Users").hasChild(Assists.user.getUid())) {
                                    holder.i = dataSnapshot.child(picname).child("Users").getChildrenCount();
//                                    Log.e("No. of likes", String.valueOf(holder.i));

                                    currentuser_db.child(picname).child("Users").child(Assists.user.getUid()).removeValue();
                                    picLike = false;
                                    if (holder.i > 0) {
                                        holder.i--;
                                        String like = String.valueOf(holder.i);
                                        currentuser_db.child(picname).child("Likes").setValue(like);
                                    } else {
                                        String like = String.valueOf(holder.i);
                                        currentuser_db.child(picname).child("Likes").setValue(like);
                                    }
                                } else {
                                    holder.i = dataSnapshot.child(picname).child("Users").getChildrenCount();
//                                    Log.e("No. of likes", String.valueOf(holder.i));
                                    holder.i++;
                                    String like = String.valueOf(holder.i);
                                    currentuser_db.child(picname).child("Users").child(Assists.user.getUid()).setValue(Assists.user.getDisplayName());
                                    currentuser_db.child(picname).child("Likes").setValue(like);
//                                    Log.e("Likes=====", like);
                                    picLike = false;



                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                if (dataSnapshot.hasChild(picname)) {
                                    holder.databaseReference.child(picname).removeValue();
                                    Assists.userimages.remove(position);
                                    Toast.makeText(context, "Post Delete", Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception e) {
                                Log.e(TAG, "onDataChange: " + e.getMessage());
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.sharepost(pic);

                }
            });
            holder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.comment(context, picname);

                }
            });

        } catch (Exception e) {

            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }


    @Override
    public int getItemCount() {

        return Assists.userimages.size();

    }
}


