package rs.ac.bg.fon.silab.dms.rest.services.tree.dto;

/**
 * Created by igor on 1/18/17.
 */
class DocumentTypeResponse {
    public long id;
    public String name;

    public DocumentTypeResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
