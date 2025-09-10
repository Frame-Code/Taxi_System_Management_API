CREATE PROCEDURE spGetNearbyCabs(
	@pointWTK NVARCHAR(70),
	@meters INT
)
AS
BEGIN
	SELECT *
        FROM dbo.[address] a
        INNER JOIN dbo.taxi_live_address tla
            ON a.id = tla.id
        WHERE a.location.STDistance(geography::STGeomFromText(@pointWTK, 4326)) <= @meters
        ORDER BY a.location.STDistance(geography::STGeomFromText(@pointWTK, 4326))
END