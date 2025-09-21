package com.digi.vavpatch.impl.res;


import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.factorytools.api.resourcepack.ModelModifiers;
import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.resourcepack.api.AssetPaths;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.api.ResourcePackBuilder;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.AtlasAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelElement;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import com.digi.vavpatch.impl.PolymerPatchForVariantsVentures;
import com.digi.vavpatch.impl.entity.model.EntityModels;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.digi.vavpatch.impl.PolymerPatchForVariantsVentures.id;

public class ResourcePackGenerator {
    private static final Set<String> EXPANDABLE = Set.of("egg");
    });

    public static void setup() {
        PolymerResourcePackUtils.RESOURCE_PACK_AFTER_INITIAL_CREATION_EVENT.register(ResourcePackGenerator::build);
    }

    private static void build(ResourcePackBuilder builder) {
        var atlas = AtlasAsset.builder();


        for (var model : EntityModels.ALL) {
            model.generateAssets(builder::addData, atlas);
        }


        private static void copyVanillaAssets (ResourcePackBuilder builder, String vanillaPath, String outputPath){
            try {
                builder.addData(outputPath, Files.readAllBytes(PolymerCommonUtils.getClientJarRoot().resolve(vanillaPath)));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}