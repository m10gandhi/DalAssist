package com.grp17.dalassist.Assist;

/* Referred Android development documentation, learn how to program and stack over flow cites
                for calling activities and event listeners */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class comment extends AppCompatActivity {

    private static final String TAG = comment.class.getSimpleName();
    public static ArrayList<HashMap<String, String>> commentsre;
    RecyclerView recyclerView;
    String name;
    EditText user_comment;
    ImageView send;
    FirebaseUser firebaseUser;
    String user_id;
    String imageName;
    Context context;
    DatabaseReference databaseReference, databaseReference4, reference;
    String s;
    Comment_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        s = getIntent().getStringExtra("picname");
        Log.e(TAG, "onCreate: " + s);

        commentsre = new ArrayList<>();
        user_comment = findViewById(R.id.usercomment);
        recyclerView = findViewById(R.id.recycler_comment);
        send = findViewById(R.id.post);

        adapter = new Comment_Adapter();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        try {
            assert firebaseUser != null;
            user_id = firebaseUser.getUid();
        } catch (NullPointerException e) {

            e.getMessage();
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm_recycle = new LinearLayoutManager(context);
//        lm_recycle.setReverseLayout(true);
//        lm_recycle.setStackFromEnd(true);
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setLayoutManager(lm_recycle);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Assists").child("comments").child(s).child("users");
        reference = FirebaseDatabase.getInstance().getReference().child("Assists").child("users");
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        commentsre.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentsre.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.e(TAG, "onDataChange: " + snapshot.getKey());

                    HashMap<String, String> map = new HashMap<>();
                    commentmodel data = snapshot.getValue(commentmodel.class);

                    assert data != null;
//                    Log.e(TAG, "onDataChange: " + data.getComment() + " " + data.getUser_id());
                    map.put("comments", data.getComment());
                    map.put("userid", data.getUser_id());

                    commentsre.add(map);
                    Log.e(TAG, "onDataChange:list " + commentsre);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        commentsre.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentsre.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.e(TAG, "onDataChange: " + snapshot.getKey());

                    HashMap<String, String> map = new HashMap<>();
                    commentmodel data = snapshot.getValue(commentmodel.class);

                    assert data != null;
//                    Log.e(TAG, "onDataChange:commentsre " + data.getComment() + " " + data.getUser_id());
                    map.put("comments", data.getComment());
                    map.put("userid", data.getUser_id());

                    commentsre.add(map);
//                    Log.e(TAG, "onDataChange: " + commentsre);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    public void post(View view) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        imageName = user_comment.getText().toString();

        try {
            if (!user_comment.getText().toString().equals("")) {

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                int milliSeconds = c.get(Calendar.MILLISECOND);

                String commentname = day + "-" + month + "-" + year + "-" + hour + ":" + minutes + ":" + milliSeconds + "" + user_id;

                databaseReference4 = databaseReference.child(commentname);
                databaseReference4.child("comment").setValue(imageName);

                assert firebaseUser != null;
                databaseReference4.child("user_id").setValue(firebaseUser.getUid());
                Log.e(TAG, "post: " + imageName + " " + firebaseUser.getUid());
                user_comment.setText("");


            } else {
                user_comment.setError("Empty");
            }
        } catch (Exception e) {
            Log.e(TAG, "onClick: " + e.getMessage());
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public static String TAG = "Comment TAG";
        TextView getname, user_comment;
        View mView;
        String userid;
        DatabaseReference databaseReference, database, reference;


        public CommentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            database = FirebaseDatabase.getInstance().getReference()
                    .child("Users");

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Comments");
            getname = mView.findViewById(R.id.comment_user_name);
            user_comment = mView.findViewById(R.id.user_comment);
            reference = FirebaseDatabase.getInstance().getReference().child("Assists").child("users");


        }


        public void setinfo(String userName, String comment1, int adapterPosition) {

            Log.e(TAG, "setinfo: " + comment1 + " " + userName);
            user_comment.setText(comment1);



            reference.child(userName).child("userinfo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    commentmodel1 data = dataSnapshot.getValue(commentmodel1.class);
                    assert data != null;
                    Log.e(TAG, "onDataChange: "+data.getUsername() );
                    getname.setText(data.getUsername());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


}
