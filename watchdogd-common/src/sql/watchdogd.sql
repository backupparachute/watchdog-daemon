drop table if exists accounts;
create table accounts (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	enabled char,
	name varchar(64),
	created_on datetime,
	last_updated timestamp
);

drop table if exists users;
create table users (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	username varchar(320),
	password varchar(64),
	account_id integer,
	enabled char,
	created_on datetime,
	last_updated timestamp,
	constraint fk_users_accounts foreign key (account_id) references accounts(id)
);
create unique index idx_username on users (username);

drop table if exists groups;
create table groups (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(128),
	created_on datetime
);
create unique index idx_group_name on groups(name);

drop table if exists users_groups_xref;
create table users_groups_xref (
	user_id integer,
	group_id integer,
	constraint fk_users_group foreign key (user_id) references users(id),
	constraint fk_groups_user foreign key (group_id) references groups(id)
);
create unique index idx_users_groups_unq on users_groups_xref(user_id,group_id);

drop table if exists roles;
create table roles (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	authority varchar(128),
	created_on datetime
);
create unique index idx_role_authority on roles(authority);

drop table if exists groups_roles_xref;
create table groups_roles_xref (
	group_id integer,
	role_id integer,
	constraint fk_role_group foreign key (role_id) references roles(id),
	constraint fk_groups_role foreign key (group_id) references groups(id)
);
create unique index idx_groups_roles_unq on groups_roles_xref(role_id, group_id);

drop table if exists site_credentials;
create table site_credentials (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	username varchar(320),
	password varchar(255),
	type varchar(64),
	created_on datetime,
	last_updated timestamp
);

drop table if exists sites;
create table sites (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(128),
	uri varchar(4096),
	type varchar(64),
	site_credential_id integer,
	public char,
	account_id integer,
	created_on datetime,
	last_updated timestamp,
	constraint fk_sites_accounts foreign key (account_id) references accounts(id),
	constraint fk_sites_credentials foreign key (site_credential_id) references site_credentials(id)
);

drop table if exists subscriptions;
create table subscriptions (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(128),
	uri varchar(4096),
	type varchar(64),
	site_credential_id integer,
	account_id integer,
	created_on datetime,
	last_updated timestamp,
	constraint fk_sites_accounts foreign key (account_id) references accounts(id),
	constraint fk_sites_credentials foreign key (site_credential_id) references site_credentials(id)
);

drop table if exists audit;
create table audit (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	level varchar(16),
	type varchar(64),
	summary varchar(256),
	description varchar(1024),	
	object_name varchar(128),
	object_identifier integer,
	object_action varchar(32),
	tags varchar(256),
	user_id integer,
	created_on datetime,
	constraint fk_audit_user foreign key (user_id) references users(id)
);

insert into accounts (id, enabled, name) values (1,'1', 'Kyles Account');
insert into users (id, account_id, username, password,enabled) values (1, 1, 'kyle','password','1');

insert into roles (id, authority) values (1,'ROLE_SITES');
insert into roles (id, authority) values (2,'ROLE_USERS');
insert into roles (id, authority) values (3,'ROLE_GREETING');

insert into groups (id, name) values (1, 'Admin');

insert into users_groups_xref (user_id, group_id) values (1,1);

insert into groups_roles_xref(group_id, role_id) values (1,1);
insert into groups_roles_xref(group_id, role_id) values (1,2);
insert into groups_roles_xref(group_id, role_id) values (1,3);

-- #############################################################################
-- ## Date: 11/30/09
-- ## changes: create preferences table
-- #############################################################################

drop table if exists preferences;
create table preferences (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(64),
	type varchar(64),
	value varchar(320),
	user_id integer,
	created_on datetime,
	last_updated timestamp,
	constraint fk_users_preferences foreign key (user_id) references users(id)
);

alter table users add column last_accessed datetime;
alter table sites add column enabled char;

drop table if exists site_reports;
create table site_reports (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	type varchar(64),
	message varchar(1024),
	status_code varchar(8),
	site_id integer,
	created_on datetime,
	constraint fk_site_report_site foreign key (site_id) references sites(id)
);

-- #############################################################################
-- ## Date: 12/02/09
-- ## changes: add site last connected date
-- #############################################################################

alter table sites add column last_connected datetime; 
alter table site_reports add column response_time integer;

drop table if exists subscriptions;
create table subscriptions (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(64),
	amount decimal(5,2),
	created_on datetime,
	weight integer,
	probe_interval integer
);

insert into subscriptions (id, name, amount, created_on, weight, probe_interval) values (1, 'beta', 0, now(), 1, -5);

alter table accounts add column subscription_id integer;
alter table accounts add constraint fk_account_subscription foreign key (subscription_id) references subscriptions(id);

alter table sites add column port integer;


