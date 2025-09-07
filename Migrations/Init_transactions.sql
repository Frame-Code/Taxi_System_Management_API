BEGIN TRY
	BEGIN TRANSACTION
		DBCC CHECKIDENT ('dbo.address', RESEED, 0);

		INSERT INTO [address] (id_city, [location], reference) VALUES
		--(2, geography::STGeomFromText('POINT(-79.879845, -2.192284)', 4326), 'malecon 2000'), --Direccion del cliente
		(2, geography::STGeomFromText('POINT(-79.886988 -2.190783)', 4326), 'parque seminario'),
		(2, geography::STGeomFromText('POINT(-79.892357 -2.188170)', 4326), 'hotel oro verde'),
		(2, geography::STGeomFromText('POINT(-79.883964 -2.196674)', 4326), 'Museo municipal'),
		(2, geography::STGeomFromText('POINT(-79.891978 -2.155990)', 4326), 'Mall del sol');
		SELECT * FROM [address]
		SELECT * FROM taxi

		INSERT INTO taxi_live_address (id, id_taxi) VALUES
		(1, 1),
		(2, 2),
		(3, 3),
		(4, 4);

		SELECT * FROM taxi

	COMMIT;
END TRY
BEGIN CATCH
	ROLLBACK;
	PRINT 'Error insertando transacciones: ' + ERROR_MESSAGE()
END CATCH