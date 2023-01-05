package game;

import gui_main.GUI;

public class Main
{
    public static void main(String[] args)
    {
        GUI gui = new GUI();
        GameController gameController = new GameController(gui);
        gameController.run();
    }
}
