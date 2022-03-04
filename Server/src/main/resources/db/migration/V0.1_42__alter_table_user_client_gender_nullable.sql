-- gender should be null because an extra dependent when created will not have a gender until the holder finishes
-- the plan subscription with all the information.
alter table frl_user_client alter column gender drop not null;
