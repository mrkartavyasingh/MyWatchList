package com.android.mywatchlist.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.adapter.MyWatchListViewHolder;
import com.android.mywatchlist.databinding.FragmentMyWatchListBinding;
import com.android.mywatchlist.models.FireStoreDatabaseModel;
import com.android.mywatchlist.request.FirebaseAuthService;
import com.android.mywatchlist.request.FirebaseService;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class MyWatchListFragment extends Fragment {
    private final MyWatchListViewHolder MY_WATCH_LIST_RECYCLE_VIEW_HOLDER = new MyWatchListViewHolder();
    private final ArrayList<FireStoreDatabaseModel> fireStoreDatabaseModelList = new ArrayList<>();
    private RecyclerView my_watch_list_RecyclerView;
    private FragmentMyWatchListBinding fragmentMyWatchListBinding;

    public MyWatchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_my_watch_list, container, false);
        fragmentMyWatchListBinding = FragmentMyWatchListBinding.bind(view1);

        if (FirebaseAuthService.getFirebaseAuth().getCurrentUser() != null) {
            AutoCompleteTextView autoCompleteTextView = fragmentMyWatchListBinding.mwlChooseCategoryAutoCompleteTextView;
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.requireContext(), R.layout.mwl_choose_category_textview, getResources().getStringArray(R.array.mwl));
            autoCompleteTextView.setAdapter(arrayAdapter);
            my_watch_list_RecyclerView = fragmentMyWatchListBinding.mwlRecyclerview;

            autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                if (Objects.equals(arrayAdapter.getItem(i), getString(R.string.movies))) {
                    FirebaseService
                            .reference_mwl_movies(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                            .orderBy("timeStamp", Query.Direction.DESCENDING)
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    Log.w("my_watch_list", error);
                                    return;
                                }
                                fireStoreDatabaseModelList.clear();
                                if (value != null && !value.getDocuments().isEmpty()) {
                                    fragmentMyWatchListBinding.mwlItemCountTextView.setVisibility(View.VISIBLE);
                                    fragmentMyWatchListBinding.mwlNoResultAvailableTextView.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                        FireStoreDatabaseModel fireStoreDatabaseModel = gson.fromJson(gson.toJsonTree(documentSnapshot.getData()).getAsJsonObject(), FireStoreDatabaseModel.class);
                                        fireStoreDatabaseModelList.add(fireStoreDatabaseModel);
//                                        String movieCountStr = "Total Movies Watched: " + get_mwl_count(getString(R.string.movies));
//
//                                        fragmentMyWatchListBinding.mwlItemCountTextView.setText(movieCountStr);
                                    }
                                } else {
                                    fragmentMyWatchListBinding.mwlItemCountTextView.setVisibility(View.GONE);
                                    fragmentMyWatchListBinding.mwlNoResultAvailableTextView.setVisibility(View.VISIBLE);
                                }
                                setMyWatchList();
                            });
                } else if (Objects.equals(arrayAdapter.getItem(i), getString(R.string.tv_shows))) {
                    FirebaseService
                            .reference_mwl_tv_shows(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                            .orderBy("timeStamp", Query.Direction.DESCENDING)
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    Log.w("my_watch_list", error);
                                    return;
                                }
                                fireStoreDatabaseModelList.clear();
                                if (value != null && !value.getDocuments().isEmpty()) {
                                    fragmentMyWatchListBinding.mwlItemCountTextView.setVisibility(View.VISIBLE);
                                    fragmentMyWatchListBinding.mwlNoResultAvailableTextView.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                        FireStoreDatabaseModel fireStoreDatabaseModel = gson.fromJson(gson.toJsonTree(documentSnapshot.getData()).getAsJsonObject(), FireStoreDatabaseModel.class);
                                        fireStoreDatabaseModelList.add(fireStoreDatabaseModel);
                                        String tvShowCountStr = "Total Seasons Watched: " + get_mwl_count(getString(R.string.tv_shows));

                                        fragmentMyWatchListBinding.mwlItemCountTextView.setText(tvShowCountStr);
                                    }
                                } else {
                                    fragmentMyWatchListBinding.mwlItemCountTextView.setVisibility(View.GONE);
                                    fragmentMyWatchListBinding.mwlNoResultAvailableTextView.setVisibility(View.VISIBLE);
                                }
                                setMyWatchList();
                            });
                } else if (Objects.equals(arrayAdapter.getItem(i), getString(R.string.anime))) {
                    FirebaseService
                            .reference_mwl_anime(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                            .orderBy("timeStamp", Query.Direction.DESCENDING)
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    Log.w("my_watch_list", error);
                                    return;
                                }
                                fireStoreDatabaseModelList.clear();
                                if (value != null && !value.getDocuments().isEmpty()) {
                                    fragmentMyWatchListBinding.mwlItemCountTextView.setVisibility(View.VISIBLE);
                                    fragmentMyWatchListBinding.mwlNoResultAvailableTextView.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                        FireStoreDatabaseModel fireStoreDatabaseModel = gson.fromJson(gson.toJsonTree(documentSnapshot.getData()).getAsJsonObject(), FireStoreDatabaseModel.class);
                                        fireStoreDatabaseModelList.add(fireStoreDatabaseModel);

                                        String tvShowCountStr = "Total Seasons Watched: " + get_mwl_count(getString(R.string.anime));
                                        ;
                                        fragmentMyWatchListBinding.mwlItemCountTextView.setText(tvShowCountStr);
                                    }
                                } else {
                                    fragmentMyWatchListBinding.mwlItemCountTextView.setVisibility(View.GONE);
                                    fragmentMyWatchListBinding.mwlNoResultAvailableTextView.setVisibility(View.VISIBLE);
                                }
                                setMyWatchList();
                            });
                }
            });
            GlobalVariable.setMwl(fireStoreDatabaseModelList);
        } else {
            Toast.makeText(view1.getContext(), "Please Login!!!", Toast.LENGTH_LONG).show();
        }

        return view1;
    }

    private void setMyWatchList() {
        MY_WATCH_LIST_RECYCLE_VIEW_HOLDER.setFireStoreDatabaseModelList(fireStoreDatabaseModelList);
        my_watch_list_RecyclerView.setAdapter(MY_WATCH_LIST_RECYCLE_VIEW_HOLDER);
        my_watch_list_RecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private String get_mwl_count(String type) {
        final String[] cnt = {""};
        FirebaseService.reference_users
                .document(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                .addSnapshotListener(((value, error) -> {
                    if (error != null) {
                        Log.w("my_watch_list", error);
                        return;
                    }
//                    if (value != null) {
//                        if (Objects.equals(type, getString(R.string.movies))) {
//                            cnt[0] = value.getData().get("movies_count").toString();
//                        } else if (Objects.equals(type, getString(R.string.tv_shows))) {
//                            cnt[0] = Objects.requireNonNull(Objects.requireNonNull(value.getData()).get("tv_shows_count")).toString();
//                        } else if (Objects.equals(type, getString(R.string.anime))) {
//                            cnt[0] = Objects.requireNonNull(Objects.requireNonNull(value.getData()).get("anime_count")).toString();
//                        }
//                    }
                }));
        return cnt[0];
    }
}