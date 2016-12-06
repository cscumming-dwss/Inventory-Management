USE [InventoryTEST]
GO

/****** Object:  Table [dbo].[product]    Script Date: 12/02/2016 15:29:37 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[product](
	[ID] [int] IDENTITY(6497,1) NOT NULL,
	[Barcode] [nvarchar](50) NOT NULL,
	[PropertyTag] [nvarchar](50) NULL,
	[DateEntered] [date] NOT NULL,
	[Serial] [nvarchar](50) NULL,
	[OfficeCode] [nvarchar](50) NULL,
	[Description] [nvarchar](50) NULL,
	[Type] [nvarchar](50) NULL,
	[Model] [nvarchar](50) NULL,
	[Manufacturer] [nvarchar](50) NULL,
	[Operator] [nvarchar](50) NULL,
	[Unit] [nvarchar](50) NULL,
	[Comments] [nvarchar](max) NULL,
	[Cost] [money] NULL,
	[ShowComments] [bit] NULL,
	[Vendor] [nvarchar](50) NULL,
	[Tech] [nvarchar](50) NULL,
	[DateReceived] [date] NOT NULL,
	[PurchaseOrder] [nvarchar](50) NULL,
	[BudgetCode] [nvarchar](50) NULL,
	[LU] [nvarchar](50) NULL,
	[LastVerified] [date] NOT NULL,
	[ComputerRelated] [bit] NOT NULL,
	[Excessed] [bit] NULL,
	[LocationCode] [nvarchar](50) NULL,
	[LocationType] [nvarchar](50) NULL,
	[ReplacementApproved] [date] NOT NULL,
	[ItemReplaced] [bit] NULL,
	[Notes] [nvarchar](max) NULL,
	[LastInventoried] [date] NOT NULL,
	[MarkedIS] [bit] NULL,
	[ActionLog] [nvarchar](max) NULL,
	[HeatTicket] [int] NULL,
 CONSTRAINT [PK_Items] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF_Items_ComputerRelated]  DEFAULT ((1)) FOR [ComputerRelated]
GO

ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF_Items_MarkedIS]  DEFAULT ((0)) FOR [MarkedIS]
GO

