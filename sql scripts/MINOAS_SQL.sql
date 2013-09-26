##############################################
# Σχολικό Έτος
##############################################
INSERT INTO minoas..MINOAS_SCHOOL_YEAR(IS_CURRENT_YEAR, END_DATE, START_DATE, TITLE, DESCRIPTION) 
    VALUES (1,'06/30/2009', '09/01/2008', '2008-2009', 'Σχολικό Έτος 2008-2009')
##############################################
# Ειδικότητες
##############################################
INSERT INTO minoas..MINOAS_SPECIALIZATION(SPECIALIZATION_ID, TITLE) 
    SELECT * FROM mkdb..EIDIK

INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) 
    SELECT * FROM mkdb..EIDIK

INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΚΑΛΧ', 'Κίνηση Χορός - Κλασικός Χορός -Σύγχρονος Χορός')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΚΑΛΚΘ','Κινηματογράφος - Θέατρο')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕ1714','Μουσικής Τεχνολογίας')

INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ01','ΠΕ Διοικητικός')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ02','ΠΕ Μηχανικών')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ03','ΠΕ Πληροφορικής')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ04','ΠΕ Γεωτεχνικών')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ05','ΠΕ Περιβάλλοντος')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ06','ΠΕ Μεταφραστών - Διερμηνέων')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ07','ΠΕ Βιβλιοθηκονόμων')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΠΕΔΙ08','ΠΕ Ιατρών')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΤΕΔΙ01','ΤΕ Διοικητικός - Λογιστικός')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΤΕΔΙ02','ΤΕ Πληροφορικής')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΤΕΔΙ03','ΤΕ Μηχανικών')
INSERT INTO minoas..SPECIALIZATION(SPECIALIZATION_ID, TITLE) VALUES('ΤΕΔΙ04','ΤΕ Γραφικών Τεχνών και Καλλ. Σπουδών')






##############################################
# ΠΥΣΔΕ
##############################################
SET IDENTITY_INSERT minoas..MINOAS_PYSDE ON 
INSERT INTO minoas..MINOAS_PYSDE(ID,TITLE, IS_LOCAL_PYSDE) VALUES(1,'ΠΥΣΔΕ ΗΡΑΚΛΕΙΟΥ',1)
INSERT INTO minoas..MINOAS_PYSDE(ID,TITLE, IS_LOCAL_PYSDE) VALUES(2,'ΠΥΣΔΕ ΡΕΘΥΜΝΟΥ',0)
INSERT INTO minoas..MINOAS_PYSDE(ID,TITLE, IS_LOCAL_PYSDE) VALUES(3,'ΠΥΣΔΕ ΛΑΣΙΘΙΟΥ',0)
INSERT INTO minoas..MINOAS_PYSDE(ID,TITLE, IS_LOCAL_PYSDE) VALUES(4,'ΠΥΣΔΕ ΧΑΝΙΩΝ',0)
SET IDENTITY_INSERT minoas..MINOAS_PYSDE OFF

##############################################
# ΓΡΑΦΕΙΑ
##############################################
SET IDENTITY_INSERT minoas..ORGANIZATIONAL_OFFICE ON 
INSERT INTO minoas..ORGANIZATIONAL_OFFICE(ID, TITLE, VERSION) VALUES(1,'1o Γραφείο', 0)
INSERT INTO minoas..ORGANIZATIONAL_OFFICE(ID, TITLE, VERSION) VALUES(2,'2o Γραφείο', 0)
INSERT INTO minoas..ORGANIZATIONAL_OFFICE(ID, TITLE, VERSION) VALUES(3,'Γραφείο Τεχνικής Εκπαίδευσης', 0)
INSERT INTO minoas..ORGANIZATIONAL_OFFICE(ID, TITLE, VERSION) VALUES(4,'Δν/νση Δθ/μιας Εκπ/σης Ηρακλείου', 0)
SET IDENTITY_INSERT minoas..ORGANIZATIONAL_OFFICE OFF

