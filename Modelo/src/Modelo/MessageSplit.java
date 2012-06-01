package Modelo;

public class MessageSplit {

    public double[] parse(String message) {
        String[] tokens = message.split("//");
        double[] tokensD = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            tokensD[i] = Double.parseDouble(tokens[i]);
        }
        return tokensD;
    }
}
