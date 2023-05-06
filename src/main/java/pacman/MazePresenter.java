/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of app gui
 */

package pacman;

import pacman.adapters.PlayerAdapterAZDW;
import pacman.adapters.PlayerAdapterMouse;
import pacman.adapters.ReplayAdapter;
import pacman.common.MazeObject;
import pacman.game.Game;

import pacman.common.Maze;
import pacman.game.GhostMoving;
import pacman.game.GhostObject;
import pacman.game.PathField;
import pacman.view.FieldView;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazePresenter {
    KeyListener PlayerAdapterAZDW;
    MouseListener PlayerAdapterMouse;
    private JLabel Llives;
    private JLabel LKeys;
    private JLabel LWin;
    private JPanel mainPanel;
    private JFrame frame;
    private final Maze maze;
    GhostMoving move;
    String [] args;

    public MazePresenter(Maze maze, String [] args) {
        this.maze = maze;
        this.PlayerAdapterAZDW = new PlayerAdapterAZDW(((Game) maze).pacman());
        this.PlayerAdapterMouse = new PlayerAdapterMouse(((Game) maze).pacman(), (Game) maze);
        this.args = args;
    }

    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String) null, var2);
        }

    }

    private void initializeInterface() {
        frame = new JFrame("Lidl Pacman");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setPreferredSize(new Dimension(600, 700));
        frame.setResizable(false);
        frame.addKeyListener(PlayerAdapterAZDW);

        int rows = this.maze.numRows();
        int cols = this.maze.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        mainPanel = new JPanel(layout);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(this.maze.getField(i, j), new FieldView.FieldChangedCallback() {
                    @Override
                    public void onFieldChanged() {
                        update();
                    }
                });
                field.addMouseListener(PlayerAdapterMouse);
                mainPanel.add(field);
            }
        }

        JPanel scorePanel = new JPanel(new BorderLayout());

        JPanel scoreLabels = new JPanel(new FlowLayout(FlowLayout.CENTER));
        LKeys = new JLabel("Keys: 0");
        LWin = new JLabel("");
        Llives = new JLabel("Lives: " + ((Game) maze).pacman().getLives());
        scoreLabels.add(LWin);
        scoreLabels.add(LKeys);
        scoreLabels.add(Llives);

        JPanel replayPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton replayButton = new JButton("Replay");
        replayButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter replay file name:");
            if (input != null) {

                new Thread(() -> {
                    ReplayGame Rgame = new ReplayGame(args, input);
                    Rgame.execute();
                }).start();
                frame.requestFocusInWindow();
            }
        });
        replayPanel.add(replayButton);

        scorePanel.add(scoreLabels, BorderLayout.WEST);
        scorePanel.add(replayPanel, BorderLayout.EAST);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.getContentPane().add(scorePanel, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        move = new GhostMoving((PathField) ((Game) maze).pacman().getField(),(GhostObject) maze.ghosts().get(0),(Game) maze);
        move.start();
        frame.requestFocusInWindow();
    }

    public void update() {
        int lives = ((Game) maze).pacman().getLives();
        int keys = ((Game) maze).pacman().getKeys();
        boolean win = ((Game) maze).pacman().getTarget();
        Llives.setText("Lives: " + lives);
        LKeys.setText("Keys: " + keys);
        if (lives <= 0) {
            Llives.setText("");
            LKeys.setText("");
            LWin.setText("GAME OVER!");
            frame.removeKeyListener(PlayerAdapterAZDW);
            Component[] components = mainPanel.getComponents();
            for (Component component : components) {
                if (component instanceof FieldView) {
                    component.removeMouseListener(PlayerAdapterMouse);
                }
            }
            move.overgame();
        }
        if (win) {
            Llives.setText("");
            LKeys.setText("");
            LWin.setText("YOU WIN!");
            frame.removeKeyListener(PlayerAdapterAZDW);
            Component[] components = mainPanel.getComponents();
            for (Component component : components) {
                if (component instanceof FieldView) {
                    component.removeMouseListener(PlayerAdapterMouse);
                }
            }
            move.overgame();

        }
    }
}
