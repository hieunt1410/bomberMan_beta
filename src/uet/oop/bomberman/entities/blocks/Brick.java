package uet.oop.bomberman.entities.blocks;

import javafx.scene.image.Image;
import uet.oop.bomberman.CreateMap;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntityList;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ConcurrentModificationException;


public class Brick extends Entity {
    private boolean broken;
    private int cnt = 0;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        broken = false;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    @Override
    public void update() {
        if (broken) {
            animate = animate > 100 ? 0 : animate + 1;
            cnt++;
            setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 40).getFxImage());
            CreateMap.setGrid(this.y / Sprite.SCALED_SIZE, this.x / Sprite.SCALED_SIZE, ' ');
            if (cnt == 50){
                Game.entityList.getBricks().remove(this);
                if(Game.entityList.getBomberman().getBuffItem() >= 20) return;
                int x = (int) (Math.random() * 10) + 1;
                if(x <= 3) Game.entityList.getBomberman().increaseBuffItem();
                if(x == 2) Game.entityList.addItems(new SpeedItem(this.getX()/Sprite.SCALED_SIZE,
                        this.getY()/Sprite.SCALED_SIZE, Sprite.powerup_speed.getFxImage() ));
                else if(x == 3) Game.entityList.addItems(new BombItem(this.getX()/Sprite.SCALED_SIZE,
                        this.getY()/Sprite.SCALED_SIZE, Sprite.powerup_bombs.getFxImage() ));
                else if(x == 1) Game.entityList.addItems(new FlameItem(this.getX()/Sprite.SCALED_SIZE,
                        this.getY()/Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage() ));
            }
        }
    }

}
