/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of replay view
 */


import adapters.ReplayAdapter;
import game.Game;

import common.Maze;
import game.GhostMoving;

import view.FieldView;

import java.awt.*;


import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReplayPresenter {

    ReplayAdapter ButtonAdapter;
    private JLabel Llives;
    private JLabel LKeys;
    private JLabel LWin;
    private JPanel mainPanel;
    private JFrame frame;
    private final Maze maze;
    GhostMoving move;
    String [] args;

    public ReplayPresenter(Maze maze, String name){
        this.maze = maze;
        this.ButtonAdapter = new ReplayAdapter(((Game) maze).pacman(),(Game) maze, name);
    }
    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    private void initializeInterface() {
        frame = new JFrame("Replay");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setPreferredSize(new Dimension(600, 700));
        frame.setResizable(false);
        frame.addKeyListener(new ReplayAdapter(((Game) maze).pacman(),(Game) maze));

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
                mainPanel.add(field);
            }
        }

        JButton button1 = new JButton("Backwards");
        JButton button2 = new JButton("Play");
        JButton button3 = new JButton("+1");
        JButton button4 = new JButton("-1");
        JButton button5 = new JButton("Stop");
        button1.addActionListener(e -> {
            ButtonAdapter.buttonPressed(1);
            frame.requestFocusInWindow();
        });
        button2.addActionListener(e -> {
            ButtonAdapter.buttonPressed(2);
            frame.requestFocusInWindow();
        });
        button3.addActionListener(e -> {
            ButtonAdapter.buttonPressed(3);
            frame.requestFocusInWindow();
        });
        button4.addActionListener(e -> {
            ButtonAdapter.buttonPressed(4);
            frame.requestFocusInWindow();
        });
        button5.addActionListener(e -> {
            ButtonAdapter.buttonPressed(5);
            frame.requestFocusInWindow();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.add(button5);

        JPanel scorePanel = new JPanel(new BorderLayout());

        JPanel scoreLabels = new JPanel(new FlowLayout(FlowLayout.CENTER));
        LKeys = new JLabel("Keys: 0");
        LWin = new JLabel("");
        Llives = new JLabel("Lives: " + ((Game) maze).pacman().getLives());
        scoreLabels.add(LWin);
        scoreLabels.add(LKeys);
        scoreLabels.add(Llives);

        scorePanel.add(buttonPanel, BorderLayout.NORTH);
        scorePanel.add(scoreLabels, BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.getContentPane().add(scorePanel, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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

        }
        if (win) {
            Llives.setText("");
            LKeys.setText("");
            LWin.setText("YOU WIN!");
        }
    }
}

