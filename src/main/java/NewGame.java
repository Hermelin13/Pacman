/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of gameplay gui
 */

import common.Maze;
import game.Game;
import game.MazeConfigure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NewGame {

    private final String [] args;
    private final String fileName;

    public NewGame (String [] mainargs, String fileName){
        this.args = mainargs;
        this.fileName = fileName;
    }

    public void execute () {
        /*if (args.length < 1) {
            System.err.println("Usage: MazeConfigure <filename>");
            System.exit(51);
        }*/
        Game game = null;

        MazeConfigure mc = new MazeConfigure();
        try (
                BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            int rows = Integer.parseInt(line.split(" ")[0]);
            int cols = Integer.parseInt(line.split(" ")[1]);


            mc.startReading(rows, cols);

            while ((line = br.readLine()) != null) {
                if (!mc.processLine(line)) {
                    System.err.println("Invalid input format");
                    System.err.println(line);
                    System.exit(1);
                }
            }

            if (!mc.stopReading() || !mc.isInputValid()) {
                System.err.println("Invalid input format");
                System.exit(1);
            }

            Maze maze = mc.createMaze();
            game = (Game) maze;
            // Do something with the created maze
        } catch (
                IOException e) {
            System.err.println("Error reading file: " + fileName);
            System.exit(1);
        }


        MazePresenter mazePresenter = new MazePresenter(game, args, fileName);
        mazePresenter.open();
    }
}


