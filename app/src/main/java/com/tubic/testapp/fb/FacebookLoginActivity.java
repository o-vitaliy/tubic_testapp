package com.tubic.testapp.fb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Uran on 1/5/2017.
 */

public class FacebookLoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logOut();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Picker.sSingleton.picked(loginResult.getAccessToken().getToken());
                finish();

            }

            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Picker.sSingleton != null)
            Picker.sSingleton.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static class Picker {
        private static Picker sSingleton;

        public static Picker getInstance(Context context) {
            if (sSingleton == null) {
                sSingleton = new Picker(context.getApplicationContext());
            }
            return sSingleton;
        }

        private Context mContext;
        private Subscriber<? super String> mOnSubscribe;

        Picker(Context context) {
            mContext = context;
        }

        public Observable<String> login() {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    mOnSubscribe = subscriber;

                    Intent intent = new Intent(mContext, FacebookLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }

        void picked(String facebookToken) {
            mOnSubscribe.onNext(facebookToken);

            destroy();
        }

        private void destroy() {
            mOnSubscribe.onCompleted();
            sSingleton = null;
        }

    }

}
