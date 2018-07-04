package ru.csu.nodb.data;

import java.util.SortedMap;
import java.util.TreeMap;

public class DataStorageImpl implements DataStorage {

    public static DataStorageImpl getInstance() {
        return ourInstance;
    }

    private DataStorageImpl() {}

    public String read(Integer index) {
        return storageMap.get(index);
    }

    public void write(String value) {
        Integer nextKey = storageMap.isEmpty() ? 1 : storageMap.lastKey() + 1;
        storageMap.put(nextKey, value);
    }

    private static DataStorageImpl ourInstance = new DataStorageImpl();
    private SortedMap<Integer, String> storageMap = new TreeMap<>();
}
