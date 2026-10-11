/****** Object:  UserDefinedTableType [{{SCHEMA}}].[detajet_termineve_array]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE TYPE [{{SCHEMA}}].[detajet_termineve_array] AS TABLE(
	[ID_sherbimit] [bigint] NOT NULL,
	[id_atributit] [bigint] NULL,
	[kohezgjatja] [int] NULL,
	[pagesa] [decimal](10, 2) NULL
)
GO
/****** Object:  UserDefinedTableType [{{SCHEMA}}].[historiku_detajet_array]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE TYPE [{{SCHEMA}}].[historiku_detajet_array] AS TABLE(
	[emri_sherbimit] [nvarchar](100) NULL,
	[emri_atributit] [nvarchar](100) NULL,
	[pershkrimi] [nvarchar](100) NULL,
	[pagesa] [decimal](10, 2) NULL
)
GO
/****** Object:  UserDefinedTableType [{{SCHEMA}}].[setAvailableSkills]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE TYPE [{{SCHEMA}}].[setAvailableSkills] AS TABLE(
	[id_skills] [bigint] NOT NULL,
	PRIMARY KEY CLUSTERED 
(
	[id_skills] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
GO
/****** Object:  UserDefinedTableType [{{SCHEMA}}].[setDaysOfTheWeek]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE TYPE [{{SCHEMA}}].[setDaysOfTheWeek] AS TABLE(
	[day_of_week] [int] NOT NULL,
	[start_time] [time](7) NOT NULL,
	[end_time] [time](7) NOT NULL,
	[pause_start] [time](7) NULL,
	[pause_end] [time](7) NULL,
	CHECK (([day_of_week]>=(1) AND [day_of_week]<=(7)))
)
GO
/****** Object:  Table [{{SCHEMA}}].[Aprovals]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[Aprovals](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](255) NOT NULL,
	[otp] [nvarchar](10) NULL,
	[created] [datetime2](7) NULL,
	[client_data] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[atributet_sherbimeve]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[atributet_sherbimeve](
	[id_atributit] [int] IDENTITY(74,1) NOT NULL,
	[ID] [int] NULL,
	[opsioni] [nvarchar](255) NULL,
	[pershkrimi_opsionit] [nvarchar](max) NULL,
	[qmimi] [decimal](10, 0) NULL,
	[zbritja] [int] NULL,
	[kohezgjatja] [int] NULL,
 CONSTRAINT [PK_atributet_sherbimeve_id_atributit] PRIMARY KEY CLUSTERED 
(
	[id_atributit] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[attendance]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[attendance](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ID_Employee] [int] NOT NULL,
	[date] [date] NOT NULL,
	[started_at] [time](7) NOT NULL,
	[ended_at] [time](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[availabilityDetails]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[availabilityDetails](
	[id_availability_details] [bigint] IDENTITY(1,1) NOT NULL,
	[id_availability] [bigint] NOT NULL,
	[day_of_week] [int] NOT NULL,
	[start_time] [time](7) NOT NULL,
	[end_time] [time](7) NOT NULL,
	[pause_start] [time](7) NULL,
	[pause_end] [time](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_availability_details] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[availableSkill]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[availableSkill](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_availability] [bigint] NOT NULL,
	[id_skills] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[clients]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[clients](
	[ID] [int] IDENTITY(8,1) NOT NULL,
	[emri] [nvarchar](20) NULL,
	[mbiemri] [nvarchar](30) NULL,
	[numri_telefonit] [nvarchar](60) NULL,
	[data_regjistrimit] [datetime] NULL,
	[pershkrimi] [nvarchar](255) NULL,
	[email] [nvarchar](100) NULL,
	[emailVerified] [bit] NOT NULL,
 CONSTRAINT [PK_clients_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[detajet_termineve]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[detajet_termineve](
	[id_detajetTermineve] [bigint] IDENTITY(1,1) NOT NULL,
	[id_terminit] [int] NOT NULL,
	[ID_sherbimit] [int] NOT NULL,
	[id_atributit] [int] NULL,
	[pagesa] [decimal](10, 2) NULL,
	[kohezgjatja] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_detajetTermineve] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[emailVerificationOTP]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[emailVerificationOTP](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[otp] [nvarchar](6) NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[createdAt] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[employeeAvailability]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[employeeAvailability](
	[id_availability] [bigint] IDENTITY(1,1) NOT NULL,
	[id_employee] [int] NOT NULL,
	[start_date] [date] NOT NULL,
	[end_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_availability] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[employees]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[employees](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[emri] [nvarchar](20) NOT NULL,
	[mbiemri] [nvarchar](20) NOT NULL,
	[gjinia] [nvarchar](10) NULL,
	[pershkrimi] [nvarchar](255) NULL,
	[username] [nvarchar](20) NOT NULL,
	[userpassword] [nvarchar](100) NOT NULL,
	[data_regjistrimit] [datetime] NULL,
	[numri_telefonit] [nvarchar](30) NULL,
	[email] [nvarchar](50) NULL,
	[is_active] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[historiku]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[historiku](
	[id_historikut] [int] IDENTITY(40,1) NOT NULL,
	[ID] [int] NULL,
	[data_sherbimit] [datetime2](7) NULL,
	[emri_mbiemri_punonjesit] [nvarchar](50) NULL,
 CONSTRAINT [PK_historiku_id_historikut] PRIMARY KEY CLUSTERED 
(
	[id_historikut] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[historiku_detajet]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[historiku_detajet](
	[id_historikut_detajet] [bigint] IDENTITY(1,1) NOT NULL,
	[id_historikut] [int] NOT NULL,
	[emri_sherbimit] [nvarchar](100) NULL,
	[emri_atributit] [nvarchar](150) NULL,
	[pershkrimi] [nvarchar](150) NULL,
	[pagesa] [decimal](10, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_historikut_detajet] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[LoginOTP]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[LoginOTP](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[numri_telefonit] [nvarchar](15) NOT NULL,
	[otp] [nvarchar](6) NOT NULL,
	[CreatedAt] [datetime2](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[qr_session]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[qr_session](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[code] [varchar](255) NULL,
	[expires_at] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[sherbimet]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[sherbimet](
	[ID] [int] IDENTITY(4,1) NOT NULL,
	[emri_sherbimit] [nvarchar](255) NULL,
	[pershkrimi] [nvarchar](max) NULL,
	[qmimi_baze] [decimal](10, 5) NULL,
	[is_active] [smallint] NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
	[zbritja] [int] NULL,
	[imagepath] [nvarchar](255) NULL,
	[kohezgjatja] [int] NULL,
 CONSTRAINT [PK_sherbimet_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[skills]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[skills](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_employee] [int] NOT NULL,
	[id_service] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [unique_employee_service] UNIQUE NONCLUSTERED 
(
	[id_employee] ASC,
	[id_service] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[terminet]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[terminet](
	[id_terminit] [int] IDENTITY(235,1) NOT NULL,
	[ID] [int] NULL,
	[pershkrimi] [nvarchar](max) NULL,
	[data_caktimit] [datetime2](0) NULL,
	[employee_id] [int] NULL,
	[status] [nvarchar](15) NULL,
	[data_krijimit] [datetime2](7) NOT NULL,
 CONSTRAINT [PK_terminet_id_terminit] PRIMARY KEY CLUSTERED 
(
	[id_terminit] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [{{SCHEMA}}].[users]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [{{SCHEMA}}].[users](
	[ID] [int] IDENTITY(3,1) NOT NULL,
	[emri] [nvarchar](20) NOT NULL,
	[mbiemri] [nvarchar](20) NOT NULL,
	[username] [nvarchar](255) NULL,
	[userpassword] [nvarchar](255) NULL,
	[dateRegistered] [datetime] NULL,
 CONSTRAINT [PK_users_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Index [ID]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE NONCLUSTERED INDEX [ID] ON [{{SCHEMA}}].[atributet_sherbimeve]
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_clients_email]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_clients_email] ON [{{SCHEMA}}].[clients]
(
	[email] ASC
)
WHERE ([email] IS NOT NULL)
WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [ID]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE NONCLUSTERED INDEX [ID] ON [{{SCHEMA}}].[historiku]
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [ID]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE NONCLUSTERED INDEX [ID] ON [{{SCHEMA}}].[terminet]
(
	[ID] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_terminet_employee_data]    Script Date: 11/10/2026 3:00:40 PM ******/
