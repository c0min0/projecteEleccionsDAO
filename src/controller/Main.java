package controller;

import static view.Print.println;

//TODO: Acabar d'implementar CandidaturaDAO
//TODO: Fer ControllerCandidatura

//TODO: Implementar comAutonomaDAO
//TODO: Fer ControllerComAutonoma

public class Main {
    public static void main(String[] args) {
        try {
            Controller.menuInicial();
        } catch (Exception e) {
            println("Ha hagut una problema, disculpa les molèsties. :(");
        }
    }
}