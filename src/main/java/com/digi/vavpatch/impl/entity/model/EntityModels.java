package com.digi.vavpatch.impl.entity.model;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.MurkEntityModel;
import com.faboslav.variantsandventures.common.entity.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import com.digi.vavpatch.impl.entity.model.emuvanilla.PolyModelInstance;
import com.digi.vavpatch.impl.entity.model.emuvanilla.model.EntityModel;
import com.digi.vavpatch.impl.entity.model.emuvanilla.model.LayerDefinition;
import com.digi.vavpatch.impl.entity.model.emuvanilla.model.MeshTransformer;
import com.digi.vavpatch.impl.entity.model.emuvanilla.model.ModelPart;
import com.digi.vavpatch.impl.entity.model.entity.*;
import com.digi.vavpatch.impl.res.ResourcePackGenerator;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Function;

public interface EntityModels {
    List<PolyModelInstance<?>> ALL = new ArrayList<>();

    PolyModelInstance<MurkEntityModel> MURK = create(MurkEntityModel::new, MurkEntityModel.getTexturedModelData(), VariantsAndVentures.makeID("entity/murk/murk"));

    MeshTransformer humanLikeScaling = MeshTransformer.scaling(0.9375F);
    LayerDefinition villagerData = LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64).apply(humanLikeScaling);
    PolyModelInstance<VillagerModel> VILLAGER = create(modelPart -> new VillagerModel(modelPart, true), villagerData, FriendsAndFoes.makeID("entity/villager/villager"));
    Map<ResourceLocation, PolyModelInstance<VillagerModel>> VILLAGER_PROFESSION = Util.make(new HashMap<>(), m -> {
        var instance = create(modelPart -> new VillagerModel(modelPart, true), villagerData, FriendsAndFoes.makeID("entity/villager/profession/beekeeper"));
        m.put(FriendsAndFoes.makeID("beekeeper"), instance);
    });

    Map<ResourceLocation, PolyModelInstance<VillagerModel>> VILLAGER_TYPE = Util.make(new HashMap<>(), m -> {
        for (ResourceLocation resourceLocation : BuiltInRegistries.VILLAGER_TYPE.keySet()) {
            var instance = create(modelPart -> new VillagerModel(modelPart, false), villagerData, FriendsAndFoes.makeID("entity/villager/type/" + resourceLocation.getPath()));
            m.put(resourceLocation, instance);
        }
    });


    static <T extends EntityModel<?>> PolyModelInstance<T> create(Function<ModelPart, T> modelCreator, LayerDefinition data, ResourceLocation texture) {
        var instance = PolyModelInstance.create(modelCreator, data, texture);
        ALL.add(instance);
        return instance;
    }

}