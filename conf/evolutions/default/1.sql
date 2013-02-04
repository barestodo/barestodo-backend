# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table place (
  id                        bigint auto_increment not null,
  place_list_id             bigint not null,
  name                      varchar(255),
  location                  varchar(255),
  event_time                datetime,
  constraint pk_place primary key (id))
;

create table place_list (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_place_list primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  pseudo                    varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;


create table place_list_user (
  place_list_id                  bigint not null,
  user_id                        bigint not null,
  constraint pk_place_list_user primary key (place_list_id, user_id))
;
alter table place add constraint fk_place_place_list_1 foreign key (place_list_id) references place_list (id) on delete restrict on update restrict;
create index ix_place_place_list_1 on place (place_list_id);



alter table place_list_user add constraint fk_place_list_user_place_list_01 foreign key (place_list_id) references place_list (id) on delete restrict on update restrict;

alter table place_list_user add constraint fk_place_list_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table place;

drop table place_list;

drop table place_list_user;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