##############################################
# Σχολικές Τάξεις (Τύποι)
##############################################
SET IDENTITY_INSERT minoas..SCHOOL_CLASS ON 
INSERT INTO minoas..SCHOOL_CLASS(ID, TITLE, SCHOOL_TYPE, SORT_ORDER, VERSION) VALUES(1,'Α ΓΥΜΝΑΣΙΟΥ', 'GYM', 1,0)
INSERT INTO minoas..SCHOOL_CLASS(ID, TITLE, SCHOOL_TYPE, SORT_ORDER, VERSION) VALUES(2,'Β ΓΥΜΝΑΣΙΟΥ', 'GYM', 2,0)
INSERT INTO minoas..SCHOOL_CLASS(ID, TITLE, SCHOOL_TYPE, SORT_ORDER, VERSION) VALUES(3,'Γ ΓΥΜΝΑΣΙΟΥ', 'GYM', 2,0)
SET IDENTITY_INSERT minoas..SCHOOL_CLASS OFF

##############################################
# ΤΥΠΟΙ ΠΡΟΥΠΗΡΕΣΙΩΝ
##############################################
SET IDENTITY_INSERT minoas..WORK_EXPERIENCE_TYPE ON 
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(1, 'Ιεροψαλτική', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(2, 'Εθν.Αντίσταση', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(3, 'Ωρομίσθιος', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(4, 'Αναπληρωτής', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(5, 'ΝΠΔΔ - ΟΤΑ', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(6, 'Δημόσιο', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(7, 'Πείρα', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(8, 'Ιδιωτική Εκπ/ση', 1, 0)
INSERT INTO minoas..WORK_EXPERIENCE_TYPE(ID, TITLE, IS_ACTIVE, VERSION) VALUES(9, 'Προυπ.Σοσιαλ.Χωρών-ΕΟΚ- ΚΥΠΡΟΣ', 1, 0)
SET IDENTITY_INSERT minoas..WORK_EXPERIENCE_TYPE OFF 

##############################################
# Ομάδες Σχολείον
##############################################
SET IDENTITY_INSERT minoas..SCHOOL_GROUP ON 
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(1,'1η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(2,'2η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(3,'3η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(4,'4η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(5,'5η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(6,'6η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(7,'7η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(8,'8η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(9,'9η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(10,'10η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(11,'11η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(12,'12η Ομάδα - Α ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(13,'1η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(14,'2η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(15,'3η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(16,'4η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(17,'5η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(18,'6η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(19,'7η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(20,'8η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(21,'9η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(22,'10η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(23,'11η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(24,'12η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(25,'13η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(26,'14η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(27,'15η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(28,'16η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(29,'17η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(30,'18η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(31,'19η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(32,'20η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(33,'21η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(34,'22η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(35,'23η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(36,'24η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
INSERT INTO minoas..SCHOOL_GROUP(ID, TITLE, VERSION) VALUES(37,'25η Ομάδα - Β ΗΡΑΚΛΕΙΟΥ', 0)
SET IDENTITY_INSERT minoas..SCHOOL_GROUP OFF





##############################################
# ΣΧΟΛΕΙΑ
##############################################

INSERT INTO minoas..MINOAS_ADDRESS(ADDRESS, CITY, LATITUDE, LONGITUDE, NUMBER, POSTAL_CODE)
    VALUES('Γαζίου', 'Ηράκλειο', 20.33,10.44, '11', '71409')

SET IDENTITY_INSERT minoas..MINOAS_UNIT_CATEGORY ON 
INSERT INTO minoas..MINOAS_UNIT_CATEGORY(ID,TITLE) VALUES(1,'ΣΧΟΛΙΚΗ ΜΟΝΑΔΑ')
INSERT INTO minoas..MINOAS_UNIT_CATEGORY(ID,TITLE) VALUES(2,'Δ/ΝΣΗ Δ/ΘΜΙΑΣ ΕΚΠ/ΣΗΣ')
SET IDENTITY_INSERT minoas..MINOAS_UNIT_CATEGORY OFF

SET IDENTITY_INSERT minoas..UNIT ON
    /* ΓΡΑΦΕΙΑ */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('951', 'Δ/νσεις', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('952', 'Περιφέριες', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('953', 'Γραφεία Σχολικών Συμβούλων', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1106', 'ΚΕ.ΠΛΗΝΕΤ ΗΡΑΚΛΕΙΟΥ', 0)
    /* ΦΟΡΕΙΣ */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('954', 'ΑΕΙ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('955', 'ΤΕΙ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('956', 'ΓΑΚ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('958', 'ΟΕΕΚ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('959', 'ΥΠΕΠΘ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('960', 'ΔΗΜΟΣΙΕΣ ΒΙΒΛΙΟΘΗΚΕΣ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('961', 'ΚΕΓ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('962', 'ΙΕΡΕΣ ΜΗΤΡΟΠΟΛΕΙΣ, ΑΡΧΙΕΠΙΣΚΟΠΕΣ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('973', 'ΙΝΣΤΙΤΟΥΤΟ ΕΠΙΜΟΡΦΩΣΗΣ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1002', 'ΛΟΙΠΟΙ ΦΟΡΕΙΣ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1003', 'ΕΥΡΩΠΑΪΚΟ ΣΧΟΛΕΙΟ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1104', '1ο IEK', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1105', '2ο IEK', 0)
    /* FOREIS PYSDE */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('957', 'ΑΣΠΑΙΤΕ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('963', 'ΚΕΔΔΥ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('964', 'Π.Ε.Κ', 0)
    /* KESYP */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('965', 'Κ.Ε.Σ.Υ.Π', 0)
    /* ΕΚΦΕ */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('966', '1ο ΕΚΦΕ', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('967', '2ο ΕΚΦΕ', 0)
    /* ΚΠΕ */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('968', 'ΚΠΕ Γουβών', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('969', 'ΚΠΕ Ανωγείων', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('970', 'ΚΠΕ Νεάπολης', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('971', 'ΚΠΕ Ρούβα', 0)
    /* ΚΕΔΔΥ */

    /* TMHMATA ENTAΞΗΣ ΚΑΙ ΕΙΔΙΚΑ */
   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('671', 'ΓΥ 7ο ΗΡΑΚΛΕΙΟΥ (ΤΜΗΜΑ ΕΝΤΑΞΗΣ - ΠΡΟΒΛΗΜΑΤΑ ΟΡΑΣΗΣ)', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('671', NULL, NULL, 'Α')
    
   

   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1004', 'ΤΜΗΜΑ ΠΑΡΑΛΛΗΛΗΣ ΣΤΗΡΙΞΗΣ ΠΕΙΡΑΜΑΤΙΚΟΥ ΓΥΜΝΑΣΙΟΥ', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1004', NULL, NULL, 'Α')
   

   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1005', 'ΓΥ ΕΣΠΕΡΙΝΟ ΤΥΜΠΑΚΙΟΥ', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1005', NULL, NULL, 'Β')
   
  INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1010', '2ο ΣΕΚ ΗΡΑΚΛΕΙΟΥ', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1010', NULL, NULL, 'Α')

   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('2010', 'ΓΥ 1ο ΗΡΑΚΛΕΙΟΥ (ΤΜΗΝΑ ΕΝΤΑΞΗΣ)', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('2010', NULL, NULL, 'Α')

   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('9999', 'ΔΙΑΘΕΣΗ ΠΥΣΔΕ ΗΡΑΚΛΕΙΟΥ', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('9999', NULL, NULL, 'Α')

   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('6999', ' ΠΥΣΔΕ ΗΡΑΚΛΕΙΟΥ (Β)', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('6999', NULL, NULL, 'Β')
 
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, PYSDE_ID, version)
    VALUES ('600', 'ΣΧΟΛΕΙΟ ΔΕΥΤΕΡΗΣ ΕΥΚΑΙΡΙΑΣ (ΣΔΕ)', 1, 0)

    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, PYSDE_ID, version)
    VALUES ('1001', 'ΕΥΡΩΠΑΪΚΟ ΣΧΟΛΕΙΟ', 1, 0)
    
   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1015', 'ΓΥ 13ο ΗΡΑΚΛΕΙΟΥ (ΤΜΗΜΑ ΕΝΤΑΞΗΣ)', 0)
   INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1015', NULL, NULL, 'Α')

   INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1111', 'ΙΕΚ ΗΡΑΚΛΕΙΟΥ', 0)
 INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1111', NULL, NULL, 'Α')

INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1112', 'ΓΥ 4ο ΗΡΑΚΛΕΙΟΥ (ΤΜΗΜΑ ΠΑΡΑΛΛΗΛΗΣ ΣΤΗΡΙΞΗΣ)', 0)
 INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1112', NULL, NULL, 'Α')



INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1113', 'ΠΕΚ Ηρακλείου', 0)
 INSERT INTO minoas..SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION) VALUES('1113', NULL, NULL, 'Α')

    /* SPORTS_CHIEF */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('972', 'Γρ. Φυσικής Αγωγής', 0)

    /* VARIOUS */
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('974', 'Τοπική Αυτοδιοίκηση', 0)


    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1006', 'ΕΛΜΕ Ηρακλείου', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1007', 'ΑΠΥΣΔΕ Κρήτης', 0)
    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1009', 'Συμβουλευτικός Σταθμός Νέων', 0)
    

    INSERT INTO minoas..UNIT(UNIT_ID, TITLE, version) VALUES('1020', 'Μονάδα Πρωτοβάθμιας', 0)
   
   

SET IDENTITY_INSERT minoas..UNIT OFF

INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (951, 'TO_OFFICE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (952, 'TO_OFFICE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (953, 'TO_OFFICE', 0)
        
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (954, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (955, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (956, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (958, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (959, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (960, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (961, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (962, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (973, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (1002, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (1003, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (1009, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (1104, 'FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (1105, 'FOREIS', 0)

INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (957, 'PYSDE_FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (963, 'PYSDE_FOREIS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (964, 'PYSDE_FOREIS', 0)
     
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (965, 'KESSYP', 0)

INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (503, 'FULL_TO_SCHOOL', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('078', 'FULL_TO_SCHOOL', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('600', 'FULL_TO_SCHOOL', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('421', 'FULL_TO_SCHOOL', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('1004', 'FULL_TO_SCHOOL', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('1015', 'FULL_TO_SCHOOL', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('990', 'FULL_TO_SCHOOL', 0)

INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (705       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7012      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7016      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (709       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7017      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7018      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7019      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7020      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7013      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (706       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7021      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (707       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7022      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (708       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7023      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7010      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7024      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7025      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7026      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7027      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7028      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7029      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7030      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (701       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (6999      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7032      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7033      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7034      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7035      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7036      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7037      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7038      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7039      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7040      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7041      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7042      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7043      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7044      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (703       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7046      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7047      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7048      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7049      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7050      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7011      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7051      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7052      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7053      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (702       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7055      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7056      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7057      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7058      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7059      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7060      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7061      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7014      , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (704       , 'OTHER_PYSDE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (7062      , 'OTHER_PYSDE', 0)



INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('974', 'VARIOUS', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES ('1020', 'VARIOUS', 0)

INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (966, 'EKFE', 0)
INSERT INTO minoas..SECONDMENT_UNIT(UNIT_ID, SECONDMENT_TYPE, VERSION) VALUES (967, 'EKFE', 0)


INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '410',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1010',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1020',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '966',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '967',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '990',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1005',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_OFFICE', '972',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1015',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1111',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1112',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_OFFICE', '1106',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1004',0)
INSERT INTO minoas..DISPOSAL_UNIT(DISPOSAL_TARGET_TYPE, UNIT_ID, VERSION) VALUES('TO_SCHOOL', '1113',0)







INSERT INTO minoas..MINOAS_UNIT(UNIT_ID, TITLE, ADDRESS_ID, PYSDE_ID )
    SELECT  s.KVDSX, s.SXOLEIO, (SELECT 1), (SELECT 1) FROM mkdb..SXOLEIA s

INSERT INTO minoas..MINOAS_SCHOOL(UNIT_ID, MINISTRY_CODE, POINTS, REGION)
    SELECT u.UNIT_ID, s.KOD_SXOLEIOY, (SELECT 10), s.PERIOXH FROM minoas..MINOAS_UNIT u INNER JOIN mkdb..SXOLEIA s ON u.UNIT_ID COLLATE DATABASE_DEFAULT=s.KVDSX COLLATE DATABASE_DEFAULT

INSERT INTO minoas..MINOAS_UNIT_GATEGORIES(CATEGORY_ID, UNIT_ID) 
    SELECT (SELECT c.ID FROM minoas..MINOAS_UNIT_CATEGORY c WHERE TITLE='ΣΧΟΛΙΚΗ ΜΟΝΑΔΑ'), u.UNIT_ID FROM minoas..MINOAS_UNIT u WHERE (TITLE LIKE 'ΓΥ%' OR TITLE LIKE 'ΓΛ%' OR TITLE LIKE 'ΕΠΑ%')


SELECT * FROM UNIT

##############################################
# ΤΥΠΟΙ ΑΔΕΙΩΝ
# (ΕΠΙΠΛΕΟΝ ΚΩΔΙΚΟΙ - ΟΙ ΑΡΧΙΚΟΙ ΕΧΟΥΝ ΜΕΤΑΦΕΡΘΕΙ)
##############################################

INSERT INTO EMPLOYEE_LEAVE_TYPE(VERSION, DESCRIPTION, GENERATES_CDRS, LEGACY_CODE, EMPLOYEE_TYPE, IS_ACTIVE)
    VALUES(0,'Ανευ Αποδοχών', 0, '44', 'REGULAR', 1)


##############################################
# ΤΥΠΟΙ ΤΕΡΜΑΤΙΣΜΟΥ ΥΠΗΡΕΣΙΩΝ
##############################################

SELECT * FROM EMPLOYEE_TERMINATION_REASON_TYPE

INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Μετάθεση', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Παραίτηση λόγω συνταξιοδότησης', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Απόλυση λόγω ορίου ηλικίας και 35ετίας', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Παραίτηση', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Μη ανάληψη υπηρεσίας', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Λήξη Σύμβασης', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Λήξη Θητείας', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Μετακίνηση', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Αυτοδίκαιη παραίτηση λόγω αποδοχής άλλης θέσης στο δημόσιο', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Προσυνταξιοδοτική διαθεσιμότητα', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Διαθεσιμότητα', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Εφεδρεία', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Αυτοδίκαιη έκπτωση λόγω ποινικής καταδίκης', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Απόλυση λόγω ακαταλληλότητας κατά το άρθρο 95 του υπαλληλικού κώδικα', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Θάνατος', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Απόλυση λόγω επιβολής της ποινής οριστικής παύσης', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Απόλυση για σωματική ή πνευματική ανικανότητα', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Έκπτωση λόγω απώλειας ιθαγένειας', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Λανθασμένη εισαγωγή', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Λοιπές Περιπτώσεις', NULL)
INSERT INTO EMPLOYEE_TERMINATION_REASON_TYPE(INSERTED_ON, VERSION, DESCRIPTION, INSERTED_BY_ID) VALUES(CURRENT_TIMESTAMP, 0, 'Μετάταξη', NULL)
##############################################
# ΤΥΠΟΙ ΑΔΕΙΩΝ
##############################################

INSERT EMPLOYEE_LEAVE_TYPE(DESCRIPTION, GENERATES_CDRS, BASIC_TYPE, VERSION, INSERTED_ON, LEGACY_CODE, EMPLOYEE_TYPE)
SELECT  p.PERIGRAFH, 1, 'GENERIC_LEAVE', 0, CURRENT_TIMESTAMP,p.KAD,'REGULAR' FROM mkdb..KVD_PROYP p
    WHERE (p.KAD NOT LIKE '1%' AND p.KAD NOT LIKE '2%')

##############################################
# ΕΚΤΥΠΩΣΕΙΣ
##############################################
SELECT * FROM PRINTOUT_RECIPIENTS

INSERT PRINTOUT_RECIPIENTS(INSERTED_ON, VERSION, RECIPIENT_TITLE, INSERTED_BY_ID) VALUES(NULL, 0, 'Ενδιαφερόμενος/νη', NULL)
INSERT PRINTOUT_RECIPIENTS(INSERTED_ON, VERSION, RECIPIENT_TITLE, INSERTED_BY_ID) VALUES(NULL, 0, 'Σχολείο Οργανικής', NULL)
INSERT PRINTOUT_RECIPIENTS(INSERTED_ON, VERSION, RECIPIENT_TITLE, INSERTED_BY_ID) VALUES(NULL, 0, 'Σχολείο που Υπηρετεί', NULL)
INSERT PRINTOUT_RECIPIENTS(INSERTED_ON, VERSION, RECIPIENT_TITLE, INSERTED_BY_ID) VALUES(NULL, 0, 'Υπηρεσία που Υπηρετεί', NULL)

SELECT * FROM PRINTOUT_SIGNATURES
INSERT PRINTOUT_SIGNATURES(INSERTED_ON, VERSION, SIGNATURE_TITLE, SIGNATURE_NAME, INSERTED_BY_ID) VALUES(NULL, 0,'O Διευθυντής της Δ/νσης Δ/θμιας Εκπ/σης', 'ΜΑΝΟΥΣΑΚΗΣ ΓΕΩΡΓΙΟΣ',NULL)
INSERT PRINTOUT_SIGNATURES(INSERTED_ON, VERSION, SIGNATURE_TITLE, SIGNATURE_NAME, INSERTED_BY_ID) VALUES(NULL, 0,'Ο Αναπληρωτής του Διευθυντού της Δ/νσης Δ/θμιας Εκπ/σης ', 'ΚΥΠΡΑΙΟΣ ΙΩΑΝΝΗΣ',NULL)
##############################################
# ΠΡΟΣΩΠΙΚΟ
##############################################

# ΜΟΝΙΜΟ

INSERT INTO minoas..EMPLOYEE(LEGACY_CODE, FIRST_NAME,LAST_NAME,FATHER_NAME, MOTHER_NAME, BIRTH_DAY, VAT_NUMBER, IS_ACTIVE, EMPLOYEE_TYPE, PYSDE_ID, LAST_SPECIALIZATION_ID) 
    SELECT b.KVD, b.ONOMA, b.EPIUETO, b.PATERAS, b.ON_MHTROS, b.DGEN, b.AFM, 0, 'REGULAR', (SELECT 1),b.EIDKLAD
        FROM mkdb..basiko b WHERE b.EIDKLAD COLLATE DATABASE_DEFAULT IN (SELECT e.SPECIALIZATION_ID COLLATE DATABASE_DEFAULT FROM minoas..SPECIALIZATION e)


INSERT INTO minoas..REGULAR_EMPLOYEE_INFO(ID, APPOINTMENT_GOF, APPOINTMENT_GOF_DATE, REGISTRY_ID) 
    SELECT e.ID, b.FEK, b.DFEK, LTRIM(RTRIM(b.AM)) FROM EMPLOYEE e INNER JOIN mkdb..basiko b on e.LEGACY_CODE COLLATE DATABASE_DEFAULT
        =b.KVD COLLATE DATABASE_DEFAULT WHERE b.AM IS NOT NULL

# ΣΥΝΑΤΑΞΕΙΣ

UPDATE minoas..EMPLOYEE  SET IS_ACTIVE=0 
    WHERE ID IN (
SELECT e2.ID FROM minoas..EMPLOYEE e2 
    INNER JOIN mkdb..basiko b ON e2.LEGACY_CODE COLLATE DATABASE_DEFAULT=b.KVD COLLATE DATABASE_DEFAULT
WHERE b.ANHKEI_ID='9'AND e2.IS_ACTIVE=1)

UPDATE minoas..SERVICE_ALLOCATION SET IS_ACTIVE=0
    WHERE ID IN (
    SELECT s.ID FROM SERVICE_ALLOCATION s 
        INNER JOIN EMPLOYEE e ON e.ID=s.EMPLOYEE_ID
    WHERE e.IS_ACTIVE=0
)

UPDATE minoas..SECONDMENT SET IS_ACTIVE=0 
    WHERE ID IN (
    SELECT s.ID FROM SECONDMENT s 
        INNER JOIN EMPLOYEE e ON e.ID=s.EMPLOYEE_ID
        WHERE e.IS_ACTIVE=0
)

UPDATE minoas..EMPLOYEE_LEAVE SET IS_ACTIVE=0 
    WHERE ID IN (
    SELECT l.ID FROM EMPLOYEE_LEAVE l 
        INNER JOIN EMPLOYEE e ON e.ID=l.EMPLOYEE_ID
        WHERE e.IS_ACTIVE=0
)

UPDATE minoas..EMPLOYMENT SET IS_ACTIVE=0
    WHERE ID IN (
    SELECT em.ID FROM EMPLOYMENT em 
        INNER JOIN EMPLOYEE e ON e.ID=em.EMPLOYEE_ID
        WHERE e.IS_ACTIVE=0
    )



SELECT COUNT(*) FROM minoas..EMPLOYEE WHERE IS_ACTIVE=0
/*
INSERT minoas..MINOAS_EMPLOYEE_REGULAR(EMPLOYEE_ID,APPOINTMENT_GOF, APPOINTMENT_GOF_DATE, REGISTRY_ID) 
    SELECT e.EMPLOYEE_ID, b.FEK, b.DFEK, b.AM 
        FROM  minoas..MINOAS_EMPLOYEE e INNER JOIN mkdb..basiko b on e.LEGACY_CODE COLLATE DATABASE_DEFAULT
=b.KVD COLLATE DATABASE_DEFAULT 
*/
INSERT minoas..EMPLOYMENT(EMPLOYEE_ID, SCHOOL_ID, SCHOOL_YEAR_ID, SPECIALIZATION_ID, EMPLOYMENT_TYPE, MANDATORY_WORK_HRS, FINAL_WORKING_HOURS, IS_ACTIVE) 
    SELECT e.ID, b.OKVD, (SELECT s.id FROM SCHOOL_YEAR s WHERE s.IS_CURRENT_YEAR=1), b.EIDKLAD, 'REGULAR', (SELECT 21), (SELECT 21), 1 FROM minoas..EMPLOYEE e INNER JOIN mkdb..basiko b on e.LEGACY_CODE COLLATE DATABASE_DEFAULT
=b.KVD COLLATE DATABASE_DEFAULT

UPDATE minoas..EMPLOYMENT SET IS_ACTIVE=1 WHERE EMPLOYMENT.ID IN (
SELECT em.ID FROM minoas..EMPLOYMENT em 
    INNER JOIN minoas..EMPLOYEE e ON em.EMPLOYEE_ID=e.ID
WHERE e.IS_ACTIVE=1
)


#INSERT minoas..MINOAS_EMPLOYMENT_REGULAR(EMPLOYMENT_ID, MANDATORY_WORK_HRS) SELECT e.EMPLOYMENT_ID, (SELECT 10)FROM minoas..MINOAS_EMPLOYMENT e




# ΑΝΑΠΛΗΡΩΤΕΣ

INSERT minoas..MINOAS_EMPLOYEE(LEGACY_CODE, FIRST_NAME,LAST_NAME,FATHER_NAME, IS_ACTIVE, EMPLOYEE_TYPE, PYSDE_ID, LAST_SPECIALIZATION_ID)
    SELECT ('ANA_'+KVD), ONOMA, EPIUETO, PATERAS,0, 'DEPUTY', 1, a.EIDIK FROM mkdb..PR_ANAPL a WHERE a.EIDIK IN (SELECT e.EID FROM mkdb..EIDIK e)


INSERT minoas..MINOAS_EMPLOYMENT(SCHOOL_ID,EMPLOYEE_ID, SCHOOL_YEAR_ID, SPECIALIZATION_ID, ESTABLISHED_DATE, TERMINATED_DATE, EMPLOYMENT_TYPE, MANDATORY_WORK_HRS,IS_ACTIVE)
SELECT b.KSXOL, e.ID,(SELECT s.id FROM minoas..MINOAS_SCHOOL_YEAR s WHERE s.IS_CURRENT_YEAR=1), a.EIDIK, b.D_ANAL, b.D_APOL, 'DEPUTY', (SELECT 21), 1
    FROM minoas..MINOAS_EMPLOYEE AS e 
    INNER JOIN mkdb..PR_ANAPL_B AS b ON (e.LEGACY_CODE COLLATE DATABASE_DEFAULT=('ANA_'+b.KVD) COLLATE DATABASE_DEFAULT)
    INNER JOIN mkdb..PR_ANAPL AS a ON (e.LEGACY_CODE COLLATE DATABASE_DEFAULT=('ANA_'+a.KVD) COLLATE DATABASE_DEFAULT)
    WHERE b.SXOL_ETOS='2008-2009'


#INSERT minoas..MINOAS_EMPLOYMENT_DEPUTY(EMPLOYMENT_ID, MANDATORY_WORK_HRS)
#SELECT em.EMPLOYMENT_ID, b.ORES 
#    FROM minoas..MINOAS_EMPLOYEE AS e 
#    INNER JOIN mkdb..PR_ANAPL_B AS b ON (e.LEGACY_CODE COLLATE DATABASE_DEFAULT=('ANA_'+b.KVD) COLLATE DATABASE_DEFAULT)
#    INNER JOIN minoas..MINOAS_EMPLOYMENT AS em ON e.EMPLOYEE_ID=em.EMPLOYEE_ID
#WHERE b.SXOL_ETOS='2007-2008'



UPDATE minoas..EMPLOYEE 
    SET CURRENT_EMPLOYMENT_ID = er.ID
       FROM minoas..EMPLOYEE AS e INNER JOIN minoas..EMPLOYMENT AS er ON er.EMPLOYEE_ID=e.ID
    
    
# ΑΠΟΣΠΑΣΕΙΣ


INSERT INTO minoas..SECONDMENT_UNIT(SECONDMENT_TYPE, UNIT_ID, VERSION)
    SELECT (SELECT 'FULL_TO_SCHOOL'), u.UNIT_ID, 0 FROM minoas..UNIT AS u
        INNER JOIN minoas..SCHOOL AS s ON u.UNIT_ID=s.UNIT_ID
        WHERE (u.TITLE LIKE 'ΓΥ%' OR u.TITLE LIKE 'ΓΛ%')
        ORDER BY u.TITLE

INSERT INTO minoas..SECONDMENT_UNIT(SECONDMENT_TYPE, UNIT_ID, VERSION)
    SELECT (SELECT 'FULL_TO_SCHOOL'), u.UNIT_ID, 0 FROM minoas..UNIT AS u
        INNER JOIN minoas..SCHOOL AS s ON u.UNIT_ID=s.UNIT_ID
        WHERE (u.TITLE LIKE 'ΕΠΑ%')
        ORDER BY u.TITLE


INSERT INTO minoas..SECONDMENT_UNIT(SECONDMENT_TYPE, UNIT_ID, VERSION)
    SELECT (SELECT 'FULL_TO_SCHOOL'), u.UNIT_ID, 0 FROM minoas..UNIT AS u
        INNER JOIN minoas..SCHOOL AS s ON u.UNIT_ID=s.UNIT_ID
        WHERE (u.TITLE LIKE 'ΕΕΕΕΚ%')
        ORDER BY u.TITLE

INSERT INTO minoas..SECONDMENT_UNIT(SECONDMENT_TYPE, UNIT_ID, VERSION)
    SELECT (SELECT 'FULL_TO_OFFICE'), u.UNIT_ID, 0 FROM minoas..UNIT AS u
        INNER JOIN minoas..SCHOOL AS s ON u.UNIT_ID=s.UNIT_ID
        WHERE (u.TITLE LIKE '%Γρ%' OR u.TITLE LIKE '%ΘΜΙΑΣ%')
        ORDER BY u.TITLE


    

UPDATE minoas..MINOAS_ADDRESS SET version=0
UPDATE minoas..MINOAS_UNIT SET version=0
UPDATE minoas..MINOAS_SPECIALIZATION SET version=0
UPDATE minoas..MINOAS_EMPLOYEE SET version=0
UPDATE minoas..MINOAS_EMPLOYMENT SET version=0
UPDATE minoas..MINOAS_REGULAR_EMPLOYEE_INFO SET version=0
