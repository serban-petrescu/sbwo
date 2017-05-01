CREATE OR REPLACE VIEW PUBLIC.V_PERSON_SEARCH AS 
SELECT 
    T_PERSON.C_ID AS C_ID, 
    CASE WHEN (T_PERSON.C_PERSON_TYPE = 0) THEN ((T_PERSON.C_FIRST_NAME || ' ') || T_PERSON.C_LAST_NAME) ELSE T_PERSON.C_NAME END AS C_NAME, 
    T_PERSON.C_PERSON_TYPE AS C_TYPE, 
    T_LOCATION_COUNTRY.C_NAME AS C_COUNTRY, 
    T_LOCATION_REGION.C_NAME AS C_REGION, 
    T_LOCATION.C_ADDRESS AS C_ADDRESS, 
    UPPER(CONCAT_WS(' ', T_PERSON.C_FIRST_NAME, T_PERSON.C_LAST_NAME, T_PERSON.C_NAME, T_LOCATION.C_ADDRESS, T_LOCATION_REGION.C_NAME)) AS C_SEARCH 
FROM PUBLIC.T_PERSON 
LEFT OUTER JOIN PUBLIC.T_LOCATION 
    ON T_PERSON.C_LOCATION_ID = T_LOCATION.C_ID 
LEFT OUTER JOIN PUBLIC.T_LOCATION_COUNTRY 
    ON T_LOCATION.C_COUNTRY_ID = T_LOCATION_COUNTRY.C_ID 
LEFT OUTER JOIN PUBLIC.T_LOCATION_REGION 
    ON T_LOCATION.C_REGION_ID = T_LOCATION_REGION.C_ID 
WHERE T_PERSON.C_DELETED = 0;