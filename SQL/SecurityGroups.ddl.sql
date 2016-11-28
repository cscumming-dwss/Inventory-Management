USE [Inventory]
GO

/****** Object:  Table [dbo].[SecurityGroups]    Script Date: 11/14/2016 09:17:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SecurityGroups](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[PermissionLevel] [nvarchar](max) NOT NULL,
	[Data] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_SecurityGroups] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

