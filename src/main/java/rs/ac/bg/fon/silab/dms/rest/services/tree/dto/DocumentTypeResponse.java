package rs.ac.bg.fon.silab.dms.rest.services.tree.dto;

class DocumentTypeResponse {
    public final long id;
    public final String name;

    DocumentTypeResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
