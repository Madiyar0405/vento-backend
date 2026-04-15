CREATE TABLE warehouses (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
                            name VARCHAR(255) NOT NULL,
                            address VARCHAR(255),
                            created_at TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_warehouses_org ON warehouses(organization_id);