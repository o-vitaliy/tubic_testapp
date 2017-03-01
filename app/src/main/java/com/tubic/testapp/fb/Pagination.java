package com.tubic.testapp.fb;

import com.google.common.base.Strings;

public class Pagination {

    private String after;
    private boolean loading;

    private boolean isFirstCall = true;

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
        isFirstCall = false;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean canLoad() {
        return isFirstCall || !Strings.isNullOrEmpty(after);
    }
}
