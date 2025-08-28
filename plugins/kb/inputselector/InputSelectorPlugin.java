package com.kb.inputselector;

import net.runelite.api.Client;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

@PluginDescriptor(
    name = "Input Selector",
    description = "Toggle input to the game client",
    tags = {"input", "control", "disable"}
)
public class InputSelectorPlugin extends Plugin {

    private static final BufferedImage ENABLED_IMAGE;
    private static final BufferedImage DISABLED_IMAGE;

    static {
        // Load icons from the same package
        ENABLED_IMAGE = ImageUtil.loadImageResource(InputSelectorPlugin.class, "enabled_small.png");
        DISABLED_IMAGE = ImageUtil.loadImageResource(InputSelectorPlugin.class, "disabled_small.png");
    }

    @Inject
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    private NavigationButton enableButton;
    private NavigationButton disableButton;

    @Override
    protected void startUp() {
        enableButton = NavigationButton.builder()
            .icon(ENABLED_IMAGE)
            .tooltip("Enable Input")
            .onClick(this::enableClick)
            .build();

        disableButton = NavigationButton.builder()
            .icon(DISABLED_IMAGE)
            .tooltip("Disable Input")
            .onClick(this::disableClick)
            .build();

        addAndRemoveButtons();
    }

    @Override
    protected void shutDown() {
        clientToolbar.removeNavigation(enableButton);
        clientToolbar.removeNavigation(disableButton);
    }

    private void enableClick() {
        // Cast client to Component to access setEnabled
        Component gameCanvas = (Component) client;
        gameCanvas.setEnabled(true);

        // Make canvas focusable again
        client.getCanvas().setFocusable(true);

        addAndRemoveButtons();
    }

    private void disableClick() {
        // Cast client to Component to access setEnabled
        Component gameCanvas = (Component) client;
        gameCanvas.setEnabled(false);

        // Remove focus capability from canvas
        client.getCanvas().setFocusable(false);

        addAndRemoveButtons();
    }

    private void addAndRemoveButtons() {
        // Remove both buttons first
        clientToolbar.removeNavigation(enableButton);
        clientToolbar.removeNavigation(disableButton);

        // Add the appropriate button based on current state
        Component gameCanvas = (Component) client;
        clientToolbar.addNavigation(gameCanvas.isEnabled() ? disableButton : enableButton);
    }
}