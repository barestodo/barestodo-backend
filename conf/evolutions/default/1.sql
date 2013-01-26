# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table place (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  location                  varchar(255),
  constraint pk_place primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table place;

SET FOREIGN_KEY_CHECKS=1;

