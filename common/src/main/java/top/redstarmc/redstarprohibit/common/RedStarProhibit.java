package top.redstarmc.redstarprohibit.common;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public interface RedStarProhibit {
    public static final AtomicReference<RedStarProhibit> instance = new AtomicReference<>();

    @SuppressWarnings("unchecked")
    public static <T extends RedStarProhibit> T getInstance() {
        return (T) instance.get();
    }

    public static void setInstance(RedStarProhibit plugin) {
        instance.set(plugin);
    }


    public default void onStart(){
        setInstance(this);
        loadManagers();
    }
    public default void onClose(){

    }

    public void loadManagers();

    public File getDataFolder();

}
