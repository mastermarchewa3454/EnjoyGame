package com.enjoy.mathero.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class CustomList<T> extends AbstractList<T> {
    private List<T> wrapperList = new ArrayList<>();

    @Override
    public boolean add(T t) {
        return wrapperList.add(t);
    }

    @Override
    public T get(int index) {
        return wrapperList.get(index);
    }

    @Override
    public int size() {
        return wrapperList.size();
    }

    public List<T> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<T> wrapperList) {
        this.wrapperList = wrapperList;
    }

    @JsonIgnore
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
