package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.Menu;

import uet.oop.bomberman.CreateMap;
import uet.oop.bomberman.Game;

import uet.oop.bomberman.entities.blocks.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.entities.EntityList.bomberman;

public class Bomber extends Entity {
    private boolean dieTime = false;
    private boolean isAlive;
    private int speed = Sprite.SCALED_SIZE / 8;
    private int speedItemDuration;
    private boolean hasTouchedSpeedItem = false;
    private boolean once = false;
    public List<Bomb> bombs;

    public void setDieTime(boolean dieTime) {
        this.dieTime = dieTime;
    }

    public boolean isDieTime() {
        return dieTime;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        isAlive = true;
        bombs = new ArrayList<>();
    }


    public void setSpeedItemDuration(int speedItemDuration) {
        this.speedItemDuration = speedItemDuration;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setHasTouchedSpeedItem(boolean hasTouchedSpeedItem) {
        this.hasTouchedSpeedItem = hasTouchedSpeedItem;
    }

    @Override
    public void update() throws IOException {
        if (isAlive) {
            for (int i = 0; i < bomberman.bombs.size(); i++) {
                if (!this.intersects(bomberman.bombs.get(i)) && bomberman.bombs.get(i).isAllow()) {
                    bomberman.bombs.get(i).setAllow(false);
                }
            }
        }
        if (isHasTouchedSpeedItem()) {
            Menu.Score++;
            hasTouchedSpeedItem = false;
        }
         if (speedItemDuration > 0) {
                speedItemDuration--;
                if (!once) {
                    speed += 2;
                    once = true;
                }
            } else {
                if (once) {
                    once = false;
                    speed -= 2;
                }
            }
        } else {
            CreateMap.createMapLevel(Game.getCurLevel());
        }
    }

    public void goUp() {
        for (int i = 1; i <= this.speed; ++i) {
            this.y -= 1;
            animate = animate > 100 ? 0 : animate + 1;
            if (checkWall() || checkBrick() || checkBomb()) {
                this.y += 1;
                if (this.x % Sprite.SCALED_SIZE <=  Sprite.SCALED_SIZE / 4) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE);
                }
                if(this.x % Sprite.SCALED_SIZE >= 3 * Sprite.SCALED_SIZE / 4) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE + 1);
                }
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 45).getFxImage());
    }

    public void goDown() {
        for (int i = 1; i <= this.speed; ++i) {
            this.y += 1;
            animate = animate > 100 ? 0 : animate + 1;
            if (checkWall() || checkBrick() || checkBomb()) {
                this.y -= 1;
                if (this.x % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 4) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE);
                }
                if (this.x % Sprite.SCALED_SIZE >= 3 * Sprite.SCALED_SIZE / 4) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE + 1);
                }
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 45).getFxImage());
    }

    public void goLeft() {
        for (int i = 1; i <= this.speed; ++i) {
            this.x -= 1;
            animate = animate > 100 ? 0 : animate + 1;
            if (checkWall() || checkBrick() || checkBomb()) {
                this.x += 1;
                if (this.y % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 4) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE);
                }
                if (this.y % Sprite.SCALED_SIZE >= 3 * Sprite.SCALED_SIZE / 4) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE + 1);
                }
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 45).getFxImage());
    }

    public void goRight() {
        for (int i = 1; i <= this.speed; ++i) {
            this.x += 1;
            animate = animate > 100 ? 0 : animate + 1;
            if (checkWall() || checkBrick() || checkBomb()) {
                this.x -= 1;
                if (this.y % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 4) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE);
                }
                if (this.y % Sprite.SCALED_SIZE >= 3 * Sprite.SCALED_SIZE / 4) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE + 1);
                }
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 45).getFxImage());
    }

    public void placeBomb() {
        Bomb.setBomb();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x + 4, y);
    }
}
