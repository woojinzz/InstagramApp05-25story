package com.koddev.instagramtest.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koddev.instagramtest.Dm_Chatlist;
import com.koddev.instagramtest.Dm_UserAdapter;
import com.koddev.instagramtest.Dm_UserU;
import com.koddev.instagramtest.R;

import java.util.ArrayList;
import java.util.List;

public class Dm_ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private Dm_UserAdapter userAdapter;
    private List<Dm_UserU> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<Dm_Chatlist> usersLists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dm_fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersLists = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dm_Chatlist chatlist = snapshot.getValue(Dm_Chatlist.class);
                    usersLists.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void chatList() {
        mUsers = new ArrayList<Dm_UserU>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Dm_UserU user = snapshot.getValue(Dm_UserU.class);
                    for (Dm_Chatlist chatlist : usersLists){
                        if (user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new Dm_UserAdapter(getContext(), mUsers,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
