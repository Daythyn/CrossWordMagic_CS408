package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutAdapter extends FragmentStateAdapter {

    public static final int NUM_TABS = 2;

    public TabLayoutAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = null;

        Bundle args = new Bundle();


        if(position == 0){
            fragment = new PuzzleFragment();
            args.putInt(PuzzleFragment.ARG_ID, position + 1);
            fragment.setArguments(args);
        }
        else if(position == 1){
            fragment = new ClueFragment();
            args.putInt(ClueFragment.ARG_ID, position + 1);
            fragment.setArguments(args);
        }

        return fragment;

    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }

}
