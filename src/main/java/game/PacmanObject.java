/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of pacman
 */

package game;

import common.Field;

import java.util.logging.Logger;


public class PacmanObject extends BasicObject {

    int lives;
    int hasKeys;
    boolean hasTarget;
    Logger logger;

    public PacmanObject(Field f, Logger logger) {
        super(f);
        this.lives = 3;
        this.hasKeys = 0;
        this.hasTarget = false;
        this.logger = logger;
    }

    public boolean isPacman() {
        return true;
    }


    @Override
    public int getLives() {
        return this.lives;
    }

    @Override
    public int getKeys() {
        return this.hasKeys;
    }


    @Override
    public boolean getTarget() {
        return this.hasTarget;
    }

    public void hit() {
        this.lives -= 1;
    }
    public void noHit(){
        this.lives+=1;
    }

    public void gotKey() {
        this.hasKeys += 1;
    }

    public void notGotKey(){
        this.hasKeys -=1;
    }

    public void gotTarget () {
        this.hasTarget = true;
    }

    public void notGotTarget()
    {
        this.hasTarget = false;
    }

    @Override
    public boolean canMove(Field.Direction direction) {
        return this.mainF.nextField(direction).canMove();
    }

    @Override
    public boolean move(Field.Direction direction) {
        try {
            logger.info(this.hashCode() + " " + isPacman() + " " + direction);
            Field tmp = (Field) this.mainF.nextField(direction);
            tmp.put(this);
            this.mainF.remove(this);
            setField(tmp);

        } catch (UnsupportedOperationException exception) {
            System.err.println((exception.getMessage()));
            return false;
        }
        return true;
    }


    @Override
    public boolean moveBack(Field.Direction direction) {
        System.out.println("move back");
        PathField tmp = (PathField) this.mainF.nextField(direction);
        tmp.putBack(this);
        this.mainF.removeBack(this);
        setField(tmp);

        return  true;
    }
}
