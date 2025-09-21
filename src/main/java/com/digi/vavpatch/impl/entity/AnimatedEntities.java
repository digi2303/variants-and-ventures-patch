package com.digi.vavpatch.impl.entity;

import com.faboslav.variantsandventures.common.init.VariantsAndVenturesEntityTypes;
import com.digi.vavpatch.impl.entity.holder.*;
import com.digi.vavpatch.impl.entity.model.EntityModels;
import com.digi.vavpatch.impl.entity.model.emuvanilla.PolyModelInstance;
import com.digi.vavpatch.impl.entity.model.emuvanilla.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.IdentityHashMap;
import java.util.function.Function;

public class AnimatedEntities {

    protected static final IdentityHashMap<EntityType<?>, Function<Entity, ? extends SimpleElementHolder<?, ?>>> ENTITY_FACTORIES = new IdentityHashMap<>();

    static {
        registerEntity(FriendsAndFoesEntityTypes.ICEOLOGER.get(), EntityModels.ICEOLOGER, SimpleElementHolder::new);
        registerEntity(FriendsAndFoesEntityTypes.MURK.get(), EntityModels.MURK, SimpleElementHolder::new);
        registerEntity(EntityType.VILLAGER, EntityModels.VILLAGER, VillagerElementHolder::new);
    }

    public static <T extends Entity, X extends EntityModel<T>> void registerEntity(EntityType<T> type, PolyModelInstance<X> defaultModel, Function<T, ? extends SimpleElementHolder<T, X>> factory) {
        ENTITY_FACTORIES.put(type, entity -> {
            SimpleElementHolder<T, X> elementHolder = factory.apply((T) entity);
            if (defaultModel != null) {
                elementHolder.setMainModel(defaultModel);
            }
            return elementHolder;
        });
    }
}