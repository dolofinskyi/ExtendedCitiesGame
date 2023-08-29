package ua.citiesgame.game;

public enum MoveResponse {
    OK("Ok"),
    CITY_ALREADY_USED("City already used!"),
    CITY_IS_NOT_EXIST("City does not exist!"),
    WRONG_CITY("City is not match!"),
    SURRENDER("Surrender");

    private final String message;

    MoveResponse(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
