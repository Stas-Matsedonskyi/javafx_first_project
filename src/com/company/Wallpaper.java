package com.company;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Wallpaper {
    Image image;
    public  static ImageView imageView;
    public static Rectangle border;

    static Group wallpaperGroup;
    Wallpaper() throws FileNotFoundException {
        image = new Image(new FileInputStream("src/sample/world.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(4000);
        imageView.setFitHeight(3500);
        this.imageView = imageView;

        border = new Rectangle(0,0,(int)this.imageView.getFitWidth(), (int)this.imageView.getFitHeight());
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLUEVIOLET);

        Group sub = new Group( new Group(this.imageView, border));
        this.wallpaperGroup = sub;
    }

    public static Group getWallGroup() {
        return wallpaperGroup;
    }

}
