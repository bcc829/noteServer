-- DROP SEQUENCE public.member_seq;

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

ALTER TABLE public."member" ALTER COLUMN seq_id SET DEFAULT NEXTVAL('member_seq') ;
ALTER TABLE public."member" ADD reg_date date NULL DEFAULT now() ;
ALTER TABLE member ADD constraint unique_id unique (id)

