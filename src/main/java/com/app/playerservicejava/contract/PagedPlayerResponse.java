package com.app.playerservicejava.contract;

import java.util.List;

public class PagedPlayerResponse {
    private List<PlayerResponse> data;
    private int page;
    private int size;
    private long totalElements;

    public PagedPlayerResponse(List<PlayerResponse> data, int page, int size, long totalElements) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
    }


    public List<PlayerResponse> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
