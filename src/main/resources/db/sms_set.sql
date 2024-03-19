-- Create table
create table sms_set
(
  id            VARCHAR2(64) not null,
  company_id   VARCHAR2(64),
  company_name   VARCHAR2(64),
  is_send      CHAR(1) default '1' not null,
  remarks      VARCHAR2(50),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  del_flag    CHAR(1) default '0' not null
);
-- Add comments to the table 
comment on table sms_set
  is '短信设置';
-- Add comments to the columns
comment on column sms_set.id
  is 'id';
comment on column sms_set.company_id
  is '机构ID';
  comment on column sms_set.company_name
  is '机构名';
comment on column sms_set.is_send
  is '是否发送';
-- Create/Recreate primary, unique and foreign key constraints
