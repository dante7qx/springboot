package org.dante.springboot.prop;

/**
 * ManufacturerEnum
 * 
 * @author dante
 *
 */
public enum ManufacturerEnum {
	
	HUAWEI(2011,"HUAWEI Technology Co.,Ltd"),
    H3C(25506,"H3C"),
    CISCO(9, "Cisco Systems, Inc.");


    private final Integer objectId;
    private final String manufacturerName;

    private ManufacturerEnum(Integer objectId, String manufacturerName){
        this.objectId = objectId;
        this.manufacturerName = manufacturerName;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }
	
}
