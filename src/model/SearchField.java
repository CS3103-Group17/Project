package model;

public class SearchField {
    /* Basic search term. */
    private String keyword;
    
    public SearchField(String keyword) {
        this.setKeyword(keyword);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
        
        if (!searchField.getKeyword().equals(keyword)) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        
        hashCode = hashCode * 91 + this.keyword.hashCode();
        
        return hashCode;
    }
}
