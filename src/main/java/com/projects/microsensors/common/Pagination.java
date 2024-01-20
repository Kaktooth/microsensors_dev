package com.projects.microsensors.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Pagination<T> {

    private final List<T> items;

    public Pagination(List<T> items) {
        this.items = items;
    }

    public int getPageCount() {
        return (items.size() - 1) / AppConstraints.Pagination.PAGE_SIZE;
    }


    public List<T> getCurrentPageContent(int page, int size) {
        List<T> itemsSet = items;
        itemsSet = itemsSet.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return itemsSet;
    }

    public List<Integer> getPageNumbers(int pageCount) {
        return IntStream.rangeClosed(1, pageCount + 1)
                .boxed()
                .toList();
    }

    public Map<Integer, List<T>> toMap(Integer size, Integer pages) {
        Map<Integer, List<T>> itemsMap = new HashMap<>();
        for (Integer page : getPageNumbers(pages)) {
            itemsMap.put(page, getCurrentPageContent(page, size));
        }
        return itemsMap;
    }
}
