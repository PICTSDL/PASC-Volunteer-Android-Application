package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<ModelPosts> PostList;
    public Context mCntxt;
    public Context context;
    public String nameOfInterested = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    PopupMenu.OnMenuItemClickListener p;

    public PostAdapter(ArrayList<ModelPosts> postList, PopupMenu.OnMenuItemClickListener p) {
        // mCntxt=context1;
        //context=context2;
        this.p = p;
        PostList = postList;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ModelPosts currentPost = PostList.get(position);


        holder.textView1.setText(currentPost.getTitle());
        holder.textView2.setText(currentPost.getOwnerOfPost());
        holder.textView3.setText(currentPost.getDate());
        holder.textView4.setText(currentPost.getTime());
        holder.textView5.setText(currentPost.getDescription());
        if (currentPost.ownerOfPost == FirebaseAuth.getInstance().getCurrentUser().toString()) {
            holder.textView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popup = new PopupMenu(mCntxt, holder.textView6);
                    popup.inflate(R.menu.option);


                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.EditPost: {

                                    DocumentSnapshot ds = null;
                                    String s = ds.getString("Description");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                    builder.setTitle("Edit Post:");
                                    final EditText et = new EditText(v.getContext());
                                    et.setText(s);
                                    builder.setView(et);
                                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String docid = db.collection("Post").document(currentPost.ownerOfPost).getId();
                                            String newdesc = et.getText().toString();
                                            DocumentReference dr = db.collection("Post").document(docid);
                                            dr.update("Description", newdesc);
                                            Toast.makeText(mCntxt, "Successfull", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            dialog.dismiss();
                                        }
                                    });
                                    Dialog dialog = builder.create();
                                    dialog.show();
                                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_blue_bright);
                                }
                                break;
                                case R.id.ViewList: {

                                    break;
                                }

                            }


                            return false;
                        }
                    });
                    popup.show();
                }
            });

        } else if (currentPost.ownerOfPost != FirebaseAuth.getInstance().getCurrentUser().toString()) {
            holder.textView6.setVisibility(View.INVISIBLE);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(final View v, int pos) {
                CheckBox chk = (CheckBox) v;
                if (chk.isChecked()) {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("name", nameOfInterested);

                    db.collection("Post").document().collection("Interested").document()
                            .set(map2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "name added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (!chk.isChecked()) {

                }
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_template, parent, false);
        return new PostAdapter.ViewHolder(v);
    }

    @Override

    public int getItemCount() {
        return PostList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        private final Context context;
        public TextView textView5;
        public TextView textView6;
        public CheckBox interestedCheckBox;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            interestedCheckBox = itemView.findViewById(R.id.interestedCB);
            textView1 = itemView.findViewById(R.id.textView_title);
            textView2 = itemView.findViewById(R.id.textView_owner);
            textView3 = itemView.findViewById(R.id.textView_date);
            textView4 = itemView.findViewById(R.id.textView_time);
            textView5 = itemView.findViewById(R.id.textView_description);
            textView6 = itemView.findViewById(R.id.textView_options);
            context = itemView.getContext();

            interestedCheckBox.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