drop table if exists site_params;
create table site_params (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(64),
	value varchar(1024),
	type varchar(64),
	created_on datetime,
	site_id integer
	,constraint fk_site_params_site foreign key (site_id) references sites(id)
);

drop table if exists contacts;
create table contacts (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,name varchar(128)
	,value varchar(1024)
	,type varchar(32)
	,created_on datetime
	,account_id integer
	,constraint fk_contacts_accounts foreign key (account_id) references accounts(id)
);

drop table if exists site_notifications;
create table site_notifications (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,type varchar(64)
	,success char
	,attempts integer
	,created_on datetime
	,site_report_id integer
	,contact_id integer
	,constraint fk_site_report_sites_notification foreign key (site_report_id) references site_reports(id)
	,constraint fk_contact_sites_notification foreign key (contact_id) references contacts(id)
);

drop table if exists sites_contacts_xref;
create table sites_contacts_xref (
	site_id integer
	,contact_id integer
	,constraint fk_site_contact_xref_contact foreign key (contact_id) references contacts(id)
	,constraint fk_site_contact_xref_site foreign key (site_id) references sites(id)
);


alter table sites add column status varchar(32);
alter table site_reports add column location varchar(64);
alter table site_reports change status_code status_code integer;

alter table sites add column keyword varchar(255);
alter table sites add column post_data varchar(1024);

-- #############################################################################
-- ## Date: 06/18/10
-- ## changes: first test deploy
-- #############################################################################
alter table users add column firstname varchar(64);
alter table users add column lastname varchar(64);
create unique index idx_account_name on accounts (name);
	

-- #############################################################################
-- ## Date: 06/22/10
-- ## changes: adding support request table
-- #############################################################################
drop table if exists requests;
create table requests (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	email varchar(320),
	subject varchar(255),
	message varchar(1024),
	ticket_number varchar(16),
	processed datetime,
	created_on datetime,
	last_updated timestamp
);

-- #############################################################################
-- ## Date: 07/06/10
-- ## changes: adding support request table
-- #############################################################################
alter table sites drop column last_connected;
alter table sites drop column status;
alter table sites drop column keyword;
alter table sites drop column post_data;

alter table sites add column site_report_id integer;
alter table sites add constraint fk_site_site_report foreign key (site_report_id) references site_reports(id);

alter table site_params add column last_updated timestamp;

alter table contacts add column last_updated timestamp;

alter table subscriptions add column last_updated timestamp;

-- #############################################################################
-- ## Date: 07/15/10
-- ## changes: adding site statuses table
-- #############################################################################

alter table sites drop column site_report_id;
alter table sites add column site_status_id integer;
alter table sites add constraint fk_site_site_status foreign key (site_status_id) references site_statuses(id);

drop table if exists site_statuses;
create table site_statuses (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,status varchar(16)
	,type varchar(16)
	,site_id integer
	,last_connected datetime
	,created_on datetime
	,notified datetime
	,last_updated timestamp
	,count integer
	,constraint fk_site_status_site foreign key (site_id) references sites(id)
);

-- #############################################################################
-- ## Date: 07/20/10
-- ## changes: adding schema version table
-- #############################################################################
drop table if exists schema_versions;
create table schema_versions (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,name varchar(16)
	,version varchar(16)
	,created_on datetime
);

insert into schema_versions (version,created_on) values ('1000', CURRENT_TIMESTAMP);
	
-- #############################################################################
-- ## Date: 08/15/10
-- #############################################################################

insert into schema_versions (version,created_on) values ('1001', CURRENT_TIMESTAMP);

alter table schema_versions add column description varchar(256);
alter table site_notifications add column JSON varchar(4096);

-- #############################################################################
-- ## Date: 08/16/10
-- #############################################################################

insert into schema_versions (version,created_on) values ('1002', CURRENT_TIMESTAMP);

alter table site_notifications add column subject varchar(140);
alter table site_notifications add column body varchar(4096);

-- #############################################################################
-- ## Date: 08/18/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1003','add site notification error columns', CURRENT_TIMESTAMP);

alter table site_notifications add column error_code integer;
alter table site_notifications add column error_message varchar(1024);

-- #############################################################################
-- ## Date: 08/23/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1004','site notification flags', CURRENT_TIMESTAMP);
	
alter table site_notifications add column content_ready char;
alter table contacts add column enabled char default 0;

-- #############################################################################
-- ## Date: 08/25/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1005','site flags', CURRENT_TIMESTAMP);
	
alter table sites add column do_not_queue char default 0;

-- #############################################################################
-- ## Date: 08/27/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1006','site notification', CURRENT_TIMESTAMP);
	
alter table site_notifications add column site_id integer;

alter table site_notifications add constraint fk_site_notifications_site foreign key (site_id) references sites(id);

-- #############################################################################
-- ## Date: 09/01/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1007','request type', CURRENT_TIMESTAMP);
	
