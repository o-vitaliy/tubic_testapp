package com.tubic.testapp.fb;

import com.google.common.base.Strings;

class Pagination {

    private String after;
    private boolean loading;

    private boolean isFirstCall = true;

    String getAfter() {
        return after;
    }

    void setAfter(String after) {
        this.after = after;
        isFirstCall = false;
    }

    boolean isLoading() {
        return loading;
    }

    void setLoading(boolean loading) {
        this.loading = loading;
    }

    boolean canLoad() {
        return isFirstCall || !Strings.isNullOrEmpty(after);
    }
}
