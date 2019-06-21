package keystore.services;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class StorageServiceTest {

    @Test
    public void put() {
        String key = "k1";
        String value = "v1";

        keystore.services.StorageService storageService = new keystore.services.StorageService();
        storageService.put(key, value);
        Optional<String> maybeValue = storageService.get(key);

        assertTrue("value must be present", maybeValue.isPresent());
        assertEquals("retrieved value should be equal to value set", value, maybeValue.get());

        String value2 = "v2";
        storageService.put(key, value2);
        maybeValue = storageService.get(key);

        assertTrue("value must be present", maybeValue.isPresent());
        assertEquals("put should overwrite existing value", value2, maybeValue.get());
    }

    @Test
    public void get() {
        String key = "k1";
        String value = "v1";

        keystore.services.StorageService storageService = new keystore.services.StorageService();
        storageService.put(key, value);
        Optional<String> maybeValue = storageService.get(key);

        assertTrue("svalue must be present", maybeValue.isPresent());
        assertEquals("get should retrieve a value already set", value, maybeValue.get());

        maybeValue = storageService.get("nonexistent");
        assertFalse("nonexistent key must not be present", maybeValue.isPresent());
    }

    @Test
    public void delete() {
        String key = "k1";
        String value = "v1";

        keystore.services.StorageService storageService = new keystore.services.StorageService();
        storageService.put(key, value);

        assertTrue("must delete existing key", storageService.delete(key));
        assertFalse("deleted key must not exist", storageService.get(key).isPresent());

        assertFalse("must return false when trying to delete non-existent key", storageService.delete("nonexistent"));
    }
}