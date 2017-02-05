package com.example.jeffreynyauke.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeffreynyauke.myapplication.R;
import com.example.jeffreynyauke.myapplication.adapters.MusicAdapter;
import com.example.jeffreynyauke.myapplication.app.Config;
import com.example.jeffreynyauke.myapplication.models.Music;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jeffrey Nyauke on 1/31/2017.
 */

public class MusicFragment extends Fragment {

    @BindView(R.id.fragment_recycler)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private MusicAdapter musicAdapter;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music, container, false);

        unbinder = ButterKnife.bind(this, view);

        // Set recyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);

        // Initialize adapter
        List<Music> emptyMessageChat=new ArrayList<Music>();
        musicAdapter=new MusicAdapter(getActivity(), emptyMessageChat);

        // Set adapter to recyclerView
        recyclerView.setAdapter(musicAdapter);

        //Initialize firebase for these posts
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(Config.FIREBASE_MUSIC).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                Music music = dataSnapshot.getValue(Music.class);
                musicAdapter.refillAdapter(music);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  view;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}