CREATE NONCLUSTERED INDEX [IX_terminet_employee_data] ON [{{SCHEMA}}].[terminet]
(
	[employee_id] ASC,
	[data_caktimit] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [{{SCHEMA}}].[Aprovals] ADD  DEFAULT (sysdatetime()) FOR [created]
GO
ALTER TABLE [{{SCHEMA}}].[atributet_sherbimeve] ADD  DEFAULT (NULL) FOR [ID]
GO
ALTER TABLE [{{SCHEMA}}].[atributet_sherbimeve] ADD  DEFAULT (NULL) FOR [opsioni]
GO
ALTER TABLE [{{SCHEMA}}].[atributet_sherbimeve] ADD  DEFAULT (NULL) FOR [qmimi]
GO
ALTER TABLE [{{SCHEMA}}].[atributet_sherbimeve] ADD  DEFAULT (NULL) FOR [zbritja]
GO
ALTER TABLE [{{SCHEMA}}].[attendance] ADD  DEFAULT (NULL) FOR [ended_at]
GO
ALTER TABLE [{{SCHEMA}}].[availabilityDetails] ADD  DEFAULT (NULL) FOR [pause_start]
GO
ALTER TABLE [{{SCHEMA}}].[availabilityDetails] ADD  DEFAULT (NULL) FOR [pause_end]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  DEFAULT (NULL) FOR [emri]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  DEFAULT (NULL) FOR [mbiemri]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  DEFAULT (NULL) FOR [numri_telefonit]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  CONSTRAINT [DF_clients_data_regjistrimit]  DEFAULT (getdate()) FOR [data_regjistrimit]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  DEFAULT (NULL) FOR [pershkrimi]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  DEFAULT (NULL) FOR [email]
GO
ALTER TABLE [{{SCHEMA}}].[clients] ADD  DEFAULT ((0)) FOR [emailVerified]
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve] ADD  DEFAULT (NULL) FOR [id_atributit]
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve] ADD  DEFAULT ((0)) FOR [pagesa]
GO
ALTER TABLE [{{SCHEMA}}].[emailVerificationOTP] ADD  DEFAULT (getdate()) FOR [createdAt]
GO
ALTER TABLE [{{SCHEMA}}].[employees] ADD  DEFAULT (getdate()) FOR [data_regjistrimit]
GO
ALTER TABLE [{{SCHEMA}}].[employees] ADD  DEFAULT ((1)) FOR [is_active]
GO
ALTER TABLE [{{SCHEMA}}].[historiku] ADD  DEFAULT (getdate()) FOR [data_sherbimit]
GO
ALTER TABLE [{{SCHEMA}}].[LoginOTP] ADD  DEFAULT (getutcdate()) FOR [CreatedAt]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT (NULL) FOR [emri_sherbimit]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT (NULL) FOR [qmimi_baze]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT ((1)) FOR [is_active]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT (getdate()) FOR [updated_at]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT (NULL) FOR [zbritja]
GO
ALTER TABLE [{{SCHEMA}}].[sherbimet] ADD  DEFAULT (NULL) FOR [imagepath]
GO
ALTER TABLE [{{SCHEMA}}].[terminet] ADD  DEFAULT (NULL) FOR [ID]
GO
ALTER TABLE [{{SCHEMA}}].[terminet] ADD  DEFAULT (NULL) FOR [data_caktimit]
GO
ALTER TABLE [{{SCHEMA}}].[terminet] ADD  DEFAULT (NULL) FOR [employee_id]
GO
ALTER TABLE [{{SCHEMA}}].[terminet] ADD  CONSTRAINT [DF_status]  DEFAULT ('pending') FOR [status]
GO
ALTER TABLE [{{SCHEMA}}].[terminet] ADD  DEFAULT (sysdatetime()) FOR [data_krijimit]
GO
ALTER TABLE [{{SCHEMA}}].[users] ADD  DEFAULT (N'') FOR [emri]
GO
ALTER TABLE [{{SCHEMA}}].[users] ADD  DEFAULT (N'') FOR [mbiemri]
GO
ALTER TABLE [{{SCHEMA}}].[users] ADD  DEFAULT (NULL) FOR [username]
GO
ALTER TABLE [{{SCHEMA}}].[users] ADD  DEFAULT (NULL) FOR [userpassword]
GO
ALTER TABLE [{{SCHEMA}}].[users] ADD  DEFAULT (getdate()) FOR [dateRegistered]
GO
ALTER TABLE [{{SCHEMA}}].[atributet_sherbimeve]  WITH NOCHECK ADD  CONSTRAINT [atributet_sherbimeve$ID] FOREIGN KEY([ID])
REFERENCES [{{SCHEMA}}].[sherbimet] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[atributet_sherbimeve] CHECK CONSTRAINT [atributet_sherbimeve$ID]
GO
ALTER TABLE [{{SCHEMA}}].[attendance]  WITH CHECK ADD  CONSTRAINT [attendance_id_employee] FOREIGN KEY([ID_Employee])
REFERENCES [{{SCHEMA}}].[employees] ([ID])
GO
ALTER TABLE [{{SCHEMA}}].[attendance] CHECK CONSTRAINT [attendance_id_employee]
GO
ALTER TABLE [{{SCHEMA}}].[availabilityDetails]  WITH CHECK ADD FOREIGN KEY([id_availability])
REFERENCES [{{SCHEMA}}].[employeeAvailability] ([id_availability])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[availableSkill]  WITH CHECK ADD  CONSTRAINT [id_availability_ava_skills] FOREIGN KEY([id_availability])
REFERENCES [{{SCHEMA}}].[employeeAvailability] ([id_availability])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[availableSkill] CHECK CONSTRAINT [id_availability_ava_skills]
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve]  WITH CHECK ADD  CONSTRAINT [fk_id_atributit] FOREIGN KEY([id_atributit])
REFERENCES [{{SCHEMA}}].[atributet_sherbimeve] ([id_atributit])
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve] CHECK CONSTRAINT [fk_id_atributit]
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve]  WITH CHECK ADD  CONSTRAINT [fk_ID_sherbimit] FOREIGN KEY([ID_sherbimit])
REFERENCES [{{SCHEMA}}].[sherbimet] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve] CHECK CONSTRAINT [fk_ID_sherbimit]
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve]  WITH CHECK ADD  CONSTRAINT [fk_id_terminit] FOREIGN KEY([id_terminit])
REFERENCES [{{SCHEMA}}].[terminet] ([id_terminit])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[detajet_termineve] CHECK CONSTRAINT [fk_id_terminit]
GO
ALTER TABLE [{{SCHEMA}}].[employeeAvailability]  WITH CHECK ADD FOREIGN KEY([id_employee])
REFERENCES [{{SCHEMA}}].[employees] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[historiku]  WITH CHECK ADD  CONSTRAINT [fk_ID] FOREIGN KEY([ID])
REFERENCES [{{SCHEMA}}].[clients] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[historiku] CHECK CONSTRAINT [fk_ID]
GO
ALTER TABLE [{{SCHEMA}}].[historiku_detajet]  WITH CHECK ADD  CONSTRAINT [fk_id_historikut] FOREIGN KEY([id_historikut])
REFERENCES [{{SCHEMA}}].[historiku] ([id_historikut])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[historiku_detajet] CHECK CONSTRAINT [fk_id_historikut]
GO
ALTER TABLE [{{SCHEMA}}].[skills]  WITH CHECK ADD  CONSTRAINT [fk_emp_id] FOREIGN KEY([id_employee])
REFERENCES [{{SCHEMA}}].[employees] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[skills] CHECK CONSTRAINT [fk_emp_id]
GO
ALTER TABLE [{{SCHEMA}}].[skills]  WITH CHECK ADD  CONSTRAINT [fk_id_service] FOREIGN KEY([id_service])
REFERENCES [{{SCHEMA}}].[sherbimet] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[skills] CHECK CONSTRAINT [fk_id_service]
GO
ALTER TABLE [{{SCHEMA}}].[terminet]  WITH CHECK ADD  CONSTRAINT [fk_employee_id] FOREIGN KEY([employee_id])
REFERENCES [{{SCHEMA}}].[employees] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[terminet] CHECK CONSTRAINT [fk_employee_id]
GO
ALTER TABLE [{{SCHEMA}}].[terminet]  WITH CHECK ADD  CONSTRAINT [fk_terminet_client_id] FOREIGN KEY([ID])
REFERENCES [{{SCHEMA}}].[clients] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [{{SCHEMA}}].[terminet] CHECK CONSTRAINT [fk_terminet_client_id]
GO
ALTER TABLE [{{SCHEMA}}].[terminet]  WITH CHECK ADD  CONSTRAINT [CHK_status_values] CHECK  (([status]='executing' OR [status]='overdue' OR [status]='pending'))
GO
ALTER TABLE [{{SCHEMA}}].[terminet] CHECK CONSTRAINT [CHK_status_values]
GO
/****** Object:  StoredProcedure [{{SCHEMA}}].[create_appointment]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [{{SCHEMA}}].[create_appointment]
(
    @IDClientit BIGINT,
    @IDEmployee BIGINT,
    @pershkrimi NVARCHAR(255) = NULL,
    @data_caktimit DATETIME2,
    @detajet_termineve [{{SCHEMA}}].detajet_termineve_array READONLY,
    @status BIT OUTPUT
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        DECLARE @newID BIGINT;

        ---------------------------------------------------
        -- 1. CHECK FOR CONFLICT (same employee + same time)
        ---------------------------------------------------
        IF EXISTS (
            SELECT 1
            FROM [{{SCHEMA}}].terminet
            WHERE employee_id = @IDEmployee
              AND DATEADD(MINUTE, DATEDIFF(MINUTE, 0, data_caktimit), 0) =
                  DATEADD(MINUTE, DATEDIFF(MINUTE, 0, @data_caktimit), 0)
        )
        BEGIN
            SET @status = 0;
            RETURN;
        END

        ---------------------------------------------------
        -- 2. TRANSACTION START
        ---------------------------------------------------
        BEGIN TRANSACTION;

        ---------------------------------------------------
        -- 3. INSERT APPOINTMENT
        -- ?? FIXED: assumes ID is IDENTITY (MOST COMMON CASE)
        ---------------------------------------------------
          INSERT INTO [{{SCHEMA}}].terminet (ID, employee_id, pershkrimi, data_caktimit)
        VALUES (@IDClientit, @IDEmployee, @pershkrimi, @data_caktimit);

        -- Get new appointment ID
        SET @newID = SCOPE_IDENTITY();

        ---------------------------------------------------
        -- 4. INSERT DETAILS (TVP)
        ---------------------------------------------------
        EXEC [{{SCHEMA}}].krijo_detaj_terminit
            @id_terminit = @newID,
            @detajet_termineve = @detajet_termineve;

        ---------------------------------------------------
        -- 5. COMMIT
        ---------------------------------------------------
        COMMIT TRANSACTION;

        SET @status = 1;

    END TRY
    BEGIN CATCH

        IF XACT_STATE() <> 0
            ROLLBACK TRANSACTION;

        SET @status = 0;
    END CATCH
END
GO
/****** Object:  StoredProcedure [{{SCHEMA}}].[register_history]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [{{SCHEMA}}].[register_history] 

@ID int,
@id_employee int,
@historiku_detajet [{{SCHEMA}}].historiku_detajet_array READONLY
as 
begin
SET NOCOUNT ON;

    BEGIN TRANSACTION;


declare @newID int;

INSERT INTO [{{SCHEMA}}].historiku (ID, emri_mbiemri_punonjesit)
VALUES (
    @ID,
    (SELECT emri + ' ' + mbiemri
     FROM [{{SCHEMA}}].employees
     WHERE ID = @id_employee)
);

 set @newID = SCOPE_IDENTITY();

 insert into [{{SCHEMA}}].historiku_detajet(id_historikut, emri_sherbimit, emri_atributit, pershkrimi, pagesa)
 SELECT @newID, emri_sherbimit, emri_atributit, pershkrimi, pagesa from @historiku_detajet;
  commit transaction;
 end;
GO
/****** Object:  StoredProcedure [{{SCHEMA}}].[setAvailableDates]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [{{SCHEMA}}].[setAvailableDates]
    @id_employee INT,
    @start_date DATE,
    @end_date DATE,
    @daysOfTheWeek [{{SCHEMA}}].setDaysOfTheWeek READONLY,
    @availableSkills [{{SCHEMA}}].setAvailableSkills READONLY
AS
BEGIN
    SET NOCOUNT ON;

    IF @end_date < @start_date
    BEGIN
        THROW 50002, 'End date cannot be before start date.', 1;
    END;

    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Check for overlapping availability
        IF EXISTS
        (
            SELECT 1
            FROM [{{SCHEMA}}].employeeAvailability
            WHERE id_employee = @id_employee
              AND @start_date <= end_date
              AND @end_date >= start_date
        )
        BEGIN
            THROW 50001, 'Employee already has availability in this date range.', 1;
        END;

        -- Check that all selected skills belong to this employee
        IF EXISTS
        (
            SELECT 1
            FROM @availableSkills s
            LEFT JOIN [{{SCHEMA}}].skills sk
                ON sk.id = s.id_skills
                AND sk.id_employee = @id_employee
            WHERE sk.id IS NULL
        )
        BEGIN
            THROW 50003, 'One or more skills do not belong to this employee.', 1;
        END;

        DECLARE @id_availability BIGINT;

        -- Insert availability
        INSERT INTO [{{SCHEMA}}].employeeAvailability
        (
            id_employee,
            start_date,
            end_date
        )
        VALUES
        (
            @id_employee,
            @start_date,
            @end_date
        );

        SET @id_availability = SCOPE_IDENTITY();

        -- Insert availability details
        INSERT INTO [{{SCHEMA}}].availabilityDetails
        (
            id_availability,
            day_of_week,
            start_time,
            end_time,
            pause_start,
            pause_end
        )
        SELECT
            @id_availability,
            day_of_week,
            start_time,
            end_time,
            pause_start,
            pause_end
        FROM @daysOfTheWeek;

        -- Insert available skills
        INSERT INTO [{{SCHEMA}}].availableSkill
        (
            id_availability,
            id_skills
        )
        SELECT
            @id_availability,
            id_skills
        FROM @availableSkills;

        COMMIT TRANSACTION;

    END TRY

    BEGIN CATCH

        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        THROW;

    END CATCH
END;
GO
/****** Object:  StoredProcedure [{{SCHEMA}}].[AddAttendance]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [{{SCHEMA}}].[AddAttendance]
    @ID_Employee INT,
    @started_at TIME,
    @date DATE
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;


        -- Check if employee is available on this date
        IF NOT EXISTS
        (
            SELECT 1
            FROM employeeAvailability ea
            INNER JOIN availabilityDetails ad
                ON ea.id_availability = ad.id_availability
            WHERE ea.id_employee = @ID_Employee
            AND @date BETWEEN ea.start_date AND ea.end_date
            AND ad.day_of_week = DATEPART(WEEKDAY, @date)
            AND @started_at BETWEEN ad.start_time AND ad.end_time
        )
        BEGIN
            THROW 50001, 'Employee is not available on this date or time.', 1;
        END;


        -- Insert attendance
        INSERT INTO attendance
        (
            ID_Employee,
            started_at,
            date
        )
        VALUES
        (
            @ID_Employee,
            @started_at,
            @date
        );


        COMMIT TRANSACTION;

    END TRY

    BEGIN CATCH

        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        THROW;

    END CATCH
END;
GO
/****** Object:  StoredProcedure [{{SCHEMA}}].[clientHistory]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [{{SCHEMA}}].[clientHistory]
  @ClientId BIGINT
   as 
   begin
          SELECT
        c.emri, c.mbiemri,
        h.emri_mbiemri_punonjesit,
        h.data_sherbimit,

        d.id_historikut_detajet,
        d.emri_sherbimit,
        d.pagesa

    FROM historiku h
     JOIN Clients c ON c.ID = h.ID
    LEFT JOIN historiku_detajet d ON d.id_historikut = h.id_historikut

    WHERE h.ID = @ClientId;
    END;
GO
/****** Object:  StoredProcedure [{{SCHEMA}}].[krijo_detaj_terminit]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [{{SCHEMA}}].[krijo_detaj_terminit]
(
    @id_terminit BIGINT,
    @detajet_termineve [{{SCHEMA}}].detajet_termineve_array READONLY
)
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO detajet_termineve
    (
        id_terminit,
        ID_sherbimit,
        id_atributit,
        kohezgjatja,
        pagesa
    )
    SELECT
        @id_terminit,
        ID_sherbimit,
        id_atributit,
        kohezgjatja,
        pagesa
    FROM @detajet_termineve;
END
GO
/****** Object:  Trigger [{{SCHEMA}}].[trg_skills_delete]    Script Date: 11/10/2026 3:00:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [{{SCHEMA}}].[trg_skills_delete]
ON [{{SCHEMA}}].[skills]
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DELETE av
    FROM availableSkill av
    INNER JOIN deleted d
        ON av.id_skills = d.id;
END;
GO
ALTER TABLE [{{SCHEMA}}].[skills] ENABLE TRIGGER [trg_skills_delete]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'beautysalon.atributet_sherbimeve' , @level0type=N'SCHEMA',@level0name=N'{{SCHEMA}}', @level1type=N'TABLE',@level1name=N'atributet_sherbimeve'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'beautysalon.clients' , @level0type=N'SCHEMA',@level0name=N'{{SCHEMA}}', @level1type=N'TABLE',@level1name=N'clients'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'beautysalon.historiku' , @level0type=N'SCHEMA',@level0name=N'{{SCHEMA}}', @level1type=N'TABLE',@level1name=N'historiku'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'beautysalon.sherbimet' , @level0type=N'SCHEMA',@level0name=N'{{SCHEMA}}', @level1type=N'TABLE',@level1name=N'sherbimet'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'beautysalon.terminet' , @level0type=N'SCHEMA',@level0name=N'{{SCHEMA}}', @level1type=N'TABLE',@level1name=N'terminet'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'beautysalon.users' , @level0type=N'SCHEMA',@level0name=N'{{SCHEMA}}', @level1type=N'TABLE',@level1name=N'users'
GO