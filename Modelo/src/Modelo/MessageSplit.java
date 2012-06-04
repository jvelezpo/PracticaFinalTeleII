package Modelo;

public class MessageSplit {

    public String[] parse(String message) {
        String[] tokens = message.split("//");
        return tokens;
    }
}
