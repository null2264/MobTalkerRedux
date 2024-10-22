package org.arsparadox.mobtalkerredux;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DialogueScreen extends Screen {

    private static final ResourceLocation SOLIAH = new ResourceLocation("mobtalkerredux", "textures/gui/soliah.png");
    private String characterName = "Cupa";
    private DialogueManager dialogueManager;

    protected DialogueScreen(DialogueManager dialogueManager) {
        super(new TextComponent("Mob Talker"));
        this.dialogueManager = dialogueManager;
    }

    @Override
    protected void init() {
        // Initialize the display
        updateDisplay();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            if (dialogueManager.getCurrentDialogue().map(Dialogue::getChoices).orElse(List.of()).isEmpty()) {
                // Proceed to next dialogue if there are no choices
                dialogueManager.proceedToNextDialogue();
                updateDisplay();
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void updateDisplay() {
        clearWidgets();  // Clear previous widgets/buttons if any
        var currentDialogueOpt = dialogueManager.getCurrentDialogue();

        currentDialogueOpt.ifPresent(currentDialogue -> {
            String dialogueText = currentDialogue.getContent();
            // Add choice buttons if available
            List<Choice> choices = currentDialogue.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                Choice choice = choices.get(i);
                this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 + (i * 24), 100, 20,
                        new TextComponent(choice.getButtonText()), e -> onPress(choice)));
            }
            // Draw updated dialogue content and character name
            drawDialogue(dialogueText, characterName);
        });
    }

    private void onPress(Choice choice) {
        // Handle button press
        Minecraft.getInstance().player.sendMessage(new TextComponent("You chose: " + choice.getButtonText()), Minecraft.getInstance().player.getUUID());
        // Process choice impacts here, like changing affection or proceeding to a specific next dialogue
        dialogueManager.proceedToChosenDialogue(choice.getNextDialogId());
        updateDisplay();
    }

    private void drawDialogue(String dialogueText, String characterName) {
        // Logic only for drawing other elements remains here
        // Update with new dialogue text and character name appropriately
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        // Set up for texture rendering
        RenderSystem.setShaderTexture(0, SOLIAH);

        // Blit the texture at desired position (with reduced size)
        blit(poseStack, this.width / 2 - 75, this.height / 6, 0, 0, 150, 225, 200, 300);

        // Render updated Name and Dialogue Text here instead of static text
        drawCenteredString(poseStack, this.font, characterName, this.width / 2, this.height / 6 - 30, 0xFFFFFF);

        var currentDialogueOpt = dialogueManager.getCurrentDialogue();
        currentDialogueOpt.ifPresent(currentDialogue -> {
            // Draw the green text box for dialogue
            fill(poseStack, this.width / 2 - 150, this.height - 80, this.width / 2 + 150, this.height - 20, 0xAA004400);
            drawString(poseStack, this.font, currentDialogue.getContent(), this.width / 2 - 145, this.height - 70 , 0xFFFFFF);
        });

        // Render buttons after the anime girl texture and green text box
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Don't pause the game
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        // Disable background darkening
    }
}