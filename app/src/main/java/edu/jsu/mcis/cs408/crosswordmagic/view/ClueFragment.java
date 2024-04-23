package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;

public class ClueFragment extends Fragment implements AbstractView{

    public static final String ARG_ID = "id";

    private edu.jsu.mcis.cs408.crosswordmagic.databinding.FragmentClueBinding binding;
    CrosswordMagicController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = edu.jsu.mcis.cs408.crosswordmagic.databinding.FragmentClueBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /* get controller, register Fragment as a View */
        this.controller = ((MainActivity)getContext()).getController();
        controller.addView(this);

        controller.getCluesAcross();
        controller.getCluesDown();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {


        String name = evt.getPropertyName();
        Object value = evt.getNewValue();

        if (name.equals(CrosswordMagicController.CLUES_ACROSS)) {

                binding.aContainer.setText(value.toString());

        }

        if (name.equals(CrosswordMagicController.CLUES_DOWN)) {

            binding.dContainer.setText(value.toString());

        }
    }


}
