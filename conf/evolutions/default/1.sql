# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table circle (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_circle primary key (id))
;

create table place (
  id                        bigint auto_increment not null,
  place_list_id             bigint,
  name                      varchar(255),
  location                  varchar(255),
  event_time                datetime,
  constraint pk_place primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  pseudo                    varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;


create table circle_user (
  circle_id                      bigint not null,
  user_id                        bigint not null,
  constraint pk_circle_user primary key (circle_id, user_id))
;
alter table place add constraint fk_place_parent_1 foreign key (place_list_id) references circle (id) on delete restrict on update restrict;
create index ix_place_parent_1 on place (place_list_id);



alter table circle_user add constraint fk_circle_user_circle_01 foreign key (circle_id) references circle (id) on delete restrict on update restrict;

alter table circle_user add constraint fk_circle_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table circle;

drop table circle_user;

drop table place;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

