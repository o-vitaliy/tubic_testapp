package com.tubic.testapp.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Uran on 1/16/2017.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJavaCallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJavaCallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt(Call<R> call) {
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(new Func1<Throwable, Observable>() {
                @Override
                public Observable call(Throwable throwable) {
                    return Observable.error(asRetrofitException(throwable));
                }
            });
        }

        private ApiException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();

                Converter<ResponseBody, Error> converter = retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                Error error = null;
                try {
                    error = converter.convert(response.errorBody());
                } catch (IOException e) {
                    throw new RuntimeException("can't convert api error to error object");
                }
                return ApiException.httpError(response.raw().request().url().toString(), error.code, error.message);
            }
            // A network error happened
            if (throwable instanceof IOException) {
                return ApiException.networkError((IOException) throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error

            return ApiException.unexpectedError(throwable);
        }
    }

    private class Error {
        int code;
        String message;
    }
}
