package com.grp17.dalassist.Assist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp17.dalassist.MainActivity;
import com.grp17.dalassist.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/** Referred android documentation **/

public class Assists extends AppCompatActivity {

    private static final String TAG = Assists.class.getSimpleName();
    public static ArrayList<HashMap<String, String>> userimages;
    public static FirebaseUser user;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assists);
        firebaseAuth = FirebaseAuth.getInstance();
        userimages = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Assists.this, uploadimage.class));
            }
        });


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().clear();
        LinearLayoutManager lm_recycle = new LinearLayoutManager(getApplicationContext());
//        lm_recycle.setReverseLayout(true);
        recyclerView.setLayoutManager(lm_recycle);
        mainAdapter = new MainAdapter(getApplicationContext());
        mainAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mainAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainAdapter.arrays1.clear();
        userimages.clear();
        mainAdapter.notifyDataSetChanged();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference reference = databaseReference.child("Assists").child("User Pictures");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    model data = snapshot.getValue(model.class);

                    assert data != null;
//                    Log.e(TAG, "onDataChange: " + data.getPicName() + data.getPic() + data.getUserName());

                    MainAdapter.arrays1.clear();
                    HashMap<String, String> map = new HashMap<>();

                    map.put("pic", data.getMedium());
                    map.put("picName", data.getPicName());
                    map.put("userName", data.getUserName());
                    map.put("user_id", data.getUser_id());
                    map.put("description", data.getDescription());

                    userimages.add(map);
                    MainAdapter.arrays1 = (ArrayList<HashMap<String, String>>) userimages.clone();
                    mainAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mainAdapter);

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
        mainAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mainAdapter);
    }


    /** Referred the following site for calling activities the activities concepts
     https://stackoverflow.com/questions/4169714/how-to-call-activity-from-a-menu-item-in-android **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void logoutUser() {
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();
        this.finish();
        startActivity(new Intent(this, MainActivity.class));

    }

    public static class blogdatahelper extends RecyclerView.ViewHolder {
        private final String TAG = blogdatahelper.class.getSimpleName();
        public DatabaseReference mdatabaseReference, databaseReference;
        View view;
        TextView username, descriptions, number;
        ImageView like, comment, share, delete, image;
        Long i;

        Context context;

        public blogdatahelper(View itemView, Context context) {
            super(itemView);
            this.context = context;
            view = itemView;

            username = view.findViewById(R.id.username);
            descriptions = view.findViewById(R.id._description);
            number = view.findViewById(R.id.likes);
            like = view.findViewById(R.id.like_btn);
            comment = view.findViewById(R.id.comment_btn);
            share = view.findViewById(R.id.share);
            delete = view.findViewById(R.id.delete);

            image = view.findViewById(R.id.mainimage);

            mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Assists").child("likes");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Assists").child("User Pictures");
        }


        public void setinfo(HashMap<String, String> arraylist, int position) {

            Log.e(TAG, "setinfo: " + arraylist);

            username.setText(arraylist.get("userName"));
            descriptions.setText(arraylist.get("description"));

            Picasso.get()
                    .load(arraylist.get("pic"))
                    .into(image);


        }

        public void setLike(String ImageName) {
            DatabaseReference currentuser_db = mdatabaseReference;

/* Referred the following website for Event Listeners concepts
            https://www.learnhowtoprogram.com/android/data-persistence/firebase-reading-data-and-event-listeners */

            currentuser_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(ImageName).child("Users").hasChild(Assists.user.getUid())) {

                        like.setImageResource(R.drawable.like2);

                    } else {
                        like.setImageResource(R.drawable.like);
                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        public void setNumberLike(String picname) {

            DatabaseReference currentuser_db = mdatabaseReference;

            currentuser_db.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Long i = dataSnapshot.child(picname).child("Users").getChildrenCount();

                    if (i == 1 || i == 0) {
                        number.setText(i + " friend liked your post");
                    } else if (i > 1) {
                        number.setText(i + " friends liked your post");
                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void sharepost(String picname) {

            Picasso.get().load(picname).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                    view.getContext().startActivity(Intent.createChooser(i, "Share Image"));
                }



                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });

        }

        public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
            Uri bmpUri = null;
            try {
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = Uri.fromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }

        public void comment(Context context, String picname) {

            Intent intent = new Intent(context, com.grp17.dalassist.Assist.comment.class);
            intent.putExtra("picname", picname);
            Log.e(TAG, "comment: " + picname);
            view.getContext().startActivity(intent);

        }


    }
}
