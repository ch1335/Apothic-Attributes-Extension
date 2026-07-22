package com.chen1335.apothicattributesextension.mixinsAPI;

import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;

public interface AttributesGuiExtension {
    boolean apothicAttributesExtension$keyPressed(KeyEvent event);

    boolean apothicAttributesExtension$charTyped(CharacterEvent event);
}
