package Model;
public class Document {
    private String name;
    private String data;
    private String owner;
    public Integer id;

    public Document(String docName, String docData, String username) {
        this.name = docName;
        this.data = docData;
        this.owner = username;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getDocName() {
        return this.name;
    }

    public String getDocData() {
        return this.data;
    }

    public void updateDocData(String updatedDocumentData) {
        this.data = updatedDocumentData;
    }

    public void setDocumentId(int id) {
        this.id = id;
    }

}