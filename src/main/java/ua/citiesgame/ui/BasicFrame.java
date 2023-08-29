package ua.citiesgame.ui;

import javax.swing.*;

public abstract class BasicFrame extends JFrame {
    private final JPanel root = new JPanel();

    public BasicFrame(String title){
        setTitle(title);
        setResizable(false);
        setSize(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        add(root);
        init();
        setVisible(true);
    }

    abstract void init();

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
    public JPanel getRoot(){
        return root;
    }
}
