CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,

                          category_id UUID REFERENCES categories(id) ON DELETE SET NULL,

                          name VARCHAR(255) NOT NULL,
                          sku VARCHAR(255) UNIQUE,
                          barcode VARCHAR(255),

                          unit VARCHAR(20) NOT NULL,
                          min_stock_level NUMERIC(12,2) NOT NULL DEFAULT 0,
                          archived BOOLEAN NOT NULL DEFAULT false,

                          created_at TIMESTAMPTZ DEFAULT now()
);