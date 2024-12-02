package dha.libapp.effects;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
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

}
