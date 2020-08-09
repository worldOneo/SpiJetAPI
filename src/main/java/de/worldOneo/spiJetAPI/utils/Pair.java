package de.worldOneo.spiJetAPI.utils;

import lombok.Data;

@Data
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
