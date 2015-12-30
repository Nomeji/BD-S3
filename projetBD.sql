Create or replace type adresse as object(
departement varchar2(20),
ville varchar2(20),
numero varchar2(20),
rue varchar2(20)
);

CREATE TABLE utilisateur (
  id number NOT NULL constraint PK_utilisateur Primary Key,
  nom varchar2(20) NOT NULL,
  prenom varchar2(20) NOT NULL,
  pseudo varchar2(20) NOT NULL,
  mdp varchar2(20) NOT NULL,
  mail varchar2(40) NOT NULL,
  anneepourstage date
);


CREATE TABLE stage (
  numstage number NOT NULL constraint PK_stage Primary Key,
  objetdustage varchar2(50) NOT NULL,
  typematerielutilise varchar2(50) NOT NULL,
  system varchar2(30) NOT NULL,
  language varchar2(50) NOT NULL,
  remunere number(1) NOT NULL,
  remuneration number NOT NULL,
  encadreinformaticien number(1) NOT NULL,
  appelinformaticien number(1) NOT NULL,
  travailseul number(1) NOT NULL,
  tailleequipe number NOT NULL,
  datetrouverstage date
);

CREATE TABLE typeutilisateur (
  numtypeutilisateur number NOT NULL constraint PK_typeutilisateur Primary Key,
  nomtypeutilisateur varchar2(20) NOT NULL
);

CREATE TABLE avis (
  idavis number constraint PK_avis Primary Key,
  niveauconnaissance varchar2(10) NOT NULL,
  organisation varchar2(10) NOT NULL,
  initiative varchar2(10) NOT NULL,
  perseverence varchar2(10) NOT NULL,
  efficacite varchar2(10) NOT NULL,
  interetautravail varchar2(10) NOT NULL,
  presentation varchar2(10) NOT NULL,
  ponctualite varchar2(10) NOT NULL,
  assiduite varchar2(10) NOT NULL,
  expression varchar2(10) NOT NULL,
  communication varchar2(10) NOT NULL,
  embauchable number(1) NOT NULL,/*boolean*/
  raisonembauchable varchar2(50) NOT NULL
);

CREATE TABLE avisetudiant (
  idavisetu number NOT NULL constraint PK_avisetudiant Primary Key,
  satisfait number(1) NOT NULL,/*boolean*/
  explicationsatisfaction varchar2(100) NOT NULL,
  objectifatteint number(1) NOT NULL,/*boolean*/
  explicationobjnonatteint varchar2(100) DEFAULT NULL,
  satisfactionenseignement number(1) NOT NULL,/*boolean*/
  matierepasasserdvp varchar2(100) DEFAULT NULL,
  apportstage varchar2(100) NOT NULL
);

CREATE TABLE entreprise (
  ident number NOT NULL constraint PK_entreprise Primary Key,
  nom varchar2(20) NOT NULL,
  adresseent adresse NOT NULL,
  tel varchar2(12) NOT NULL,
  fonctionresponsable varchar2(20) NOT NULL,
  nomrh varchar2(20) NOT NULL,
  mailrh varchar2(40) NOT NULL,
  telrh varchar2(12) NOT NULL,
  nomtaxeappren varchar2(20) NOT NULL,
  mailtaxeappren varchar2(40) NOT NULL,
  teltaxeappren varchar2(12) NOT NULL,
  nomrelationsecoles varchar2(20) NOT NULL,
  mailrelationsecoles varchar2(40) NOT NULL,
  telrelationsecoles varchar2(12) NOT NULL,
  presencetuteursoutenance number(1) NOT NULL
);


CREATE TABLE infoeleve (
  idinfo number NOT NULL constraint PK_infoeleve Primary Key,
  datenaissance date NOT NULL,
  numsecuritesociale number NOT NULL,
  numcarteetudiant number NOT NULL,
  numine varchar2(15) NOT NULL,
  attestationrc varchar2(15) NOT NULL,
  adresseeleve adresse NOT NULL,
  codePostal number NOT NULL,
  Ville number(20) NOT NULL,
  tel varchar2(12) NOT NULL
);


CREATE TABLE utilisateurtype (
  id number NOT NULL constraint CIR_utilisateur1 references utilisateur,
  numtypeutilisateur number NOT NULL constraint CIR_typeutilisateur references typeutilisateur
);
CREATE TABLE profreferent (
  idprof number NOT NULL constraint CIR_prof references utilisateur,
  idEleve number NOT NULL constraint CIR_eleve1 references utilisateur
);
CREATE TABLE tuteurentreprise (
  idtuteur number NOT NULL constraint CIR_tuteur references utilisateur,
  idEleve number NOT NULL constraint CIR_eleve2 references utilisateur
);
CREATE TABLE stageutilisateur (
  id number NOT NULL constraint CIR_utilisateur2 references utilisateur,
  numstage number NOT NULL constraint CIR_stage1 references stage
);
CREATE TABLE stageent (
  ident number NOT NULL constraint CIR_entreprise references entreprise,
  numstage number NOT NULL constraint CIR_stage2 references stage
);

CREATE TABLE stageinfoetudiant (
  numstage number NOT NULL constraint CIR_stage3 references stage,
  idinfo number NOT NULL constraint CIR_infoeleve references infoeleve
);

CREATE TABLE avisstage (
  numstage number NOT NULL constraint CIR_stage4 references stage,
  idavis number NOT NULL constraint CIR_avis references avis
);
CREATE TABLE avisetustage (
  numstage number NOT NULL constraint CIR_stage5 references stage,
  idavisetu number NOT NULL constraint CIR_avisetudiant references avisetudiant
);

create table statistique (
  nbavecstage number,
  nbsansstage number
);

/*drop table statistique;
drop table utilisateurType;
drop table tuteurEntreprise;
drop table profreferent;
drop table stageutilisateur;
drop table stageinfoetudiant;
drop table stageent;
drop table avisstage;
drop table avisetustage;
drop table typeutilisateur;
drop table utilisateur;
drop table stage;
drop table infoeleve;
drop table avis;
drop table avisetudiant;
drop table entreprise;
drop type adresse;
*/



