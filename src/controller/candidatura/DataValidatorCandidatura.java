package controller.candidatura;

import controller.DataValidator;


public class DataValidatorCandidatura extends DataValidator {
    /**
     * Valida un nom de persona segons la seva longitud.
     * @param nom Nom a validar.
     * @return True si el nom té menys de 30 caràcters, false si no.
     */
    static boolean isNomCurt(String nom) {
        return DataValidator.isVarchar30(nom);
    }
    /**
     * Valida un nom de persona segons la seva longitud.
     * @param nom Nom a validar.
     * @return True si el nom té menys de 30 caràcters, false si no.
     */
    static boolean isNomLlarg(String nom) {
        return DataValidator.isVarchar30(nom);
    }

    /**
     * Valida un cognom de persona segons la seva longitud.
     * @param cog1 Cognom a validar.
     * @return True si el cognom té menys de 30 caràcters, false si no.
     */
    static boolean isCog1(String cog1) {
        return DataValidator.isVarchar30(cog1);
    }

    /**
     * Valida un segon cognom de persona segons la seva longitud.
     * @param cog2 Segon cognom a validar.
     * @return True si el segon cognom té menys de 30 caràcters, false si no.
     */
    static boolean isChar(String char6) {
        return DataValidator.isChar6(char6);
    }

    /**
     * Valida un sexe comprovant que sigui M o F.
     * @param sexe Sexe a validar.
     * @return True si el sexe és M o F, false si no.
     */
    static boolean isSexe(String sexe) {
        return sexe.equals("M") || sexe.equals("F") || sexe.equals("m") || sexe.equals("f");
    }

    /**
     * Valida un DNI comprovant que tingui 8 caràcters.
     * @param dni DNI a validar.
     * @return True si el DNI té 8 caràcters, false si no.
     */
    static boolean isDNI(String dni) {
        return dni.length() == 8;
    }

}
