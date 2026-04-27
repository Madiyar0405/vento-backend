CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       organization_id UUID REFERENCES organizations(id) ON DELETE CASCADE,

                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,

                       name VARCHAR(255) NOT NULL,
                       surname VARCHAR(255) NOT NULL,

                       role user_role NOT NULL DEFAULT 'MANAGER',
                       status user_status NOT NULL DEFAULT 'PENDING',

                       is_email_verified BOOLEAN DEFAULT FALSE,

                       created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       updated_at TIMESTAMPTZ
);

CREATE INDEX idx_users_org ON users(organization_id);
CREATE INDEX idx_users_email ON users(email);