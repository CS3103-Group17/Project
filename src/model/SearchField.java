package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchField {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    private String name;
    
    private transient LocalDate arrivalDate;
    private transient LocalDate departureDate;
    private transient String pointOfInterest;
    
    /* Name will be converted to lower case. */
    public SearchField(String name) {
        this.name = name.toLowerCase();
        this.arrivalDate = null;
        this.departureDate = null;
        this.pointOfInterest = "";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }
    
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        if (arrivalDate != null) {
            this.arrivalDate = LocalDate.parse(dateFormatter.format(arrivalDate), dateFormatter);
        } else {
            this.arrivalDate = arrivalDate;
        }
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        if (departureDate != null) {
            this.departureDate = LocalDate.parse(dateFormatter.format(departureDate), dateFormatter);
        } else {
            this.departureDate = departureDate;
        }
    }

    public String getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(String pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }
    
    public DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        
        if (!(object instanceof SearchField)) {
            return false;
        }
        
        SearchField searchField = (SearchField) object;
        
        if (!searchField.getName().equals(name)) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        
        hashCode = hashCode * 91 + this.name.hashCode();
        
        return hashCode;
    }
}
