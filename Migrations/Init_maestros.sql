DECLARE @startInsert INT = 0;

BEGIN TRY
	BEGIN TRANSACTION
			-- Borrar las secuencia identity de todas las tablas
			DECLARE @schema sysname = 'dbo';
			DECLARE @sql nvarchar(max) = N'';

			SELECT @sql = STRING_AGG(
				'DBCC CHECKIDENT (N''' + s.name + '.' + t.name + ''', RESEED, 0);',
				CHAR(13)
			)
			FROM sys.identity_columns ic
			JOIN sys.tables t  ON ic.object_id = t.object_id
			JOIN sys.schemas s ON t.schema_id = s.schema_id
			WHERE s.name = @schema;

			PRINT @sql
			EXEC sp_executesql @sql;
			SET @startInsert = 1;
			PRINT 'Secuencia reseteada correctamente'
	COMMIT;
END TRY
BEGIN CATCH
	ROLLBACK;
	PRINT 'Error borrando secuencia: ' + ERROR_MESSAGE();
END CATCH

IF(@startInsert = 0)
BEGIN
	PRINT 'No se puede insertar datos sin haberse borrado correctamente la secuencia'
	RETURN;
END

BEGIN TRY
	BEGIN TRANSACTION
			PRINT 'Inicio de inseraccion de datos...'
			INSERT INTO province (name) VALUES
			('Guayas');
			PRINT 'Provincias insertadas'

			INSERT INTO city (name, id_province) VALUES
			('Duran', 1),
			('Guayaquil', 1);
			PRINT 'Cuidades insertadas'

			INSERT INTO permission (permission_name, created_at, is_deleted) VALUES
			('CREATE', CAST(GETDATE() AS DATE), 0),
			('READ', CAST(GETDATE() AS DATE), 0),
			('UPDATE', CAST(GETDATE() AS DATE), 0),
			('DELETE', CAST(GETDATE() AS DATE), 0);
			PRINT 'Permisos insertadas'

			INSERT INTO [role] (role_name, is_deleted, created_at) VALUES
			('ADMIN', 0, CAST(GETDATE() AS DATE)),
			('DRIVER', 0, CAST(GETDATE() AS DATE)),
			('CLIENT', 0, CAST(GETDATE() AS DATE));
			PRINT 'Roles insertados'

			INSERT INTO role_permission (role_id, permission_id) VALUES
			(1, 1), (1, 2), (1, 3), (1, 4),
			(2, 1), (2, 2), (2, 3),
			(3, 1), (3, 2), (3, 3);
			PRINT 'Role_permission insertados'

			INSERT INTO fare (id, price_per_minute, price_per_km, base_fare, created_at) VALUES
			(1, 0.15, 0.40, 1.50, CAST(GETDATE() AS DATE));
			PRINT 'Precio insertado'


			INSERT INTO vehicle ([year], color, brand, model, license_plate, photo, created_at, created_by, is_deleted) VALUES
			('2025', 'negro', 'Chevrolet', 'Aveo', 'ABC-123', null, CAST(GETDATE() AS DATE), 'ADMIN', 0),
			('2024', 'rojo', 'Ford', 'F150', 'DEF-123', null, CAST(GETDATE() AS DATE), 'ADMIN', 0),
			('2023', 'blanco', 'Hyundai', 'Spark', 'HIJ-123', null, CAST(GETDATE() AS DATE), 'ADMIN', 0),
			('2022', 'gris', 'Tesla', 'truck', 'KLM-123', null, CAST(GETDATE() AS DATE), 'ADMIN', 0);
			PRINT 'Vehiculos insertados'

			INSERT INTO car(id, chassis_number) VALUES
			(1, 'ABCD-1234'),
			(2, 'DEFG-1234'),
			(3, 'HIJK-1234'),
			(4, 'KLMN-1234');
			PRINT 'Carros insertados'

			INSERT INTO license (driver_license, license_type, issuance_date, expiration_date, created_at, created_by, is_deleted) VALUES
			('ABC-123', 'B', CAST(GETDATE() AS DATE), '2027-01-01', CAST(GETDATE() AS DATE), 'ADMIN', 0),
			('DEF-123', 'B', CAST(GETDATE() AS DATE), '2027-01-01', CAST(GETDATE() AS DATE), 'ADMIN', 0),
			('HIJ-123', 'B', CAST(GETDATE() AS DATE), '2027-01-01', CAST(GETDATE() AS DATE), 'ADMIN', 0),
			('KLM-123', 'B', CAST(GETDATE() AS DATE), '2027-01-01', CAST(GETDATE() AS DATE), 'ADMIN', 0);
			PRINT 'Licencias insertadas'

			INSERT INTO users(names, last_names, email, phone, password_hash, photo, born_date, id_role, created_at, created_by, is_deleted) VALUES
			('Daniel', 'Mora Cantillo', 'mail@email.com', '0123456789', '{bcrypt}$2a$10$Lj02VmKkthT.mYxAUy1TrewKX3n8hMtksJEO1sJHHHrg04dFcO15a', null, '2000-01-01', 1,  CAST(GETDATE() AS DATE), 'ADMIN', 0), --contraseña = password / USER ADMIN
			('Rebeca', 'Mora Cantillo', 'mail1@email.com', '0123456781', '{bcrypt}$2a$10$Lj02VmKkthT.mYxAUy1TrewKX3n8hMtksJEO1sJHHHrg04dFcO15a', null, '2000-01-01', 3,  CAST(GETDATE() AS DATE), 'ADMIN', 0), -- USER CLIENT
			('Jose', 'Mora Cantillo', 'mail2@email.com', '0123456782', '{bcrypt}$2a$10$Lj02VmKkthT.mYxAUy1TrewKX3n8hMtksJEO1sJHHHrg04dFcO15a', null, '2000-01-01', 2,  CAST(GETDATE() AS DATE), 'ADMIN', 0), -- USER DRIVER
			('Isur', 'Mora Cantillo', 'mail3@email.com', '0123456783', '{bcrypt}$2a$10$Lj02VmKkthT.mYxAUy1TrewKX3n8hMtksJEO1sJHHHrg04dFcO15a', null, '2000-01-01', 2,  CAST(GETDATE() AS DATE), 'ADMIN', 0), -- USER DRIVER
			('Joel', 'Mora Cantillo', 'mail4@email.com', '0123456784', '{bcrypt}$2a$10$Lj02VmKkthT.mYxAUy1TrewKX3n8hMtksJEO1sJHHHrg04dFcO15a', null, '2000-01-01', 2,  CAST(GETDATE() AS DATE), 'ADMIN', 0), -- USER DRIVER
			('Ronald', 'Mora Cantillo', 'mail5@email.com', '0123456785', '{bcrypt}$2a$10$Lj02VmKkthT.mYxAUy1TrewKX3n8hMtksJEO1sJHHHrg04dFcO15a', null, '2000-01-01', 2,  CAST(GETDATE() AS DATE), 'ADMIN', 0) -- USER DRIVER
			PRINT 'Usuarios insertados'

			INSERT INTO	[admin] (id_user) VALUES
			(1)
			PRINT 'Admin insertado'

			INSERT INTO client ([user_id]) VALUES
			(2)
			PRINT 'Cliente insertado'

			INSERT INTO driver (id_user, id_license, [address], entry_date, experience_years) VALUES 
			(3, 1, 'Coop. Las manzanas', CAST(GETDATE() AS DATE), 2),
			(4, 2, 'Cdla. Los arboles', CAST(GETDATE() AS DATE), 2),
			(5, 3, 'Urb. Puntilla', CAST(GETDATE() AS DATE), 2),
			(6, 4, 'Urb. manantial', CAST(GETDATE() AS DATE), 2)
			PRINT 'Conductores insertados'

			INSERT INTO taxi(id_vehicle, id_driver, [status], created_at, created_by, is_deleted) VALUES
			(1, 1, 'ENABLE', CAST(GETDATE() AS DATE), 'ADMIN', 0),
			(2, 2, 'ENABLE', CAST(GETDATE() AS DATE), 'ADMIN', 0),
			(3, 3, 'ENABLE', CAST(GETDATE() AS DATE), 'ADMIN', 0),
			(4, 4, 'ENABLE', CAST(GETDATE() AS DATE), 'ADMIN', 0);
			PRINT 'Taxis insertados'
			SELECT * FROM taxi

			PRINT 'Maestros insertados correctamente'
			COMMIT;
END TRY
BEGIN CATCH
	ROLLBACK;
	PRINT 'Error insertando los maestros: ' + ERROR_MESSAGE()
END CATCH


