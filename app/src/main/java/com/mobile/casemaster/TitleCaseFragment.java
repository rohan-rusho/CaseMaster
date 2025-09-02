package com.mobile.casemaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class TitleCaseFragment extends Fragment {

    private AppCompatEditText inputValue;
    private TextView textResultPlaceholder;
    private TextView textResult;
    private AppCompatButton btnConvert, btnCopy, btnShare;

    public TitleCaseFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titlecase, container, false);

        inputValue = view.findViewById(R.id.inputValue);
        textResultPlaceholder = view.findViewById(R.id.textResultPlaceholder);
        textResult = view.findViewById(R.id.textResult);
        btnConvert = view.findViewById(R.id.btnConvert);
        btnCopy = view.findViewById(R.id.btnCopy);
        btnShare = view.findViewById(R.id.btnShare);

        btnConvert.setOnClickListener(v -> {
            String input = inputValue.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(getContext(), "Please enter text", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder titleCase = new StringBuilder();
            for (String word : input.split("\\s+")) {
                if (!word.isEmpty()) {
                    titleCase.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.substring(1).toLowerCase())
                            .append(" ");
                }
            }

            textResult.setText(titleCase.toString().trim());
            textResultPlaceholder.setVisibility(View.GONE);
            btnCopy.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
        });

        btnCopy.setOnClickListener(v -> copyText());
        btnShare.setOnClickListener(v -> shareText());

        return view;
    }

    private void copyText() {
        String result = textResult.getText().toString();
        if (!TextUtils.isEmpty(result)) {
            ClipboardManager clipboard = (ClipboardManager) requireContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Text Result", result);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareText() {
        String result = textResult.getText().toString();
        if (!TextUtils.isEmpty(result)) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, result);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }
}
