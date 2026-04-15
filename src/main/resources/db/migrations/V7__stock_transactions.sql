CREATE TABLE stock_transactions (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                    organization_id UUID NOT NULL REFERENCES organizations(id),
                                    product_id UUID NOT NULL REFERENCES products(id),
                                    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
                                    warehouse_to_id UUID REFERENCES warehouses(id),

                                    user_id UUID NOT NULL REFERENCES users(id),

                                    type transaction_type NOT NULL,
                                    quantity NUMERIC(12,2) NOT NULL CHECK (quantity > 0),
                                    price_per_unit NUMERIC(12,2),
                                    commentary TEXT,

                                    created_at TIMESTAMPTZ DEFAULT now(),

                                    CONSTRAINT chk_transfer_warehouse
                                        CHECK (type != 'TRANSFER' OR warehouse_to_id IS NOT NULL)
    );