package com.tubic.testapp.google;

import com.tubic.testapp.common.State;

/**
 * Created by ovitaliy on 28.02.2017.
 */

class GoogleSearchState extends State {

    private static final String QUERY = "query";

    String getQuery() {
        return (String) get(QUERY);
    }

    static class Builder extends State.Builder {

        Builder() {
            super(new GoogleSearchState());
        }

        State.Builder setQuery(String query) {
            state.put(QUERY, query);
            return this;
        }
    }
}
