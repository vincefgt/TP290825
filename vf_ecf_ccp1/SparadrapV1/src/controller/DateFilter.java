package controller;

public enum DateFilter {
    TODAY("Aujourd'hui"),
    THIS_WEEK("Cette semaine"),
    THIS_MONTH("Ce mois"),
    THIS_YEAR("Cette année"),
    ALL_TIME("Toutes les dates");

    private final String displayName;

    DateFilter(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}