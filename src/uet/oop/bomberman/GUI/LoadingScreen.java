package uet.oop.bomberman.GUI;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import uet.oop.bomberman.CreateMap;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Settings;
import uet.oop.bomberman.SoundManager;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen {
    static Image loading;
    static ImageView loader;
    static float i = 0;

    public static void createLoadingScreen(Group root) {
        loading = new Image("images/author1.png");
        loader = new ImageView(loading);
        loader.setX(0);
        loader.setY(0);
        loader.setFitHeight(Settings.HEIGHT);
        loader.setFitWidth(Settings.WIDTH);

        root.getChildren().addAll(loader);
        loader.setVisible(false);
    }

    public static void startloadingScreen(Group root) {
        Game.gamestate = "loading";
        setVisual();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3));
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        //Finish splash with fade out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3));
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();
        //After fade in, start fade out
        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished(e -> {
            fadeIn.play();
        });
        ProgressBar bar = new ProgressBar();
        bar.setLayoutX(100);
        bar.setLayoutY(400);
        bar.setMinSize(300, 30);
        bar.setVisible(true);
        bar.setProgress(0);
        root.getChildren().add(bar);

        Timer time = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                bar.setProgress(i++ / 100);
                System.out.println(i);
                if (i == 100) {
                    time.cancel();
                    i = 0;
                    bar.setVisible(false);
                    switch (Menubutton.myEnum) {
                        case 1:
                            System.out.println("Low level");
                            CreateMap.createMapLevel(1);
                            new SoundManager("sound/pacbaby.wav", "ingame");
                            System.out.println("pressed");
                            Menubutton.newGame = true;
                            Menubutton.Setting = true;
                            Game.gamestate = "running";
                            break;
                        case 2:
                            System.out.println("Medium level");
                            CreateMap.createMapLevel(2);
                            new SoundManager("sound/pacbaby.wav", "ingame");
                            System.out.println("pressed");
                            Menubutton.newGame = true;
                            Menubutton.Setting = true;
                            Game.gamestate = "running";
                            break;
                        case 3:
                            System.out.println("High level");
                            CreateMap.createMapLevel(3);
                            new SoundManager("sound/pacbaby.wav", "ingame");
                            System.out.println("pressed");
                            Menubutton.newGame = true;
                            Menubutton.Setting = true;
                            Game.gamestate = "running";
                            break;
                        case 4:
                            System.out.println("next level");
                            if (Game.getLevel() < 2) Game.setLevel(Game.getLevel() + 1);
                            CreateMap.createMapLevel(Game.getLevel());
                            new SoundManager("sound/pacbaby.wav", "ingame");
                            System.out.println(Game.getLevel());
                            Game.gamestate = "running";
                    }
                }
            }
        };
        time.schedule(task, 0, 20);
    }

    public static void setVisual() {
        Menubutton.easy.setVisible(false);
        Menubutton.medium.setVisible(false);
        Menubutton.hard.setVisible(false);
        Menubutton.newgame.setVisible(false);
        Menubutton.setting.setVisible(false);
        Menubutton.exit.setVisible(false);
        Menubutton.mainMenu.setVisible(false);
        Menubutton.resume.setVisible(false);
    }
}
