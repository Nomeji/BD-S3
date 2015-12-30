Create or replace TRIGGER trigger_ontstagescetteannee
AFTER INSERT ON stageutilisateur 
  BEGIN
      Update statistique 
        set nbavecstage = nbstage(); /*met à jour nbavecstage car il y a une personne en plus qui a un stage*/
      Update statistique 
        set nbsansstage = nbnonstage();  /*met à jour nbsansstage car il y a une personne en moins qui n'a pas de stage*/
END;

Create or replace TRIGGER trigger_ontpasstagescetteannee
BEFORE INSERT ON utilisateurtype FOR EACH ROW /* pas sur utilisateur car il n'est pas forcement un etudiant et on a mis before car after ne fonctionnait pas*/
  BEGIN    
  if (:NEW.numtypeutilisateur=1)then
      Update statistique 
        set nbsansstage=nbnonstage()+1;/* comme le trigger se lance avant l'insert on rajoute 1 a nbnonstage()*/
    end if;
END;

