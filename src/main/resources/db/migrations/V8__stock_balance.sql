CREATE TABLE stock_balance (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                               product_id UUID NOT NULL REFERENCES products(id),
                               warehouse_id UUID NOT NULL REFERENCES warehouses(id),

                               quantity NUMERIC(14,3) NOT NULL DEFAULT 0,
                               updated_at TIMESTAMPTZ DEFAULT now(),

                               UNIQUE(product_id, warehouse_id),
                               CHECK (quantity >= 0)
);