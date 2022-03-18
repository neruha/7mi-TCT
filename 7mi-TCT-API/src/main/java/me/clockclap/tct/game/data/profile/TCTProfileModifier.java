package me.clockclap.tct.game.data.profile;

public abstract class TCTProfileModifier {

    TCTProperty<String, Object> property;

    public TCTProfileModifier(TCTProperty<String, Object> defaults) {
        property = defaults;
    }

    public abstract void save();

    private void set(String k, Object v) {
        if(property.containsKey(k)) {
            property.set(k, v);
        }
    }

    public TCTProfileModifier setBoolean(String key, boolean value) {
        set(key, value);
        return this;
    }

    public TCTProfileModifier setInt(String key, int value) {
        set(key, value);
        return this;
    }

    public TCTProfileModifier setFloat(String key, float value) {
        set(key, value);
        return this;
    }

    public TCTProfileModifier setDouble(String key, double value) {
        set(key, value);
        return this;
    }

    public TCTProfileModifier setLong(String key, long value) {
        set(key, value);
        return this;
    }

    public TCTProfileModifier setShort(String key, short value) {
        set(key, value);
        return this;
    }

    public TCTProfileModifier setString(String key, String value) {
        set(key, value);
        return this;
    }

}
