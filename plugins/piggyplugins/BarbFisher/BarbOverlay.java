package net.runelite.client.plugins.BarbFisher;

import com.google.inject.Inject;
import net.runelite.client.plugins.BarbFisher.UiLayoutOption;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class BarbOverlay extends OverlayPanel {
    private final BarbPlugin plugin;

    @Inject
    public BarbOverlay(BarbPlugin plugin) {
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        UiLayoutOption selectedLayout = plugin.config.uiLayout();
        panelComponent.getChildren().clear();
        //panelComponent.setBackgroundColor(new Color(23, 23, 37, 234));
        graphics.setStroke(new BasicStroke(1));

        switch (selectedLayout) {
            case FULL:
                addFullOverlay();
                break;
            case SIMPLE:
                addSimpleOverlay();
                break;
            case DEBUG:
                addDebugOverlay(graphics);
                break;
            case NONE:
            default:
                break;
        }

        return super.render(graphics);
    }

    private void addFullOverlay() {
        panelComponent.setPreferredSize(new Dimension(500, 200)); // Adjust as needed
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("[B] Barbarian Fishing")
                .color(new Color(134, 196, 63))
                .build());

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Enabled")
                .color(Color.GREEN)
                .build());



        Duration runtime = Duration.between(plugin.getStartTime(), Instant.now());
        double hoursElapsed = runtime.getSeconds() / 3600.0; // Convert seconds to hours


        panelComponent.getChildren().add(LineComponent.builder()
                .left("Version:")
                .leftColor(Color.WHITE)
                .right("1.0")
                .rightColor(new Color(134, 196, 63))
                .build());

        String runtimeStr = formatDuration(runtime);
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Runtime:")
                .leftColor(Color.WHITE)
                .right(runtimeStr)
                .rightColor(Color.WHITE)
                .build());

        String state = plugin.currentState().toString();
        panelComponent.getChildren().add(LineComponent.builder()
                .left("State:")
                .right(state)
                .leftColor(Color.WHITE)
                .build());

    }

    private void addSimpleOverlay() {
        panelComponent.setPreferredSize(new Dimension(150, 30)); // Adjust as needed
        String title = "[B] Barbarian Fishing v1.0";
        String enabledText = "Plugin Enabled";

        panelComponent.getChildren().add(TitleComponent.builder()
                .text(title)
                .color(new Color(134, 196, 63))
                .build());

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Enabled")
                .color(Color.GREEN)
                .build());

    }

    private void addDebugOverlay(Graphics2D graphics) {
    }

    private void addDebugLine(String text) {
        panelComponent.getChildren().add(LineComponent.builder()
                .left(text)
                .leftColor(Color.ORANGE)
                .build());
    }

    private String formatDuration(Duration duration) {
        if (duration.toHours() > 0) {
            return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
        } else {
            return String.format("%02d:%02d", duration.toMinutes(), duration.toSecondsPart());
        }
    }
}


