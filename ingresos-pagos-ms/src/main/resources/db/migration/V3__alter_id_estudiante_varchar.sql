ALTER TABLE pagos ALTER COLUMN id_estudiante TYPE VARCHAR(36) USING id_estudiante::text;
