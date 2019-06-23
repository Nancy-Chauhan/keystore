package keystore.services;

import keystore.models.KeyValue;

public class ChangeEvent {
    private ChangeEvent.Type type;

    private KeyValue entry;

    public ChangeEvent(Type type, KeyValue entry) {
        this.type = type;
        this.entry = entry;
    }

    public Type getType() {
        return type;
    }

    public KeyValue getEntry() {
        return entry;
    }

    public enum Type {
        GET,
        SET,
        DELETE
    }
}
