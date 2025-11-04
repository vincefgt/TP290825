package controller;

public enum DateFilter {
    TODAY("Aujourd'hui"),
    THIS_DAY("Le"),
    THIS_WEEK("Cette semaine"),
    //LAST_MONTH("31 jours"),
    THIS_MONTH("Ce mois"),
   // LAST_YEAR("1 an"),
    THIS_YEAR("Cette année"),
    ALL_TIME("Toutes les dates"),
    MY_DATE("Date spe");

    private final String displayName;

    DateFilter(String displayName) {
        this.displayName = displayName;
    }

    public static DateFilter getDateFilterFromString(String selected) {
        switch (selected) {
            case "Aujourd'hui":
                return DateFilter.TODAY;
            case "Le":
                return DateFilter.THIS_DAY;
            case "Cette semaine":
                return DateFilter.THIS_WEEK;
            case "Ce mois":
                return DateFilter.THIS_MONTH;
            case "Date spe":
                return DateFilter.MY_DATE;
            case "Cette année":
                return DateFilter.THIS_YEAR;
           /* case "1 an":
                return DateFilter.LAST_YEAR;
            case "31 jours":
                return DateFilter.LAST_MONTH;*/
            default:
                return DateFilter.ALL_TIME;
        }
    }


    @Override
    public String toString() {
        return displayName;
    }
}