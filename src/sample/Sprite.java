package sample;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends Transition {
    final private ImageView imageView;
    final private int count;
    final private int columns;
    final private int width;
    final private int height;

    public Sprite(ImageView imageView, Duration duration, int count, int columns,  int width, int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);

    }

    //Виглядає складно)))))
    @Override
    protected void interpolate(double frac) {
        int index = Math.min((int) Math.floor(frac * count), count -1);
        int x = (index % columns) *width ;
        int y = (index / columns) * height ;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}
