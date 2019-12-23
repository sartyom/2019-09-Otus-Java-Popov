package ru.otus.hw11.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<Integer, Integer> cache = new MyCache<>();
        HwCacheListener<Integer, Integer> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListener(listener);
        HwCacheListener<Integer, Integer> listenerWithException = new HwCacheListener<>() {
            @Override
            public void notify(Integer key, Integer value, HwCacheAction action) {
                throw new RuntimeException("something wrong");
            }

            @Override
            public String toString() {
                return "Listener which throws exception.";
            }
        };
        cache.addListener(listenerWithException);
        cache.put(1, 1);

        logger.info("getValue:{}", cache.get(1));
        cache.remove(1);
        cache.removeListener(listener);

    }
}
