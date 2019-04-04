package com.juliedai.android.movietrailers.data.repository;

/**
 * @author  Julie Dai
 */

public interface Listener<Data> {

    void onSuccess(Data data);

    void onFailure();
}
