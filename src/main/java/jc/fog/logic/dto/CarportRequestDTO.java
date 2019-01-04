package jc.fog.logic.dto;

/**
 *
 * @author Jesper
 */
public class CarportRequestDTO {
    private int id, rooftypeId, shedId, width, height, length, slope;
    //private int dimensionerId;
    private String remark;
    private ShedDTO shedDTO;

    /**
     * Konstruktør for carport forespørgsler, hvor alle argumenter er kendt.
     * @param id
     * @param rooftypeId
     * @param slope
     * @param shedId
     * @param width
     * @param height
     * @param length
     * @param remark
     * @param shedLength
     * @param shedWidth 
     */
    public CarportRequestDTO(int id, int rooftypeId, int slope, int shedId, int width, 
                           int height, int length, String remark, int shedLength, int shedWidth) {
        this.id = id;
        this.rooftypeId = rooftypeId;
        this.slope = slope;
        this.shedId = shedId;
        this.width = width;
        this.height = height;
        this.length = length;
        this.remark = remark;
        
        if (shedId != 0)
            this.shedDTO = new ShedDTO(shedId, id, shedLength, shedWidth);
    }
    
    /**
     * Konstruktør for en carport request som ikke findes i databasen endnu.
     * id og shedId er således ukendt.
     * @param rooftypeId
     * @param slope
     * @param width
     * @param height
     * @param length
     * @param remark
     * @param shedLength
     * @param shedWidth 
     */
    public CarportRequestDTO(int rooftypeId, int slope, int width, 
                           int height, int length, String remark, int shedLength, int shedWidth)
    {
        this.rooftypeId = rooftypeId;
        this.slope = slope;
        this.width = width;
        this.height = height;
        this.length = length;
        this.remark = remark;
        
        // Since shed id is unknown, if sheds width and length are provided, pass in zeros as ids.
        if (shedLength > 0 && shedWidth > 0)
            this.shedDTO = new ShedDTO(0, 0, shedLength, shedWidth);
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getRooftypeId() {
        return rooftypeId;
    }

    public void setRooftypeId(int rooftypeId) {
        this.rooftypeId = rooftypeId;
    }

    public int getShedId() {
        return shedId;
    }

    public void setShedId(int shedId) {
        this.shedId = shedId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

//    public int getDimensionerId() {
//        return dimensionerId;
//    }
//
//    public void setDimensionerId(int dimensionerId) {
//        this.dimensionerId = dimensionerId;
//    }

    public int getSlope() {
        return slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public ShedDTO getShedDTO()
    {
        return shedDTO;
    }
    
    public void setShedDTO(ShedDTO value)
    {
        this.shedDTO = value;
    }
    
    
    
}
