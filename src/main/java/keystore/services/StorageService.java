package keystore.services;

import java.util.HashMap;
import java.util.Optional;

public class StorageService {

    private final HashMap<String, String> storage;

    public StorageService() {
        storage = new HashMap<>();
    }

    public void put(String key, String value) {
        storage.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(storage.get(key));
    }

    public boolean delete(String key) {
        return storage.remove(key) != null;
    }
}