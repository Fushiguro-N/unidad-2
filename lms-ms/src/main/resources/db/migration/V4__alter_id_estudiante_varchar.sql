ALTER TABLE matriculas ALTER COLUMN id_estudiante TYPE VARCHAR(36) USING id_estudiante::text;
