package keystore.services;

import keystore.models.KeyValue;

import java.util.*;

public class KeyValueStoreServiceImpl implements KeyValueStoreService {
    private HashMap<String, KeyValue> valueMap;

    private List<ChangeListener> listeners;

    public KeyValueStoreServiceImpl() {
        valueMap = new HashMap<>();
        listeners = new ArrayList<>();
    }

    @Override
    public void add(KeyValue value) {
        valueMap.put(value.getKey(), value);
        fireChangeEvent(new ChangeEvent(ChangeEvent.Type.SET, value));
    }

    @Override
    public Collection<KeyValue> getAll() {
        return valueMap.values();
    }

    @Override
    public Optional<KeyValue> get(String Key) {
        KeyValue keyValue = valueMap.get(Key);
        fireChangeEvent(new ChangeEvent(ChangeEvent.Type.GET, keyValue));
        return Optional.ofNullable(keyValue);
    }

    @Override
    public boolean delete(String Key) {
        KeyValue keyValue = valueMap.get(Key);
        fireChangeEvent(new ChangeEvent(ChangeEvent.Type.DELETE, keyValue));
        return valueMap.remove(Key) != null;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireChangeEvent(ChangeEvent e) {
        for (ChangeListener listener : listeners) {
            listener.handle(e);
        }
    }
}

