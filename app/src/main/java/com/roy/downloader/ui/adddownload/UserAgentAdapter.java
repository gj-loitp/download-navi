package com.roy.downloader.ui.adddownload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.roy.downloader.R;
import com.roy.downloader.core.model.data.entity.UserAgent;

public class UserAgentAdapter extends ArrayAdapter<UserAgent> {
    private final DeleteListener deleteListener;

    public UserAgentAdapter(@NonNull Context context, DeleteListener deleteListener) {
        super(context, R.layout.v_spinner_user_agent_item);

        this.deleteListener = deleteListener;
    }

    @Override
    public View getDropDownView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.v_spinner_user_agent_item, parent, false);
        }

        UserAgent userAgent = getItem(position);
        if (userAgent != null) {
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(userAgent.userAgent);
        }

        /* Ignore read only system agents (e.g system user agent) and null */
        ImageView deleteButton = view.findViewById(R.id.delete);
        if (userAgent != null && !userAgent.readOnly)
            deleteButton.setOnClickListener((View v) -> {
                if (deleteListener != null)
                    deleteListener.onDelete(userAgent);
            });
        else
            deleteButton.setVisibility(View.GONE);

        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.v_spinner_user_agent_view, parent, false);
        }

        UserAgent userAgent = getItem(position);
        if (userAgent != null) {
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(userAgent.userAgent);
        }

        return view;
    }

    public interface DeleteListener {
        void onDelete(UserAgent userAgent);
    }
}
