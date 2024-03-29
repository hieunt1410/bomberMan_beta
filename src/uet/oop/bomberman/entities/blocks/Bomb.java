package uet.oop.bomberman.entities.blocks;

import javafx.scene.image.Image;
import uet.oop.bomberman.CreateMap;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.SoundManager;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntityList;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomb extends Entity {
    private int count;
    private boolean fire = false;
    private static int radius = 1;
    private boolean allow;


    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        count = 0;
        allow = true;
        fire = false;
    }

    public boolean isFire() {
        return fire;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean x) {
        allow = x;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        Bomb.radius = radius;
    }

    public void explode() {
        int c = this.x / Sprite.SCALED_SIZE;
        int r = this.y / Sprite.SCALED_SIZE;

        Game.entityList.addFlame(new Flame(c, r, Sprite.bomb_exploded2.getFxImage()));

        for (int i = 1; i <= Bomb.radius; i++) {
            Flame flame = new Flame(c + i, r, Sprite.explosion_horizontal2.getFxImage());
            if (!flame.checkBrick() && !flame.checkWall() && !flame.checkBox() && !flame.checkTree()) {
                Game.entityList.addFlame(flame);
            } else break;
        }
        for (int i = 1; i <= Bomb.radius; i++) {
            Flame flame = new Flame(c - i, r, Sprite.explosion_horizontal2.getFxImage());
            if (!flame.checkBrick() && !flame.checkWall() && !flame.checkBox() && !flame.checkTree()) {
                Game.entityList.addFlame(flame);
            } else break;
        }
        for (int i = 1; i <= Bomb.radius; i++) {
            Flame flame = new Flame(c, r + i, Sprite.explosion_vertical2.getFxImage());
            if (!flame.checkBrick() && !flame.checkWall() && !flame.checkBox() && !flame.checkTree()) {
                Game.entityList.addFlame(flame);
            } else break;
            ;
        }
        for (int i = 1; i <= Bomb.radius; i++) {
            Flame flame = new Flame(c, r - i, Sprite.explosion_vertical2.getFxImage());
            if (!flame.checkBrick() && !flame.checkWall() && !flame.checkBox() && !flame.checkTree()) {
                Game.entityList.addFlame(flame);
            } else break;
        }
    }


    @Override
    public void update() {
        int timeOut = 250;
        int row = this.y / Sprite.SCALED_SIZE;
        int col = this.x / Sprite.SCALED_SIZE;
        if (count < timeOut) {
            count++;
            setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, count, 90).getFxImage());
            CreateMap.getGrid()[row][col] = '#';
        } else if (count < 300) {
            count++;
            fire = true;
            for (Flame flame : Game.entityList.getFlames()) flame.update();
            if (CreateMap.getGrid()[row][col] != ' ') CreateMap.setGrid(row, col, ' ');
        } else {
            if (Game.entityList.getBomberman().getBombs() == 0)
                Game.entityList.getBomberman().addBomb();
            Game.entityList.getBombs().remove(this);
            Game.entityList.getFlames().clear();
            fire = false;
        }
        if (count == 250) new SoundManager("sound/boom.wav", "explosion");
    }
}
