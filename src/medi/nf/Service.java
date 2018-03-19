/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

/**
 *
 * @author CRISTANTE
 */
public enum Service {
    PEDIATRIE("Pédiatrie", "Clinique"),
    SOINS_INTENSIFS("Soins intensifs", "Clinique"),
    GENERALISTE("Généraliste", "Clinique"),
    LABORATOIRE("Laboratoire d'analyses", "Médico-Technique"),
    ANAPATHOLOGIE("Anapathologie", "Médico-Technique"),
    HEMATOLOGIE("Hématologie", "Médico-Technique"),
    ANESTHESIE("Anesthésie", "Anesthésie"),
    IMAGERIE("Imagerie", "Imagerie"),
    RECHERCHE("Recherche", "Médico-Technique"),
    SANTE_PUBLIQUE("Santé publique", "Médico-Technique"),
    CANCER("Cancer et maladies du sang", "Clinique"),
    COUPLE_ENFANT("Couple Enfant", "Clinique"),
    ENDOCRINOLOGIE("Endocrinologie", "Clinique"),
    UROLOGIE("Urologie", "Clinique"),
    NEPHROLOGIE("Néphrologie hémodialyse", "Clinique"),
    TRANSPLANTATION("Transplantation", "Clinique"),
    URGENCES("Urgences", "Urgences"),
    GERONTOLOGIE("Gérontologie", "Clinique"),
    PSYCHIATRIE("Psychiatrie", "Clinique"),
    NEUROLOGIE("Neurologie", "Clinique"),
    CARDIOLOGIE("Cardiologie", "Clinique"),;

    private String name = "";
    private String type = "";

    Service(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    public static String getEnumByString(String code) {
        for (Service s : Service.values()) {
            if (code == s.name) {
                return s.name();
            }
        }
        return null;
    }
}
