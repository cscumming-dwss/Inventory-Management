USE [InventoryTEST]
GO

/****** Object:  Table [dbo].[LU_Locations]    Script Date: 11/14/2016 09:17:02 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[LU_Locations](
	[ID] [int] IDENTITY(10000,1) NOT NULL,
	[LocationCode] [nchar](5) NOT NULL,
	[LU1] [nchar](4) NULL,
	[LU2] [nchar](4) NULL,
	[DefaultPrinter] [nvarchar](7) NULL,
 CONSTRAINT [PK_LU_Locations] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

