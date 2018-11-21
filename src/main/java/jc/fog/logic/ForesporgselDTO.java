package jc.fog.logic;

/**
 *
 * @author Jesper
 */
public class ForesporgselDTO {
    private int Id, vareId, skurId, bredde, hoejde, laengde, dimensionerId, haeldning;
    private String bemaerkning;
    private ShedDTO skurDTO;

    public ForesporgselDTO(int Id, int vareId, int haeldning, int skurId, int bredde, 
                           int hoejde, int laengde, String bemaerkning, int skurLaengde, int skurBredde) {
        this.Id = Id;
        this.vareId = vareId;
        this.haeldning = haeldning;
        this.skurId = skurId;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.laengde = laengde;
        this.bemaerkning = bemaerkning;
        
        if (skurId != 0)
            this.skurDTO = new ShedDTO(skurId, skurLaengde, skurBredde);
    }

    public ForesporgselDTO(int Id, int bredde, int hoejde, int laengde) {
        this.Id = Id;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.laengde = laengde;
    }
    
    
    
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getVareId() {
        return vareId;
    }

    public void setVareId(int vareId) {
        this.vareId = vareId;
    }

    public int getSkurId() {
        return skurId;
    }

    public void setSkurId(int skurId) {
        this.skurId = skurId;
    }

    public int getBredde() {
        return bredde;
    }

    public void setBredde(int bredde) {
        this.bredde = bredde;
    }

    public int getHoejde() {
        return hoejde;
    }

    public void setHoejde(int hoejde) {
        this.hoejde = hoejde;
    }

    public int getLaengde() {
        return laengde;
    }

    public void setLaengde(int laengde) {
        this.laengde = laengde;
    }

    public int getDimensionerId() {
        return dimensionerId;
    }

    public void setDimensionerId(int dimensionerId) {
        this.dimensionerId = dimensionerId;
    }

    public int getHaeldning() {
        return haeldning;
    }

    public void setHaeldning(int haeldning) {
        this.haeldning = haeldning;
    }

    public String getBemaerkning() {
        return bemaerkning;
    }

    public void setBemaerkning(String bemaerkning) {
        this.bemaerkning = bemaerkning;
    }
    
    public ShedDTO getSkurDTO()
    {
        return skurDTO;
    }
    
    public void setSkurDTO(ShedDTO value)
    {
        this.skurDTO = value;
    }
    
    
    
}
