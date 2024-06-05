package uk.ac.aber.cs221.gp02;

import uk.ac.aber.cs221.gp02.board.Color;

public class Player {

    private Color color;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
