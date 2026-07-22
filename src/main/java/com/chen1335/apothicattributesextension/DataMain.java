package com.chen1335.apothicattributesextension;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ApothicAttributesExtension.MODID)
public final class DataMain {
    private DataMain() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new AttributeTagsProvider(generator.getPackOutput(), event.getLookupProvider()));
    }
}
