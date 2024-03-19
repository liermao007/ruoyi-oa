
create table table_set
(
  id           VARCHAR2(64),
  tableName       VARCHAR2(64),
  company_id    VARCHAR2(255),
  is_send      CHAR(1) default '0' not null
);

comment on table table_set
  is '短信设置';
comment on column table_set.id
  is 'id';
  comment on column table_set.table_id
  is '表id';
comment on column table_set.tableName
  is '表名';
comment on column table_set.is_send
  is '是否发送';

-- Create/Recreate primary, unique and foreign key constraints