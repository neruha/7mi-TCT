package me.clockclap.tct.game.data.profile;

import java.util.List;
import java.util.Map;

public abstract class TctProfileModifier {

    TctProperty<String, Object> property;

    public TctProfileModifier(TctProperty<String, Object> defaults) {
        property = defaults;
    }

    public abstract void save();

    private void set(String k, Object v) {
        if(property.containsKey(k)) {
            property.set(k, v);
        }
    }

    public TctProfileModifier setBoolean(String key, boolean value) {
        set(key, value);
        return this;
    }

    public TctProfileModifier setInt(String key, int value) {
        set(key, value);
        return this;
    }

    public TctProfileModifier setFloat(String key, float value) {
        set(key, value);
        return this;
    }

    public TctProfileModifier setDouble(String key, double value) {
        set(key, value);
        return this;
    }

    public TctProfileModifier setLong(String key, long value) {
        set(key, value);
        return this;
    }

    public TctProfileModifier setShort(String key, short value) {
        set(key, value);
        return this;
    }

    public TctProfileModifier setString(String key, String value) {
        set(key, value);
        return this;
    }

}
