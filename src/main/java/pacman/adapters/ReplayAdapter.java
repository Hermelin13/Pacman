/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation replay buttons and key listener
 */

package pacman.adapters;

import pacman.Replay.Replay;
import pacman.game.Game;
import pacman.game.PacmanObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ReplayAdapter extends KeyAdapter{

    boolean stop = false;
    boolean next = false;
    boolean previsious = false;
    boolean Pforward = false;
    boolean Pbackward = false;
    public static File myFile;
    public static Replay mewReplay;
    Thread executeThread;

    Thread executeBackThread;

    public ReplayAdapter(PacmanObject pacman, Game maze, String name) {
        myFile = new File(name);
        mewReplay= new Replay(maze, myFile);
    }

    public ReplayAdapter(PacmanObject pacman, Game maze) {
        mewReplay= new Replay(maze, myFile);
    }

    public void stop(){
        try {
            executeBackThread.interrupt();
        }
        catch (Exception e){}

        try {
            executeThread.interrupt();
        }
        catch (Exception e) {}
    }

    public void buttonPressed(int button) {
        switch (button) {
            case 1 -> {
                stop();
                executeBackThread = new Thread(() -> {
                    try {
                        mewReplay.executeBackward();
                    } catch (InterruptedException ev) {
                    }
                });
                executeBackThread.start();
            }
            case 2 -> {
                    stop();
                    executeThread = new Thread(() -> {
                        try {
                            mewReplay.execute();
                        } catch (InterruptedException ev) {
                        }
                    });
                    executeThread.start();
                }
            case 3 -> {
                stop();
                mewReplay.nextStep();
            }
            case 4 -> {
                stop();
                mewReplay.prevStep();
            }
            case 5 -> stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'r') {
            stop();
            executeThread = new Thread(() -> {
                try {
                    mewReplay.execute();
                } catch (InterruptedException ev) {
                }
            });
            executeThread.start();
        }
        if (e.getKeyChar() == 'n') {
            stop();
            mewReplay.nextStep();
        }
        if (e.getKeyChar() == 'p') {
            stop();
            mewReplay.prevStep();
        }
        if(e.getKeyChar()=='b')
        {
            stop();
            executeBackThread = new Thread(() -> {
                try {
                    mewReplay.executeBackward();
                } catch (InterruptedException ev) {
                }
            });
            executeBackThread.start();
        }

        if (e.getKeyChar() == ' ') {
            stop();
        }
    }
}
