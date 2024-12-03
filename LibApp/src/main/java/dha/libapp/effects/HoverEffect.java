package dha.libapp.effects;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HoverEffect {

    public static void applyFadeEffect(Node node, double from, double to, int millis) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(millis), node);
        fadeIn.setFromValue(from);
        fadeIn.setToValue(to);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(millis), node);
        fadeOut.setFromValue(to);
        fadeOut.setToValue(from);

        node.setOnMouseEntered(e -> {
            fadeOut.stop();
            fadeIn.playFromStart();
        });

        node.setOnMouseExited(e -> {
            fadeIn.stop();
            fadeOut.playFromStart();
        });
    }

    public static void applyBackgroundColorEffect(Node node, String fromHex, String toHex, int millis) {
        String fromColor = fromHex.startsWith("#") ? fromHex : "#" + fromHex;
        String toColor = toHex.startsWith("#") ? toHex : "#" + toHex;

        node.setOnMouseEntered(e -> {
            node.setStyle("-fx-background-color: " + toColor + ";");
        });

        node.setOnMouseExited(e -> {
            node.setStyle("-fx-background-color: " + fromColor + ";");
        });
    }

}
