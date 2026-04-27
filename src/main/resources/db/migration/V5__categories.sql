CREATE TABLE categories (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
                            name VARCHAR(255) NOT NULL,
                            created_at TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_categories_org ON categories(organization_id);