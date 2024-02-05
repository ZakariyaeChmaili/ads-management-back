CREATE TABLE public.platform_table
(
    id     bigserial NOT NULL,
    image  varchar(255) NULL,
    "name" varchar(255) NULL,
    url    varchar(255) NULL,
    CONSTRAINT platform_table_pkey PRIMARY KEY (id)
);



CREATE TABLE public.user_table
(
    id         bigserial NOT NULL,
    "password" varchar(255) NULL,
    "role"     varchar(255) NULL,
    username   varchar(255) NULL,
    CONSTRAINT user_table_pkey PRIMARY KEY (id),
    CONSTRAINT user_table_role_check CHECK (((role)::text = ANY ((ARRAY['USER':: character varying, 'ADMIN':: character varying])::text[])
) )
);



CREATE TABLE public.partner_table
(
    id      bigserial NOT NULL,
    logo    varchar(255) NULL,
    "name"  varchar(255) NULL,
    url     varchar(255) NULL,
    user_id int8 NULL,
    CONSTRAINT partner_table_pkey PRIMARY KEY (id),
    CONSTRAINT fktmj0akgt3i7krdksud6qrebuy FOREIGN KEY (user_id) REFERENCES public.user_table (id)
);



CREATE TABLE public.campaign_table
(
    id          bigserial NOT NULL,
    description varchar(255) NULL,
    status      varchar(255) NULL,
    title       varchar(255) NULL,
    partner_id  int8 NULL,
    CONSTRAINT campaign_table_pkey PRIMARY KEY (id),
    CONSTRAINT campaign_table_status_check CHECK (((status)::text = ANY ((ARRAY['DONE':: character varying, 'ONGOING':: character varying, 'DRAFT':: character varying])::text[])
) ),
	CONSTRAINT fklyisl8cbjvudvtc03sgremuxr FOREIGN KEY (partner_id) REFERENCES public.partner_table(id)
);



CREATE TABLE public.platform_campaign
(
    id          bigserial NOT NULL,
    campaign_id int8 NULL,
    platform_id int8 NULL,
    CONSTRAINT platform_campaign_pkey PRIMARY KEY (id),
    CONSTRAINT fk5kwv7ngvobykvsv35a6jo5tqo FOREIGN KEY (platform_id) REFERENCES public.platform_table (id),
    CONSTRAINT fkpov2bqhhv0isrble6vji9ek4k FOREIGN KEY (campaign_id) REFERENCES public.campaign_table (id)
);



CREATE TABLE public.ad_table
(
    id          bigserial NOT NULL,
    description varchar(255) NULL,
    image_url   varchar(255) NULL,
    title       varchar(255) NULL,
    compaign_id int8 NULL,
    CONSTRAINT ad_table_pkey PRIMARY KEY (id),
    CONSTRAINT fk21k9rg0dmo8mrin7wijkslktd FOREIGN KEY (compaign_id) REFERENCES public.campaign_table (id)
);