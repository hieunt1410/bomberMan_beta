package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.GUI.Menu;
import uet.oop.bomberman.entities.EntityList;
import uet.oop.bomberman.entities.blocks.Bomb;
import uet.oop.bomberman.entities.blocks.Brick;
import uet.oop.bomberman.entities.blocks.Wall;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.entities.EntityList.bomberman;

public class Game extends Application {
    ImageView startscreenView;
    ImageView playbutton;
    ImageView gamemodeHard;
    ImageView gamemodeEasy;
    ImageView gamemodeMedium;

    private static int level = 1;
    public static String gamestate = " ";
    private static GraphicsContext gc;
    private static Canvas canvas;
    public static long time = 0;

    public static void main(String[] args) {
        Application.launch(Game.class);
    }

    public static int getLevel() {
        return level;
    }
    public static void setLevel(int lv) {
        level = lv;
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("BomberMan");
        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);
        // Tao Canvas
        canvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
        canvas.setLayoutY(30);
        gc = canvas.getGraphicsContext2D();

        Image playButton = new Image("images/button1.png");
        Image startscreen = new Image("images/author1.png");
        Image Gamemode = new Image("images/author1.png");

        startscreenView = new ImageView(startscreen);
        startscreenView.setX(0);
        startscreenView.setY(0);
        startscreenView.setFitHeight(Settings.HEIGHT);
        startscreenView.setFitWidth(Settings.WIDTH);

        playbutton = new ImageView(playButton);
        playbutton.setLayoutX(170);
        playbutton.setLayoutY(210);
        playbutton.setFitHeight(40);
        playbutton.setFitWidth(160);

        //gamemode
        gamemodeHard = new ImageView(Gamemode);
        gamemodeHard.setLayoutX(170);
        gamemodeHard.setLayoutY(360);
        gamemodeHard.setFitHeight(40);
        gamemodeHard.setFitWidth(160);

        gamemodeMedium = new ImageView(Gamemode);
        gamemodeMedium.setLayoutX(170);
        gamemodeMedium.setLayoutY(310);
        gamemodeMedium.setFitHeight(40);
        gamemodeMedium.setFitWidth(160);

        gamemodeEasy = new ImageView(Gamemode);
        gamemodeEasy.setLayoutX(170);
        gamemodeEasy.setLayoutY(260);
        gamemodeEasy.setFitHeight(40);
        gamemodeEasy.setFitWidth(160);
        // Tao root container
        Group root = new Group();

        root.getChildren().add(canvas);

        //Menu.createMenu(root);
        Menu.createMenu(root);
        root.getChildren().add(startscreenView);
        root.getChildren().add(playbutton);

        playbutton.setVisible(false);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
        scene.setFill(Color.WHITE);

//        CreateMap.createMapLevel(level);

        new SoundManager("sound/start.wav", "title");
        SoundManager.updateSound();
        gamestate = "startmenu";
        AnimationTimer timer = new AnimationTimer() {
            long start=System.currentTimeMillis();
            long before=0;
            @Override
            public void handle(long l) {
                if (gamestate.equals("startmenu")) {
                    showMenu();
                }
                if (gamestate.equals("running")) {
                    render();
                    update();
                    long end=(System.currentTimeMillis()-start)/1000;
                    if(end-before==1)Game.time--;
                    before=end;
                }
                if (gamestate.equals("pause")) {

                }
                if (gamestate.equals("gameover")) {
                    //show gameover trong muc images + time

                    //reset game hoac show menu
                }
                if (gamestate.equals("nextLevel")) {
                    //tao level

                }
            }
        };

        timer.start();


        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("UP")) {
                bomberman.goUp();
            } else if (event.getCode().toString().equals("DOWN")) {
                bomberman.goDown();
            } else if (event.getCode().toString().equals("LEFT")) {
                bomberman.goLeft();
            } else if (event.getCode().toString().equals("RIGHT")) {
                bomberman.goRight();
            } else if (event.getCode().toString().equals("SPACE") && bomberman.bombs.isEmpty()) {
                bomberman.placeBomb();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("LEFT")) {
                bomberman.setImg(Sprite.player_left.getFxImage());
            } else if (event.getCode().toString().equals("UP")) {
                bomberman.setImg(Sprite.player_up.getFxImage());
            } else if (event.getCode().toString().equals("RIGHT")) {
                bomberman.setImg(Sprite.player_right.getFxImage());
            } else if (event.getCode().toString().equals("DOWN")) {
                bomberman.setImg(Sprite.player_down.getFxImage());
            } else if (event.getCode().toString().equals("C")) {
                for (int i = 0; i < EntityList.enemies.size(); i++) EntityList.enemies.get(i).setAlive(false);
            }
        });


        playbutton.setOnMouseClicked(event -> {
            //render game mode va chon
            playbutton.setVisible(false);
            root.getChildren().add(gamemodeHard);
            root.getChildren().add(gamemodeEasy);
            root.getChildren().add(gamemodeMedium);
        });

        //chon gamemode
        gamemodeHard.setOnMouseClicked(event -> {
            hideMenu();
            gamestate = "running";
        });

        gamemodeMedium.setOnMouseClicked(event -> {
            hideMenu();
            CreateMap.createMapLevel(2);
            gamestate = "running";
        });

        gamemodeEasy.setOnMouseClicked(event -> {
            hideMenu();
            CreateMap.createMapLevel(1);
            gamestate = "running";
        });
    }

    public void update() {
        Menu.updateMenu();
        EntityList.walls.forEach(Wall::update);
        if (Bomb.isFire()) EntityList.bricks.forEach(Brick::update);
        for (int i = 0; i < EntityList.items.size(); i++) EntityList.items.get(i).update();
        for (int i = 0; i < bomberman.bombs.size(); i++) bomberman.bombs.get(i).update();
        for (int i = 0; i < EntityList.flames.size(); i++) EntityList.flames.get(i).update();
        for (int i = 0; i < EntityList.enemies.size(); i++) EntityList.enemies.get(i).update();
        EntityList.portal.update();
        bomberman.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntityList.grasses.forEach(g -> g.render(gc));
        EntityList.walls.forEach(g -> g.render(gc));
        EntityList.portal.render(gc);
        EntityList.bricks.forEach(g -> g.render(gc));
        for (int i = 0; i < EntityList.items.size(); i++) EntityList.items.get(i).render(gc);
        for (int i = 0; i < EntityList.enemies.size(); i++) EntityList.enemies.get(i).render(gc);
        for (int i = 0; i < bomberman.bombs.size(); i++) bomberman.bombs.get(i).render(gc);
        for (int i = 0; i < EntityList.flames.size() && Bomb.isFire(); i++) EntityList.flames.get(i).render(gc);
        bomberman.render(gc);
    }

    public void showMenu() {
        startscreenView.setVisible(true);
        playbutton.setVisible(true);
    }

    public void hideMenu() {
        startscreenView.setVisible(false);
        playbutton.setVisible(false);
        gamemodeEasy.setVisible(false);
        gamemodeMedium.setVisible(false);
        gamemodeHard.setVisible(false);
        SoundManager.gamestate = "ingame";
        SoundManager.updateSound();
        new SoundManager("sound/boom.wav", "ingame");
    }

    public static void moveCamera(int x, int y) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.translate(- x, -y);
    }
}

