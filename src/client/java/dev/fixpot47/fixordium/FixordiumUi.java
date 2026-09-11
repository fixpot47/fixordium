package dev.fixpot47.fixordium;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class FixordiumUi {
    private static final String INTERMEDIARY = "intermediary";
    private static boolean warned;

    private FixordiumUi() {
    }

    public static void addToggle(Object screen) {
        if (screen == null) {
            return;
        }

        try {
            MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();

            Class<?> screenClass = mappedClass(mappings, "net.minecraft.class_437");
            Class<?> elementClass = mappedClass(mappings, "net.minecraft.class_364");
            Class<?> textClass = mappedClass(mappings, "net.minecraft.class_2561");
            Class<?> buttonClass = mappedClass(mappings, "net.minecraft.class_4185");
            Class<?> pressActionClass = mappedClass(mappings, "net.minecraft.class_4185$class_4241");
            Class<?> builderClass = mappedClass(mappings, "net.minecraft.class_4185$class_7840");
            Class<?> clickableWidgetClass = mappedClass(mappings, "net.minecraft.class_339");

            String widthFieldName = mappings.mapFieldName(
                    INTERMEDIARY,
                    "net.minecraft.class_437",
                    "field_22789",
                    "I"
            );
            Field widthField = screenClass.getField(widthFieldName);
            int screenWidth = widthField.getInt(screen);

            Object pressAction = Proxy.newProxyInstance(
                    pressActionClass.getClassLoader(),
                    new Class<?>[]{pressActionClass},
                    (proxy, method, args) -> {
                        if (method.getName().equals("onPress") && args != null && args.length == 1) {
                            FixordiumClient.toggle();
                            setButtonMessage(mappings, clickableWidgetClass, textClass, args[0], buttonLabel());
                            return null;
                        }

                        if (method.getDeclaringClass() == Object.class) {
                            return switch (method.getName()) {
                                case "toString" -> "FixordiumPressAction";
                                case "hashCode" -> System.identityHashCode(proxy);
                                case "equals" -> proxy == (args == null ? null : args[0]);
                                default -> null;
                            };
                        }
                        return null;
                    }
            );

            Object label = literalText(mappings, textClass, buttonLabel());
            String builderMethodName = mappings.mapMethodName(
                    INTERMEDIARY,
                    "net.minecraft.class_4185",
                    "method_46430",
                    "(Lnet/minecraft/class_2561;Lnet/minecraft/class_4185$class_4241;)Lnet/minecraft/class_4185$class_7840;"
            );
            Method builderMethod = buttonClass.getMethod(builderMethodName, textClass, pressActionClass);
            Object builder = builderMethod.invoke(null, label, pressAction);

            String dimensionsName = mappings.mapMethodName(
                    INTERMEDIARY,
                    "net.minecraft.class_4185$class_7840",
                    "method_46434",
                    "(IIII)Lnet/minecraft/class_4185$class_7840;"
            );
            Method dimensions = builderClass.getMethod(dimensionsName, int.class, int.class, int.class, int.class);
            dimensions.invoke(builder, Math.max(6, screenWidth - 126), 6, 120, 20);

            addTooltip(mappings, builderClass, textClass, builder);

            String buildName = mappings.mapMethodName(
                    INTERMEDIARY,
                    "net.minecraft.class_4185$class_7840",
                    "method_46431",
                    "()Lnet/minecraft/class_4185;"
            );
            Method build = builderClass.getMethod(buildName);
            Object button = build.invoke(builder);

            String addChildName = mappings.mapMethodName(
                    INTERMEDIARY,
                    "net.minecraft.class_437",
                    "method_37063",
                    "(Lnet/minecraft/class_364;)Lnet/minecraft/class_364;"
            );
            Method addChild = screenClass.getDeclaredMethod(addChildName, elementClass);
            addChild.setAccessible(true);
            addChild.invoke(screen, button);
        } catch (ReflectiveOperationException | RuntimeException exception) {
            if (!warned) {
                warned = true;
                System.err.println("[Fixordium] Could not add Video Settings toggle: " + exception.getMessage());
            }
        }
    }

    private static void addTooltip(
            MappingResolver mappings,
            Class<?> builderClass,
            Class<?> textClass,
            Object builder
    ) throws ReflectiveOperationException {
        Class<?> tooltipClass = mappedClass(mappings, "net.minecraft.class_7919");
        Object description = literalText(
                mappings,
                textClass,
                "Skips rendering players and mobs that are outside your view to reduce unnecessary rendering work."
        );

        String tooltipOfName = mappings.mapMethodName(
                INTERMEDIARY,
                "net.minecraft.class_7919",
                "method_47407",
                "(Lnet/minecraft/class_2561;)Lnet/minecraft/class_7919;"
        );
        Method tooltipOf = tooltipClass.getMethod(tooltipOfName, textClass);
        Object tooltip = tooltipOf.invoke(null, description);

        String tooltipName = mappings.mapMethodName(
                INTERMEDIARY,
                "net.minecraft.class_4185$class_7840",
                "method_46436",
                "(Lnet/minecraft/class_7919;)Lnet/minecraft/class_4185$class_7840;"
        );
        Method setTooltip = builderClass.getMethod(tooltipName, tooltipClass);
        setTooltip.invoke(builder, tooltip);
    }

    private static void setButtonMessage(
            MappingResolver mappings,
            Class<?> clickableWidgetClass,
            Class<?> textClass,
            Object button,
            String value
    ) throws ReflectiveOperationException {
        String setMessageName = mappings.mapMethodName(
                INTERMEDIARY,
                "net.minecraft.class_339",
                "method_25355",
                "(Lnet/minecraft/class_2561;)V"
        );
        Method setMessage = clickableWidgetClass.getMethod(setMessageName, textClass);
        setMessage.invoke(button, literalText(mappings, textClass, value));
    }

    private static Object literalText(MappingResolver mappings, Class<?> textClass, String value)
            throws ReflectiveOperationException {
        String literalName = mappings.mapMethodName(
                INTERMEDIARY,
                "net.minecraft.class_2561",
                "method_43470",
                "(Ljava/lang/String;)Lnet/minecraft/class_5250;"
        );
        Method literal = textClass.getMethod(literalName, String.class);
        return literal.invoke(null, value);
    }

    private static Class<?> mappedClass(MappingResolver mappings, String intermediaryName)
            throws ClassNotFoundException {
        return Class.forName(mappings.mapClassName(INTERMEDIARY, intermediaryName));
    }

    private static String buttonLabel() {
        return "Fixordium: " + (FixordiumClient.isEnabled() ? "ON" : "OFF");
    }
}
