package com.digi.vavpatch.impl;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.resourcepack.extras.api.format.item.ItemAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.item.model.BasicItemModel;
import eu.pb4.polymer.resourcepack.extras.api.format.item.tint.MapColorTintSource;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PolymerPatchForVariantsVentures implements ModInitializer {
	public static final String MOD_ID = "variantsandventures-polymer-patch";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final List<Runnable> LATE_INIT = new ArrayList<>();

    @Override
	public void onInitialize() {
        PolymerResourcePackUtils.addModAssets(VariantsAndVentures.MOD_ID);
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        ResourcePackExtras.forDefault().addBridgedModelsFolder(VariantsAndVentures.makeID("entity"), (id, b) -> {
            return new ItemAsset(new BasicItemModel(id, List.of(new MapColorTintSource(0xFFFFFF))), new ItemAsset.Properties(true, true));
        });
        ResourcePackGenerator.setup();
	}
}