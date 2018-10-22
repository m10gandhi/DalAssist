package com.grp17.dalassist.Assist;
/* Referred following links
*   https://stackoverflow.com/questions/44595266/unable-to-display-recyclerview-contents-in-fragment-class
*   https://stackoverflow.com/questions/49578822/android-recyclerview-within-a-fragment-loading-data-from-a-content-provider-not
*   */
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grp17.dalassist.R;

import java.util.HashMap;


public class Comment_Adapter extends RecyclerView.Adapter<comment.CommentViewHolder> {
    private static final String TAG = Comment_Adapter.class.getSimpleName();

    @Override
    public comment.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commentrecycler, parent, false);

        comment.CommentViewHolder vh = new comment.CommentViewHolder(row_itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(comment.CommentViewHolder holder, int position) {

        try {

            HashMap<String, String> searchModel = comment.commentsre.get(position);
            String userName = searchModel.get("userid");
            String comment = searchModel.get("comments");

            holder.setinfo(userName, comment, position);


        } catch (Exception e) {

            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return comment.commentsre.size();
    }
}
