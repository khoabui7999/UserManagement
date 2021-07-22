CREATE DATABASE "UserManagement"

CREATE SCHEMA user_detail;

CREATE TABLE user_detail.role (
    id integer NOT NULL,
    role_name character varying(50) NOT NULL
);

CREATE TABLE user_detail.user_role (
    id integer NOT NULL,
    user_id integer,
    role_id integer
);

CREATE TABLE user_detail."user" (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    encrypted_password character varying(500) NOT NULL
);


ALTER TABLE ONLY user_detail.role
    ADD CONSTRAINT "Role_pkey" PRIMARY KEY (id);


ALTER TABLE ONLY user_detail.user_role
    ADD CONSTRAINT "User_Role_pkey" PRIMARY KEY (id);


ALTER TABLE ONLY user_detail."user"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);



ALTER TABLE ONLY user_detail.user_role
    ADD CONSTRAINT user_role_uk UNIQUE (user_id, role_id);



ALTER TABLE ONLY user_detail.user_role
    ADD CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES user_detail."user"(id);



ALTER TABLE ONLY user_detail.user_role
    ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES user_detail.role(id);



