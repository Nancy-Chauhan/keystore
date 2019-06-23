package keystore.models;

public class KeyValue {
    private String key;
    private String value;

    public KeyValue(String Key, String Value) {
        this.key = Key;
        this.value = Value ;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}




