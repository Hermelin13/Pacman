/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Implementation of move
 */

package pacman.Replay;

import pacman.common.Field;
import pacman.common.MazeObject;
import pacman.game.BasicObject;
import pacman.game.Game;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Move implements UiAction {
    public boolean isPacman;
    public Field.Direction direction;
    public int objectId = 0;
    public BasicObject mazeObject =null;
    static Dictionary<Integer,BasicObject> basicObjectsDictionary = new Hashtable<>();;

    public Move(String line) {

        String[] strArr = line.split(" ");


        objectId = Integer.parseInt(strArr[0]);
        if (basicObjectsDictionary.get(objectId) != null) {
            mazeObject = basicObjectsDictionary.get(objectId);
        }
        if (strArr[2].equalsIgnoreCase("U")) this.direction = Field.Direction.U;
        else if (strArr[2].equalsIgnoreCase("D")) this.direction = Field.Direction.D;
        else if (strArr[2].equalsIgnoreCase("R")) this.direction = Field.Direction.R;
        else if (strArr[2].equalsIgnoreCase("L")) this.direction = Field.Direction.L;
        if (strArr[1].equalsIgnoreCase("true")) {
            this.isPacman = true;
        } else {
            this.isPacman = false;
        }
        this.mazeObject = basicObjectsDictionary.get(objectId);
    }

    public void execute() {
        System.out.println(objectId);
        basicObjectsDictionary.get(objectId).move(this.direction);
    }

    public static void addObject(String line, Game maze) {
        String[] strArr = line.split(" ");
        int row = 0;
        int col = 0;
        Field f;
        BasicObject o;
        int objectId = 0;
        objectId = Integer.parseInt(strArr[1]);
        row = Integer.parseInt(strArr[3]);
        col = Integer.parseInt(strArr[4]);
        f = maze.getField(row, col);
        o = (BasicObject) f.get();
        System.out.println(objectId);
        System.out.println(o);
        basicObjectsDictionary.put(objectId, o);
        return;
    }

    @Override
    public void run() {
        execute();
    }

    @Override
    public void undo() {
        System.out.println("hello");
        if(this.direction== Field.Direction.U)basicObjectsDictionary.get(objectId).moveBack(Field.Direction.D);
        if(this.direction== Field.Direction.D)basicObjectsDictionary.get(objectId).moveBack(Field.Direction.U);
        if(this.direction== Field.Direction.L)basicObjectsDictionary.get(objectId).moveBack(Field.Direction.R);
        if(this.direction== Field.Direction.R)basicObjectsDictionary.get(objectId).moveBack(Field.Direction.L);
    }
}
