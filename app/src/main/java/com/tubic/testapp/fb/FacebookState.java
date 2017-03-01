package com.tubic.testapp.fb;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class FacebookState extends com.tubic.testapp.common.State {

    private static final String AFTER = "after";


    private FacebookState() {
    }

    public static class Builder extends com.tubic.testapp.common.State.Builder {


        protected Builder() {
            super(new FacebookState());
        }

        public Builder setAfter(String after) {
            state.put(AFTER, after);
            return this;
        }

    }

    public String getAfter() {
        return (String) get(AFTER);
    }

}
