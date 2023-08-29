package ua.citiesgame.game.players;

public class Player {
    private final String nickname;
    private final PlayerType type;

    public Player(String nickname, PlayerType type){
        this.nickname = nickname;
        this.type = type;
    }

    public String getNickname(){
        return nickname;
    }

    public PlayerType getPlayerType(){
        return type;
    }
}
