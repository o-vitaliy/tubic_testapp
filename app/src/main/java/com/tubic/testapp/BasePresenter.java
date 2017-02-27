package com.tubic.testapp;

import com.tubic.testapp.utils.EspressoIdlingResource;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ovi on 06/02/17.
 */

public abstract class BasePresenter {

    protected abstract void start();
    protected abstract void stop();

    protected final <O> Observable<O> config(Observable<O> oObservable) {
        oObservable = oObservable/*.retryWhen(new RxRetryWithDelay())
                .doOnError(new RxErrorAction(getView().getContext()))*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::onExecutionStarted)
                .doOnTerminate(this::onExecutionCompleted)
                .doOnError(this::onError)

        ;

        return oObservable;
    }


    private void onError(Throwable error) {
        if (BuildConfig.DEBUG) {
            String tag = getClass().getSimpleName();
            if (tag.length() > 25)
                tag = tag.substring(0, 25);
            // Log.e(tag, "error in the presenter", error);
        }
    }

    private void onExecutionStarted() {
        EspressoIdlingResource.increment(); // App is busy until further notice
    }


    private void onExecutionCompleted() {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement(); // Set app as idle.
        }
    }


}
