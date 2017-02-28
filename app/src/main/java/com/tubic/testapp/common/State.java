package com.tubic.testapp.common;

import com.tubic.testapp.data.Image;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class State extends HashMap<String, Object> {

    private static final String PAGINATION_OFFSET = "page";
    private static final String ITEM_OFFSET = "offset";
    private static final String ITEMS = "item";


    protected State() {
    }

    public static class Builder {

        protected final State state;

        public Builder() {
            this.state = new State();
        }

        protected Builder(State state) {
            this.state = state;
        }

        public Builder setPaginationOffset(int page) {
            state.put(PAGINATION_OFFSET, page);
            return this;
        }


        public Builder setItemOffset(int offset) {
            state.put(ITEM_OFFSET, offset);
            return this;
        }

        public Builder setItems(List<Image> images) {
            state.put(ITEMS, images);
            return this;
        }

        public State build() {
            return state;
        }
    }

    public int getPaginationOffset() {
        return (int) get(PAGINATION_OFFSET);
    }

    public int getItemOffset() {
        return (int) get(ITEM_OFFSET);
    }

    @SuppressWarnings("unchecked")
    public List<Image> getImages() {
        return (List<Image>) get(ITEMS);
    }
}
