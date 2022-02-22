package com.oao.repository;

import com.oao.model.response.House;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.ArrayList;


public interface HousesManagementDAO {

    @SqlUpdate("INSERT INTO HOUSE_INFO(HOUSE_ID, HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER) VALUES(nextval('seq_id'), ?, ?, ?, ?)")
    @GetGeneratedKeys("HOUSE_ID")
    String saveHouse(String houseNumber, String streetName, int postalCode, String owner);

    @SqlUpdate("UPDATE HOUSE_INFO SET DELETED_AT = SYSDATE WHERE HOUSE_ID = :houseId")
    void removeHouse(@Bind("houseId") String houseId);

    @SqlQuery("select count(*) from HOUSE_INFO where HOUSE_ID= :houseId and DELETED_AT IS null")
    int hasHouse(@Bind("houseId") String houseId);

    @SqlQuery("select count(*) from HOUSE_INFO where HOUSE_NUMBER= :houseNumber and STREET_NAME= :streetName and POSTAL_CODE= :postalCode and OWNER= :owner and DELETED_AT IS null")
    int hasHouse(@Bind("houseNumber") String houseNumber, @Bind("streetName") String streetName, @Bind("postalCode") int postalCode, @Bind("owner") String owner);

    @SqlQuery("select * from HOUSE_INFO where DELETED_AT IS null order by house_id desc")
    @RegisterConstructorMapper(House.class)
    ArrayList<House> getHouses();
}
