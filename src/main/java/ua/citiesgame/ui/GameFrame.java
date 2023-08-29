package ua.citiesgame.ui;

import ua.citiesgame.game.Game;
import ua.citiesgame.game.MoveResponse;
import ua.citiesgame.game.players.Player;
import ua.citiesgame.game.players.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFrame extends BasicFrame {
    private final Game game;

    private JLabel statLabel;
    public ArrayList<JButton> buttons = new ArrayList<>();

    public GameFrame(Game game) {
        super("Ukrainian Cities Game: Game");
        this.game = game;
        moveHandler(game.getPrevCity());
    }

    @Override
    public void init() {
        JPanel cityPanel = new JPanel();
        JPanel statsPanel = new JPanel();
        JLabel cityLabel = new JLabel("Enter city name: ");
        statLabel = new JLabel();
        JTextField cityField = new JTextField(10);
        JButton nextRelayButton = new JButton("OK");
        JButton surrenderButton = new JButton("Surrender");
        nextRelayButton.addActionListener(e -> moveHandler(cityField.getText()));
        surrenderButton.addActionListener(e -> surrenderHandler());
        cityPanel.setLayout(new FlowLayout());
        statsPanel.setLayout(new FlowLayout());
        cityPanel.add(cityLabel);
        cityPanel.add(cityField);
        cityPanel.add(nextRelayButton);
        cityPanel.add(surrenderButton);
        statsPanel.add(statLabel);
        buttons.add(nextRelayButton);
        buttons.add(surrenderButton);
        this.getRoot().add(cityPanel);
        this.getRoot().add(statsPanel);
    }

    private void moveHandler(String city){
        if (game.getPlayerByIndex(game.getTurn()).getPlayerType() == PlayerType.HUMAN && city.length() == 0) {
            showMessage("City field can`t be empty!");
            return;
        }
        MoveResponse response = game.move(city);
        switch (response){
            case CITY_IS_NOT_EXIST, CITY_ALREADY_USED, WRONG_CITY ->
                    showMessage(response.getMessage());
            case SURRENDER -> surrenderHandler();
            case OK -> refreshStats();
        }
    }

    private void surrenderHandler(){
        game.surrender();
        if (game.getPlayers().size() == 1){
            handleAllButtons(false, buttons);
            showMessage(game.getPlayerByIndex(game.getTurn()).getNickname() + " won!");
            return;
        }
        refreshStats();
    }

    private void handleAllButtons(boolean isEnabled, ArrayList<JButton> buttons){
        for (JButton button: buttons) button.setEnabled(isEnabled);
    }

    private void refreshStats(){
        Player player = game.getPlayerByIndex(game.getTurn());
        if (game.getPrevCity().isEmpty()) {
            statLabel.setText(player.getNickname() + " starts! Pick the random one city :D");
        } else{
            statLabel.setText(player.getNickname() + ", city for you: " + game.getPrevCity());
        }
    }
}
