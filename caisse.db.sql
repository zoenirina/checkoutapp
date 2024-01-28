BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS " groupe" (
	"idGroupe"	INTEGER NOT NULL,
	"nomGroupe"	VARCHAR(100) NOT NULL,
	"description"	VARCHAR(100),
	PRIMARY KEY("idGroupe")
);
CREATE TABLE IF NOT EXISTS "utilisateur" (
	"idUtilisateur"	int,
	"nomUtilisateur"	varchar(100),
	"password"	varchar(70),
	"idGroupe"	int,
	"actif"	int,
	"dateCreation"	datetime,
	FOREIGN KEY("idGroupe") REFERENCES " groupe"("idGroupe") on delete cascade
);
CREATE TABLE IF NOT EXISTS "profile" (
	"idProfile"	INTEGER NOT NULL,
	"nom"	VARCHAR(50),
	"prenom"	VARCHAR(100),
	"fonction"	varchar(50),
	"dateNaiss"	date,
	"tel"	varchar(20),
	"idUtilisateur"	INTEGER,
	CONSTRAINT "fk_idutilisateur" FOREIGN KEY("idUtilisateur") REFERENCES "utilisateur"("idUtilisateur"),
	PRIMARY KEY("IdProfile")
);
CREATE TABLE IF NOT EXISTS "config" (
	"idConfig"	INTEGER NOT NULL,
	"DBuserName"	VARCHAR(20),
	"DBpassWord"	VARCHAR(50),
	"DBpath"	varchar(100),
	"codeRecuperation"	varchar(30),
	"DBName"	varchar(20),
	PRIMARY KEY("IdConfig")
);
COMMIT;
