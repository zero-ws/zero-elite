package io.zerows.core.metadata.uca.scanner;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import io.vertx.up.fn.Fn;
import io.vertx.up.util.Ut;
import org.junit.runner.RunWith;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author lang : 2024-04-17
 */
@SuppressWarnings("all")
class ClassScannerCommon implements ClassScanner {

    @Override
    public Set<Class<?>> scan(final ClassLoader loader) {
        // 保证线程安全
        final Set<Class<?>> classSet = Collections.synchronizedSet(new HashSet<>());

        Fn.jvmAt(() -> {
            final ClassPath cp = ClassPath.from(loader);
            final ImmutableSet<ClassPath.ClassInfo> set = cp.getTopLevelClasses();
            final ConcurrentMap<String, Set<String>> packageMap = new ConcurrentHashMap<>();
            // 性能提高一倍，并行流处理更合理，暂时没发现明显问题
            Ut.Log.metadata(getClass()).info("Ignored package size: {0}, please check whether contains yours.",
                String.valueOf(ClassFilter.SKIP_PACKAGE.length));
            set.parallelStream().forEach(cls -> {
                final String packageName = cls.getPackageName();
                final boolean skip = Arrays.stream(ClassFilter.SKIP_PACKAGE).anyMatch(packageName::startsWith);
                if (!skip) {
                    try {
                        classSet.add(Thread.currentThread().getContextClassLoader().loadClass(cls.getName()));
                    } catch (final Throwable ex) {

                    }
                }
            });
        });

        // 过滤合法的 Class
        return classSet.stream()
            .filter(this::isValid)
            .collect(Collectors.toSet());
    }

    private boolean isValidMember(final Class<?> type) {
        try {
            // Fix issue of Guice
            // java.lang.NoClassDefFoundError: camundajar/impl/scala/reflect/macros/blackbox/Context
            type.getDeclaredMethods();
            type.getDeclaredFields();
            return true;
        } catch (NoClassDefFoundError ex) {
            return false;
        }
    }

    private boolean isValid(final Class<?> type) {
        return !type.isAnonymousClass()                             // Ko Anonymous
            && !type.isAnnotation()                                 // Ko Annotation
            && !type.isEnum()                                       // Ko Enum
            && Modifier.isPublic(type.getModifiers())               // Ko non-public
            // Ko abstract class, because interface is abstract, single condition is invalid
            && !(Modifier.isAbstract(type.getModifiers()) && !type.isInterface())
            && !Modifier.isStatic(type.getModifiers())              // Ko Static
            && !Throwable.class.isAssignableFrom(type)              // Ko Exception
            && !type.isAnnotationPresent(RunWith.class)             // Ko Test Class
            && isValidMember(type);                          // Ko `Method/Field`
    }
}
