package keystore.services;

import keystore.models.KeyValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class KeyValueStoreServiceImpl implements KeyValueStoreService {
    private HashMap<String, KeyValue> valueMap;

    public KeyValueStoreServiceImpl() {
        valueMap = new HashMap<>();
    }

    @Override
    public void add(KeyValue value) {
        valueMap.put(value.getKey(), value);
    }

    @Override
    public Collection<KeyValue> getAll() {
        return valueMap.values();
    }

    @Override
    public Optional<KeyValue> get(String Key) {
        return Optional.ofNullable(valueMap.get(Key));
    }

    @Override
    public boolean delete(String Key) {
        return valueMap.remove(Key) != null;
    }
}

