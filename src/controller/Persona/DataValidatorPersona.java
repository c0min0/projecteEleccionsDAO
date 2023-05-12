package controller.Persona;

import controller.DataValidator;

/**
 * Classe que conté els validadors de dades de la taula persones.
 */
public class DataValidatorPersona extends DataValidator {
    /**
     * Valida un nom de persona segons la seva longitud.
     * @param nom Nom a validar.
     * @return True si el nom té menys de 30 caràcters, false si no.
     */
    static boolean isNom(String nom) {
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
    static boolean isCog2(String cog2) {
        return DataValidator.isVarchar30(cog2);
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
     * Valida una data comprovant que tingui el format dd/mm/aaaa.
     * @param data Data a validar.
     * @return True si la data té el format dd/mm/aaaa, false si no.
     */
    public static boolean isDataNaix(String data) {
        return isDate(data);
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
