package keystore.services;

import keystore.models.KeyValue;

import java.util.Collection;
import java.util.Optional;

public interface KeyValueStoreService {
    public void add(KeyValue Value);

    public Collection<KeyValue> getAll();

    public Optional<KeyValue> get(String Key);

    public boolean delete(String Key);
}
