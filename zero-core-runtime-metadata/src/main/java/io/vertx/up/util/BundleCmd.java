package io.vertx.up.util;

import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * @author lang : 2024-04-22
 */
class BundleCmd {

    static void bindCommand(final BundleContext context,
                            final Object... args) {
        String commandName = null;
        Class<?> commandCls = null;

        for (int idx = 0; idx < args.length; idx++) {
            if (idx % 2 == 0) {
                commandName = (String) args[idx];
            } else {
                commandCls = (Class<?>) args[idx];
            }
            if (Ut.isNotNull(commandName, commandCls)) {
                final Hashtable<String, Object> props = configureCommand(commandName);
                assert commandCls != null;
                context.registerService(commandCls.getName(), Ut.instance(commandCls, context), props);

                commandName = null;
                commandCls = null;
            }
        }
    }

    private static Hashtable<String, Object> configureCommand(final String scope,
                                                              final String function) {
        final Hashtable<String, Object> props = new Hashtable<>();
        props.put("osgi.command.scope", scope);
        props.put("osgi.command.function", new String[]{function});
        return props;
    }


    private static Hashtable<String, Object> configureCommand(final String function) {
        return configureCommand("zoi", function);
    }
}
