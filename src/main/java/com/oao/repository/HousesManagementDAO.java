package com.oao.repository;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface HousesManagementDAO {

    @SqlUpdate("INSERT INTO HOUSE_INFO(HOUSE_ID, HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER) VALUES(nextval('seq_id'), ?, ?, ?, ?)")
    @GetGeneratedKeys("HOUSE_ID")
    String saveHouse(String houseNumber,String streetName,int postalCode,String owner);

    @SqlUpdate("UPDATE HOUSE_INFO SET DELETED_AT = SYSDATE WHERE HOUSE_ID = :houseId")
    void removeHouse(@Bind("houseId") String houseId);

    @SqlQuery("select count(*) from HOUSE_INFO where HOUSE_ID= :houseId")
    int hasHouse(@Bind("houseId") String houseId);

}
