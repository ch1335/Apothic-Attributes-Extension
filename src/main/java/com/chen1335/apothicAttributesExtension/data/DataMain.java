package com.chen1335.apothicAttributesExtension.data;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ApothicAttributesExtension.MODID)
public class DataMain {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(event.includeServer(), new AttributeTagsProvider(generator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
    }
}
