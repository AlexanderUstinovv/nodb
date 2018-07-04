package ru.csu.nodb.data;

public interface DataStorage {
    String read(Integer index);
    void write(String value);
}

