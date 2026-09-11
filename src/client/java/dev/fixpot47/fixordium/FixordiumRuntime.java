package dev.fixpot47.fixordium;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

import java.lang.reflect.Method;

public final class FixordiumRuntime {
    private static final String INTERMEDIARY = "intermediary";

    private static volatile boolean reflectionReady;
    private static volatile boolean reflectionFailed;
    private static Class<?> livingEntityClass;
    private static Method getBoundingBox;
    private static Method frustumIsVisible;

    private FixordiumRuntime() {
    }

    public static void initialize() {
        // Reflection is initialized lazily on the first rendered entity.
        // This keeps startup lightweight and lets Fabric finish setting up mappings first.
    }

    public static boolean shouldCull(Object entity, Object frustum) {
        if (!FixordiumClient.isEnabled() || entity == null || frustum == null) {
            return false;
        }

        if (!ensureReflection()) {
            return false;
        }

        if (!livingEntityClass.isInstance(entity)) {
            return false;
        }

        try {
            Object box = getBoundingBox.invoke(entity);
            if (box == null) {
                return false;
            }

            boolean visible = (boolean) frustumIsVisible.invoke(frustum, box);
            return !visible;
        } catch (ReflectiveOperationException exception) {
            failReflection(exception);
            return false;
        }
    }

    private static synchronized boolean ensureReflection() {
        if (reflectionReady) {
            return true;
        }
        if (reflectionFailed) {
            return false;
        }

        try {
            MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();

            String livingEntityName = mappings.mapClassName(INTERMEDIARY, "net.minecraft.class_1309");
            String entityName = mappings.mapClassName(INTERMEDIARY, "net.minecraft.class_1297");
            String frustumName = mappings.mapClassName(INTERMEDIARY, "net.minecraft.class_4604");

            livingEntityClass = Class.forName(livingEntityName);
            Class<?> entityClass = Class.forName(entityName);
            Class<?> frustumClass = Class.forName(frustumName);

            String getBoundingBoxName = mappings.mapMethodName(
                    INTERMEDIARY,
                    "net.minecraft.class_1297",
                    "method_5829",
                    "()Lnet/minecraft/class_238;"
            );
            getBoundingBox = entityClass.getMethod(getBoundingBoxName);

            String isVisibleName = mappings.mapMethodName(
                    INTERMEDIARY,
                    "net.minecraft.class_4604",
                    "method_23093",
                    "(Lnet/minecraft/class_238;)Z"
            );

            Class<?> boxClass = Class.forName(mappings.mapClassName(INTERMEDIARY, "net.minecraft.class_238"));
            frustumIsVisible = frustumClass.getMethod(isVisibleName, boxClass);

            reflectionReady = true;
            return true;
        } catch (ReflectiveOperationException | RuntimeException exception) {
            failReflection(exception);
            return false;
        }
    }

    private static void failReflection(Exception exception) {
        if (!reflectionFailed) {
            reflectionFailed = true;
            System.err.println("[Fixordium] Entity culling bridge disabled: " + exception.getMessage());
        }
    }
}
