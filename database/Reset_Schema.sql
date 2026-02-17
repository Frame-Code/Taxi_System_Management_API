BEGIN TRY
	BEGIN TRANSACTION
			DECLARE @schema sysname = 'dbo';
			DECLARE @sql    nvarchar(max);
			------------------------------------------------------------
			-- 1) Dropear FOREIGN KEYS (todas) del esquema dbo
			------------------------------------------------------------
			SET @sql = N'';
			SELECT @sql = STRING_AGG(
			  'ALTER TABLE ' + QUOTENAME(s.name) + '.' + QUOTENAME(t.name) +
			  ' DROP CONSTRAINT ' + QUOTENAME(fk.name) + ';',
			  CHAR(10)
			)
			FROM sys.foreign_keys fk
			JOIN sys.tables t  ON t.object_id  = fk.parent_object_id
			JOIN sys.schemas s ON s.schema_id  = t.schema_id
			WHERE s.name = @schema;

			IF @sql IS NOT NULL AND @sql <> '' EXEC sp_executesql @sql;
			PRINT 'Foreign keys eliminadas'

			------------------------------------------------------------
			-- 2) Dropear UNIQUE CONSTRAINTS (UQ) nombradas UK_*
			------------------------------------------------------------
			SET @sql = N'';
			SELECT @sql = STRING_AGG(
				'ALTER TABLE ' + QUOTENAME(SCHEMA_NAME(parent_object_id)) + '.' + QUOTENAME(OBJECT_NAME(parent_object_id)) +
				' DROP CONSTRAINT ' + QUOTENAME(name) + ';',
				CHAR(10)
			)
			FROM sys.key_constraints
			WHERE type = 'UQ'
			  AND name LIKE 'UK_%'
			  AND SCHEMA_NAME(parent_object_id) = @schema;

			IF @sql IS NOT NULL EXEC sp_executesql @sql;
			PRINT 'Unique constraints eliminados'

			------------------------------------------------------------
			-- 3) Dropear ÍNDICES (no-PK) llamados UK_*
			------------------------------------------------------------
			SET @sql = N'';
			SELECT @sql = STRING_AGG(
				'DROP INDEX ' + QUOTENAME(i.name) + ' ON ' + QUOTENAME(OBJECT_SCHEMA_NAME(i.object_id)) + '.' + QUOTENAME(OBJECT_NAME(i.object_id)) + ';',
				CHAR(10)
			)
			FROM sys.indexes i
			WHERE i.name LIKE 'UK_%'
			  AND i.is_primary_key = 0
			  AND i.is_unique_constraint = 0   -- porque los UQ ya los quitamos arriba
			  AND OBJECT_SCHEMA_NAME(i.object_id) = @schema;

			IF @sql IS NOT NULL AND @sql <> '' EXEC sp_executesql @sql;
			PRINT 'Indices eliminados'

			------------------------------------------------------------
			-- 4) Dropear VISTAS del esquema
			------------------------------------------------------------
			SET @sql = N'';
			SELECT @sql = STRING_AGG(
				'DROP VIEW ' + QUOTENAME(s.name) + '.' + QUOTENAME(v.name) + ';',
				CHAR(10)
			)
			FROM sys.views v
			JOIN sys.schemas s ON v.schema_id = s.schema_id
			WHERE s.name = @schema;

			IF @sql IS NOT NULL AND @sql <> '' EXEC sp_executesql @sql;
			PRINT 'Vistas eliminadas'

			------------------------------------------------------------
			-- 5) Dropear TABLAS (ya sin FKs ni UQ/UK que estorben)
			------------------------------------------------------------
			SET @sql = N'';
			SELECT @sql = STRING_AGG(
				'DROP TABLE ' + QUOTENAME(s.name) + '.' + QUOTENAME(t.name) + ';',
				CHAR(10)
			)
			FROM sys.tables t
			JOIN sys.schemas s ON t.schema_id = s.schema_id
			WHERE s.name = @schema;

			IF @sql IS NOT NULL AND @sql <> '' EXEC sp_executesql @sql;
			PRINT 'Tablas eliminadas'
	COMMIT;
END TRY
BEGIN CATCH
	ROLLBACK;
	PRINT 'Error reseteando squema: ' + ERROR_MESSAGE();
END CATCH
