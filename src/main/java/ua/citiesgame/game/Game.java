package ua.citiesgame.game;

import ua.citiesgame.dataloaders.CityLoader;
import ua.citiesgame.game.players.Player;
import ua.citiesgame.game.players.PlayerType;
import ua.citiesgame.ui.StartFrame;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static final ArrayList<Player> players = new ArrayList<>();
    private int turn = 0;
    private final ArrayList<String> gameCities = new ArrayList<>();
    private String prevCity = "";

    public void start() {
        new StartFrame(this);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void addPlayer(Player player){
        if (!players.contains(player)) players.add(player);
    }

    public void removePlayer(Player player){
        if (player != null) players.remove(player);
    }

    public int getTurn(){
        return turn;
    }

    public MoveResponse move(String city){
        MoveResponse response =  switch (players.get(turn).getPlayerType()) {
            case HUMAN -> humanMove(city);
            case COMPUTER -> compMove(city);
        };
        checkComputerMove();
        return response;
    }

    public MoveResponse compMove(String city) {
        if (prevCity.isEmpty()){
            chooseRandCity(CityLoader.getCities());
            setTurn(turn + 1);
            return MoveResponse.OK;
        }
        ArrayList<String> availableCities = getAvailableCities(city, CityLoader.getCities());
        if (availableCities.size() == 0) return MoveResponse.SURRENDER;
        chooseRandCity(availableCities);
        setTurn(turn + 1);
        return MoveResponse.OK;
    }

    public MoveResponse humanMove(String city) {
        if (!isCityPassed(prevCity, city)) return MoveResponse.WRONG_CITY;
        if (!isCityExist(city)) return MoveResponse.CITY_IS_NOT_EXIST;
        if (isCityAlreadyUsed(city)) return MoveResponse.CITY_ALREADY_USED;
        prevCity = city;
        gameCities.add(city);
        setTurn(turn + 1);
        return MoveResponse.OK;
    }

    public Player getPlayerByIndex(int relay){
        return players.get(relay);
    }

    public Player getPlayerByNickname(String nickname){
        for (Player player: players){
            if (player.getNickname().equals(nickname)) return player;
        }
        return null;
    }

    public int randInt(int bound){
        return new Random().nextInt(bound);
    }

    public boolean isCityPassed(String prevCity, String currCity){
        if (prevCity.length() == 0) return true;
        char prevCityLastLetter = Character.toLowerCase(prevCity.charAt(prevCity.length() - 1));
        char currCityFirstLetter = Character.toLowerCase(currCity.charAt(0));
        return currCityFirstLetter == prevCityLastLetter;
    }

    public boolean isCityExist(String city) {
        for (String c : CityLoader.getCities()) {
            if (c.equalsIgnoreCase(city)) return true;
        }

        return false;
    }

    public void surrender(){
        if (players.size() == 1) return;
        removePlayer(players.get(turn));
        setTurn(turn);
        checkComputerMove();
    }

    public void setTurn(int turn){
        this.turn = turn % players.size();
    }

    public void checkComputerMove(){
        if (players.get(turn).getPlayerType() == PlayerType.COMPUTER){
            compMove(prevCity);
        }
    }

    private void chooseRandCity(ArrayList<String> cities){
        int rand = randInt(cities.size());
        String chosenCity = cities.get(rand);
        prevCity = chosenCity;
        gameCities.add(chosenCity);
    }

    private ArrayList<String> getAvailableCities(String city, ArrayList<String> cities){
        ArrayList<String> availableCities = new ArrayList<>();
        for (String c: cities) {
            if (isCityPassed(city, c) && !isCityAlreadyUsed(c)) availableCities.add(c);
        }
        return availableCities;
    }

    public boolean isCityAlreadyUsed(String city) {
        for (String c : gameCities) {
            if (c.equalsIgnoreCase(city)) return true;
        }
        return false;
    }

    public String getPrevCity(){
        return prevCity;
    }

}
