package org.arsparadox.mobtalkerredux.vn.data;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public class DialogueState {
    private String label;
    private String content;
    private ResourceLocation sprite;
    private List<Map<String, Object>> choices;

    public DialogueState(String label, String content, ResourceLocation sprite, List<Map<String, Object>> choices) {
        this.label = label;
        this.content = content;
        this.sprite = sprite;
        this.choices = choices;
    }

    // Getters and setters
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public ResourceLocation getSprite() { return sprite; }
    public void setSprite(ResourceLocation sprite) { this.sprite = sprite; }

    public List<Map<String, Object>> getChoices() {
        return choices;
    }
    public void setChoices(List<Map<String, Object>> choices) { this.choices = choices; }

}
