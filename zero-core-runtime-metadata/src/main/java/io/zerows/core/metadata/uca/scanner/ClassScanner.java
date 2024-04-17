package io.zerows.core.metadata.uca.scanner;

import io.horizon.uca.cache.Cc;
import io.vertx.up.annotations.metadata.Semantics;

import java.util.Set;

/**
 * @author lang : 2024-04-17
 */
@Semantics
public interface ClassScanner {

    Cc<String, ClassScanner> CCT_SCANNER = Cc.openThread();

    static ClassScanner of() {
        return CCT_SCANNER.pick(ClassScannerCommon::new, ClassScannerCommon.class.getName());
    }

    Set<Class<?>> scan(ClassLoader loader);
}
