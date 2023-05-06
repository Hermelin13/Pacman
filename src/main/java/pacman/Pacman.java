/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of APP
 */

package pacman;

import pacman.common.Maze;
import pacman.game.Game;
import pacman.game.MazeConfigure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pacman {


    public static void main(String[] args) {
        NewGame game = new NewGame(args);
        game.execute();
    }

}
