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



CREATE SEQUENCE public.member_seq_id_seq
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
ALTER TABLE public."member" ADD sns_principal vachar NULL ;
ALTER TABLE member ADD constraint unique_id unique (id)

ALTER TABLE public."member" ALTER COLUMN nickname SET NOT NULL;
ALTER TABLE public."member" ADD CONSTRAINT unique_nickname UNIQUE (nickname);


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
ALTER TABLE public.post DROP CONSTRAINT post_member_fk;
ALTER TABLE public.post ADD CONSTRAINT post_member_fk FOREIGN KEY (reg_id) REFERENCES public."member"(nickname) ON DELETE CASCADE ON UPDATE CASCADE;



CREATE TABLE public.post_comment (
	seq_id serial NOT NULL,
	post_seq_id int4 NULL,
	reg_id varchar NOT NULL,
	comment_seq_id int4 NULL,
	reg_date timestamp NOT NULL DEFAULT now(),
	upd_date timestamp NULL,
	del_date timestamp NULL,
	delete_flag bool NULL DEFAULT false,
	content varchar NULL,
	CONSTRAINT post_comment_pk PRIMARY KEY (seq_id),
	CONSTRAINT post_comment_member_fk FOREIGN KEY (reg_id) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT post_comment_post_comment_fk FOREIGN KEY (seq_id) REFERENCES post_comment(seq_id)
);
ALTER TABLE public.post_comment ADD CONSTRAINT post_comment_post_fk FOREIGN KEY (post_seq_id) REFERENCES public.post(seq_id);
ALTER TABLE public.post_comment ALTER COLUMN content SET NOT NULL;
ALTER TABLE public.post_comment ALTER COLUMN post_seq_id DROP NOT NULL;
ALTER TABLE public.post_comment ADD CONSTRAINT post_comment_check CHECK ((seq_id <> comment_seq_id));
ALTER TABLE public.post_comment ADD CONSTRAINT post_comment_check_two CHECK (post_seq_id is not null or comment_seq_id is not null);
ALTER TABLE public.post_comment ADD CONSTRAINT post_comment_check_three CHECK (not(post_seq_id is not null and comment_seq_id is not null));

CREATE TABLE public.post_attachment_files (
	seq_id serial NOT NULL,
	post_seq_id int NOT NULL,
	root_directory varchar NOT NULL,
	sub_directory varchar NOT NULL,
	real_file_name varchar NOT NULL,
	ext_file_name varchar NOT NULL,
	delete_flag bool NULL,
	reg_date timestamp NOT NULL,
	upd_date timestamp NULL,
	del_date timestamp NULL,
	s3_url varchar NOT NULL,
	CONSTRAINT post_attachment_files_pk PRIMARY KEY (seq_id),
	CONSTRAINT post_attachment_files_post_fk FOREIGN KEY (post_seq_id) REFERENCES public.post(seq_id) ON DELETE SET NULL ON UPDATE SET NULL
);
ALTER TABLE public.post_attachment_files ADD reg_id varchar NOT NULL;
ALTER TABLE public.post_attachment_files ADD CONSTRAINT post_attachment_files_member_fk FOREIGN KEY (reg_id) REFERENCES public."member"(nickname) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE public.post_attachment_files RENAME TO post_attachment_file;
ALTER SEQUENCE public.post_attachment_files_seq_id_seq RENAME TO post_attachment_file_seq_id_seq;
ALTER TABLE public.post_attachment_file ALTER COLUMN seq_id SET DEFAULT nextval('post_attachment_file_seq_id_seq'::regclass);
ALTER TABLE public.post_attachment_file RENAME COLUMN s3_url TO file_path;
