package top.redstarmc.redstarprohibit.common.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder<X,Y> {
    private final Map<X, Y> map = new LinkedHashMap<>();
    @NotNull
    @Contract(value = "_, _ -> new",pure = true)
    public static <X, Y> MapBuilder<X, Y> of(Class<X> keyType, Class<Y> valueType){
        return new MapBuilder<>();
    }
    @NotNull
    public MapBuilder<X, Y> set(X key,Y value){
        map.put(key,value);
        return this;
    }
    @NotNull
    public Map<X, Y> toMap(){
        return new LinkedHashMap<>(map);
    }
    public Map<X, Y> build() {
        return map;
    }
}
