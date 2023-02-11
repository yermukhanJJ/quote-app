ALTER TABLE user_score ADD COLUMN voice boolean;
ALTER TABLE quote ADD COLUMN createAt timestamp with time zone;
ALTER TABLE quote ADD COLUMN updateAt timestamp with time zone;
ALTER TABLE users ADD COLUMN createAt timestamp with time zone;