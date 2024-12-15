package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class ModelGen extends FabricModelProvider {

    public ModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleCubeAll(DRegistry.DBlocks.HEPHAESTUS_FORGE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        simpleItem(DRegistry.DItems.VALSOULSTRANGLER, generator);
        simpleItem(DRegistry.DItems.VALSOULSTRANGLER_DEACTIVATED, generator);
        simpleItem(DRegistry.DItems.ANGELS_GUARD, generator);
        simpleItem(DRegistry.DItems.ANGELS_GUARD_DEACTIVATED, generator);
        simpleItem(DRegistry.DItems.SOUL_ITEM, generator);
        simpleItem(DRegistry.DItems.EMPTY_SOUL, generator);
        simpleItem(DRegistry.DItems.SOUL_BLEACHER, generator);
        simpleItem(DRegistry.DItems.STURDY_TEMPLATE, generator);
    }

    private void simpleItem(Item item, ItemModelGenerator generator) {
        generator.register(item, Models.GENERATED);
    }
}
