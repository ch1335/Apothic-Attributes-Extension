package com.chen1335.apothicAttributesExtension.network;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import com.chen1335.apothicAttributesExtension.config.ServerConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ServerConfigPack(CompoundTag compoundTag) implements CustomPacketPayload {
    public static final Type<ServerConfigPack> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ApothicAttributesExtension.MODID, "server_config"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, ServerConfigPack> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            ServerConfigPack::compoundTag,
            ServerConfigPack::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handler(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().hasPermissions(2) || context.player().isLocalPlayer()) {
                ServerConfig.loadFromCompoundTag(compoundTag);
                ServerConfig.save();
                if (context.player() instanceof ServerPlayer serverPlayer) {
                    for (ServerPlayer player : serverPlayer.server.getPlayerList().getPlayers()) {
                        if (player != context.player()) {
                            PacketDistributor.sendToPlayer(player, this);
                        }
                    }
                }
            }
        });
    }
}
