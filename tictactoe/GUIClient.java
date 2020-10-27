import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUIClient extends Frame implements ActionListener {
    
    private final TicTacToe game;
    private int player;
    
    private final JTextField msg;
    private final JButton[][] buttons;
    
    GUIClient() {
        game = new TicTacToe();
        player = 1;
        
        setLayout(new FlowLayout());
        setTitle("TicTacToe");
        this.setBackground(Color.WHITE);
        this.setBounds(0, 0, 500, 500);
        this.setLayout(new BorderLayout());
        
        JPanel txts = new JPanel();
        txts.setLayout(new GridLayout(1, 2));
        JTextField title = new JTextField("Welcome to TicTacToe!");
        title.setBackground(Color.WHITE);
        title.setForeground(Color.BLACK);
        title.setEditable(false);
        txts.add(title);
        
        msg = new JTextField();
        msg.setText("Player #" + player + "'s (" + (player == 1 ? "X" : "O") + ") turn");
        title.setBackground(Color.WHITE);
        title.setForeground(Color.BLACK);
        title.setEditable(false);
        txts.add(msg);
        this.add(txts, BorderLayout.NORTH);
        
        JPanel bts = new JPanel();
        bts.setLayout(new GridLayout(3,3));
        
        buttons = new JButton[3][3];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(this);
                buttons[i][j] = b;
                bts.add(b);
            }
        }
        
        this.add(bts, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        bt.setEnabled(false);
        
        all:
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == bt) {
                    game.fillCell(j, i);
                    bt.setText(player == 1 ? "X" : "O");
                    break all;
                }
            }
        }
        
        if (game.isFinished()) {
            msg.setText("Player #" + player + " is the winner!");
            end();
        } else if (game.isFilled()) {
            msg.setText("It's a tie!");
            end();
        } else {
            if (player == 1) player = 2;
            else player = 1;
            
            msg.setText("Player #" + player + "'s (" + (player == 1 ? "X" : "O") + ") turn");
        }
    }
    
    private void end() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
    
    public static void main(String[] args) {
        new GUIClient();
    }
    
}
