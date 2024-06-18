package Model;
public class ExpenseMetadata {

    private String name;
    private String imgUrl;
    private String notes;

    public ExpenseMetadata(String name, String imgUrl, String notes) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.notes = notes;
    }
    public String getName() {
        return this.name;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public String getNotes() {
        return this.notes;
    }
}