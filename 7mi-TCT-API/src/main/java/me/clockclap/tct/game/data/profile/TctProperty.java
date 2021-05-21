package me.clockclap.tct.game.data.profile;

import java.util.ArrayList;
import java.util.List;

public class TctProperty<K, V> {

    private List<K> keys = new ArrayList<>();
    private List<V> entries = new ArrayList<>();

    public void add(K key, V value) {
        keys.add(key);
        entries.add(value);
    }

    public void removeKey(K key) {
        if(keys.contains(key)) {
            int index = keys.indexOf(key);
            keys.remove(index);
            entries.remove(index);
        }
    }

    public void removeEntry(V value) {
        if(entries.contains(value)) {
            int index = entries.indexOf(value);
            keys.remove(index);
            entries.remove(index);
        }
    }

    public void set(K key, V value) {
        if(keys.contains(key)) {
            int index = keys.indexOf(key);
            entries.set(index, value);
        }
    }

    public V get(K key) {
        if(keys.contains(key)) {
            int index = keys.indexOf(key);
            return entries.get(index);
        }
        return null;
    }

    public boolean contains(V value) {
        return entries.contains(value);
    }

    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    public K getKey(V value) {
        if(entries.contains(value)) {
            int index = entries.indexOf(value);
            return keys.get(index);
        }
        return null;
    }

    public List<K> getKeys() {
        return keys;
    }

    public List<V> getEntries() {
        return entries;
    }



}
