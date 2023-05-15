package controller.comAutonoma;

import controller.DataValidator;

public class DataValidatorComAutonoma extends DataValidator {
    /**
     * Comprova si un String és un valor vàlid pel camp
     * comunitat_aut_id de la taula comunitats_autonomes.
     * @param comAutonomaId String a comprovar.
     * @return True si és un valor vàlid pel camp comunitat_aut_id, false si no ho és.
     */
    static boolean isComAutonomaId(String comAutonomaId) {
        return isInt(comAutonomaId);
    }

    /**
     * Comprova si un String és un valor vàlid pel
     * camp nom de la taula comunitats_autonomes.
     * @param nom String a comprovar.
     * @return True si és un valor vàlid pel camp nom, false si no ho és.
     */
    static boolean isNom (String nom) {
        return isVarchar45(nom);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp
     * codi_ine de la taula comunitats_autonomes.
     * @param codiIne String a comprovar.
     * @return True si és un valor vàlid pel camp codi_ine, false si no ho és.
     */
    static boolean isCodiIne (String codiIne) {
        return isChar2(codiIne);
    }
}
