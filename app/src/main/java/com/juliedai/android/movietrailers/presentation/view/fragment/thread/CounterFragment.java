package com.juliedai.android.movietrailers.presentation.view.fragment.thread;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.presentation.view.activity.threads.IAsyncTaskEvents;
import com.juliedai.android.movietrailers.presentation.presenter.CounterFragmentPresenterImpl;

/**
 * @author  Julie Dai
 */

public class CounterFragment extends Fragment implements View.OnClickListener, Contract.View {

    public static final String MSG_CONTENT = "msg-content";
    private Button btnAsyncCreate;
    private Button btnAsyncStart;
    private Button btnAsyncCancel;
    private TextView txtvContent;
    private IAsyncTaskEvents iAsyncTaskEvents;
    private String fragContent;
    private Contract.Presenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity hostActivity = getActivity();

        if (hostActivity != null && hostActivity instanceof IAsyncTaskEvents) {
            iAsyncTaskEvents = (IAsyncTaskEvents)hostActivity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_counter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        btnAsyncCreate = view.findViewById(R.id.btnCreate);
        if(btnAsyncCreate != null){
            btnAsyncCreate.setOnClickListener(this);
        }

        btnAsyncStart = view.findViewById(R.id.btnStart);
        if(btnAsyncStart != null){
            btnAsyncStart.setOnClickListener(this);
        }

        btnAsyncCancel = view.findViewById(R.id.btnCancel);
        if(btnAsyncCancel != null){
            btnAsyncCancel.setOnClickListener(this);
        }

        txtvContent = view.findViewById(R.id.txtvFullScreenCounterStatus);
        if(txtvContent != null){
            Bundle data = this.getArguments();
            fragContent = "";
            if (data != null) {
                fragContent = data.getString(MSG_CONTENT);
            }
        }

        presenter = new CounterFragmentPresenterImpl(this, fragContent);
    }


    @Override
    public void onClick(View v) {
        if(!isAdded() || iAsyncTaskEvents == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.btnCreate:
                iAsyncTaskEvents.createAsyncTask();
                break;
            case R.id.btnStart:
                iAsyncTaskEvents.startAsyncTask();
                break;
            case R.id.btnCancel:
                iAsyncTaskEvents.cancelAsyncTask();
                break;
        }
    }

    @Override
    public void setTaskStatus(String content) {
        if (content != null && !TextUtils.isEmpty(content)) {
            txtvContent.setText(content);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iAsyncTaskEvents = null;
    }

    public void updateFragmentCounterStatus(String text) {
        if(txtvContent != null) {
            txtvContent.setText(text);
        }
    }
}
