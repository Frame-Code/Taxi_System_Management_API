
EXEC spShowConstraintsName 'taxi_live_address'

EXEC sp_MostrarRelacionesTabla 'address'

SELECT * FROM city

SELECT * FROM [address] a
INNER JOIN City c ON a.id_city = c.id

EXEC spGetNearbyCabs 'POINT (-79.879845 -2.192284)' , 5000

SELECT * FROM road_notification

UPDATE road_notification
SET [status] = 'ACCEPTED'
WHERE id = 52



