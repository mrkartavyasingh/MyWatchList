package com.android.mywatchlist.fragments.homepage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.adapter.GenreRecyclerViewHolder;
import com.android.mywatchlist.databinding.FragmentAdvanceSearchBinding;
import com.android.mywatchlist.models.submodels.GenreModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Objects;

public class AdvanceSearchFragment extends DialogFragment {
    private OnCallbackListener onCallbackListener;
    public interface OnCallbackListener {
        void onActionClick(ArrayList<GenreModel> selectedGenreModelArrayList);
    }
    private final GenreRecyclerViewHolder GENRE_RECYCLE_VIEW_HOLDER = new GenreRecyclerViewHolder(true);
    View view;
    FragmentAdvanceSearchBinding fragmentAdvanceSearchBinding;

    public static AdvanceSearchFragment newInstance() {
        return new AdvanceSearchFragment();
    }

    public void setOnCallbackListener(OnCallbackListener onCallbackListener) {
        this.onCallbackListener = onCallbackListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AdvanceSearchDialogBoxTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_advance_search, container, false);
        fragmentAdvanceSearchBinding = FragmentAdvanceSearchBinding.bind(view);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        layoutManager.setFlexWrap(FlexWrap.WRAP);

        assert getArguments() != null;
        if (Objects.equals(getArguments().getString("item_type"), getString(R.string.movies))) {
            GENRE_RECYCLE_VIEW_HOLDER.setGenreModelList(GlobalVariable.getMovieGenreModel());
        } else if (Objects.equals(getArguments().getString("it" +
                "+em_type"), getString(R.string.tv_shows))) {
            GENRE_RECYCLE_VIEW_HOLDER.setGenreModelList(GlobalVariable.getTvshowGenreModel());
        } else if ((Objects.equals(getArguments().getString("item_type"), getString(R.string.my_watch_list)))) {
            if (GlobalVariable.getMwl().get(0).getTitle() != null) {
                GENRE_RECYCLE_VIEW_HOLDER.setGenreModelList(GlobalVariable.getMovieGenreModel());
            } else if (GlobalVariable.getMwl().get(0).getName() != null) {
                GENRE_RECYCLE_VIEW_HOLDER.setGenreModelList(GlobalVariable.getTvshowGenreModel());
            }
        }
        fragmentAdvanceSearchBinding.advanceSearchRecyclerView.setAdapter(GENRE_RECYCLE_VIEW_HOLDER);
        fragmentAdvanceSearchBinding.advanceSearchRecyclerView.setLayoutManager(layoutManager);

        fragmentAdvanceSearchBinding.advanceSearchSubmitButton.setOnClickListener(view -> {
            ArrayList<GenreModel> genreModelArrayList = new ArrayList<>();
            for (GenreModel genreModel : GENRE_RECYCLE_VIEW_HOLDER.getGenreModelList()) {
                if (genreModel.isSelected()) {
                    genreModelArrayList.add(genreModel);
                }
            }
            Log.d("adv_search_fragment", genreModelArrayList.toString());
            onCallbackListener.onActionClick(genreModelArrayList);
            dismiss();
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onCallbackListener = (OnCallbackListener) getActivity();
    }
}
