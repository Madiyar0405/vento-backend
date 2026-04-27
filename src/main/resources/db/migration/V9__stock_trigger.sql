CREATE OR REPLACE FUNCTION fn_update_stock_balance()
RETURNS TRIGGER AS $$
BEGIN

    IF NEW.type IN ('OUT', 'TRANSFER', 'ADJUSTMENT') THEN
        INSERT INTO stock_balance(product_id, warehouse_id, quantity)
        VALUES (NEW.product_id, NEW.warehouse_id, -NEW.quantity)
        ON CONFLICT (product_id, warehouse_id)
        DO UPDATE SET quantity = stock_balance.quantity - NEW.quantity,
                      updated_at = now();
END IF;

    IF NEW.type = 'IN' THEN
        INSERT INTO stock_balance(product_id, warehouse_id, quantity)
        VALUES (NEW.product_id, NEW.warehouse_id, NEW.quantity)
        ON CONFLICT (product_id, warehouse_id)
        DO UPDATE SET quantity = stock_balance.quantity + NEW.quantity,
                      updated_at = now();
END IF;

    IF NEW.type = 'TRANSFER' AND NEW.warehouse_to_id IS NOT NULL THEN
        INSERT INTO stock_balance(product_id, warehouse_id, quantity)
        VALUES (NEW.product_id, NEW.warehouse_to_id, NEW.quantity)
        ON CONFLICT (product_id, warehouse_id)
        DO UPDATE SET quantity = stock_balance.quantity + NEW.quantity,
                      updated_at = now();
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_stock_balance
    AFTER INSERT ON stock_transactions
    FOR EACH ROW
    EXECUTE FUNCTION fn_update_stock_balance();