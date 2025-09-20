package com.digi.vavpatch.impl.entity;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils;
import eu.pb4.polymer.virtualentity.api.attachment.IdentifiedUniqueEntityAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.UniqueIdentifiableAttachment;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public record BasePolymerEntity(Entity entity) implements PolymerEntity {
    public static final ResourceLocation MODEL = VariantsAndVentures.makeID("model");

    public BasePolymerEntity {
        Function<Entity, ? extends SimpleElementHolder<?, ?>> factory = AnimatedEntities.ENTITY_FACTORIES.get(entity.getType());
        if (factory != null) {
            SimpleElementHolder<?, ?> holder = factory.apply(entity);
            IdentifiedUniqueEntityAttachment.ofTicking(MODEL, holder, entity);
        }
    }

    @Override
    public void onEntityPacketSent(Consumer<Packet<?>> consumer, Packet<?> packet) {
        if (packet instanceof ClientboundAnimatePacket) {
            return;
        }
        if (packet instanceof ClientboundSetPassengersPacket packet1 && packet1.getPassengers().length != 0) {
            var model = (SimpleElementHolder<?, ?>) UniqueIdentifiableAttachment.get(entity, MODEL).holder();
            consumer.accept(VirtualEntityUtils.createRidePacket(entity.getId(), IntList.of(model.interaction.getEntityId())));
            consumer.accept(VirtualEntityUtils.createRidePacket(model.interaction.getEntityId(), packet1.getPassengers()));
            return;
        }

        if (packet instanceof ClientboundSetEntityLinkPacket packet1) {
            var model = (SimpleElementHolder<?, ?>) UniqueIdentifiableAttachment.get(entity, MODEL).holder();
            consumer.accept(VirtualEntityUtils.createEntityAttachPacket(model.leadAttachment.getEntityId(), packet1.getDestId()));
            return;
        }
        PolymerEntity.super.onEntityPacketSent(consumer, packet);
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext packetContext) {
        return EntityType.ITEM_DISPLAY;
    }

    @Override
    public void modifyRawTrackedData(List<SynchedEntityData.DataValue<?>> data, ServerPlayer player, boolean initial) {
        PolymerEntity.super.modifyRawTrackedData(data, player, initial);
        if (initial) {
            data.add(SynchedEntityData.DataValue.create(DisplayTrackedData.TELEPORTATION_DURATION, 3));
        }
    }

}