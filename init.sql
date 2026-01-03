IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'EcommerceDB')
BEGIN
  CREATE DATABASE EcommerceDB;
END
GO