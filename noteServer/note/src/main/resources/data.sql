-- DROP SEQUENCE public.member_seq;

--docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=keesun -e POSTGRES_DB=springdata --name postgres_boot -d postgres
--
--docker exec -i -t postgres_boot bash
--
--su - postgres
--
--psql springdata
--
--데이터베이스 조회
--\list
--
--테이블 조회
--\dt
--
--쿼리
--SELECT * FROM account;



CREATE SEQUENCE public.member_seq
NO MINVALUE
NO MAXVALUE;

CREATE TABLE public."member" (
	seq_id int4 NOT NULL,
	id varchar NOT NULL,
	password varchar NOT NULL,
	address varchar NOT NULL,
	nickname varchar NULL,
	phone_number varchar NULL,
	email varchar NULL,
	CONSTRAINT pk_member PRIMARY KEY (seq_id, id)
)
WITH (
	OIDS=FALSE
) ;
COMMENT ON COLUMN public."member".seq_id IS '회원 id 고유 번호' ;
COMMENT ON COLUMN public."member".id IS '회원 id' ;
COMMENT ON COLUMN public."member".password IS '비밀번호' ;
COMMENT ON COLUMN public."member".address IS '주소' ;
COMMENT ON COLUMN public."member".nickname IS '회원 닉네임' ;
COMMENT ON COLUMN public."member".phone_number IS '회원 휴대폰 번호' ;

ALTER TABLE public."member" ALTER COLUMN seq_id SET DEFAULT nextval('member_seq_id_seq') ;
ALTER TABLE public."member" ADD reg_date date NULL DEFAULT now() ;
ALTER TABLE member ADD constraint unique_id unique (id)

-- DROP SEQUENCE public.post_seq_id_seq;

CREATE SEQUENCE public.post_seq_id_seq
NO MINVALUE
NO MAXVALUE;



CREATE TABLE public.post (
	title text NULL,
	content text NULL,
	reg_id varchar NULL,
	reg_date timestamp NULL DEFAULT now(),
	upd_date timestamp NULL,
	del_date timestamp NULL,
	seq_id serial NOT NULL,
	read_count int4 NULL DEFAULT 0,
	delete_flag bool NULL DEFAULT false,
	CONSTRAINT post_pk PRIMARY KEY (seq_id),
	CONSTRAINT post_member_fk FOREIGN KEY (reg_id) REFERENCES member(id) ON UPDATE SET NULL ON DELETE SET NULL
);

WITH (
	OIDS=FALSE
);