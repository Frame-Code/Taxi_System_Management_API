create or alter procedure SpShowPaymentMethods
as 
begin 
	declare @text varchar(250) = (
		select definition
		from sys.check_constraints
		where parent_object_id = OBJECT_ID('payment') and definition like '%payment_method%'
	)
	
	set @text = replace(@text, '(', '')
	set @text = replace(@text, ')', '')
	set @text = replace(@text, '[payment_method]=', '')
	set @text = replace(@text, '''', '')
	set @text = replace(@text, 'or', ',')
	set @text = replace(@text, ' ', '')

	select value as payment_method
	from string_split(@text, ',')
end



