package keystore.services;

import keystore.models.KeyValue;

import java.util.Collection;
import java.util.Optional;

public interface KeyValueStoreService {
    void add(KeyValue Value);

    Collection<KeyValue> getAll();

    Optional<KeyValue> get(String Key);

    boolean delete(String Key);

    void addChangeListener(ChangeListener listener);

    void removeChangeListener(ChangeListener listener);
}
