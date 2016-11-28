USE [Inventory]
GO

/****** Object:  Table [dbo].[Offices]    Script Date: 11/14/2016 09:17:35 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Offices](
	[ID] [int] IDENTITY(27,1) NOT NULL,
	[DisplayName] [nvarchar](max) NULL,
	[OfficeCode] [nvarchar](max) NULL
) ON [PRIMARY]

GO

