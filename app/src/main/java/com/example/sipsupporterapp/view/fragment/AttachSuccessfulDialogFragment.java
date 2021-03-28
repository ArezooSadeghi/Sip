package com.example.sipsupporterapp.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sipsupporterapp.R;
import com.example.sipsupporterapp.databinding.FragmentAttachSuccessfulDialogBinding;
import com.example.sipsupporterapp.viewmodel.RegisterProductViewModel;

public class AttachSuccessfulDialogFragment extends DialogFragment {
    private FragmentAttachSuccessfulDialogBinding binding;
    private RegisterProductViewModel viewModel;

    public static final String TAG = AttachSuccessfulDialogFragment.class.getSimpleName();

    public static AttachSuccessfulDialogFragment newInstance() {
        AttachSuccessfulDialogFragment fragment = new AttachSuccessfulDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterProductViewModel.class);

        viewModel.getNoConnection().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(s);
                fragment.show(getParentFragmentManager(), ErrorDialogFragment.TAG);
            }
        });

        viewModel.getTimeoutExceptionHappenSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                ErrorDialogFragment fragment = ErrorDialogFragment.newInstance("اتصال به اینترنت با خطا مواجه شد");
                fragment.show(getParentFragmentManager(), ErrorDialogFragment.TAG);
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(
                getContext()),
                R.layout.fragment_attach_successful_dialog,
                null,
                false);

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getDismissAttachSuccessfulDialogSingleLiveEvent().setValue(true);
                dismiss();
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(binding.getRoot())
                .create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        return dialog;
    }
}