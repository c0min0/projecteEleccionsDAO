package controller;

import static view.Print.println;

//TODO: Implementar comAutonomaDAO
//TODO: Fer package controller.comAutonoma

public class Main {
    public static void main(String[] args) {
        try {
            Controller.menuInicial();
        } catch (Exception e) {
            println("Ha hagut una problema, disculpa les molèsties. :(");
        }
    }
}