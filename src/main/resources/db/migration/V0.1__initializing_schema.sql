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
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    last_activity timestamp NULL DEFAULT CURRENT_TIMESTAMP,
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
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
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
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
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
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
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
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ad_table_pkey PRIMARY KEY (id),
    CONSTRAINT fk21k9rg0dmo8mrin7wijkslktd FOREIGN KEY (compaign_id) REFERENCES public.campaign_table (id)
);


-- Trigger function to update updated_at column
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for platform_table
CREATE TRIGGER platform_table_update_trigger
    BEFORE UPDATE ON platform_table
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_updated_at();

-- Trigger for user_table
CREATE TRIGGER user_table_update_trigger
    BEFORE UPDATE ON user_table
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_updated_at();

-- Trigger for partner_table
CREATE TRIGGER partner_table_update_trigger
    BEFORE UPDATE ON partner_table
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_updated_at();

-- Trigger for campaign_table
CREATE TRIGGER campaign_table_update_trigger
    BEFORE UPDATE ON campaign_table
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_updated_at();

-- Trigger for platform_campaign
CREATE TRIGGER platform_campaign_update_trigger
    BEFORE UPDATE ON platform_campaign
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_updated_at();

-- Trigger for ad_table
CREATE TRIGGER ad_table_update_trigger
    BEFORE UPDATE ON ad_table
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
EXECUTE FUNCTION update_updated_at();