alter table requests add column type varchar(32);


-- #############################################################################
-- ## Date: 09/01/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1008','applications', CURRENT_TIMESTAMP);
	
drop table if exists applications;
create table applications (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(128),
	description varchar(1024),
	account_id integer,
	token varchar(64),
	secret_key varchar(64),
	enabled char,
	created_on datetime,
	last_updated timestamp,
	constraint fk_applications_accounts foreign key (account_id) references accounts(id)
);
create unique index idx_applications on applications (token);
	
drop table if exists applications_groups_xref;
create table applications_groups_xref (
	application_id integer,
	group_id integer,
	constraint fk_applications_group foreign key (application_id) references applications(id),
	constraint fk_groups_applications foreign key (group_id) references groups(id)
);
create unique index idx_applications_groups_unq on applications_groups_xref(application_id,group_id);

-- #############################################################################
-- ## Date: 09/05/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1009','applications additions', CURRENT_TIMESTAMP);

alter table applications add column last_accessed datetime;

-- #############################################################################
-- ## Date: 09/12/10
-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1010','alter hash column lengths', CURRENT_TIMESTAMP);
	
alter table users modify password varchar(40);
alter table applications modify token varchar(40);
alter table applications modify secret_key varchar(40);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1011','audit link', '2010-10-01');
	
alter table audit add column link varchar(1024);


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1012','beta invite', '2010-10-05');

drop table if exists beta_invites;
create table beta_invites (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	invitee_account_id integer,
	invitor_account_id integer,
	email varchar(320),
	uuid varchar(64),
	created_on datetime,
	constraint fk_invitee_account_beta_invite foreign key (invitee_account_id) references accounts(id)
	,constraint fk_invitor_account_beta_invite foreign key (invitor_account_id) references accounts(id)
);


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1013','add groups and subscription', '2010-11-24');

INSERT INTO subscriptions (name, amount, created_on, weight, probe_interval) VALUES ('free', 0.00, '2010-06-24 16:00:00', 2, -10);

INSERT INTO groups ( name, created_on) VALUES ( 'user', '2010-06-22 00:00:00');
INSERT INTO groups ( name, created_on) VALUES ( 'application', '2010-09-05 00:00:00');


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1014','add site queue, rename beta invites', '2010-12-13');

drop table if exists site_queue;
create table site_queue (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	site_id integer,
	worker varchar(64),
	created_on datetime,
	processed datetime,
	constraint fk_site_site_queue foreign key (site_id) references sites(id)
);

rename table beta_invites to invites;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1015','refactor site notification to denormalize contact info', '2010-12-14');

alter table site_notifications drop contact_id;
alter table site_notifications add contact varchar(320);
alter table site_notifications add name varchar(128);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1016','add unique index on site queue for site id', '2010-12-15');

create unique index idx_site_queue_site_id_unq on site_queue(site_id);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1017','alter site queue processed', '2010-12-15');

alter table site_queue change processed processing datetime;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1018','alter invites add subscription', '2011-01-04');

alter table invites add subscription varchar(16);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1019','add role, update groups roles xref', '2011-01-04');

insert into roles (authority,created_on) values ('ROLE_APPLICATION_SITE_STATUS_UPDATE', '2011-01-05 23:12');

insert into groups_roles_xref (group_id, role_id) values (1,4);

insert into groups_roles_xref (group_id, role_id) values (2,1);
insert into groups_roles_xref (group_id, role_id) values (2,2);
insert into groups_roles_xref (group_id, role_id) values (2,3);
insert into groups_roles_xref (group_id, role_id) values (2,4);

insert into groups_roles_xref (group_id, role_id) values (3,3);
insert into groups_roles_xref (group_id, role_id) values (3,4);


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1020','increase user password hash column size', '2011-01-15');

alter table users modify column password varchar(64);


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1021','add roles', '2011-01-22');

insert into roles (authority,created_on) values ('ROLE_ADMIN_INVITES', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_INVITE', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_REQUESTS', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_TWITTER', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_USERS', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_USER', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_GROUPS', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_ADMIN_GROUP', CURRENT_TIMESTAMP);

insert into roles (authority,created_on) values ('ROLE_APPLICATIONS', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_APPLICATION', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_CONTACTS', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_CONTACT', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_SITES', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_SITE', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_HOME', CURRENT_TIMESTAMP);

delete from groups_roles_xref;

insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='admin'), id from roles;
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='user'), id from roles where authority not like '%ADMIN%';
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='application'), id from roles where authority like '%APPLICATION_SITE_STATUS_UPDATE%';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1022','clean up subscriptions', '2011-01-22');

delete from subscriptions where name = 'free';

update accounts set subscription_id = 1;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1023','add custom value to site report', '2011-01-27');

