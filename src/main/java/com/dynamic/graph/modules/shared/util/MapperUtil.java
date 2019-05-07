package com.dynamic.graph.modules.shared.util;

import org.hibernate.collection.spi.PersistentCollection;

public class MapperUtil {
    private MapperUtil() {
    }

    public static boolean wasInitialized(Object ob) {
        if (!(ob instanceof PersistentCollection)) {
            return true;
        }

        PersistentCollection pc = (PersistentCollection) ob;
        return pc.wasInitialized();
    }
}
