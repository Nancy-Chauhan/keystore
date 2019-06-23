package keystore.services;

import keystore.models.KeyValue;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class KeyValueStoreServiceImplTest {

    @Test
    public void put() {
        String key = "k1";
        String value = "v1";

        KeyValue keyValue = new KeyValue(key, value);
        KeyValueStoreServiceImpl keyValueStoreServiceImpl = new KeyValueStoreServiceImpl();
        keyValueStoreServiceImpl.add(keyValue);

        Optional<KeyValue> maybeValue = keyValueStoreServiceImpl.get(key);

        assertTrue("value must be present", maybeValue.isPresent());
        assertEquals("retrieved value should be equal to value set", keyValue, maybeValue.get());

        String value2 = "v2";
        KeyValue keyValue2 = new KeyValue(key, value2);
        keyValueStoreServiceImpl.add(keyValue2);
        maybeValue = keyValueStoreServiceImpl.get(key);

        assertTrue("value must be present", maybeValue.isPresent());
        assertEquals("put should overwrite existing value", keyValue2, maybeValue.get());
    }

    @Test
    public void get() {
        String key = "k1";
        String value = "v1";

        KeyValue keyValue = new KeyValue(key, value);
        keystore.services.KeyValueStoreServiceImpl keyValueStoreServiceImpl = new keystore.services.KeyValueStoreServiceImpl();
        keyValueStoreServiceImpl.add(keyValue);
        Optional<KeyValue> maybeValue = keyValueStoreServiceImpl.get(key);

        assertTrue("svalue must be present", maybeValue.isPresent());
        assertEquals("get should retrieve a value already set", keyValue, maybeValue.get());

        maybeValue = keyValueStoreServiceImpl.get("nonexistent");
        assertFalse("nonexistent key must not be present", maybeValue.isPresent());
    }

    @Test
    public void delete() {
        String key = "k1";
        String value = "v1";

        KeyValue keyValue = new KeyValue(key, value);
        keystore.services.KeyValueStoreServiceImpl keyValueStoreServiceImpl = new keystore.services.KeyValueStoreServiceImpl();
        keyValueStoreServiceImpl.add(keyValue);

        assertTrue("must delete existing key", keyValueStoreServiceImpl.delete(key));
        assertFalse("deleted key must not exist", keyValueStoreServiceImpl.get(key).isPresent());

        assertFalse("must return false when trying to delete non-existent key", keyValueStoreServiceImpl.delete("nonexistent"));
    }
}