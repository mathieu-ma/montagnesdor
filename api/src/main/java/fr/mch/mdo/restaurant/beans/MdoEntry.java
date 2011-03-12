package fr.mch.mdo.restaurant.beans;

import java.util.Map.Entry;

public class MdoEntry<K, V> implements Entry<K, V> {

    private K key;
    private V value;

    /**
     * @param key
     *            the key to set
     */
    public MdoEntry(K key, V value) {
	this.key = key;
	this.value = value;
    }

    @Override
    public K getKey() {
	return key;
    }

    @Override
    public V getValue() {
	return value;
    }

    @Override
    public V setValue(V value) {
	return value;
    }
}