alter table site_reports add column value integer;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1024','add custom value to site status', '2011-01-28');

alter table site_statuses add column value integer;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1025','add services table', '2011-01-28');

drop table if exists services;
create table services (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(64),
	role varchar(128),
	params varchar(1024),
	type varchar(16),
	enabled char,
	created_on datetime,
	last_updated timestamp
);
create unique index idx_service_name on services (name);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1026','add user attributes', '2011-02-25');

drop table if exists user_attributes;
create table user_attributes (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(64),
	value varchar(1024),
	type varchar(64),
	created_on datetime,
	user_id integer
	,constraint fk_user_attribs_user foreign key (user_id) references users(id)
);

alter table user_attributes add column last_updated timestamp;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1027','add user id to requests', '2011-03-13');

alter table requests add column user_id integer;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1028','add invite role', '2011-03-13');

insert into roles (authority,created_on) values ('ROLE_INVITE', CURRENT_TIMESTAMP);

insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='user'), id from roles where authority = 'ROLE_INVITE';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1029','add invite message', '2011-03-16');

alter table invites add column message varchar(1024);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1030','add site param enabled', '2011-05-22');

alter table site_params add column enabled char default 1;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1031','rename site param table', '2011-05-26');

rename table site_params to site_attributes;


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1032','insert http site attr ', '2011-05-27');

insert into site_attributes (name,value,type,site_id)
select 
"keyword","","regex",s.id
from sites s
left outer join site_attributes sa on s.id = sa.site_id
where
s.type != 'rest'
 and 
not exists (
select id from site_attributes where site_id = s.id and name='keyword'
)


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1033','help tables', '2011-06-07');

drop table if exists help;
create table help (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,title varchar(1024)
	,url varchar(1024)
	,data varchar(4096)
	,created_on datetime
);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1034','add description and tags', '2011-06-12');

alter table help add column description varchar(1024);
alter table help add column tags varchar(1024);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1035','alter help, add weight and enabled', '2011-06-12');

alter table help add column weight integer default 0;
alter table help add column enabled char default 1;


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1036','add help admin role', '2011-06-14');

insert into roles (authority,created_on) values ('ROLE_ADMIN_HELP', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='admin'), id from roles where authority = 'ROLE_ADMIN_HELP';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1037','add rest api application security role', '2011-06-27');

insert into roles (authority,created_on) values ('ROLE_APPLICATION_SITE_STATUS', CURRENT_TIMESTAMP);
insert into roles (authority,created_on) values ('ROLE_APPLICATION_SITES', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='application'), id from roles where authority = 'ROLE_APPLICATION_SITE_STATUS';
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='application'), id from roles where authority = 'ROLE_APPLICATION_SITES';
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='user'), id from roles where authority = 'ROLE_APPLICATION_SITE_STATUS';
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='user'), id from roles where authority = 'ROLE_APPLICATION_SITES';

insert into roles (authority,created_on) values ('ROLE_SITES_RSS', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='user'), id from roles where authority = 'ROLE_SITES_RSS';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1038','add security current users role', '2011-07-06');
insert into roles (authority,created_on) values ('ROLE_ADMIN_CURRENT_USERS', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='admin'), id from roles where authority = 'ROLE_ADMIN_CURRENT_USERS';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1039','add account token', '2012-03-26');
alter table accounts add column token varchar(255);

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1040','add payment role', '2012-03-28');
insert into roles (authority,created_on) values ('ROLE_PAYMENT', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='admin'), id from roles where authority = 'ROLE_PAYMENT';
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='user'), id from roles where authority = 'ROLE_PAYMENT';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1041','refactor invite group from users to admins', '2012-03-30');
--inserts might fail because it already exists
insert into roles (authority,created_on) values ('ROLE_ADMIN_INVITE', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='admin'), id from roles where authority = 'ROLE_ADMIN_INVITE';

delete from groups_roles_xref where group_id = (select id from groups where name='user') AND role_id = (select id from roles where authority = 'ROLE_INVITE');
delete from roles where authority = 'ROLE_INVITE';


-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1042','empty site queue during update, it will auto refill', '2012-04-03');

delete from site_queue;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1043','setup keyword attr name to uppercase', '2012-04-04');

update site_attributes set name = 'KEYWORD' where name = 'keyword';

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1044','remove site credentials', '2012-04-11');

alter table sites drop column site_credential_id;
drop table site_credentials;

-- #############################################################################

insert into schema_versions (version,description,created_on) values ('1045','add webhook admin role', '2012-05-07');
insert into roles (authority,created_on) values ('ROLE_ADMIN_WEBHOOKS', CURRENT_TIMESTAMP);
insert into groups_roles_xref (group_id, role_id) select (select id from groups where name='admin'), id from roles where authority = 'ROLE_ADMIN_WEBHOOKS';
