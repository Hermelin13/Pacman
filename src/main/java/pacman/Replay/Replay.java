/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of replay
 */

package pacman.Replay;

import pacman.game.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.Thread.sleep;

public class Replay {

    public Game maze;
    public File file;
    public int step=0;
    private static Stack<UiAction> doneMoves=new Stack<>();

    public static  List<UiAction> moves=new ArrayList<>();



    public Replay(Game maze, File file) {
        moves.clear();
        this.maze = maze;
        try {
            this.file = file;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("created")) {
                    System.out.println(data);
                    Move.addObject(data,maze);
                    continue;
                }
                Move newMove = new Move(data);
                System.out.println(newMove);
                System.out.println(moves.size());
                moves.add(newMove);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void execute() throws InterruptedException {
        while (step < moves.size() && !Thread.currentThread().isInterrupted()) {
            Thread. sleep(400);
            nextStep();
            if(Thread.interrupted())
            {
                return;
            }
        }
    }

    public void executeBackward() throws  InterruptedException{
        while (step>0 && !Thread.currentThread().isInterrupted()) {
            Thread. sleep(400);
            prevStep();
            if(Thread.interrupted())
            {
                return;
            }
        }
    }

    public void nextStep()
    {
        if(step<moves.size()) {
            System.out.println(step);
            System.out.println(moves.size());
            System.out.println(moves.get(step).objectId);
            moves.get(step).run();
            doneMoves.add(moves.get(step));
            step++;
        }
    }

    //how to go back
    public void prevStep()
    {
        if(step>0) {
            doneMoves.pop().undo();
            step--;
        }
    }
}
