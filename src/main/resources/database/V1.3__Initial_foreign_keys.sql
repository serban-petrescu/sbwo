ALTER TABLE PUBLIC.T_PERSON ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKDQDY3EB68W9BTL7CXR1DMG9QO FOREIGN KEY(C_CHANGEDBY_ID) REFERENCES PUBLIC.T_USER(C_ID) ON DELETE SET NULL NOCHECK;
ALTER TABLE PUBLIC.T_EXPERTISE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK116KXE40I0RMPYSYQR64RTQ33 FOREIGN KEY(C_CHANGEDBY_ID) REFERENCES PUBLIC.T_USER(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_USER_HOME_TILE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKN49EJWNQ6BR7M9O9GMXCJP910 FOREIGN KEY(C_USER_ID) REFERENCES PUBLIC.T_USER(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_USER_FAVOURITE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK9R1QB6GERYIE1M3JREXJB5F26 FOREIGN KEY(C_USER_ID) REFERENCES PUBLIC.T_USER(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_EXPERTISE_FINE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK_T_EXPERTISE_FINE_C_EXPERTISE_ID FOREIGN KEY(C_EXPERTISE_ID) REFERENCES PUBLIC.T_EXPERTISE(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_LOCATION ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK3CE4H89VB3UMGYWX88EU2TMFH FOREIGN KEY(C_ADM_UNIT_ID) REFERENCES PUBLIC.T_LOCATION_ADM_UNIT(C_ID) ON DELETE SET NULL NOCHECK;
ALTER TABLE PUBLIC.T_PERSON_EMAIL_ADDRESS ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKSJHORRTRL3AHBCRQ2CX5GX0AA FOREIGN KEY(C_PERSON_ID) REFERENCES PUBLIC.T_PERSON(C_ID) ON DELETE CASCADE NOCHECK;
ALTER TABLE PUBLIC.T_LOCATION_ADM_UNIT ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK1UOM0IKJSV6K0AHLACJSV7O71 FOREIGN KEY(C_REGION_ID) REFERENCES PUBLIC.T_LOCATION_REGION(C_ID) ON DELETE CASCADE NOCHECK;
ALTER TABLE PUBLIC.T_LOCATION ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKH6S1PVCR6QGJXMOOOKVJRU56U FOREIGN KEY(C_COUNTRY_ID) REFERENCES PUBLIC.T_LOCATION_COUNTRY(C_ID) ON DELETE SET NULL NOCHECK;
ALTER TABLE PUBLIC.T_EXPERTISE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKEF4E9MJ8WBR8X7X8S37W69HJ1 FOREIGN KEY(C_CREATEDBY_ID) REFERENCES PUBLIC.T_USER(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_LOCATION ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK97B32H6GC0A601TNDJ9A6TQOH FOREIGN KEY(C_REGION_ID) REFERENCES PUBLIC.T_LOCATION_REGION(C_ID) ON DELETE SET NULL NOCHECK;
ALTER TABLE PUBLIC.T_PERSON ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKFLHF0P9249FIGJEM7JGVBR6VW FOREIGN KEY(C_CREATEDBY_ID) REFERENCES PUBLIC.T_USER(C_ID) ON DELETE SET NULL NOCHECK;
ALTER TABLE PUBLIC.T_USER_PREFERENCE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKMMODM3SCEAISNQUY0QHICYQK4 FOREIGN KEY(C_USER_ID) REFERENCES PUBLIC.T_USER(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_EXPERTISE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK_T_EXPERTISE_C_LOCATION_ID FOREIGN KEY(C_LOCATION_ID) REFERENCES PUBLIC.T_LOCATION(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_PERSON ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK223D2M6XX4H5RF3CHEKRH2AK7 FOREIGN KEY(C_LOCATION_ID) REFERENCES PUBLIC.T_LOCATION(C_ID) ON DELETE SET NULL NOCHECK;
ALTER TABLE PUBLIC.T_EXPERTISE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKH5VV7IFYOIKCL4W3TQRUXI229 FOREIGN KEY(C_USER_ID) REFERENCES PUBLIC.T_USER(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_EXPERTISE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKBYGEPF7RUEKIR0TVR8Y16H65D FOREIGN KEY(C_COURT_ID) REFERENCES PUBLIC.T_COURT(C_ID) NOCHECK;
ALTER TABLE PUBLIC.T_PERSON_BANK_ACCOUNT ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK3YK684WEJYLAB0TGKH8AJU9M0 FOREIGN KEY(C_PERSON_ID) REFERENCES PUBLIC.T_PERSON(C_ID) ON DELETE CASCADE NOCHECK;
ALTER TABLE PUBLIC.T_LOCATION_REGION ADD CONSTRAINT IF NOT EXISTS PUBLIC.FKB2GGF6SNT8EUUWDB1FWYSQPRY FOREIGN KEY(C_COUNTRY_ID) REFERENCES PUBLIC.T_LOCATION_COUNTRY(C_ID) ON DELETE CASCADE NOCHECK;
ALTER TABLE PUBLIC.T_PERSON_TELEPHONE ADD CONSTRAINT IF NOT EXISTS PUBLIC.FK5SRAF08F7IUJV9BD6OG6Q0R9R FOREIGN KEY(C_PERSON_ID) REFERENCES PUBLIC.T_PERSON(C_ID) ON DELETE CASCADE NOCHECK;
