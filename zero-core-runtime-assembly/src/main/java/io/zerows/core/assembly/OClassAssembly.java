package io.zerows.core.assembly;

import com.google.inject.Injector;
import io.horizon.runtime.Runner;
import io.vertx.up.eon.KMeta;
import io.vertx.up.util.Ut;
import io.zerows.core.assembly.uca.scan.*;
import io.zerows.core.metadata.store.classes.OClassCache;
import io.zerows.core.metadata.zdk.Inquirer;

import java.util.Set;

/**
 * 中间层，针对扫描出现的二次处理过程，不同环境双用模式
 * <pre><code>
 *     1. 全局环境
 *     2. OSGI 环境，会调用此处的服务来执行添加，OSGI 不走底层服务处理
 * </code></pre>
 *
 * @author lang : 2024-04-19
 */
public class OClassAssembly {

    private static Injector DI;

    /**
     * 全局环境专用方法
     * <pre><code>
     *     1. {@link io.vertx.up.annotations.Infusion} - {@link InquirerPlugin}
     *     2. {@link io.vertx.up.annotations.Queue} - {@link InquirerQueue}
     *     3. {@link io.vertx.up.annotations.EndPoint} - {@link InquirerEndPoint}
     *     4. {@link io.vertx.up.annotations.Worker} - {@link InquirerWorker}
     * </code></pre>
     */
    public static void configure() {
        // 读取全局类缓存
        final OClassCache processor = OClassCache.of();
        final long start = System.currentTimeMillis();
        Runner.run("meditate-class",
            // @Infusion
            () -> {
                final Inquirer<Set<Class<?>>> plugins = Ut.singleton(InquirerPlugin.class);
                processor.compile(KMeta.Typed.INFUSION, plugins::scan);
            },
            // @Queue
            () -> {
                final Inquirer<Set<Class<?>>> queues = Ut.singleton(InquirerQueue.class);
                processor.compile(KMeta.Typed.QUEUE, queues::scan);
            },
            // @EndPoint
            () -> {
                final Inquirer<Set<Class<?>>> endPoints = Ut.singleton(InquirerEndPoint.class);
                processor.compile(KMeta.Typed.ENDPOINT, endPoints::scan);
            },
            // @Worker
            () -> {
                final Inquirer<Set<Class<?>>> workers = Ut.singleton(InquirerWorker.class);
                processor.compile(KMeta.Typed.WORKER, workers::scan);
            }
        );
        long end = System.currentTimeMillis();
        Ut.Log.boot(OClassAssembly.class).info(" {0}ms / Zero Timer: Meditate Class Scanned!",
            end - start);


        // DI 初始化
        final Inquirer<Injector> guice = Ut.singleton(InquirerGuice.class);
        DI = guice.scan(processor.get());
        end = System.currentTimeMillis() - end;
        Ut.Log.boot(OClassAssembly.class).info(" {1}ms / Zero Zone DI Environment.... Size= {0}", String.valueOf(processor.get().size()), String.valueOf(end));
    }

    public static class Stored {
        public static synchronized Injector DI() {
            return DI;
        }

        public static synchronized Set<Class<?>> CLASS_ALL() {
            return OClassCache.of().get();
        }

        public static synchronized Set<Class<?>> CLASS_ENDPOINT() {
            return OClassCache.of().get(KMeta.Typed.ENDPOINT);
        }

        public static synchronized Set<Class<?>> CLASS_QUEUE() {
            return OClassCache.of().get(KMeta.Typed.QUEUE);
        }

        public static synchronized Set<Class<?>> CLASS_WORKER() {
            return OClassCache.of().get(KMeta.Typed.WORKER);
        }
    }
}
