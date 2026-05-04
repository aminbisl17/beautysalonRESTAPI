
alter table sherbimet drop constraint DF__sherbimet__is_ac__74AE54BC;
ALTER TABLE sherbimet
ADD CONSTRAINT DF_sherbimet_is_active
DEFAULT 1 FOR is_active;
select * from sherbimet;