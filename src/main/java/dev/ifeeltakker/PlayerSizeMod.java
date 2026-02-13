package dev.ifeeltakker;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSizeMod implements ClientModInitializer {

    public static final HashMap<UUID, Float> playerSizes = new HashMap<>();

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    ClientCommandManager.literal("player")
                            .then(ClientCommandManager.literal("size")
                                    .then(ClientCommandManager.argument("size", DoubleArgumentType.doubleArg(0.0, 5.0))
                                            .executes(context -> {
                                                double size = DoubleArgumentType.getDouble(context, "size");
                                                AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;
                                                if (player != null) {
                                                    playerSizes.put(player.getUuid(), (float) size);
                                                }
                                                return 1;
                                            })
                                    )
                            )
            );
        });
    }

    public static float getPlayerSize(UUID uuid) {
        return playerSizes.getOrDefault(uuid, 1.0f);
    }
}
