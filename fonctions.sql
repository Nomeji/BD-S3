Create or replace Function nbstage return integer is
nbavecstage integer;
Begin
  Select count(*) into nbavecstage/* met la valuer dans nb avecstage*/
  from utilisateur U, utilisateurtype A, typeutilisateur T
  where U.id=A.id/*liaison*/
  and A.numtypeutilisateur=T.numtypeutilisateur
  and nomTypeUtilisateur='ETUDIANT'/*type=etudiant*/
  and extract(year from anneepourstage) = extract(year from CURRENT_DATE)/*pour que ce soit les stages de l'annee courante*/
  and U.id IN (select B.id
               from stageutilisateur B);/* si id est dans stageutilisateur c'est que l'etudiant a un stage*/            
  return nbavecstage;/*retourne la valeur obtenu*/
END nbstage;

Create or replace Function nbnonstage return number is
nbsansstage number;
Begin
/*idem que nbstage mais avec not in pour ce qui n'ont pas de stage*/
  Select count(*) into nbsansstage
  from utilisateur U, utilisateurtype A, typeutilisateur T
  where U.id=A.id
  and A.numtypeutilisateur=T.numtypeutilisateur
  and nomTypeUtilisateur='ETUDIANT'
  and extract(year from anneepourstage) = extract(year from CURRENT_DATE)/*pour que ce soit les stages de l'annee courante*/
  and U.id NOT IN (select id /* ne doit pas etre dans stageutilisateur*/
               from stageutilisateur);         
  return nbsansstage;/*retourne la valeur obtenu*/
END nbnonstage;



Create or replace Function nbnonstageannee(dateUser date) return integer is
nbsansstageannee integer;
Begin
  select count (*) into nbsansstageannee
  from utilisateur U, utilisateurtype A, typeutilisateur T
  where U.id=A.id
  and A.numtypeutilisateur=T.numtypeutilisateur
  and nomTypeUtilisateur='ETUDIANT'/*tout les etudiants*/
  and extract(year from U.anneepourstage) = extract(year from dateUser)/*dont l'annee ou ils doivent passer leur stage est la meme que l'annee de la date fourni par user*/
  and U.id in(select B.id/* ne doit pas etre dans la table stageutilisateur*//*id de tout ceux qui ont trouvés un stage avant date choisie par user*/
              from stageutilisateur B, stage S
              where B.numstage=S.numstage
              and datetrouverstage<=dateUser);  
  return nbsansstageannee;/*retourne la valeur obtenu*/
END nbnonstageannee;

Create or replace Function nbstagiaireparentreprise(n  integer, nument  integer)return integer is
nbstagiaire integer;
Begin
  select count(*) into  nbstagiaire
  from entreprise E, stage S, stageent X
  where E.ident=X.ident
  and X.numstage=S.numstage
  and E.ident=nument
  and extract(year from datetrouverstage)>=extract(year from Current_DATE)-n/*date trouver stage doit etre superieur a l'anne actuel - le nombre choisie par user*/
  and S.numstage IN (select numstage/*on doit eviter que sa compte aussi les utilisateur de type tuteur ou professeur*/
                      from stageutilisateur A, utilisateurtype B, typeutilisateur T
                      where A.id=B.id
                      and B.numtypeutilisateur=T.numtypeutilisateur
                      and T.nomtypeutilisateur='ETUDIANT');
  return nbstagiaire;/*retourne la valeur obtenu*/
END nbstagiaireparentreprise;

create or replace function nbstagiairemoyen(n integer)return integer is
nbmoyen integer;
Begin
  select count(*)/n into nbmoyen
  from stage S, stageutilisateur A
  where S.numstage=A.numstage
  and extract(year from datetrouverstage)>=extract(year from current_date)-n/* annee du stage doit etre superieur ou egal a l'annee actuel moins le nombre d'anne choisi par user*/
  and A.id IN (select B.id
                from utilisateurtype B, typeutilisateur T
                where B.numtypeutilisateur=T.numtypeutilisateur
                and T.nomtypeutilisateur='ETUDIANT');
  return nbmoyen;/*retourne la valeur obtenu*/
END nbstagiairemoyen;

create or replace function nbstagezonechoisi(departement varchar2,ville varchar2) return integer is
  nbstage integer;
  Begin
    select count(*) into nbstage
    from stage S, stageent A, entreprise E
    where E.ident=A.ident
    and A.numstage=S.numstage
    and E.adresseent.departement= departement
    and E.adresseent.ville = ville
    and S.numstage IN (select numstage/*on doit eviter que sa compte aussi les utilisateur de type tuteur ou professeur*/
                      from stageutilisateur A, utilisateurtype B, typeutilisateur T
                      where A.id=B.id
                      and B.numtypeutilisateur=T.numtypeutilisateur
                      and T.nomtypeutilisateur='ETUDIANT');
  return nbstage;/*retourne la valeur obtenu*/
END nbstagezonechoisi;

create or replace function nbstagetoutezone (departement Out Varchar, ville Out Varchar) return integer is
  nbstage integer;
  BEGIN
    select E.adresseent.departement, E.adresseent.ville, count(*) into departement,ville, nbstage
     from stage S, stageent A, entreprise E
    where E.ident=A.ident
    and A.numstage=S.numstage
    and S.numstage IN (select numstage/*on doit eviter que sa compte aussi les utilisateur de type tuteur ou professeur*/
                      from stageutilisateur A, utilisateurtype B, typeutilisateur T
                      where A.id=B.id
                      and B.numtypeutilisateur=T.numtypeutilisateur
                      and T.nomtypeutilisateur='ETUDIANT')
    group by E.adresseent.departement, E.adresseent.ville;
  return nbstage;
end nbstagetoutezone;

create or replace function entreprisecontact (n in integer, telephone out varchar2, mRH out varchar2, tRH out varchar2)return varchar2 is
  nomentreprise varchar2(20);
  BEGIN
    select distinct nom , tel, mailRH, telRH into nomentreprise, telephone, mRH, tRH /* Ce que l'on pensxe etre les contacts*/
    from stage S, stageent A, entreprise E
    where E.ident=A.ident
    and A.numstage=S.numstage
    and extract(year from datetrouverstage)>=extract(year from current_date)-n
    and S.numstage IN (select numstage/*on doit eviter que sa compte aussi les utilisateur de type tuteur ou professeur*/
                      from stageutilisateur A, utilisateurtype B, typeutilisateur T
                      where A.id=B.id
                      and B.numtypeutilisateur=T.numtypeutilisateur
                      and T.nomtypeutilisateur='ETUDIANT');
  return nomentreprise;
END entreprisecontact;

create or replace function nbentreprise return integer is
nbent integer;
Begin
  select count(*)into nbent from entreprise;/*retourn le nombre d'entreprise*/
return nbent;
end nbentreprise;

