package controller.candidatura;

import controller.DataValidator;

/**
 * Classe que conté els validadors de dades de la taula candidatures.
 */
public class DataValidatorCandidatura extends DataValidator {

    /**
     * Comprova si un String és un valor vàlid pel camp eleccio_id de la taula candidatures.
     * És l'únic id de la base de dades que no és INT UNSIGNED, sinó TINYINT UNSIGNED
     * @param eleccio_id String a comprovar.
     * @return True si és un valor vàlid pel camp eleccio_id, false si no ho és.
     */
    static boolean isEleccioId(String eleccio_id) {
        return isInt(eleccio_id);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp codi_candidatura de la taula candidatures.
     * @param codiCandidatura String a comprovar.
     * @return True si és un valor vàlid pel camp codi_candidatura, false si no ho és.
     */
    static boolean isCodiCandidatura(String codiCandidatura) {
        return isChar6(codiCandidatura);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp nom_curt de la taula candidatures.
     * @param nomCurt String a comprovar.
     * @return True si és un valor vàlid pel camp nom_curt, false si no ho és.
     */
    static boolean isNomCurt(String nomCurt) {
        return isVarchar50(nomCurt);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp nom_llarg de la taula candidatures.
     * @param nomLlarg String a comprovar.
     * @return True si el nom llarg és vàlid, false si no.
     */
    static boolean isNomLlarg(String nomLlarg) {
        return isVarchar150(nomLlarg);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp codi_acumulacio_provincia de la taula candidatures.
     * @param codiAcProvincia String a comprovar.
     * @return True si és un valor vàlid pel camp codi_acumulacio_provincia, false si no ho és.
     */
    static boolean isCodiAcProvincia(String codiAcProvincia) {
        return isChar6(codiAcProvincia);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp codi_acumulacio_ca de la taula candidatures.
     * @param codiAcCA String a comprovar.
     * @return True si és un valor vàlid pel camp codi_acumulacio_ca, false si no ho és.
     */
    static boolean isCodiAcCA(String codiAcCA) {
        return isChar6(codiAcCA);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp codi_acumulacio_nacional de la taula candidatures.
     * @param codiAcNacional String a comprovar.
     * @return True si és un valor vàlid pel camp codi_acumulacio_nacional, false si no ho és.
     */
    static boolean isCodiAcNacional(String codiAcNacional) {
        return isChar6(codiAcNacional);
    }

}
