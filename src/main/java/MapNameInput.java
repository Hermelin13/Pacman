import javax.swing.*;
import java.awt.*;

public class MapNameInput extends JFrame {
    private final JTextField textField;
    private final JLabel label;

    public String fileName;
    public boolean start = false;

    public MapNameInput(String[] args) {
        super("Map Input");
        setSize(400, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the label and add it to the JFrame
        label = new JLabel("Path to the map you want to play:", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Create the text field and add it to the JFrame
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 40)); // Set the preferred size of the text field
        add(textField, BorderLayout.SOUTH);

        JButton button = new JButton("Play");
        button.addActionListener(e -> {
            fileName = textField.getText();
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    NewGame game = new NewGame(args, fileName);
                    game.execute();
                    return null;
                }
            };
            worker.execute();
        });
        add(button, BorderLayout.EAST);

        setVisible(true);
    }

    public String whichMap (){
        return this.fileName;
    }
}