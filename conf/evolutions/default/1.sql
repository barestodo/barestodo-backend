# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table place (
  id                        bigint auto_increment not null,
  todo_list_id              bigint not null,
  name                      varchar(255),
  location                  varchar(255),
  event_time                datetime,
  constraint pk_place primary key (id))
;

create table todo_list (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_todo_list primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  pseudo                    varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;


create table todo_list_user (
  todo_list_id                   bigint not null,
  user_id                        bigint not null,
  constraint pk_todo_list_user primary key (todo_list_id, user_id))
;
alter table place add constraint fk_place_todo_list_1 foreign key (todo_list_id) references todo_list (id) on delete restrict on update restrict;
create index ix_place_todo_list_1 on place (todo_list_id);



alter table todo_list_user add constraint fk_todo_list_user_todo_list_01 foreign key (todo_list_id) references todo_list (id) on delete restrict on update restrict;

alter table todo_list_user add constraint fk_todo_list_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table place;

drop table todo_list;

drop table todo_list_user;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

