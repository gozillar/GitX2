package com.example.android.gitx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.gitx.R;
import com.example.android.gitx.UserDetails;
import com.example.android.gitx.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @g_okeke on 10/03/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

    private List<User> userList;
    private static final String TAG = UserAdapter.class.getSimpleName();

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.Holder holder, int position) {

        final User currentUser = userList.get(position);
        holder.uLogin.setText(currentUser.getLogin());
        Glide.with(holder.itemView.getContext())
                .load(currentUser.getAvatarUrl())
                .into(holder.uPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, UserDetails.class);
                intent.putExtra("user", currentUser);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(User user) {
        Log.d(TAG, user.getAvatarUrl());
        userList.add(user);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView uPhoto;
        private TextView uLogin;

        public Holder(View itemView) {
            super(itemView);
            uPhoto = (ImageView) itemView.findViewById(R.id.userPhoto);
            uLogin = (TextView) itemView.findViewById(R.id.user_gitname);
        }

    }



}
