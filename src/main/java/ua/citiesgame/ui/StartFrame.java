package ua.citiesgame.ui;

import ua.citiesgame.game.Game;
import ua.citiesgame.game.players.Player;
import ua.citiesgame.game.players.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class StartFrame extends BasicFrame {
    private final int MAX_TEXTAREA_CHARS = 10;
    private final Game game;

    public StartFrame(Game game) {
        super("Ukrainian Cities Game: Lobby");
        this.game = game;
    }

    @Override
    public void init() {
        JPanel statsGroup = new JPanel();
        JPanel buttonsGroup = new JPanel();
        JPanel nicknameGroup = new JPanel();
        JLabel nicknameLabel = new JLabel("Enter nickname:");
        JLabel statsLabel = new JLabel("Pls, add a new player :)");
        JButton addPlayerButton = new JButton("Add Player");
        JButton removePlayerButton = new JButton("Remove Player");
        JButton startButton = new JButton("Start!");
        JTextField nicknameField = new JTextField(MAX_TEXTAREA_CHARS);

        nicknameField.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (nicknameField.getText().length() >= MAX_TEXTAREA_CHARS){
                            e.consume();
                        }
                    }
                }
        );

        addPlayerButton.addActionListener(e -> {
            String nickname = nicknameField.getText().strip();

            if (nickname.length() < 4) {
                showMessage("Nickname must be at least 4 characters!");
                return;
            }

            if (getAllNicknames().contains(nickname)) {
                showMessage("Nickname is already exist!");
                return;
            }

            PlayerType type = nickname.equals("comp") ? PlayerType.COMPUTER : PlayerType.HUMAN;
            Player newPlayer = new Player(nickname, type);
            game.addPlayer(newPlayer);
            nicknameField.setText("");
            startButton.setEnabled(game.getPlayers().size() >= 2);
            statsLabel.setText("Players: " + String.join(", ", getAllNicknames()));
        });


        removePlayerButton.addActionListener(e -> {
            String nickname = nicknameField.getText().strip();
            game.removePlayer(game.getPlayerByNickname(nickname));
            nicknameField.setText("");
            startButton.setEnabled(game.getPlayers().size() >= 2);
            statsLabel.setText("Players: " + String.join(", ", getAllNicknames()));
        });


        startButton.addActionListener(e -> {
            if (game.getPlayers().size() >= 2){
                this.dispose();
                new GameFrame(game);
            }
        });

        nicknameGroup.setLayout(new FlowLayout());
        buttonsGroup.setLayout(new FlowLayout());
        buttonsGroup.add(addPlayerButton);
        buttonsGroup.add(removePlayerButton);
        buttonsGroup.add(startButton);
        startButton.setEnabled(false);
        nicknameGroup.add(nicknameLabel);
        nicknameGroup.add(nicknameField);
        statsGroup.add(statsLabel);
        this.getRoot().add(nicknameGroup);
        this.getRoot().add(buttonsGroup);
        this.getRoot().add(statsGroup);
    }

    public ArrayList<String> getAllNicknames(){
        List<String> nicknames = game.getPlayers().stream().map(Player::getNickname).toList();
        return new ArrayList<>(nicknames);
    }
}
